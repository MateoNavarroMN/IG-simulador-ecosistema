/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simuladorecosistema.controlador;

/**
 *
 * @author felipemolina
 */
public class ControladorSimulacion {
    
}
package simuladorecosistema.controlador;

import java.util.ArrayList;
import simuladorecosistema.modelo.Clima;
import simuladorecosistema.modelo.Ecosistema;
import simuladorecosistema.modelo.ReporteFinal;
import simuladorecosistema.vista.IVista;

// Maneja el ciclo de la simulacion: crear, avanzar turnos, intervenir y detectar el fin.
// Solo habla con la vista a traves de IVista; no usa Swing ni consola.
public class ControladorSimulacion {

    // Cada cuantos turnos se ofrece intervenir
    public static final int TURNOS_ENTRE_INTERVENCIONES = 3;

    private final IVista vista;
    private final ValidadorConfiguracion validador;
    private Ecosistema ecosistema;
    private boolean terminada;

    public ControladorSimulacion(IVista vista) {
        this.vista = vista;
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
        } else if (ecosistema.getTurnoActual() % TURNOS_ENTRE_INTERVENCIONES == 0) {
            vista.ofrecerIntervencion(ecosistema.getTurnoActual());
        }
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