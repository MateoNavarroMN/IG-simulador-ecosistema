/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simuladorecosistema;

/**
 *
 * @author felipemolina
 */
public abstract class Entidad {

    private String nombre;
    private double energia;
    private int edad;
    private boolean viva;

    private static final double COSTO_ENERGIA_BASE = 5.0;

    public Entidad(String nombre, double energia, int edad, boolean viva) {
        this.nombre = (nombre != null && !nombre.trim().isEmpty()) ? nombre : "Entidad-SinNombre";
        this.setEnergia(energia); // Usa el setter con validación
        this.setEdad(edad);
        this.viva = viva;
    }

    // Métodos abstractos obligatorios
    public abstract void actuar(Ecosistema eco);

    public abstract void mostrarEstado();

    // Método concreto: incrementa edad y descuenta energía base por existir
    public void envejecer() {
        if (this.viva) {
            this.edad++;
            this.setEnergia(this.energia - COSTO_ENERGIA_BASE);
            if (this.energia <= 0) {
                this.viva = false;
            }
        }
    }

    // Getters y Setters con validaciones (encapsulamiento completo)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
    }

    public double getEnergia() {
        return energia;
    }

    public void setEnergia(double energia) {
        // Validación obligatoria: la energía no puede ser negativa, se lleva a 0
        if (energia < 0) {
            this.energia = 0;
        } else {
            this.energia = energia;
        }
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = Math.max(0, edad);
    }

    public boolean isViva() {
        return viva;
    }

    public void setViva(boolean viva) {
        this.viva = viva;
    }
}
    

