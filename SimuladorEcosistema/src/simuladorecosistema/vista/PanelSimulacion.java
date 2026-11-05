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
import javax.swing.JOptionPane;
import simuladorecosistema.controlador.ControladorSimulacion;
import simuladorecosistema.modelo.Clima;

public class PanelSimulacion extends javax.swing.JPanel {

    private VentanaPrincipal ventana;
    private final DefaultListModel<String> modeloEventos = new DefaultListModel<>();
    private int turnoActual;
    private int turnosTotales;
    
    public PanelSimulacion() {
        initComponents();
        lstEventos.setModel(modeloEventos);
        tblEntidades.setModel(crearModeloTabla(new Object[0][]));
        setSimulacionActiva(false);
         // Carga los combos de la intervención
        String[] nombresClimas = new String[Clima.values().length];
        for (int i = 0; i < nombresClimas.length; i++) {
            nombresClimas[i] = Clima.values()[i].getNombre();
        }
        cmbClimaNuevo.setModel(new javax.swing.DefaultComboBoxModel<>(nombresClimas));
        cmbEntidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Planta", "Conejo", "Lobo"}));
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
        lblSinSimulacion = new javax.swing.JLabel();
        tabsCentro = new javax.swing.JTabbedPane();
        scrollEntidades = new javax.swing.JScrollPane();
        tblEntidades = new javax.swing.JTable();
        panelSur = new javax.swing.JPanel();
        panelAcciones = new javax.swing.JPanel();
        btnAvanzar = new javax.swing.JButton();
        btnNueva = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCargar = new javax.swing.JButton();
        panelIntervencion = new javax.swing.JPanel();
        lblIntervencion = new javax.swing.JLabel();
        cmbClimaNuevo = new javax.swing.JComboBox<>();
        btnCambiarClima = new javax.swing.JButton();
        cmbEntidad = new javax.swing.JComboBox<>();
        btnAgregar = new javax.swing.JButton();
        btnSoloAvanzar = new javax.swing.JButton();
        lblMensaje = new javax.swing.JLabel();
        scrollEventos = new javax.swing.JScrollPane();
        lstEventos = new javax.swing.JList<>();

        setLayout(new java.awt.BorderLayout());

        panelNorte.setLayout(new java.awt.GridLayout(2, 1));

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

        lblSinSimulacion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSinSimulacion.setForeground(new java.awt.Color(255, 51, 51));
        lblSinSimulacion.setText("No hay una simulación en curso. Creá una nueva o cargá la guardada");
        panelNorte.add(lblSinSimulacion);

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

        panelSur.setLayout(new java.awt.GridLayout(3, 1));

        btnAvanzar.setText("Avanzar turno");
        btnAvanzar.addActionListener(this::btnAvanzarActionPerformed);
        panelAcciones.add(btnAvanzar);

        btnNueva.setText("Nueva simulación");
        btnNueva.addActionListener(this::btnNuevaActionPerformed);
        panelAcciones.add(btnNueva);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        panelAcciones.add(btnGuardar);

        btnCargar.setText("Cargar");
        btnCargar.addActionListener(this::btnCargarActionPerformed);
        panelAcciones.add(btnCargar);

        panelSur.add(panelAcciones);

        panelIntervencion.setBorder(javax.swing.BorderFactory.createTitledBorder("Intervención"));
        panelIntervencion.setPreferredSize(new java.awt.Dimension(646, 100));

        lblIntervencion.setText("Intervención deshabilitada");
        panelIntervencion.add(lblIntervencion);

        panelIntervencion.add(cmbClimaNuevo);

        btnCambiarClima.setText("Cambiar clima");
        btnCambiarClima.addActionListener(this::btnCambiarClimaActionPerformed);
        panelIntervencion.add(btnCambiarClima);

        panelIntervencion.add(cmbEntidad);

        btnAgregar.setText("Agregar entidad");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);
        panelIntervencion.add(btnAgregar);

        btnSoloAvanzar.setText("Solo avanzar");
        btnSoloAvanzar.addActionListener(this::btnSoloAvanzarActionPerformed);
        panelIntervencion.add(btnSoloAvanzar);

        panelSur.add(panelIntervencion);
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
    }// </editor-fold>//GEN-END:initComponents

    private void btnAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanzarActionPerformed
        setIntervencionHabilitada(false);
        ventana.getControlador().avanzarTurno();
    }//GEN-LAST:event_btnAvanzarActionPerformed

    private void btnNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaActionPerformed
        ControladorSimulacion controlador = ventana.getControlador();
        if (controlador.hayEcosistema() && !controlador.estaTerminada()) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "Hay una simulación en curso. ¿Querés empezar una nueva?",
                    "Nueva simulación", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
        }
        ventana.mostrarPantalla(VentanaPrincipal.CONFIGURACION);
    }//GEN-LAST:event_btnNuevaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
         ventana.getControlador().guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
        ControladorSimulacion controlador = ventana.getControlador();
        if (controlador.hayEcosistema() && !controlador.estaTerminada()) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "Se va a reemplazar la simulación actual por la guardada. ¿Continuar?",
                    "Cargar simulación", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
        }
        controlador.cargar();
    }//GEN-LAST:event_btnCargarActionPerformed

    private void btnCambiarClimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarClimaActionPerformed
         Clima nuevo = Clima.values()[cmbClimaNuevo.getSelectedIndex()];
        if (ventana.getControlador().cambiarClima(nuevo)) {
            setIntervencionHabilitada(false);
            mostrarMensaje("Clima cambiado a " + nuevo.getNombre(), false);
        }
    }//GEN-LAST:event_btnCambiarClimaActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        String tipo = (String) cmbEntidad.getSelectedItem();
        if (ventana.getControlador().agregarEntidad(tipo.toLowerCase())) {
            setIntervencionHabilitada(false);
            mostrarMensaje("Se agregó: " + tipo, false);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnSoloAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoloAvanzarActionPerformed
        setIntervencionHabilitada(false);
        ventana.getControlador().avanzarTurno();
    }//GEN-LAST:event_btnSoloAvanzarActionPerformed

    // La ventana se la pasa a cada panel justo después de crearlo
    public void setVentana(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }

    // Actualiza los contadores y la tabla; el controlador lo llama al iniciar y después de cada turno
    public void actualizarEstado(int turno, int total, int plantas, int conejos, int lobos, String clima) {
        setSimulacionActiva(true);
        turnoActual = turno;
        turnosTotales = total;
        lblTurno.setText("Turno: " + turno + " / " + total);
        lblClima.setText("Clima: " + clima);
        lblPlantas.setText("Plantas: " + plantas);
        lblConejos.setText("Conejos: " + conejos);
        lblLobos.setText("Lobos: " + lobos);
        actualizarTabla();
    }

        // Muestra los eventos del turno que acaba de procesarse y avisa cuántos hubo
    public void mostrarEventos(ArrayList<String> eventos) {
        modeloEventos.clear();
        if (eventos.isEmpty()) {
            modeloEventos.addElement("Sin eventos relevantes en este turno.");
        }
        for (String evento : eventos) {
            modeloEventos.addElement(evento);
        }
        mostrarMensaje("Turno procesado: " + eventos.size() + " eventos", false);
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
    
    // Con simulación: se ven los datos y los botones; sin simulación: aviso y botones apagados
    public void setSimulacionActiva(boolean activa) {
        lblSinSimulacion.setVisible(!activa);
        btnAvanzar.setEnabled(activa);
        btnGuardar.setEnabled(activa);
        if (!activa) {
            turnoActual = 0;
            turnosTotales = 0;
            lblTurno.setText("Turno: -");
            lblClima.setText("Clima: -");
            lblPlantas.setText("Plantas: -");
            lblConejos.setText("Conejos: -");
            lblLobos.setText("Lobos: -");
            modeloEventos.clear();
            tblEntidades.setModel(crearModeloTabla(new Object[0][]));
            setIntervencionHabilitada(false);
        }
    }

    // La ventana lo llama cuando no hay simulación (primer arranque o archivo inexistente o dañado)
    public void mostrarSinSimulacion(String motivo) {
        setSimulacionActiva(false);
        mostrarMensaje(motivo, false);
    }

    // El controlador lo llama cada 3 turnos: se habilitan los controles de intervención
    public void ofrecerIntervencion(int turno) {
        setIntervencionHabilitada(true);
    }

    // Habilita o deshabilita los controles de intervención y lo explica en la etiqueta
    private void setIntervencionHabilitada(boolean habilitada) {
        cmbClimaNuevo.setEnabled(habilitada);
        btnCambiarClima.setEnabled(habilitada);
        cmbEntidad.setEnabled(habilitada);
        btnAgregar.setEnabled(habilitada);
        btnSoloAvanzar.setEnabled(habilitada);

        if (habilitada) {
            lblIntervencion.setText("Podés intervenir ahora: cambiá el clima, agregá una entidad o avanzá sin cambios");
        } else if (turnosTotales == 0) {
            lblIntervencion.setText("Intervención deshabilitada");
        } else {
            int cada = ControladorSimulacion.TURNOS_ENTRE_INTERVENCIONES;
            int proximo = (turnoActual / cada + 1) * cada;
            if (proximo < turnosTotales) {
                lblIntervencion.setText("Intervención deshabilitada. Próxima oportunidad: turno " + proximo);
            } else {
                lblIntervencion.setText("Intervención deshabilitada. No quedan más oportunidades");
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAvanzar;
    private javax.swing.JButton btnCambiarClima;
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNueva;
    private javax.swing.JButton btnSoloAvanzar;
    private javax.swing.JComboBox<String> cmbClimaNuevo;
    private javax.swing.JComboBox<String> cmbEntidad;
    private javax.swing.JLabel lblClima;
    private javax.swing.JLabel lblConejos;
    private javax.swing.JLabel lblIntervencion;
    private javax.swing.JLabel lblLobos;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblPlantas;
    private javax.swing.JLabel lblSinSimulacion;
    private javax.swing.JLabel lblTurno;
    private javax.swing.JList<String> lstEventos;
    private javax.swing.JPanel panelAcciones;
    private javax.swing.JPanel panelContadores;
    private javax.swing.JPanel panelIntervencion;
    private javax.swing.JPanel panelNorte;
    private javax.swing.JPanel panelSur;
    private javax.swing.JScrollPane scrollEntidades;
    private javax.swing.JScrollPane scrollEventos;
    private javax.swing.JTabbedPane tabsCentro;
    private javax.swing.JTable tblEntidades;
    // End of variables declaration//GEN-END:variables
}
