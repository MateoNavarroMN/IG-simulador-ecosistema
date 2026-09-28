package simuladorecosistema;

public abstract class Animal extends Entidad implements Mortal {
    private int velocidad;
    private double peso;
    
     public Animal(String nombre, double energia, int edad, boolean viva, int velocidad, double peso) {
        super(nombre, energia, edad, viva);
        this.setVelocidad(velocidad);
        this.setPeso(peso);
    }
     
    public abstract void comer(Ecosistema eco);

    public void moverse() {
        if (this.isViva()) {
            System.out.println(this.getNombre() + " se desplazo por el ecosistema (velocidad: " + velocidad + ").");
        }
    }
    
    // Implementación de la interface Mortal
    @Override
    public boolean estaVivo() {
        return this.isViva();
    }

    @Override
    public void morir() {
        this.setViva(false);
        this.setEnergia(0);
    }

    // Getters y Setters con validación
    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = Math.max(1, velocidad);
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = Math.max(0.5, peso);
    }
}
