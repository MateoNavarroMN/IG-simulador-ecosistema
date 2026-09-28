package simuladorecosistema;

import java.util.ArrayList;

public class Ecosistema {

    private ArrayList<Planta> plantas;
    private ArrayList<Conejo> conejos;
    private ArrayList<Lobo> lobos;
    private Clima climaActual;
    private int turnoActual;
    private int turnosTotales;

    // Contadores para validaciones e historial del reporte final
    private int totalLobosCreados;
    private int nacimientosPlantas;
    private int nacimientosConejos;
    private int muertesPlantas;
    private int muertesConejos;
    private int muertesLobos;
    private ArrayList<String> eventosTurnoActual;

    // Nuevos nacimientos del turno en curso (para evitar ConcurrentModificationException)
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
        this.eventosTurnoActual = new ArrayList<>();
        this.nuevasPlantasTurno = new ArrayList<>();
        this.nuevosConejosTurno = new ArrayList<>();
    }

    // REQUISITO POO: SOBRECARGA DE MÉTODOS (Versión 1: sin energía inicial)
    // Genera energía aleatoria dentro de un rango razonable (40 a 75)
    public boolean agregarEntidad(String tipo) {
        double energiaAleatoria = 40.0 + (Math.random() * 35.0);
        return agregarEntidad(tipo, energiaAleatoria);
    }

    // REQUISITO POO: SOBRECARGA DE MÉTODOS (Versión 2: con energía inicial)
    public boolean agregarEntidad(String tipo, double energiaInicial) {
        if (tipo == null) {
            return false;
        }

        String tipoLimpio = tipo.trim().toLowerCase();

        switch (tipoLimpio) {
            case "planta":
                int numPlanta = plantas.size() + novasPlantasCount() + 1;
                int tamanioAleatorio = (int) (Math.random() * 5) + 1;
                Planta nuevaPlanta = new Planta("Helecho-" + numPlanta, energiaInicial, tamanioAleatorio);
                plantas.add(nuevaPlanta);
                return true;

            case "conejo":
                int numConejo = conejos.size() + nuevosConejosCount() + 1;
                Conejo nuevoConejo = new Conejo("Conejo-" + numConejo, energiaInicial);
                conejos.add(nuevoConejo);
                return true;

            case "lobo":
                if (totalLobosCreados >= MAX_LOBOS_SIMULACION) {
                    System.out.println("[!] No se pueden agregar más lobos. Se alcanzó el límite máximo de " 
                            + MAX_LOBOS_SIMULACION + " lobos en la simulación.");
                    return false;
                }
                totalLobosCreados++;
                Lobo nuevoLobo = new Lobo("Lobo-" + totalLobosCreados, energiaInicial);
                lobos.add(nuevoLobo);
                return true;

            default:
                System.out.println("[!] Tipo de entidad no reconocido: " + tipo);
                return false;
        }
    }

    private int novasPlantasCount() {
        return nuevasPlantasTurno != null ? nuevasPlantasTurno.size() : 0;
    }

    private int nuevosConejosCount() {
        return nuevosConejosTurno != null ? nuevosConejosTurno.size() : 0;
    }

    public void cambiarClima(Clima nuevo) {
        if (nuevo != null) {
            this.climaActual = nuevo;
        }
    }

    // Métodos puente que usan Planta, Conejo y Lobo
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

    public Planta buscarPlantaViva() {
        return null; // Se completa en el Commit 2
    }

    public boolean hayOtroConejoVivo(Conejo actual) {
        return false; // Se completa en el Commit 2
    }

    public Conejo obtenerConejoVivoAleatorio() {
        return null; // Se completa en el Commit 2
    }

    public void registrarEvento(String evento) {
        if (evento != null && !evento.isEmpty()) {
            eventosTurnoActual.add(evento);
        }
    }
}

