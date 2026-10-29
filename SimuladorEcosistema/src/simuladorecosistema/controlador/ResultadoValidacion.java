package simuladorecosistema.controlador;

import java.util.ArrayList;

// Resultado de validar el formulario: o hay errores para mostrar, o hay una configuracion valida.
public class ResultadoValidacion {

    private final ArrayList<String> errores;
    private final ConfiguracionSimulacion configuracion;

    public ResultadoValidacion(ArrayList<String> errores, ConfiguracionSimulacion configuracion) {
        this.errores = errores;
        this.configuracion = configuracion;
    }

    public boolean esValido() {
        return errores.isEmpty();
    }

    // Un mensaje por campo con problema, en el orden del formulario
    public ArrayList<String> getErrores() {
        return new ArrayList<>(errores);
    }

    // null si hay errores
    public ConfiguracionSimulacion getConfiguracion() {
        return configuracion;
    }
}