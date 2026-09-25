/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package simuladorecosistema;

/**
 *
 * @author felipemolina
 */
public interface Reproducible {
    boolean puedeReproducirse();

    void reproducirse(Ecosistema eco);

    default void intentarReproduccion(Ecosistema eco) {
        if (puedeReproducirse()) {
            reproducirse(eco);
        }
    }
}
