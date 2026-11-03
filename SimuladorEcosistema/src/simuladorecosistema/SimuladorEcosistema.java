package simuladorecosistema;

import simuladorecosistema.vista.VentanaPrincipal;

/**
 * Punto de entrada de la aplicación.
 * Abre la ventana principal y carga la simulación guardada, si hay una.
 */
public class SimuladorEcosistema {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
            ventana.getControlador().iniciar(); // carga la simulación guardada, si hay una
        });
    }
}