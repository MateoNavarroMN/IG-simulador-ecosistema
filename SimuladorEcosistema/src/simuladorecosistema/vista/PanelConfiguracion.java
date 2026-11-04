/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package simuladorecosistema.vista;

import simuladorecosistema.modelo.Clima;
/**
 *
 * @author Agos
 */
public class PanelConfiguracion extends javax.swing.JPanel {
    
    private VentanaPrincipal ventana;
  
    public PanelConfiguracion() {
        initComponents();
        // Carga el combo de climas: la primera opción es "sin elegir"
        javax.swing.DefaultComboBoxModel<String> modeloClimas = new javax.swing.DefaultComboBoxModel<>();
        modeloClimas.addElement("Elegí un clima");
        for (Clima clima : Clima.values()) {
            modeloClimas.addElement(clima.getNombre());
        }
        cmbClima.setModel(modeloClimas);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblPlantas = new javax.swing.JLabel();
        txtPlantas = new javax.swing.JTextField();
        lblConejos = new javax.swing.JLabel();
        txtConejos = new javax.swing.JTextField();
        lblLobos = new javax.swing.JLabel();
        txtLobos = new javax.swing.JTextField();
        lblTurnos = new javax.swing.JLabel();
        txtTurnos = new javax.swing.JTextField();
        lblClima = new javax.swing.JLabel();
        cmbClima = new javax.swing.JComboBox<>();
        lblErrores = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnIniciar = new javax.swing.JButton();

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Configuración de la simulación");

        lblPlantas.setText("Plantas (5 - 30)");

        txtPlantas.addActionListener(this::txtPlantasActionPerformed);

        lblConejos.setText("Conejos (2 - 15)");

        lblLobos.setText("Lobos (1 - 5)");

        lblTurnos.setText("Turnos (10 - 50)");

        lblClima.setText("Clima inicial");

        lblErrores.setForeground(new java.awt.Color(255, 0, 0));
        lblErrores.setText(" ");

        btnIniciar.setText("Iniciar simulación");
        btnIniciar.addActionListener(this::btnIniciarActionPerformed);
        jPanel1.add(btnIniciar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblConejos)
                    .addComponent(lblPlantas)
                    .addComponent(lblLobos)
                    .addComponent(lblTurnos)
                    .addComponent(lblClima))
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblErrores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbClima, javax.swing.GroupLayout.Alignment.LEADING, 0, 115, Short.MAX_VALUE)
                            .addComponent(txtTurnos, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLobos, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtConejos, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPlantas, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 31, Short.MAX_VALUE)))
                .addGap(94, 94, 94))
            .addGroup(layout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlantas)
                    .addComponent(txtPlantas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtConejos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConejos))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLobos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLobos))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTurnos)
                    .addComponent(txtTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClima)
                    .addComponent(cmbClima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblErrores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtPlantasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlantasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlantasActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
                int indice = cmbClima.getSelectedIndex();
        Clima clima = (indice <= 0) ? null : Clima.values()[indice - 1];

        limpiarErrores();
        ventana.getControlador().iniciarSimulacion(txtPlantas.getText(), txtConejos.getText(),
                txtLobos.getText(), txtTurnos.getText(), clima);
    }//GEN-LAST:event_btnIniciarActionPerformed

     // La ventana se la pasa a cada panel justo después de crearlo
    public void setVentana(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }

    // Muestra los errores del controlador (uno por línea)
    public void mostrarErrores(String mensaje) {
        lblErrores.setText("<html>" + mensaje.replace("\n", "<br>") + "</html>");
    }

    public void limpiarErrores() {
        lblErrores.setText("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JComboBox<String> cmbClima;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblClima;
    private javax.swing.JLabel lblConejos;
    private javax.swing.JLabel lblErrores;
    private javax.swing.JLabel lblLobos;
    private javax.swing.JLabel lblPlantas;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTurnos;
    private javax.swing.JTextField txtConejos;
    private javax.swing.JTextField txtLobos;
    private javax.swing.JTextField txtPlantas;
    private javax.swing.JTextField txtTurnos;
    // End of variables declaration//GEN-END:variables
}
