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
        java.awt.GridBagConstraints gridBagConstraints;

        lblTitulo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtLobos = new javax.swing.JTextField();
        txtConejos = new javax.swing.JTextField();
        lblLobos = new javax.swing.JLabel();
        lblConejos = new javax.swing.JLabel();
        txtPlantas = new javax.swing.JTextField();
        lblTurnos = new javax.swing.JLabel();
        cmbClima = new javax.swing.JComboBox<>();
        lblPlantas = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnIniciar = new javax.swing.JButton();
        lblErrores = new javax.swing.JLabel();
        txtTurnos = new javax.swing.JTextField();
        lblClima = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Configuración de la simulación");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 249;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        add(lblTitulo, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 51;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 51, 0, 0);
        jPanel2.add(txtLobos, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 51;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 51, 0, 0);
        jPanel2.add(txtConejos, gridBagConstraints);

        lblLobos.setText("Lobos (1 - 5)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 6, 0, 0);
        jPanel2.add(lblLobos, gridBagConstraints);

        lblConejos.setText("Conejos (2 - 15)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 6, 0, 0);
        jPanel2.add(lblConejos, gridBagConstraints);

        txtPlantas.addActionListener(this::txtPlantasActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 51;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 51, 0, 0);
        jPanel2.add(txtPlantas, gridBagConstraints);

        lblTurnos.setText("Turnos (10 - 50)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 6, 0, 0);
        jPanel2.add(lblTurnos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 51, 0, 0);
        jPanel2.add(cmbClima, gridBagConstraints);

        lblPlantas.setText("Plantas (5 - 30)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 6, 0, 0);
        jPanel2.add(lblPlantas, gridBagConstraints);

        btnIniciar.setText("Iniciar simulación");
        btnIniciar.addActionListener(this::btnIniciarActionPerformed);
        jPanel1.add(btnIniciar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 43, 6, 0);
        jPanel2.add(jPanel1, gridBagConstraints);

        lblErrores.setForeground(new java.awt.Color(255, 0, 0));
        lblErrores.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.ipadx = 302;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 51, 0, 0);
        jPanel2.add(lblErrores, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 51;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 51, 0, 0);
        jPanel2.add(txtTurnos, gridBagConstraints);

        lblClima.setText("Clima inicial");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 6, 0, 0);
        jPanel2.add(lblClima, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 159;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 6, 0);
        add(jPanel2, gridBagConstraints);
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
    private javax.swing.JPanel jPanel2;
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
