package simuladorecosistema;

public class Ecosistema {
    // Esqueleto temporal para que Planta, Conejo y Lobo compile sin errores.
    // Implementar las listas y el flujo de turnos.
    
    private Clima climaActual = Clima.SOLEADO;
    
    public Clima getClimaActual() {
        return climaActual;
    }
    
    public boolean climaPermiteReproduccionPlanta() {
        return climaActual.permiteReproduccionPlanta();
    }

    public double getMultiplicadorReproduccionPlanta() {
        return climaActual.getMultiplicadorReproduccionPlanta();
    }

    public void agregarPlantaHija(Planta nuevaPlanta) {
        // Se implementará en la etapa de Ecosistema
    }

    public void agregarConejoHijo(Conejo nuevoConejo) {
        // Se implementará en la etapa de Ecosistema
    }

    public Planta buscarPlantaViva() {
        return null; // Buscará en el ArrayList<Planta>
    }

    public boolean hayOtroConejoVivo(Conejo actual) {
        return false; // Verificará en el ArrayList<Conejo>
    }

    public Conejo obtenerConejoVivoAleatorio() {
        return null; // Seleccionará del ArrayList<Conejo>
    }
    
    public void registrarEvento(String evento) {
        System.out.println(evento);
    }
}
