/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package simuladorecosistema;

/**
 *
 * @author felipemolina
 */
public interface Mortal {
    boolean estaVivo();

    void morir();

    double getEnergia();
    String getNombre();

    default void verificarMuerte() {
        if (estaVivo() && getEnergia() <= 0) {
            morir();
            System.out.println(getNombre() + " murio por quedarse sin energia.");
        }
    }
}
