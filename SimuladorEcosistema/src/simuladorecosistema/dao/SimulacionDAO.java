package simuladorecosistema.dao;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import simuladorecosistema.modelo.Clima;
import simuladorecosistema.modelo.Conejo;
import simuladorecosistema.modelo.Lobo;
import simuladorecosistema.modelo.Planta;
import simuladorecosistema.modelo.RegistroPoblacion;
import simuladorecosistema.modelo.Snapshot;

// Guarda y carga la simulacion en un archivo de texto. No muestra mensajes ni usa Swing:
// solo devuelve el resultado para que el controlador avise a la vista.
//
// FORMATO DEL ARCHIVO (UTF-8, una linea por dato, siempre en este orden):
//   SIMULACION_ECOSISTEMA 1                          cabecera y version del formato
//   clima=SOLEADO                                    nombre del enum Clima
//   turnoActual=3
//   turnosTotales=20
//   totalLobosCreados=2
//   nacimientosPlantas=0
//   nacimientosConejos=0
//   muertesPlantas=0
//   muertesConejos=0
//   muertesLobos=0
//   turnoMayorActividad=1
//   maxEventosTurno=0
//   PLANTA;nombre;energia;edad;viva;tamanio          una linea por planta
//   CONEJO;nombre;energia;edad;viva;velocidad;peso   una linea por conejo
//   LOBO;nombre;energia;edad;viva;velocidad;peso;exitosCaza   una linea por lobo
//   HISTORIAL;turno;plantas;conejos;lobos            una linea por turno procesado
//   FIN                                              marca de archivo completo
// Los decimales usan punto y viva es true o false.
//
// AL CARGAR se valida todo: cabecera, claves en orden, numeros, rangos, clima, cantidad
// de campos por linea, FIN al final. Si algo no cumple, no se devuelve nada a medias:
// se devuelve un error con el numero de linea.
public class SimulacionDAO {

    // Relativa a la carpeta desde donde se ejecuta la aplicacion (la del proyecto)
    public static final String RUTA_POR_DEFECTO = "simulacion.txt";

    public boolean guardar(Snapshot snapshot) {
        return guardar(snapshot, RUTA_POR_DEFECTO);
    }

    // Devuelve true si el archivo quedo escrito completo; false si hubo cualquier problema
    public boolean guardar(Snapshot snapshot, String ruta) {
        if (snapshot == null || ruta == null || ruta.trim().isEmpty()) {
            return false;
        }
        Path destino = Paths.get(ruta);
        Path temporal = Paths.get(ruta + ".tmp");
        try {
            Path carpeta = destino.toAbsolutePath().getParent();
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            // Se escribe en un temporal y recien al terminar reemplaza al archivo real,
            // asi un error a mitad de escritura no deja el archivo anterior danado
            try (BufferedWriter out = Files.newBufferedWriter(temporal, StandardCharsets.UTF_8)) {
                escribir(snapshot, out);
            }
            Files.move(temporal, destino, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException | RuntimeException e) {
            try {
                Files.deleteIfExists(temporal);
            } catch (IOException ignorada) {
                // no hay nada mas que hacer con el temporal
            }
            return false;
        }
    }

    public ResultadoCarga cargar() {
        return cargar(RUTA_POR_DEFECTO);
    }

    // Lee el archivo y devuelve el snapshot, o el motivo por el que no se pudo
    public ResultadoCarga cargar(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            return ResultadoCarga.fallo("Ruta de archivo invalida");
        }
        Path origen = Paths.get(ruta);
        if (!Files.exists(origen)) {
            return ResultadoCarga.inexistente();
        }
        List<String> lineas;
        try {
            lineas = Files.readAllLines(origen, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return ResultadoCarga.fallo("No se pudo leer el archivo guardado");
        }
        try {
            return ResultadoCarga.exito(interpretar(lineas));
        } catch (ArchivoInvalidoException e) {
            return ResultadoCarga.fallo(e.getMessage());
        }
    }

    private Snapshot interpretar(List<String> lineas) throws ArchivoInvalidoException {
        // Las lineas en blanco se ignoran; se recuerda el numero real para los mensajes
        ArrayList<String> datos = new ArrayList<>();
        ArrayList<Integer> numeros = new ArrayList<>();
        for (int i = 0; i < lineas.size(); i++) {
            if (!lineas.get(i).trim().isEmpty()) {
                datos.add(lineas.get(i).trim());
                numeros.add(i + 1);
            }
        }
        if (datos.isEmpty()) {
            throw new ArchivoInvalidoException("El archivo guardado esta vacio");
        }
        if (!datos.get(0).equals("SIMULACION_ECOSISTEMA 1")) {
            throw new ArchivoInvalidoException("Linea " + numeros.get(0)
                    + ": el archivo no es una simulacion guardada o es de otra version");
        }

        int pos = 1;
        String nombreClima = leerValor(datos, numeros, pos++, "clima");
        Clima clima;
        try {
            clima = Clima.valueOf(nombreClima);
        } catch (IllegalArgumentException e) {
            throw new ArchivoInvalidoException("Linea " + numeros.get(pos - 1)
                    + ": clima desconocido (" + nombreClima + ")");
        }
        int turnoActual = leerEntero(datos, numeros, pos++, "turnoActual", 0, 50);
        int turnosTotales = leerEntero(datos, numeros, pos++, "turnosTotales", 10, 50);
        if (turnoActual > turnosTotales) {
            throw new ArchivoInvalidoException("Linea " + numeros.get(pos - 2)
                    + ": turnoActual no puede superar a turnosTotales");
        }
        int totalLobosCreados = leerEntero(datos, numeros, pos++, "totalLobosCreados", 0, 5);
        int nacPlantas = leerEntero(datos, numeros, pos++, "nacimientosPlantas", 0, Integer.MAX_VALUE);
        int nacConejos = leerEntero(datos, numeros, pos++, "nacimientosConejos", 0, Integer.MAX_VALUE);
        int muePlantas = leerEntero(datos, numeros, pos++, "muertesPlantas", 0, Integer.MAX_VALUE);
        int mueConejos = leerEntero(datos, numeros, pos++, "muertesConejos", 0, Integer.MAX_VALUE);
        int mueLobos = leerEntero(datos, numeros, pos++, "muertesLobos", 0, Integer.MAX_VALUE);
        int turnoMayor = leerEntero(datos, numeros, pos++, "turnoMayorActividad", 1, 50);
        int maxEventos = leerEntero(datos, numeros, pos++, "maxEventosTurno", 0, Integer.MAX_VALUE);

        ArrayList<Planta> plantas = new ArrayList<>();
        ArrayList<Conejo> conejos = new ArrayList<>();
        ArrayList<Lobo> lobos = new ArrayList<>();
        ArrayList<RegistroPoblacion> historial = new ArrayList<>();
        boolean fin = false;

        while (pos < datos.size() && !fin) {
            String linea = datos.get(pos);
            int nro = numeros.get(pos);
            pos++;
            if (linea.equals("FIN")) {
                fin = true;
                break;
            }
            String[] c = linea.split(";", -1);
            switch (c[0]) {
                case "PLANTA":
                    exigirCampos(c, 6, nro);
                    plantas.add(new Planta(nombre(c[1], nro), energia(c[2], nro), entero(c[3], 0, 100000, nro, "edad"),
                            booleano(c[4], nro), entero(c[5], 1, 5, nro, "tamanio")));
                    break;
                case "CONEJO":
                    exigirCampos(c, 7, nro);
                    conejos.add(new Conejo(nombre(c[1], nro), energia(c[2], nro), entero(c[3], 0, 100000, nro, "edad"),
                            booleano(c[4], nro), entero(c[5], 0, 1000, nro, "velocidad"), peso(c[6], nro)));
                    break;
                case "LOBO":
                    exigirCampos(c, 8, nro);
                    lobos.add(new Lobo(nombre(c[1], nro), energia(c[2], nro), entero(c[3], 0, 100000, nro, "edad"),
                            booleano(c[4], nro), entero(c[5], 0, 1000, nro, "velocidad"), peso(c[6], nro),
                            entero(c[7], 0, 100000, nro, "exitosCaza")));
                    break;
                case "HISTORIAL":
                    exigirCampos(c, 5, nro);
                    historial.add(new RegistroPoblacion(entero(c[1], 1, 50, nro, "turno"),
                            entero(c[2], 0, 100000, nro, "plantas"), entero(c[3], 0, 100000, nro, "conejos"),
                            entero(c[4], 0, 100000, nro, "lobos")));
                    break;
                default:
                    throw new ArchivoInvalidoException("Linea " + nro + ": tipo de linea desconocido (" + c[0] + ")");
            }
        }

        if (!fin) {
            throw new ArchivoInvalidoException("El archivo esta incompleto: falta la linea FIN");
        }
        if (pos < datos.size()) {
            throw new ArchivoInvalidoException("Linea " + numeros.get(pos) + ": hay datos despues de FIN");
        }
        if (lobos.size() > totalLobosCreados) {
            throw new ArchivoInvalidoException("Hay mas lobos que el total de lobos creados");
        }
        if (historial.size() > turnoActual) {
            throw new ArchivoInvalidoException("El historial tiene mas turnos que los jugados");
        }

        return new Snapshot(clima, turnoActual, turnosTotales, totalLobosCreados,
                nacPlantas, nacConejos, muePlantas, mueConejos, mueLobos,
                turnoMayor, maxEventos, plantas, conejos, lobos, historial);
    }

    // Lee una linea "clave=valor" que tiene que estar en la posicion esperada
    private String leerValor(ArrayList<String> datos, ArrayList<Integer> numeros, int pos, String clave)
            throws ArchivoInvalidoException {
        if (pos >= datos.size()) {
            throw new ArchivoInvalidoException("El archivo esta incompleto: falta " + clave);
        }
        String prefijo = clave + "=";
        String linea = datos.get(pos);
        if (!linea.startsWith(prefijo)) {
            throw new ArchivoInvalidoException("Linea " + numeros.get(pos) + ": se esperaba " + clave);
        }
        return linea.substring(prefijo.length()).trim();
    }

    private int leerEntero(ArrayList<String> datos, ArrayList<Integer> numeros, int pos, String clave,
            int min, int max) throws ArchivoInvalidoException {
        String valor = leerValor(datos, numeros, pos, clave);
        return entero(valor, min, max, numeros.get(pos), clave);
    }

    private int entero(String texto, int min, int max, int nroLinea, String campo)
            throws ArchivoInvalidoException {
        int valor;
        try {
            valor = Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new ArchivoInvalidoException("Linea " + nroLinea + ": " + campo + " no es un numero entero");
        }
        if (valor < min || valor > max) {
            throw new ArchivoInvalidoException("Linea " + nroLinea + ": " + campo + " fuera de rango");
        }
        return valor;
    }

    private double decimal(String texto, int nroLinea, String campo) throws ArchivoInvalidoException {
        double valor;
        try {
            valor = Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            throw new ArchivoInvalidoException("Linea " + nroLinea + ": " + campo + " no es un numero");
        }
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor < 0) {
            throw new ArchivoInvalidoException("Linea " + nroLinea + ": " + campo + " invalido");
        }
        return valor;
    }

    private double energia(String texto, int nroLinea) throws ArchivoInvalidoException {
        return decimal(texto, nroLinea, "energia");
    }

    private double peso(String texto, int nroLinea) throws ArchivoInvalidoException {
        return decimal(texto, nroLinea, "peso");
    }

    private boolean booleano(String texto, int nroLinea) throws ArchivoInvalidoException {
        String t = texto.trim();
        if (t.equals("true")) {
            return true;
        }
        if (t.equals("false")) {
            return false;
        }
        throw new ArchivoInvalidoException("Linea " + nroLinea + ": viva tiene que ser true o false");
    }

    private String nombre(String texto, int nroLinea) throws ArchivoInvalidoException {
        if (texto.trim().isEmpty()) {
            throw new ArchivoInvalidoException("Linea " + nroLinea + ": falta el nombre");
        }
        return texto.trim();
    }

    private void exigirCampos(String[] campos, int esperados, int nroLinea) throws ArchivoInvalidoException {
        if (campos.length != esperados) {
            throw new ArchivoInvalidoException("Linea " + nroLinea + ": cantidad de campos incorrecta");
        }
    }

    // Error interno de formato; cargar() lo convierte en un ResultadoCarga con fallo
    private static class ArchivoInvalidoException extends Exception {

        ArchivoInvalidoException(String mensaje) {
            super(mensaje);
        }
    }

    private void escribir(Snapshot s, BufferedWriter out) throws IOException {
        linea(out, "SIMULACION_ECOSISTEMA 1");
        linea(out, "clima=" + s.getClima().name());
        linea(out, "turnoActual=" + s.getTurnoActual());
        linea(out, "turnosTotales=" + s.getTurnosTotales());
        linea(out, "totalLobosCreados=" + s.getTotalLobosCreados());
        linea(out, "nacimientosPlantas=" + s.getNacimientosPlantas());
        linea(out, "nacimientosConejos=" + s.getNacimientosConejos());
        linea(out, "muertesPlantas=" + s.getMuertesPlantas());
        linea(out, "muertesConejos=" + s.getMuertesConejos());
        linea(out, "muertesLobos=" + s.getMuertesLobos());
        linea(out, "turnoMayorActividad=" + s.getTurnoMayorActividad());
        linea(out, "maxEventosTurno=" + s.getMaxEventosTurno());

        for (Planta p : s.getPlantas()) {
            linea(out, "PLANTA;" + p.getNombre() + ";" + p.getEnergia() + ";" + p.getEdad()
                    + ";" + p.isViva() + ";" + p.getTamanio());
        }
        for (Conejo c : s.getConejos()) {
            linea(out, "CONEJO;" + c.getNombre() + ";" + c.getEnergia() + ";" + c.getEdad()
                    + ";" + c.isViva() + ";" + c.getVelocidad() + ";" + c.getPeso());
        }
        for (Lobo l : s.getLobos()) {
            linea(out, "LOBO;" + l.getNombre() + ";" + l.getEnergia() + ";" + l.getEdad()
                    + ";" + l.isViva() + ";" + l.getVelocidad() + ";" + l.getPeso()
                    + ";" + l.getExitosCaza());
        }
        for (RegistroPoblacion r : s.getHistorialPoblacion()) {
            linea(out, "HISTORIAL;" + r.getTurno() + ";" + r.getPlantas()
                    + ";" + r.getConejos() + ";" + r.getLobos());
        }
        linea(out, "FIN");
    }

    private void linea(BufferedWriter out, String texto) throws IOException {
        out.write(texto);
        out.write("\n");
    }
}