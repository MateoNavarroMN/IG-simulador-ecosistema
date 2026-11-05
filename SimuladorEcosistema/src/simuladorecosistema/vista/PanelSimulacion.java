/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package simuladorecosistema.vista;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import simuladorecosistema.modelo.Conejo;
import simuladorecosistema.modelo.Lobo;
import simuladorecosistema.modelo.Planta;
import simuladorecosistema.modelo.Snapshot;

public class PanelSimulacion extends javax.swing.JPanel {

    private VentanaPrincipal ventana;
    private final DefaultListModel<String> modeloEventos = new DefaultListModel<>();
    private int turnoActual;
    private int turnosTotales;
    
    public PanelSimulacion() {
        initComponents();
        lstEventos.setModel(modeloEventos);
        tblEntidades.setModel(crearModeloTabla(new Object[0][]));
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelNorte = new javax.swing.JPanel();
        panelContadores = new javax.swing.JPanel();
        lblTurno = new javax.swing.JLabel();
        lblClima = new javax.swing.JLabel();
        lblPlantas = new javax.swing.JLabel();
        lblConejos = new javax.swing.JLabel();
        lblLobos = new javax.swing.JLabel();
        tabsCentro = new javax.swing.JTabbedPane();
        scrollEntidades = new javax.swing.JScrollPane();
        tblEntidades = new javax.swing.JTable();
        panelSur = new javax.swing.JPanel();
        panelAcciones = new javax.swing.JPanel();
        btnAvanzar = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();
        scrollEventos = new javax.swing.JScrollPane();
        lstEventos = new javax.swing.JList<>();

        setLayout(new java.awt.BorderLayout());

        panelNorte.setLayout(new java.awt.GridLayout());

        panelContadores.setLayout(new java.awt.GridLayout(1, 5));

        lblTurno.setText("Turno:");
        panelContadores.add(lblTurno);

        lblClima.setText("Clima:");
        panelContadores.add(lblClima);

        lblPlantas.setText("Plantas:");
        panelContadores.add(lblPlantas);

        lblConejos.setText("Conejos:");
        panelContadores.add(lblConejos);

        lblLobos.setText("Lobos:");
        panelContadores.add(lblLobos);

        panelNorte.add(panelContadores);

        add(panelNorte, java.awt.BorderLayout.PAGE_START);

        tblEntidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollEntidades.setViewportView(tblEntidades);

        tabsCentro.addTab("Entidades", scrollEntidades);

        add(tabsCentro, java.awt.BorderLayout.LINE_START);

        panelSur.setLayout(new java.awt.GridLayout(2, 1));

        btnAvanzar.setText("Avanzar turno");
        btnAvanzar.addActionListener(this::btnAvanzarActionPerformed);
        panelAcciones.add(btnAvanzar);

        panelSur.add(panelAcciones);
        panelSur.add(lblMensaje);

        add(panelSur, java.awt.BorderLayout.SOUTH);

        scrollEventos.setBorder(javax.swing.BorderFactory.createTitledBorder("Eventos del turno"));
        scrollEventos.setToolTipText("");
        scrollEventos.setPreferredSize(new java.awt.Dimension(320, 100));

        lstEventos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstEventos.setToolTipText("");
        scrollEventos.setViewportView(lstEventos);

        add(scrollEventos, java.awt.BorderLayout.EAST);
        scrollEventos.getAccessibleContext().setAccessibleName("Eventos del turno");
    }// </editor-fold>//GEN-END:initComponents

    private void btnAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanzarActionPerformed
            ventana.getControlador().avanzarTurno();
    }//GEN-LAST:event_btnAvanzarActionPerformed

    // La ventana se la pasa a cada panel justo después de crearlo
    public void setVentana(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }

    // Actualiza los contadores y la tabla; el controlador lo llama al iniciar y después de cada turno
    public void actualizarEstado(int turno, int total, int plantas, int conejos, int lobos, String clima) {
        turnoActual = turno;
        turnosTotales = total;
        lblTurno.setText("Turno: " + turno + " / " + total);
        lblClima.setText("Clima: " + clima);
        lblPlantas.setText("Plantas: " + plantas);
        lblConejos.setText("Conejos: " + conejos);
        lblLobos.setText("Lobos: " + lobos);
        actualizarTabla();
    }

    // Muestra los eventos del turno que acaba de procesarse
    public void mostrarEventos(ArrayList<String> eventos) {
        modeloEventos.clear();
        if (eventos.isEmpty()) {
            modeloEventos.addElement("Sin eventos relevantes en este turno.");
        }
        for (String evento : eventos) {
            modeloEventos.addElement(evento);
        }
    }

    // Mensaje de estado debajo de la tabla; en rojo si es un error
    public void mostrarMensaje(String texto, boolean error) {
        lblMensaje.setText(texto);
        lblMensaje.setForeground(error ? java.awt.Color.RED : javax.swing.UIManager.getColor("Label.foreground"));
    }

    // Carga la tabla con las entidades vivas
    private void actualizarTabla() {
        Snapshot foto = ventana.getControlador().getSnapshot();
        if (foto == null) {
            tblEntidades.setModel(crearModeloTabla(new Object[0][]));
            return;
        }
        ArrayList<Object[]> filas = new ArrayList<>();
        for (Planta p : foto.getPlantas()) {
            if (p.isViva()) {
                filas.add(new Object[]{"Planta", p.getNombre(), (int) p.getEnergia(), p.getEdad()});
            }
        }
        for (Conejo c : foto.getConejos()) {
            if (c.isViva()) {
                filas.add(new Object[]{"Conejo", c.getNombre(), (int) c.getEnergia(), c.getEdad()});
            }
        }
        for (Lobo l : foto.getLobos()) {
            if (l.isViva()) {
                filas.add(new Object[]{"Lobo", l.getNombre(), (int) l.getEnergia(), l.getEdad()});
            }
        }
        tblEntidades.setModel(crearModeloTabla(filas.toArray(new Object[0][])));
    }

    private DefaultTableModel crearModeloTabla(Object[][] filas) {
        String[] columnas = {"Tipo", "Nombre", "Energía", "Edad"};
        return new DefaultTableModel(filas, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvanzar;
    private javax.swing.JLabel lblClima;
    private javax.swing.JLabel lblConejos;
    private javax.swing.JLabel lblLobos;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblPlantas;
    private javax.swing.JLabel lblTurno;
    private javax.swing.JList<String> lstEventos;
    private javax.swing.JPanel panelAcciones;
    private javax.swing.JPanel panelContadores;
    private javax.swing.JPanel panelNorte;
    private javax.swing.JPanel panelSur;
    private javax.swing.JScrollPane scrollEntidades;
    private javax.swing.JScrollPane scrollEventos;
    private javax.swing.JTabbedPane tabsCentro;
    private javax.swing.JTable tblEntidades;
    // End of variables declaration//GEN-END:variables
}
