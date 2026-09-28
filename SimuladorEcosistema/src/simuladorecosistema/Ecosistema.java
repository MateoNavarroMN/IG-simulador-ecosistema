package simuladorecosistema;

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
                    System.out.println("[!] No se pueden agregar mas lobos. Limite maximo de "
                            + MAX_LOBOS_SIMULACION + " alcanzado en toda la simulacion.");
                    return false;
                }
                totalLobosCreados++;
                Lobo nuevoLobo = new Lobo("Lobo-" + totalLobosCreados, energiaInicial);
                lobos.add(nuevoLobo);
                return true;

            default:
                System.out.println("[!] Tipo de entidad invalido: " + tipo);
                return false;
        }
    }

    // =========================================================================
    // MOTOR DE SIMULACION POR TURNO (Integrante 4)
    // =========================================================================
    public void procesarTurno() {
        turnoActual++;
        eventosTurnoActual.clear();
        nuevasPlantasTurno.clear();
        nuevosConejosTurno.clear();

        System.out.println("\n=== TURNO " + turnoActual + " | Clima: " + climaActual.getNombre() + " ===");
        System.out.println("Plantas: " + contarPlantasVivas()
                + " | Conejos: " + contarConejosVivos()
                + " | Lobos: " + contarLobosVivos());

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
                // para que el metodo default verificarMuerte() de Mortal procese e imprima la baja
                if (ent.getEnergia() <= 0) {
                    ent.setViva(true);
                    eventosTurnoActual.add(ent.getNombre() + " murio de inanicion / agotamiento");
                }
            }
        }

        System.out.println("-- Eventos --");
        // Uso del metodo default verificarMuerte() de la interface Mortal
        for (Mortal m : mortales) {
            if (m.estaVivo() && m.getEnergia() <= 0) {
                m.verificarMuerte();
            }
        }

        if (eventosTurnoActual.isEmpty()) {
            System.out.println("Sin eventos relevantes en este turno.");
        } else {
            for (String ev : eventosTurnoActual) {
                System.out.println(ev);
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

        // 6. Mostrar el estado general al final del turno
        mostrarEstado();
    }

    public void generarReporteFinal() {
        System.out.println("\n==================================================");
        System.out.println("           REPORTE FINAL DE LA SIMULACION         ");
        System.out.println("==================================================");

        // 1. Causa de fin
        if (ecosistemaColapsado()) {
            ArrayList<String> extintas = new ArrayList<>();
            if (contarPlantasVivas() == 0) extintas.add("Plantas");
            if (contarConejosVivos() == 0) extintas.add("Conejos");
            if (contarLobosVivos() == 0) extintas.add("Lobos");
            System.out.println("Causa de fin: Colapso del ecosistema en el turno " + turnoActual
                    + " (Se extinguieron: " + String.join(", ", extintas) + ").");
        } else {
            System.out.println("Causa de fin: Se completaron los " + turnosTotales + " turnos configurados.");
        }

        // 2. Turno de mayor actividad
        System.out.println("Turno de mayor actividad: Turno " + turnoMayorActividad
                + " (" + maxEventosTurno + " eventos registrados).");

        // 3. Entidad mas longeva de cada tipo
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
        for (Lobo l : lobos) {
            if (loboMasLongevo == null || l.getEdad() > loboMasLongevo.getEdad()) {
                loboMasLongevo = l;
            }
        }

        System.out.println("\n--- ENTIDADES MAS LONGEVAS ---");
        System.out.println("Planta mas longeva : " + (plantaMasLongeva != null
                ? plantaMasLongeva.getNombre() + " (" + plantaMasLongeva.getEdad() + " turnos)" : "N/A"));
        System.out.println("Conejo mas longevo : " + (conejoMasLongevo != null
                ? conejoMasLongevo.getNombre() + " (" + conejoMasLongevo.getEdad() + " turnos)" : "N/A"));
        System.out.println("Lobo mas longevo   : " + (loboMasLongevo != null
                ? loboMasLongevo.getNombre() + " (" + loboMasLongevo.getEdad() + " turnos)" : "N/A"));

        // 4. Lobo con mas cacerias exitosas
        Lobo mejorCazador = null;
        for (Lobo l : lobos) {
            if (mejorCazador == null || l.getExitosCaza() > mejorCazador.getExitosCaza()) {
                mejorCazador = l;
            }
        }
        System.out.println("\n--- MEJOR DEPREDADOR ---");
        if (mejorCazador != null) {
            System.out.println("• Lobo con mas cacerias exitosas: " + mejorCazador.getNombre()
                    + " (" + mejorCazador.getExitosCaza() + " cacerias).");
        } else {
            System.out.println("• Lobo con mas cacerias exitosas: Sin registros.");
        }

        // 5. Total de nacimientos y muertes por tipo
        System.out.println("\n--- BALANCE DEMOGRAFICO TOTAL ---");
        System.out.println("Plantas -> Nacimientos: " + nacimientosPlantas + " | Muertes: " + muertesPlantas
                + " | Vivas al final: " + contarPlantasVivas());
        System.out.println("Conejos -> Nacimientos: " + nacimientosConejos + " | Muertes: " + muertesConejos
                + " | Vivos al final: " + contarConejosVivos());
        System.out.println("Lobos   -> Nacimientos: 0 (no se reproducen) | Muertes: " + muertesLobos
                + " | Vivos al final: " + contarLobosVivos());
        System.out.println("==================================================");
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

    public void mostrarEstado() {
        System.out.println("Estado: Plantas: " + contarPlantasVivas()
                + " | Conejos: " + contarConejosVivos()
                + " | Lobos: " + contarLobosVivos());
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
}