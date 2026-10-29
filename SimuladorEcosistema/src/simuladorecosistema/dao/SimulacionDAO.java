package simuladorecosistema.dao;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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