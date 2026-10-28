package simuladorecosistema.modelo;

public class Conejo extends Animal implements Reproducible {
    private static int contadorConejos = 1;
    private static final double PERDIDA_POR_HAMBRE = 15.0;
    private static final double UMBRAL_PELIGRO = 20.0;
    private static final double ENERGIA_MIN_REPRODUCCION = 60.0;
    private static final double COSTO_REPRODUCCION = 25.0;
    private static final double ENERGIA_CRIA = 45.0;
    
    private Ecosistema ecosistemaRef;
    
    public Conejo(String nombre, double energia, int edad, boolean viva, int velocidad, double peso) {
        super(nombre, energia, edad, viva, velocidad, peso);
    }
    
    // Evita que las crias nuevas repitan el numero de un conejo restaurado
    public static void sincronizarContador(int minimo) {
        if (minimo > contadorConejos) {
            contadorConejos = minimo;
        }
    }

    // Constructor abreviado para nacimientos o creacion rapida
    public Conejo(String nombre, double energia) {
        this(nombre, energia, 0, true, 12, 2.5);
    }
    
    @Override
    public void actuar(Ecosistema eco) {
        if (!this.isViva()) {
            return;
        }
        this.ecosistemaRef = eco;

        // Efecto del clima sobre la energia base del conejo
        double ajusteClima = eco.getClimaActual().getCambioEnergiaConejo();
        if (ajusteClima != 0) {
            this.setEnergia(this.getEnergia() + ajusteClima);
        }

        // Llama a comer()
        this.comer(eco);

        // Intenta reproducirse si sigue con vida
        if (this.isViva()) {
            this.intentarReproduccion(eco);
        }
    }
    
    @Override
    public void comer(Ecosistema eco) {
        if (!this.isViva()) {
            return;
        }

        Planta plantaEncontrada = eco.buscarPlantaViva();
        if (plantaEncontrada != null) {
            double ganancia = plantaEncontrada.serComida();
            this.setEnergia(this.getEnergia() + ganancia);

            if (ganancia >= 0) {
                eco.registrarEvento("COMIDA: " + this.getNombre() + " comio " 
                        + plantaEncontrada.getNombre() + " (+" + (int) ganancia + " de energia)");
            } else {
                eco.registrarEvento("COMIDA: " + this.getNombre() + " comio la planta venenosa " 
                        + plantaEncontrada.getNombre() + " (" + (int) ganancia + " de energia)");
            }
        } else {
            this.setEnergia(this.getEnergia() - PERDIDA_POR_HAMBRE);
            String alertaPeligro = (this.getEnergia() > 0 && this.getEnergia() < UMBRAL_PELIGRO)
                    ? " [PELIGRO: energia=" + (int) this.getEnergia() + "]"
                    : "";
            eco.registrarEvento("HAMBRE: " + this.getNombre() + " no encontro plantas (-" 
                    + (int) PERDIDA_POR_HAMBRE + " de energia)" + alertaPeligro);
        }
    }

    @Override
    public boolean puedeReproducirse() {
        return this.isViva() 
                && this.getEnergia() > ENERGIA_MIN_REPRODUCCION 
                && ecosistemaRef != null 
                && ecosistemaRef.hayOtroConejoVivo(this);
    }

    @Override
    public void reproducirse(Ecosistema eco) {
        this.setEnergia(this.getEnergia() - COSTO_REPRODUCCION);
        String nombreCria = "Conejo-" + (++contadorConejos);
        Conejo cria = new Conejo(nombreCria, ENERGIA_CRIA);

        eco.agregarConejoHijo(cria);
        eco.registrarEvento("NACIMIENTO: " + nombreCria + " nacio de " + this.getNombre()
                + " (energia inicial: " + (int) ENERGIA_CRIA + ")");
    }

    @Override
    public String mostrarEstado() {
        String estadoPeligro = (this.isViva() && this.getEnergia() < UMBRAL_PELIGRO) ? " [EN PELIGRO]" : "";
        return "Conejo [" + getNombre() + "] | Energia: " + (int) getEnergia() 
                + estadoPeligro + " | Vivo: " + (isViva() ? "Si" : "No");
    }
}