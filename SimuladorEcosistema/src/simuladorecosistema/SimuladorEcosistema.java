package simuladorecosistema;

import simuladorecosistema.controlador.ControladorSimulacion;
import simuladorecosistema.stubs.VistaStub;
import simuladorecosistema.vista.IVista;

/**
 * Punto de entrada de la aplicación.
 * Arma el controlador con una vista. Mientras no exista la vista real (Fase 3),
 * se usa VistaStub; al integrar, se reemplaza por la ventana principal.
 */
public class SimuladorEcosistema {

    public static void main(String[] args) {
        IVista vista = new VistaStub(); // TODO: reemplazar por la ventana principal
        ControladorSimulacion controlador = new ControladorSimulacion(vista);
        // La carga de la simulación guardada al arrancar se agrega en la Tarea 15.
    }
}