package simuladorecosistema.controlador;

import java.util.ArrayList;
import simuladorecosistema.dao.ResultadoCarga;
import simuladorecosistema.dao.SimulacionDAO;
import simuladorecosistema.modelo.Clima;
import simuladorecosistema.modelo.Ecosistema;
import simuladorecosistema.modelo.ReporteFinal;
import simuladorecosistema.modelo.Snapshot;
import simuladorecosistema.vista.IVista;

// Maneja el ciclo de la simulacion: crear, avanzar turnos, intervenir, detectar el fin,
// guardar, cargar y pedir el reporte final.
//
// CUANDO SE GUARDA: de forma automatica al crear una simulacion nueva, despues de cada turno
// (incluido el ultimo) y despues de cada intervencion (cambiar el clima o agregar una entidad).
// Asi lo guardado siempre es el ultimo estado. Tambien se puede guardar a mano con guardar().
// Al abrir la aplicacion, iniciar() carga lo guardado.
// Solo habla con la vista a traves de IVista; no usa Swing ni consola.
public class ControladorSimulacion {

    // Cada cuantos turnos se ofrece intervenir
    public static final int TURNOS_ENTRE_INTERVENCIONES = 3;

    private final IVista vista;
    private final ValidadorConfiguracion validador;
    private final SimulacionDAO dao;
    private Ecosistema ecosistema;
    private boolean terminada;

    public ControladorSimulacion(IVista vista) {
        this(vista, new SimulacionDAO());
    }

    public ControladorSimulacion(IVista vista, SimulacionDAO dao) {
        this.vista = vista;
        this.dao = dao;
        this.validador = new ValidadorConfiguracion();
        this.ecosistema = null;
        this.terminada = false;
    }

    // Punto unico donde se crea el modelo (las pruebas con stub lo reemplazan)
    protected Ecosistema crearEcosistema(Clima clima, int turnos) {
        return new Ecosistema(clima, turnos);
    }

    // Valida el formulario y arranca una simulacion nueva. Devuelve true si arranco.
    public boolean iniciarSimulacion(String plantas, String conejos, String lobos,
            String turnos, Clima clima) {
        ResultadoValidacion resultado = validador.validar(plantas, conejos, lobos, turnos, clima);
        if (!resultado.esValido()) {
            vista.mostrarError(unir(resultado.getErrores()));
            return false;
        }

        ConfiguracionSimulacion config = resultado.getConfiguracion();
        Ecosistema nuevo = crearEcosistema(config.getClima(), config.getTurnos());
        for (int i = 0; i < config.getPlantas(); i++) {
            nuevo.agregarEntidad("planta");
        }
        for (int i = 0; i < config.getConejos(); i++) {
            nuevo.agregarEntidad("conejo");
        }
        for (int i = 0; i < config.getLobos(); i++) {
            nuevo.agregarEntidad("lobo");
        }

        ecosistema = nuevo;
        terminada = false;
        mostrarEstado();
        guardarAutomatico();
        return true;
    }

    // Procesa un turno, muestra eventos y estado, ofrece intervenir cada 3 turnos y detecta el fin
    public void avanzarTurno() {
        if (!hayEcosistema()) {
            vista.mostrarError("No hay una simulacion en marcha");
            return;
        }
        if (terminada) {
            vista.mostrarError("La simulacion ya termino");
            return;
        }

        ArrayList<String> eventos = ecosistema.procesarTurno();
        vista.mostrarEventos(eventos);
        mostrarEstado();

        if (ecosistema.ecosistemaColapsado() || ecosistema.getTurnoActual() >= ecosistema.getTurnosTotales()) {
            terminada = true;
            vista.mostrarMensaje(motivoDeFin());
            vista.mostrarReporte(ecosistema.obtenerReporteFinal());
        } else if (ecosistema.getTurnoActual() % TURNOS_ENTRE_INTERVENCIONES == 0) {
            vista.ofrecerIntervencion(ecosistema.getTurnoActual());
        }
        guardarAutomatico();
    }

    // Intervencion: cambiar el clima
    public boolean cambiarClima(Clima nuevo) {
        if (!puedeIntervenir()) {
            return false;
        }
        if (nuevo == null) {
            vista.mostrarError("Elegi un clima");
            return false;
        }
        ecosistema.cambiarClima(nuevo);
        mostrarEstado();
        guardarAutomatico();
        return true;
    }

    // Intervencion: agregar una planta, un conejo o un lobo (maximo 5 lobos en toda la simulacion)
    public boolean agregarEntidad(String tipo) {
        if (!puedeIntervenir()) {
            return false;
        }
        if ("lobo".equalsIgnoreCase(tipo) && !ecosistema.puedeAgregarMasLobos()) {
            vista.mostrarError("Ya se crearon los 5 lobos permitidos");
            return false;
        }
        if (!ecosistema.agregarEntidad(tipo)) {
            vista.mostrarError("No se pudo agregar: " + tipo);
            return false;
        }
        mostrarEstado();
        guardarAutomatico();
        return true;
    }

    // Se llama una vez al abrir la aplicacion: carga la simulacion guardada y la muestra.
    // Si no hay archivo o esta danado, avisa a la vista que no hay simulacion en marcha.
    // Una simulacion guardada que ya habia terminado muestra su reporte.
    public void iniciar() {
        ResultadoCarga resultado = dao.cargar();
        if (resultado.isArchivoInexistente()) {
            vista.mostrarSinSimulacion("No hay una simulacion guardada");
            return;
        }
        if (!resultado.esExitoso()) {
            vista.mostrarSinSimulacion("La simulacion guardada no se pudo cargar: " + resultado.getError());
            return;
        }
        Ecosistema cargado;
        try {
            cargado = Ecosistema.desdeSnapshot(resultado.getSnapshot());
        } catch (IllegalArgumentException e) {
            vista.mostrarSinSimulacion("La simulacion guardada no es valida");
            return;
        }

        ecosistema = cargado;
        terminada = ecosistema.ecosistemaColapsado()
                || ecosistema.getTurnoActual() >= ecosistema.getTurnosTotales();
        mostrarEstado();
        vista.mostrarMensaje("Se cargo la simulacion guardada");
        if (terminada) {
            vista.mostrarReporte(ecosistema.obtenerReporteFinal());
        }
    }

    // Guarda la simulacion actual con el DAO. Devuelve true si quedo guardada.
    public boolean guardar() {
        if (!hayEcosistema()) {
            vista.mostrarError("No hay una simulacion para guardar");
            return false;
        }
        if (!dao.guardar(ecosistema.exportarSnapshot())) {
            vista.mostrarError("No se pudo guardar la simulacion");
            return false;
        }
        vista.mostrarMensaje("Simulacion guardada");
        return true;
    }

    // Carga la simulacion guardada y la deja lista para seguir. Devuelve true si se cargo.
    public boolean cargar() {
        ResultadoCarga resultado = dao.cargar();
        if (!resultado.esExitoso()) {
            vista.mostrarError(resultado.getError());
            return false;
        }
        Snapshot snapshot = resultado.getSnapshot();
        Ecosistema cargado;
        try {
            cargado = Ecosistema.desdeSnapshot(snapshot);
        } catch (IllegalArgumentException e) {
            vista.mostrarError("La simulacion guardada no es valida");
            return false;
        }

        ecosistema = cargado;
        terminada = ecosistema.ecosistemaColapsado()
                || ecosistema.getTurnoActual() >= ecosistema.getTurnosTotales();
        mostrarEstado();
        vista.mostrarMensaje("Simulacion cargada");
        return true;
    }

    // Pide el reporte final al modelo y se lo entrega a la vista (solo si la simulacion termino)
    public boolean mostrarReporte() {
        if (!hayEcosistema()) {
            vista.mostrarError("No hay una simulacion en marcha");
            return false;
        }
        if (!terminada) {
            vista.mostrarError("La simulacion todavia no termino");
            return false;
        }
        vista.mostrarReporte(ecosistema.obtenerReporteFinal());
        return true;
    }

    public boolean hayEcosistema() {
        return ecosistema != null;
    }

    public boolean estaTerminada() {
        return terminada;
    }

    private boolean puedeIntervenir() {
        if (!hayEcosistema()) {
            vista.mostrarError("No hay una simulacion en marcha");
            return false;
        }
        if (terminada) {
            vista.mostrarError("La simulacion ya termino");
            return false;
        }
        return true;
    }

    // Guardado silencioso: solo avisa a la vista si falla
    private void guardarAutomatico() {
        if (!dao.guardar(ecosistema.exportarSnapshot())) {
            vista.mostrarError("No se pudo guardar la simulacion automaticamente");
        }
    }

    private void mostrarEstado() {
        vista.mostrarEstado(ecosistema.getTurnoActual(), ecosistema.getTurnosTotales(),
                ecosistema.contarPlantasVivas(), ecosistema.contarConejosVivos(),
                ecosistema.contarLobosVivos(), ecosistema.getClimaActual().getNombre());
    }

    private String motivoDeFin() {
        if (ecosistema.ecosistemaColapsado()) {
            ReporteFinal reporte = ecosistema.obtenerReporteFinal();
            return "La simulacion termino: el ecosistema colapso (se extinguio: "
                    + unirComas(reporte.getEspeciesExtintas()) + ")";
        }
        return "La simulacion termino: se completaron los " + ecosistema.getTurnosTotales() + " turnos";
    }

    private String unir(ArrayList<String> textos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < textos.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(textos.get(i));
        }
        return sb.toString();
    }

    private String unirComas(ArrayList<String> textos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < textos.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(textos.get(i));
        }
        return sb.toString();
    }
}