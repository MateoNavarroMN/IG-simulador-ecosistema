package simuladorecosistema.modelo;

import java.util.ArrayList;

// Foto completa de una simulacion: es lo que el DAO guarda y devuelve al cargar.
// Las entidades que contiene son copias, no las del ecosistema en marcha.
public class Snapshot {

    private final Clima clima;
    private final int turnoActual;
    private final int turnosTotales;
    private final int totalLobosCreados;
    private final int nacimientosPlantas;
    private final int nacimientosConejos;
    private final int muertesPlantas;
    private final int muertesConejos;
    private final int muertesLobos;
    private final int turnoMayorActividad;
    private final int maxEventosTurno;
    private final ArrayList<Planta> plantas;
    private final ArrayList<Conejo> conejos;
    private final ArrayList<Lobo> lobos;

    public Snapshot(Clima clima, int turnoActual, int turnosTotales, int totalLobosCreados,
            int nacimientosPlantas, int nacimientosConejos,
            int muertesPlantas, int muertesConejos, int muertesLobos,
            int turnoMayorActividad, int maxEventosTurno,
            ArrayList<Planta> plantas, ArrayList<Conejo> conejos, ArrayList<Lobo> lobos) {
        this.clima = clima;
        this.turnoActual = turnoActual;
        this.turnosTotales = turnosTotales;
        this.totalLobosCreados = totalLobosCreados;
        this.nacimientosPlantas = nacimientosPlantas;
        this.nacimientosConejos = nacimientosConejos;
        this.muertesPlantas = muertesPlantas;
        this.muertesConejos = muertesConejos;
        this.muertesLobos = muertesLobos;
        this.turnoMayorActividad = turnoMayorActividad;
        this.maxEventosTurno = maxEventosTurno;
        this.plantas = plantas;
        this.conejos = conejos;
        this.lobos = lobos;
    }

    public Clima getClima() {
        return clima;
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

    public ArrayList<Planta> getPlantas() {
        return plantas;
    }

    public ArrayList<Conejo> getConejos() {
        return conejos;
    }

    public ArrayList<Lobo> getLobos() {
        return lobos;
    }
}