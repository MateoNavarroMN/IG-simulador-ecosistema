package simuladorecosistema.controlador;

import simuladorecosistema.modelo.Clima;

// Configuracion inicial ya validada: el controlador la usa para crear la simulacion.
public class ConfiguracionSimulacion {

    private final int plantas;
    private final int conejos;
    private final int lobos;
    private final int turnos;
    private final Clima clima;

    public ConfiguracionSimulacion(int plantas, int conejos, int lobos, int turnos, Clima clima) {
        this.plantas = plantas;
        this.conejos = conejos;
        this.lobos = lobos;
        this.turnos = turnos;
        this.clima = clima;
    }

    public int getPlantas() {
        return plantas;
    }

    public int getConejos() {
        return conejos;
    }

    public int getLobos() {
        return lobos;
    }

    public int getTurnos() {
        return turnos;
    }

    public Clima getClima() {
        return clima;
    }
}