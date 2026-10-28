package simuladorecosistema.modelo;

import java.util.ArrayList;

// Datos del reporte final de la simulacion. El modelo solo los entrega;
// la vista decide como mostrarlos.
public class ReporteFinal {

    private final boolean colapso;
    private final int turnoFinal;
    private final int turnosTotales;
    private final ArrayList<String> especiesExtintas;
    private final int turnoMayorActividad;
    private final int eventosMayorActividad;
    private final Planta plantaMasLongeva;
    private final Conejo conejoMasLongevo;
    private final Lobo loboMasLongevo;
    private final Lobo mejorCazador;
    private final int nacimientosPlantas;
    private final int nacimientosConejos;
    private final int muertesPlantas;
    private final int muertesConejos;
    private final int muertesLobos;
    private final int plantasVivas;
    private final int conejosVivos;
    private final int lobosVivos;

    public ReporteFinal(boolean colapso, int turnoFinal, int turnosTotales,
            ArrayList<String> especiesExtintas, int turnoMayorActividad, int eventosMayorActividad,
            Planta plantaMasLongeva, Conejo conejoMasLongevo, Lobo loboMasLongevo, Lobo mejorCazador,
            int nacimientosPlantas, int nacimientosConejos,
            int muertesPlantas, int muertesConejos, int muertesLobos,
            int plantasVivas, int conejosVivos, int lobosVivos) {
        this.colapso = colapso;
        this.turnoFinal = turnoFinal;
        this.turnosTotales = turnosTotales;
        this.especiesExtintas = especiesExtintas;
        this.turnoMayorActividad = turnoMayorActividad;
        this.eventosMayorActividad = eventosMayorActividad;
        this.plantaMasLongeva = plantaMasLongeva;
        this.conejoMasLongevo = conejoMasLongevo;
        this.loboMasLongevo = loboMasLongevo;
        this.mejorCazador = mejorCazador;
        this.nacimientosPlantas = nacimientosPlantas;
        this.nacimientosConejos = nacimientosConejos;
        this.muertesPlantas = muertesPlantas;
        this.muertesConejos = muertesConejos;
        this.muertesLobos = muertesLobos;
        this.plantasVivas = plantasVivas;
        this.conejosVivos = conejosVivos;
        this.lobosVivos = lobosVivos;
    }

    public boolean isColapso() {
        return colapso;
    }

    public int getTurnoFinal() {
        return turnoFinal;
    }

    public int getTurnosTotales() {
        return turnosTotales;
    }

    // Nombres de las poblaciones extintas ("Plantas", "Conejos", "Lobos"); vacia si no hubo colapso
    public ArrayList<String> getEspeciesExtintas() {
        return new ArrayList<>(especiesExtintas);
    }

    public int getTurnoMayorActividad() {
        return turnoMayorActividad;
    }

    public int getEventosMayorActividad() {
        return eventosMayorActividad;
    }

    // Las tres siguientes y getMejorCazador() devuelven null si no hubo entidades de ese tipo
    public Planta getPlantaMasLongeva() {
        return plantaMasLongeva;
    }

    public Conejo getConejoMasLongevo() {
        return conejoMasLongevo;
    }

    public Lobo getLoboMasLongevo() {
        return loboMasLongevo;
    }

    public Lobo getMejorCazador() {
        return mejorCazador;
    }

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

    public int getPlantasVivas() {
        return plantasVivas;
    }

    public int getConejosVivos() {
        return conejosVivos;
    }

    public int getLobosVivos() {
        return lobosVivos;
    }
}