/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simuladorecosistema;

/**
 *
 * @author felipemolina
 */
public class Planta extends Entidad implements Reproducible, Mortal {

    private int tamanio; // Rango: 1 a 5
    private static int contadorPlantas = 1;
    private static final double ENERGIA_MINIMA_REPRODUCCION = 50.0;
    private static final double COSTO_REPRODUCCION = 20.0;
    private static final double ENERGIA_PLANTA_HIJA = 40.0;

    public Planta(String nombre, double energia, int edad, boolean viva, int tamanio) {
        super(nombre, energia, edad, viva);
        this.setTamanio(tamanio);
    }

    // Constructor simplificado útil para crear plantas nuevas rápido
    public Planta(String nombre, double energia, int tamanio) {
        this(nombre, energia, 0, true, tamanio);
    }

    @Override
    public void actuar(Ecosistema eco) {
        if (!isViva()) {
            return;
        }
        // Las plantas ganan un poco de energía por fotosíntesis/clima y luego intentan reproducirse
        double factorClima = eco.getMultiplicadorReproduccionPlanta();
        this.setEnergia(this.getEnergia() + (10.0 * factorClima));

        if (eco.climaPermiteReproduccionPlanta()) {
            intentarReproduccion(eco);
        }
    }

    @Override
    public boolean puedeReproducirse() {
        return this.isViva() && this.getEnergia() >= ENERGIA_MINIMA_REPRODUCCION;
    }

    @Override
    public void reproducirse(Ecosistema eco) {
        // Descuenta energía a la planta madre
        this.setEnergia(this.getEnergia() - COSTO_REPRODUCCION);

        // Crea la nueva planta
        String nuevoNombre = "Helecho-" + (++contadorPlantas);
        int nuevoTamanio = (int) (Math.random() * 5) + 1;
        Planta nuevaPlanta = new Planta(nuevoNombre, ENERGIA_PLANTA_HIJA, 0, true, nuevoTamanio);

        eco.agregarPlantaHija(nuevaPlanta);
        eco.registrarEvento("Planta '" + this.getNombre() + "' se reprodujo -> nueva planta '" 
                + nuevoNombre + "' (energia: " + (int) ENERGIA_PLANTA_HIJA + ")");
    }

    // Reduce energía al mínimo (0) y retorna el valor nutritivo (tamanio * 10)
    public double serComida() {
        double valorNutritivo = this.tamanio * 10.0;
        this.setEnergia(0);
        this.morir();
        return valorNutritivo;
    }

    @Override
    public void mostrarEstado() {
        System.out.println("Planta [" + getNombre() + "] | Tamaño: " + tamanio 
                + " | Energía: " + (int) getEnergia() + " | Viva: " + (isViva() ? "Sí" : "No"));
    }

    // Implementación de Mortal
    @Override
    public boolean estaVivo() {
        return isViva();
    }

    @Override
    public void morir() {
        setViva(false);
        setEnergia(0);
    }

    // Getter y Setter de tamanio con validación (1 a 5)
    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        if (tamanio < 1) {
            this.tamanio = 1;
        } else if (tamanio > 5) {
            this.tamanio = 5;
        } else {
            this.tamanio = tamanio;
        }
    }
}
