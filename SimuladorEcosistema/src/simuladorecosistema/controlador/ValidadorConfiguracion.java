package simuladorecosistema.controlador;

import java.util.ArrayList;
import simuladorecosistema.modelo.Clima;

// Valida los datos del formulario de configuracion. Recibe los textos tal como los
// escribio el usuario y devuelve los errores para que la vista los muestre.
public class ValidadorConfiguracion {

    public static final int MIN_PLANTAS = 5;
    public static final int MAX_PLANTAS = 30;
    public static final int MIN_CONEJOS = 2;
    public static final int MAX_CONEJOS = 15;
    public static final int MIN_LOBOS = 1;
    public static final int MAX_LOBOS = 5;
    public static final int MIN_TURNOS = 10;
    public static final int MAX_TURNOS = 50;

    public ResultadoValidacion validar(String plantas, String conejos, String lobos,
            String turnos, Clima clima) {
        ArrayList<String> errores = new ArrayList<>();

        Integer p = leerEntero("Plantas", plantas, MIN_PLANTAS, MAX_PLANTAS, errores);
        Integer c = leerEntero("Conejos", conejos, MIN_CONEJOS, MAX_CONEJOS, errores);
        Integer l = leerEntero("Lobos", lobos, MIN_LOBOS, MAX_LOBOS, errores);
        Integer t = leerEntero("Turnos", turnos, MIN_TURNOS, MAX_TURNOS, errores);
        if (clima == null) {
            errores.add("Clima inicial: elegi un clima");
        }

        if (!errores.isEmpty()) {
            return new ResultadoValidacion(errores, null);
        }
        return new ResultadoValidacion(errores, new ConfiguracionSimulacion(p, c, l, t, clima));
    }

    // Devuelve el numero si es valido; si no, agrega el error y devuelve null
    private Integer leerEntero(String campo, String texto, int min, int max, ArrayList<String> errores) {
        if (texto == null || texto.trim().isEmpty()) {
            errores.add(campo + ": el campo esta vacio");
            return null;
        }
        int valor;
        try {
            valor = Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            errores.add(campo + ": tiene que ser un numero entero");
            return null;
        }
        if (valor < min || valor > max) {
            errores.add(campo + ": tiene que estar entre " + min + " y " + max);
            return null;
        }
        return valor;
    }
}