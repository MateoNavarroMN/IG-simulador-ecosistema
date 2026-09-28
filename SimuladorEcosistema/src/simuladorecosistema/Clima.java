package simuladorecosistema;

public enum Clima {
    SOLEADO("Soleado", 1.5, 5.0, 0.0, 0.0),
    LLUVIOSO("Lluvioso", 2.0, 3.0, -5.0, 0.0),
    SEQUIA("Sequia", 0.5, -5.0, 0.0, 0.0),
    INVIERNO("Invierno", 0.0, -8.0, 0.0, 0.20);
 
    private final String nombre;
    private final double multiplicadorReproduccionPlanta;
    private final double cambioEnergiaConejo;
    private final double cambioEnergiaLobo;
    private final double bonusExitoCazaLobo;
    
    Clima(String nombre, double multPlanta, double energiaConejo, double energiaLobo, double bonusCaza) {
        this.nombre = nombre;
        this.multiplicadorReproduccionPlanta = multPlanta;
        this.cambioEnergiaConejo = energiaConejo;
        this.cambioEnergiaLobo = energiaLobo;
        this.bonusExitoCazaLobo = bonusCaza;
    }
    
    public boolean permiteReproduccionPlanta() {
        return this != INVIERNO && multiplicadorReproduccionPlanta > 0;
    }

    public String getNombre() {
        return nombre;
    }

    public double getMultiplicadorReproduccionPlanta() {
        return multiplicadorReproduccionPlanta;
    }

    public double getCambioEnergiaConejo() {
        return cambioEnergiaConejo;
    }

    public double getCambioEnergiaLobo() {
        return cambioEnergiaLobo;
    }

    public double getBonusExitoCazaLobo() {
        return bonusExitoCazaLobo;
    }
}
