package simuladorecosistema.modelo;

import java.util.ArrayList;

public class Ecosistema {

    private ArrayList<Planta> plantas;
    private ArrayList<Conejo> conejos;
    private ArrayList<Lobo> lobos;
    private Clima climaActual;
    private int turnoActual;
    private int turnosTotales;

    // Contadores para validaciones y reporte final
    private int totalLobosCreados;
    private int nacimientosPlantas;
    private int nacimientosConejos;
    private int muertesPlantas;
    private int muertesConejos;
    private int muertesLobos;
    private int turnoMayorActividad;
    private int maxEventosTurno;

    private ArrayList<String> eventosTurnoActual;
    private ArrayList<Planta> nuevasPlantasTurno;
    private ArrayList<Conejo> nuevosConejosTurno;

    private static final int MAX_LOBOS_SIMULACION = 5;

    public Ecosistema(Clima climaInicial, int turnosTotales) {
        this.plantas = new ArrayList<>();
        this.conejos = new ArrayList<>();
        this.lobos = new ArrayList<>();
        this.climaActual = (climaInicial != null) ? climaInicial : Clima.SOLEADO;
        this.turnoActual = 0;
        this.turnosTotales = Math.max(10, Math.min(50, turnosTotales));
        this.totalLobosCreados = 0;
        this.nacimientosPlantas = 0;
        this.nacimientosConejos = 0;
        this.muertesPlantas = 0;
        this.muertesConejos = 0;
        this.muertesLobos = 0;
        this.turnoMayorActividad = 1;
        this.maxEventosTurno = 0;
        this.eventosTurnoActual = new ArrayList<>();
        this.nuevasPlantasTurno = new ArrayList<>();
        this.nuevosConejosTurno = new ArrayList<>();
    }

    // SOBRECARGA 1: Sin energia inicial
    public boolean agregarEntidad(String tipo) {
        double energiaAleatoria = 45.0 + (Math.random() * 30.0);
        return agregarEntidad(tipo, energiaAleatoria);
    }

    // SOBRECARGA 2: Con energia inicial
    public boolean agregarEntidad(String tipo, double energiaInicial) {
        if (tipo == null) {
            return false;
        }

        String tipoLimpio = tipo.trim().toLowerCase();

        switch (tipoLimpio) {
            case "planta":
                int numPlanta = plantas.size() + nuevasPlantasTurno.size() + 1;
                int tamanioAleatorio = (int) (Math.random() * 5) + 1;
                Planta nuevaPlanta = new Planta("Helecho-" + numPlanta, energiaInicial, tamanioAleatorio);
                plantas.add(nuevaPlanta);
                return true;

            case "conejo":
                int numConejo = conejos.size() + nuevosConejosTurno.size() + 1;
                Conejo nuevoConejo = new Conejo("Conejo-" + numConejo, energiaInicial);
                conejos.add(nuevoConejo);
                return true;

            case "lobo":
                if (totalLobosCreados >= MAX_LOBOS_SIMULACION) {
                    return false;
                }
                totalLobosCreados++;
                Lobo nuevoLobo = new Lobo("Lobo-" + totalLobosCreados, energiaInicial);
                lobos.add(nuevoLobo);
                return true;

            default:
                return false;
        }
    }

    // =========================================================================
    // MOTOR DE SIMULACION POR TURNO (Integrante 4)
    // =========================================================================
    public ArrayList<String> procesarTurno() {
        turnoActual++;
        eventosTurnoActual.clear();
        nuevasPlantasTurno.clear();
        nuevosConejosTurno.clear();

        int plantasVivasInicio = contarPlantasVivas();
        int conejosVivosInicio = contarConejosVivos();
        int lobosVivosInicio = contarLobosVivos();

        // 1. Actuan las plantas (ganan energia por clima y se reproducen)
        for (Planta p : plantas) {
            if (p.isViva()) {
                p.actuar(this);
            }
        }

        // 2. Actuan los conejos (buscan plantas para comer e intentan reproducirse)
        for (Conejo c : conejos) {
            if (c.isViva()) {
                c.actuar(this);
            }
        }

        // Polimorfismo con ArrayList<Reproducible>: recorrido unificado de entidades reproducibles
        ArrayList<Reproducible> reproducibles = new ArrayList<>();
        reproducibles.addAll(plantas);
        reproducibles.addAll(conejos);
        for (Reproducible r : reproducibles) {
            // Si alguna entidad aun conserva energia extra suficiente tras su accion, evalua reproduccion
            if (r.puedeReproducirse() && Math.random() < 0.15) {
                r.intentarReproduccion(this);
            }
        }

        // 3. Actuan los lobos (intentan cazar un conejo segun probabilidad por energia)
        for (Lobo l : lobos) {
            if (l.isViva()) {
                l.actuar(this);
            }
        }

        // 4 y 5. Envejecimiento, gasto de energia base y verificacion de muerte polimorfica (Mortal)
        ArrayList<Mortal> mortales = new ArrayList<>();
        mortales.addAll(plantas);
        mortales.addAll(conejos);
        mortales.addAll(lobos);

        for (Mortal m : mortales) {
            if (m.estaVivo() && m instanceof Entidad) {
                Entidad ent = (Entidad) m;
                ent.envejecer();
                // Si la energia llego a 0 al envejecer, reactivamos temporalmente el flag
                // para que el metodo default verificarMuerte() de Mortal procese la baja
                if (ent.getEnergia() <= 0) {
                    ent.setViva(true);
                    eventosTurnoActual.add("MUERTE: " + ent.getNombre() + " murio por quedarse sin energia");
                }
            }
        }

        // Uso del metodo default verificarMuerte() de la interface Mortal
        for (Mortal m : mortales) {
            if (m.estaVivo() && m.getEnergia() <= 0) {
                m.verificarMuerte();
            }
        }

        // Contabilizar bajas del turno antes de incorporar a los recien nacidos
        muertesPlantas += Math.max(0, plantasVivasInicio - contarPlantasVivas());
        muertesConejos += Math.max(0, conejosVivosInicio - contarConejosVivos());
        muertesLobos += Math.max(0, lobosVivosInicio - contarLobosVivos());

        // Incorporar nacimientos del turno a las colecciones principales
        plantas.addAll(nuevasPlantasTurno);
        conejos.addAll(nuevosConejosTurno);

        // Actualizar registro del turno de mayor actividad
        if (eventosTurnoActual.size() > maxEventosTurno) {
            maxEventosTurno = eventosTurnoActual.size();
            turnoMayorActividad = turnoActual;
        }

        // 6. Devolver los eventos del turno para que la vista los muestre
        return new ArrayList<>(eventosTurnoActual);
    }

    // Entrega los datos del reporte final para que la vista los muestre
    public ReporteFinal obtenerReporteFinal() {
        ArrayList<String> extintas = new ArrayList<>();
        if (ecosistemaColapsado()) {
            if (contarPlantasVivas() == 0) extintas.add("Plantas");
            if (contarConejosVivos() == 0) extintas.add("Conejos");
            if (contarLobosVivos() == 0) extintas.add("Lobos");
        }

        Planta plantaMasLongeva = null;
        for (Planta p : plantas) {
            if (plantaMasLongeva == null || p.getEdad() > plantaMasLongeva.getEdad()) {
                plantaMasLongeva = p;
            }
        }

        Conejo conejoMasLongevo = null;
        for (Conejo c : conejos) {
            if (conejoMasLongevo == null || c.getEdad() > conejoMasLongevo.getEdad()) {
                conejoMasLongevo = c;
            }
        }

        Lobo loboMasLongevo = null;
        Lobo mejorCazador = null;
        for (Lobo l : lobos) {
            if (loboMasLongevo == null || l.getEdad() > loboMasLongevo.getEdad()) {
                loboMasLongevo = l;
            }
            if (mejorCazador == null || l.getExitosCaza() > mejorCazador.getExitosCaza()) {
                mejorCazador = l;
            }
        }

        return new ReporteFinal(ecosistemaColapsado(), turnoActual, turnosTotales, extintas,
                turnoMayorActividad, maxEventosTurno,
                plantaMasLongeva, conejoMasLongevo, loboMasLongevo, mejorCazador,
                nacimientosPlantas, nacimientosConejos,
                muertesPlantas, muertesConejos, muertesLobos,
                contarPlantasVivas(), contarConejosVivos(), contarLobosVivos());
    }

    // Foto del estado actual (copias independientes) para que el DAO la guarde
    public Snapshot exportarSnapshot() {
        ArrayList<Planta> copiaPlantas = new ArrayList<>();
        for (Planta p : plantas) {
            copiaPlantas.add(new Planta(p.getNombre(), p.getEnergia(), p.getEdad(), p.isViva(), p.getTamanio()));
        }
        ArrayList<Conejo> copiaConejos = new ArrayList<>();
        for (Conejo c : conejos) {
            copiaConejos.add(new Conejo(c.getNombre(), c.getEnergia(), c.getEdad(), c.isViva(), c.getVelocidad(), c.getPeso()));
        }
        ArrayList<Lobo> copiaLobos = new ArrayList<>();
        for (Lobo l : lobos) {
            copiaLobos.add(new Lobo(l.getNombre(), l.getEnergia(), l.getEdad(), l.isViva(), l.getVelocidad(), l.getPeso(), l.getExitosCaza()));
        }
        return new Snapshot(climaActual, turnoActual, turnosTotales, totalLobosCreados,
                nacimientosPlantas, nacimientosConejos, muertesPlantas, muertesConejos, muertesLobos,
                turnoMayorActividad, maxEventosTurno, copiaPlantas, copiaConejos, copiaLobos);
    }

    // Reconstruye un ecosistema igual al que se guardo
    public static Ecosistema desdeSnapshot(Snapshot s) {
        if (s == null || s.getClima() == null) {
            throw new IllegalArgumentException("Snapshot invalido");
        }
        Ecosistema eco = new Ecosistema(s.getClima(), s.getTurnosTotales());
        eco.turnoActual = Math.max(0, Math.min(s.getTurnoActual(), eco.turnosTotales));
        eco.totalLobosCreados = Math.max(0, s.getTotalLobosCreados());
        eco.nacimientosPlantas = Math.max(0, s.getNacimientosPlantas());
        eco.nacimientosConejos = Math.max(0, s.getNacimientosConejos());
        eco.muertesPlantas = Math.max(0, s.getMuertesPlantas());
        eco.muertesConejos = Math.max(0, s.getMuertesConejos());
        eco.muertesLobos = Math.max(0, s.getMuertesLobos());
        eco.turnoMayorActividad = Math.max(1, s.getTurnoMayorActividad());
        eco.maxEventosTurno = Math.max(0, s.getMaxEventosTurno());

        int maxPlanta = 0;
        for (Planta p : s.getPlantas()) {
            eco.plantas.add(new Planta(p.getNombre(), p.getEnergia(), p.getEdad(), p.isViva(), p.getTamanio()));
            maxPlanta = Math.max(maxPlanta, numeroDeNombre(p.getNombre()));
        }
        int maxConejo = 0;
        for (Conejo c : s.getConejos()) {
            eco.conejos.add(new Conejo(c.getNombre(), c.getEnergia(), c.getEdad(), c.isViva(), c.getVelocidad(), c.getPeso()));
            maxConejo = Math.max(maxConejo, numeroDeNombre(c.getNombre()));
        }
        for (Lobo l : s.getLobos()) {
            eco.lobos.add(new Lobo(l.getNombre(), l.getEnergia(), l.getEdad(), l.isViva(), l.getVelocidad(), l.getPeso(), l.getExitosCaza()));
        }
        Planta.sincronizarContador(maxPlanta);
        Conejo.sincronizarContador(maxConejo);
        return eco;
    }

    // Numero al final de un nombre como "Helecho-12" (0 si no tiene)
    private static int numeroDeNombre(String nombre) {
        int guion = nombre.lastIndexOf('-');
        if (guion < 0) {
            return 0;
        }
        try {
            return Integer.parseInt(nombre.substring(guion + 1));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void cambiarClima(Clima nuevo) {
        if (nuevo != null) {
            this.climaActual = nuevo;
        }
    }

    public int contarPlantasVivas() {
        int vivas = 0;
        for (Planta p : plantas) {
            if (p.isViva()) {
                vivas++;
            }
        }
        return vivas;
    }

    public int contarConejosVivos() {
        int vivos = 0;
        for (Conejo c : conejos) {
            if (c.isViva()) {
                vivos++;
            }
        }
        return vivos;
    }

    public int contarLobosVivos() {
        int vivos = 0;
        for (Lobo l : lobos) {
            if (l.isViva()) {
                vivos++;
            }
        }
        return vivos;
    }

    public boolean ecosistemaColapsado() {
        return contarPlantasVivas() == 0 || contarConejosVivos() == 0 || contarLobosVivos() == 0;
    }

    public String mostrarEstado() {
        return "Estado: Plantas: " + contarPlantasVivas()
                + " | Conejos: " + contarConejosVivos()
                + " | Lobos: " + contarLobosVivos();
    }

    public Planta buscarPlantaViva() {
        ArrayList<Planta> vivas = new ArrayList<>();
        for (Planta p : plantas) {
            if (p.isViva()) {
                vivas.add(p);
            }
        }
        if (vivas.isEmpty()) {
            return null;
        }
        int indice = (int) (Math.random() * vivas.size());
        return vivas.get(indice);
    }

    public boolean hayOtroConejoVivo(Conejo actual) {
        for (Conejo c : conejos) {
            if (c != actual && c.isViva()) {
                return true;
            }
        }
        return false;
    }

    public Conejo obtenerConejoVivoAleatorio() {
        ArrayList<Conejo> vivos = new ArrayList<>();
        for (Conejo c : conejos) {
            if (c.isViva()) {
                vivos.add(c);
            }
        }
        if (vivos.isEmpty()) {
            return null;
        }
        int indice = (int) (Math.random() * vivos.size());
        return vivos.get(indice);
    }

    public Clima getClimaActual() {
        return climaActual;
    }

    public boolean climaPermiteReproduccionPlanta() {
        return climaActual.permiteReproduccionPlanta();
    }

    public double getMultiplicadorReproduccionPlanta() {
        return climaActual.getMultiplicadorReproduccionPlanta();
    }

    public void agregarPlantaHija(Planta nuevaPlanta) {
        if (nuevaPlanta != null) {
            nuevasPlantasTurno.add(nuevaPlanta);
            nacimientosPlantas++;
        }
    }

    public void agregarConejoHijo(Conejo nuevoConejo) {
        if (nuevoConejo != null) {
            nuevosConejosTurno.add(nuevoConejo);
            nacimientosConejos++;
        }
    }

    public void registrarEvento(String evento) {
        if (evento != null && !evento.isEmpty()) {
            eventosTurnoActual.add(evento);
        }
    }

    public boolean puedeAgregarMasLobos() {
        return totalLobosCreados < MAX_LOBOS_SIMULACION;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public int getTurnosTotales() {
        return turnosTotales;
    }

    public int getTotalLobosCreados() {
        return totalLobosCreados;
    }

    // Contadores acumulados (los usa el reporte final y, mas adelante, el snapshot)
    public int getNacimientosPlantas() {
        return nacimientosPlantas;
    }

    public int getNacimientosConejos() {
        return nacimientosConejos;
    }

    public int getMuertesPlantas() {
        return muertesPlantas;
    }

    public int getMuertesConejos() {
        return muertesConejos;
    }

    public int getMuertesLobos() {
        return muertesLobos;
    }

    public int getTurnoMayorActividad() {
        return turnoMayorActividad;
    }

    public int getMaxEventosTurno() {
        return maxEventosTurno;
    }
}