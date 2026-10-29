package simuladorecosistema.dao;

import simuladorecosistema.modelo.Snapshot;

// Resultado de cargar el archivo: o viene el snapshot, o viene el motivo del fallo.
public class ResultadoCarga {

    private final Snapshot snapshot;
    private final boolean archivoInexistente;
    private final String error;

    private ResultadoCarga(Snapshot snapshot, boolean archivoInexistente, String error) {
        this.snapshot = snapshot;
        this.archivoInexistente = archivoInexistente;
        this.error = error;
    }

    public static ResultadoCarga exito(Snapshot snapshot) {
        return new ResultadoCarga(snapshot, false, null);
    }

    // No hay archivo guardado: es lo normal en el primer arranque, no un error del usuario
    public static ResultadoCarga inexistente() {
        return new ResultadoCarga(null, true, "No hay una simulacion guardada");
    }

    public static ResultadoCarga fallo(String error) {
        return new ResultadoCarga(null, false, error);
    }

    public boolean esExitoso() {
        return snapshot != null;
    }

    public boolean isArchivoInexistente() {
        return archivoInexistente;
    }

    // null si la carga fue exitosa
    public Snapshot getSnapshot() {
        return snapshot;
    }

    // null si la carga fue exitosa; si no, texto listo para mostrar al usuario
    public String getError() {
        return error;
    }
}