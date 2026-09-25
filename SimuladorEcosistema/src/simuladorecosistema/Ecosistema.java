/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simuladorecosistema;

/**
 *
 * @author felipemolina
 */
public class Ecosistema {
     // Esqueleto temporal para que Planta compile sin errores.
    
    public boolean climaPermiteReproduccionPlanta() {
        return true; 
    }

    public double getMultiplicadorReproduccionPlanta() {
        return 1.0;
    }

    public void agregarPlantaHija(Planta nuevaPlanta) {
        // Se implementará en la etapa de Ecosistema
    }

    public void registrarEvento(String evento) {
        System.out.println(evento);
    }
}
