package simuladorecosistema.stubs;

import java.util.ArrayList;
import simuladorecosistema.modelo.Clima;
import simuladorecosistema.modelo.Ecosistema;
import simuladorecosistema.modelo.ReporteFinal;

// Modelo de mentira: respeta la API publica de Ecosistema pero sin azar ni entidades reales,
// asi el controlador se prueba con resultados predecibles. Se borra al integrar.
public class EcosistemaStub extends Ecosistema {

    private static final int MAX_LOBOS = 5;

    private final int turnosTotales;
    private int turno = 0;
    private int plantas = 0;
    private int conejos = 0;
    private int lobos = 0;
    private int lobosCreados = 0;
    private Clima clima;

    public EcosistemaStub(Clima climaInicial, int turnosTotales) {
        super(climaInicial, turnosTotales);
        this.clima = (climaInicial != null) ? climaInicial : Clima.SOLEADO;
        this.turnosTotales = Math.max(10, Math.min(50, turnosTotales));
    }

    @Override
    public boolean agregarEntidad(String tipo) {
        return agregarEntidad(tipo, 50.0);
    }

    @Override
    public boolean agregarEntidad(String tipo, double energiaInicial) {
        if (tipo == null) {
            return false;
        }
        switch (tipo.trim().toLowerCase()) {
            case "planta":
                plantas++;
                return true;
            case "conejo":
                conejos++;
                return true;
            case "lobo":
                if (lobosCreados >= MAX_LOBOS) {
                    return false;
                }
                lobosCreados++;
                lobos++;
                return true;
            default:
                return false;
        }
    }

    // Cada turno: un evento fijo y un conejo menos (nunca baja de 1, asi no hay colapso)
    @Override
    public ArrayList<String> procesarTurno() {
        turno++;
        ArrayList<String> eventos = new ArrayList<>();
        eventos.add("Turno " + turno + ": evento de prueba del modelo stub");
        if (conejos > 1) {
            conejos--;
            eventos.add("MUERTE: un conejo murio (stub)");
        }
        return eventos;
    }

    @Override
    public ReporteFinal obtenerReporteFinal() {
        return new ReporteFinal(false, turno, turnosTotales, new ArrayList<String>(),
                1, 2, null, null, null, null,
                0, 0, 0, 0, 0,
                plantas, conejos, lobos);
    }

    @Override
    public void cambiarClima(Clima nuevo) {
        if (nuevo != null) {
            clima = nuevo;
        }
    }

    @Override
    public Clima getClimaActual() {
        return clima;
    }

    @Override
    public int getTurnoActual() {
        return turno;
    }

    @Override
    public int getTurnosTotales() {
        return turnosTotales;
    }

    @Override
    public int getTotalLobosCreados() {
        return lobosCreados;
    }

    @Override
    public boolean puedeAgregarMasLobos() {
        return lobosCreados < MAX_LOBOS;
    }

    @Override
    public int contarPlantasVivas() {
        return plantas;
    }

    @Override
    public int contarConejosVivos() {
        return conejos;
    }

    @Override
    public int contarLobosVivos() {
        return lobos;
    }

    @Override
    public boolean ecosistemaColapsado() {
        return plantas == 0 || conejos == 0 || lobos == 0;
    }
}