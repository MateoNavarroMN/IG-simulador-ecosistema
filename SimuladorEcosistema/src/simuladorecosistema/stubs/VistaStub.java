package simuladorecosistema.stubs;

import java.util.ArrayList;
import simuladorecosistema.modelo.ReporteFinal;
import simuladorecosistema.vista.IVista;

// Vista de mentira: imprime lo que recibe y lo guarda en un historial para poder
// revisar que el controlador llamo lo que debia. Se borra al integrar la vista real.
public class VistaStub implements IVista {

    private final ArrayList<String> llamadas = new ArrayList<>();

    private void registrar(String linea) {
        llamadas.add(linea);
        System.out.println("[VistaStub] " + linea);
    }

    public ArrayList<String> getLlamadas() {
        return new ArrayList<>(llamadas);
    }

    @Override
    public void mostrarError(String mensaje) {
        registrar("ERROR: " + mensaje);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        registrar("MENSAJE: " + mensaje);
    }

    @Override
    public void mostrarEstado(int turnoActual, int turnosTotales, int plantas, int conejos, int lobos, String clima) {
        registrar("ESTADO: turno " + turnoActual + "/" + turnosTotales + " | plantas " + plantas
                + " | conejos " + conejos + " | lobos " + lobos + " | clima " + clima);
    }

    @Override
    public void mostrarEventos(ArrayList<String> eventos) {
        for (String e : eventos) {
            registrar("EVENTO: " + e);
        }
    }

    @Override
    public void ofrecerIntervencion(int turnoActual) {
        registrar("INTERVENCION ofrecida en el turno " + turnoActual);
    }

    @Override
    public void mostrarReporte(ReporteFinal reporte) {
        registrar("REPORTE: turno final " + reporte.getTurnoFinal() + ", colapso " + reporte.isColapso());
    }

    @Override
    public void mostrarSinSimulacion(String motivo) {
        registrar("SIN SIMULACION: " + motivo);
    }
}