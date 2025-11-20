package com.mycompany.mifinanza.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HistorialTransaccionesView extends JFrame {
    private JTable tblTransacciones;
    private DefaultTableModel tableModel;
    private JButton btnCerrar;

    public HistorialTransaccionesView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Historial de Transacciones");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabla
        String[] columnNames = {"Fecha", "Descripción", "Monto", "Categoría", "Cuenta"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblTransacciones = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblTransacciones);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botón de cerrar
        btnCerrar = new JButton("Cerrar");
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnCerrar);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
    }

    public JTable getTblTransacciones() {
        return tblTransacciones;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getBtnCerrar() {
        return btnCerrar;
    }
}
