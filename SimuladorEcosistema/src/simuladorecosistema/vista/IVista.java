package simuladorecosistema.vista;

import java.util.ArrayList;
import simuladorecosistema.modelo.ReporteFinal;

// Contrato entre el controlador y la vista. El controlador solo conoce esta interfaz;
// la vista real (Swing) y VistaStub la implementan.
public interface IVista {

    // Error de validacion o de E/S, para mostrar al usuario
    void mostrarError(String mensaje);

    // Aviso informativo (simulacion guardada, cargada, etc.)
    void mostrarMensaje(String mensaje);

    // Estado actual de la simulacion, se llama al iniciar y despues de cada turno
    void mostrarEstado(int turnoActual, int turnosTotales, int plantas, int conejos, int lobos, String clima);

    // Eventos del turno que acaba de procesarse
    void mostrarEventos(ArrayList<String> eventos);

    // Cada 3 turnos: la vista ofrece cambiar el clima o agregar entidades
    void ofrecerIntervencion(int turnoActual);

    // Fin de la simulacion: la vista muestra el reporte
    void mostrarReporte(ReporteFinal reporte);

    // No hay simulacion en marcha (primer arranque o archivo inexistente/danado)
    void mostrarSinSimulacion(String motivo);
}