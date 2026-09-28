package simuladorecosistema;

public class Lobo extends Animal {
    private int exitosCaza;
    private static final double GANANCIA_POR_CAZA = 30.0;
    private static final double COSTO_INTENTO_FALLIDO = 8.0;
    
    public Lobo(String nombre, double energia, int edad, boolean viva, int velocidad, double peso) {
        super(nombre, energia, edad, viva, velocidad, peso);
        this.exitosCaza = 0;
    }

    // Constructor abreviado
    public Lobo(String nombre, double energia) {
        this(nombre, energia, 0, true, 25, 35.0);
    }
    
    @Override
    public void actuar(Ecosistema eco) {
        if (!this.isViva()) {
            return;
        }

        // Efecto del clima sobre el lobo
        double ajusteClima = eco.getClimaActual().getCambioEnergiaLobo();
        if (ajusteClima != 0) {
            this.setEnergia(this.getEnergia() + ajusteClima);
        }

        if (this.isViva()) {
            this.comer(eco);
        }
    }

    @Override
    public void comer(Ecosistema eco) {
        Conejo presa = eco.obtenerConejoVivoAleatorio();

        if (presa == null) {
            eco.registrarEvento("Lobo '" + this.getNombre() + "' busco presas pero no quedan conejos vivos.");
            return;
        }

        // La probabilidad aumenta dinamicamente con la energia del lobo (entre 15% y 80% base)
        double probBase = Math.min(0.80, Math.max(0.15, this.getEnergia() / 100.0));
        double probFinal = Math.min(0.95, probBase + eco.getClimaActual().getBonusExitoCazaLobo());

        if (Math.random() < probFinal) {
            presa.morir();
            this.setEnergia(this.getEnergia() + GANANCIA_POR_CAZA);
            this.exitosCaza++;
            eco.registrarEvento("Lobo '" + this.getNombre() + "' cazo a Conejo '" + presa.getNombre() 
                    + "' (+" + (int) GANANCIA_POR_CAZA + " energia) [cacerias: " + this.exitosCaza + "]");
        } else {
            this.setEnergia(this.getEnergia() - COSTO_INTENTO_FALLIDO);
            eco.registrarEvento("Lobo '" + this.getNombre() + "' fallo la caza");
        }
    }

    @Override
    public void mostrarEstado() {
        System.out.println("Lobo [" + getNombre() + "] | Energia: " + (int) getEnergia() 
                + " | Cacerias exitosas: " + exitosCaza + " | Vivo: " + (isViva() ? "Si" : "No"));
    }

    public int getExitosCaza() {
        return exitosCaza;
    }

    public void setExitosCaza(int exitosCaza) {
        this.exitosCaza = Math.max(0, exitosCaza);
    }
}