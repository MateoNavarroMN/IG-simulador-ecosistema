package simuladorecosistema.modelo;

// Poblacion viva de cada tipo al cierre de un turno. Lo usa el panel de estadisticas.
public class RegistroPoblacion {

    private final int turno;
    private final int plantas;
    private final int conejos;
    private final int lobos;

    public RegistroPoblacion(int turno, int plantas, int conejos, int lobos) {
        this.turno = turno;
        this.plantas = plantas;
        this.conejos = conejos;
        this.lobos = lobos;
    }

    public int getTurno() {
        return turno;
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
}