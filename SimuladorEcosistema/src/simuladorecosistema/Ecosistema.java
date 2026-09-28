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
    private ArrayList<String> eventosTurnoActual;

    // Listas auxiliares para nacimientos durante un turno
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
        this.eventosTurnoActual = new ArrayList<>();
        this.nuevasPlantasTurno = new ArrayList<>();
        this.nuevosConejosTurno = new ArrayList<>();
    }

    // SOBRECARGA 1: Sin energía inicial (asigna energía aleatoria entre 45 y 75)
    public boolean agregarEntidad(String tipo) {
        double energiaAleatoria = 45.0 + (Math.random() * 30.0);
        return agregarEntidad(tipo, energiaAleatoria);
    }

    // SOBRECARGA 2: Con energía inicial especificada
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
                    System.out.println("[!] No se pueden agregar más lobos. Límite máximo de " 
                            + MAX_LOBOS_SIMULACION + " alcanzado en toda la simulación.");
                    return false;
                }
                totalLobosCreados++;
                Lobo nuevoLobo = new Lobo("Lobo-" + totalLobosCreados, energiaInicial);
                lobos.add(nuevoLobo);
                return true;

            default:
                System.out.println("[!] Tipo de entidad inválido: " + tipo);
                return false;
        }
    }

    public void cambiarClima(Clima nuevo) {
        if (nuevo != null) {
            this.climaActual = nuevo;
        }
    }

    // Conteos de entidades vivas
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

    // Verifica si alguna de las 3 poblaciones llegó a 0
    public boolean ecosistemaColapsado() {
        return contarPlantasVivas() == 0 || contarConejosVivos() == 0 || contarLobosVivos() == 0;
    }

    // Imprime el conteo actual de cada entidad y el clima
    public void mostrarEstado() {
        System.out.println("Estado: Plantas: " + contarPlantasVivas()
                + " | Conejos: " + contarConejosVivos()
                + " | Lobos: " + contarLobosVivos()
                + " | Clima: " + climaActual.getNombre());
    }

    // Métodos de búsqueda e interacción para Planta, Conejo y Lobo
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

    // Getters para el Integrante 4 (Loop de turnos y Reporte Final)
    public ArrayList<Planta> getPlantas() {
        return plantas;
    }

    public ArrayList<Conejo> getConejos() {
        return conejos;
    }

    public ArrayList<Lobo> getLobos() {
        return lobos;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(int turnoActual) {
        this.turnoActual = Math.max(0, turnoActual);
    }

    public int getTurnosTotales() {
        return turnosTotales;
    }

    public int getTotalLobosCreados() {
        return totalLobosCreados;
    }

    public ArrayList<String> getEventosTurnoActual() {
        return eventosTurnoActual;
    }

    public ArrayList<Planta> getNuevasPlantasTurno() {
        return nuevasPlantasTurno;
    }

    public ArrayList<Conejo> getNuevosConejosTurno() {
        return nuevosConejosTurno;
    }
}
