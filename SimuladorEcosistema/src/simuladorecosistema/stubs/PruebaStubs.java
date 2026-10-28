package simuladorecosistema.stubs;

import java.util.ArrayList;
import simuladorecosistema.modelo.Clima;
import simuladorecosistema.modelo.Ecosistema;
import simuladorecosistema.vista.IVista;

// Recorrido corto que usa los dos stubs juntos, tal como lo hara el controlador.
// Ejecutar con clic derecho > Run File. Se borra al integrar.
public class PruebaStubs {

    public static void main(String[] args) {
        IVista vista = new VistaStub();
        Ecosistema modelo = new EcosistemaStub(Clima.SOLEADO, 10);

        for (int i = 0; i < 6; i++) {
            modelo.agregarEntidad("planta");
        }
        for (int i = 0; i < 4; i++) {
            modelo.agregarEntidad("conejo");
        }
        modelo.agregarEntidad("lobo");

        vista.mostrarSinSimulacion("primer arranque");
        vista.mostrarEstado(modelo.getTurnoActual(), modelo.getTurnosTotales(),
                modelo.contarPlantasVivas(), modelo.contarConejosVivos(),
                modelo.contarLobosVivos(), modelo.getClimaActual().getNombre());

        while (modelo.getTurnoActual() < modelo.getTurnosTotales()) {
            ArrayList<String> eventos = modelo.procesarTurno();
            vista.mostrarEventos(eventos);
            vista.mostrarEstado(modelo.getTurnoActual(), modelo.getTurnosTotales(),
                    modelo.contarPlantasVivas(), modelo.contarConejosVivos(),
                    modelo.contarLobosVivos(), modelo.getClimaActual().getNombre());
            if (modelo.getTurnoActual() % 3 == 0) {
                vista.ofrecerIntervencion(modelo.getTurnoActual());
            }
        }
        vista.mostrarReporte(modelo.obtenerReporteFinal());
    }
}