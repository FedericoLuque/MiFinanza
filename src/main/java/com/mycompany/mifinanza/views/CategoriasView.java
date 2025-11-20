/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.mifinanza.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreeModel;

/**
 *
 * @author federico
 */
public class CategoriasView extends javax.swing.JFrame {

    private JTree treeCategorias;
    private JButton btnAnadirRaiz;
    private JButton btnAnadirSub;
    private JButton btnEliminar;
    private JButton btnRenombrar;
    private JTextField txtNombre;

    public CategoriasView() {
        initComponents();
        setTitle("Gestión de Categorías");
        setLocationRelativeTo(null);
        setSize(400, 500);
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void setTreeModel(TreeModel model) {
        treeCategorias.setModel(model);
    }

    public JTree getTreeCategorias() { return treeCategorias; }
    public JButton getBtnAnadirRaiz() { return btnAnadirRaiz; }
    public JButton getBtnAnadirSub() { return btnAnadirSub; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnRenombrar() { return btnRenombrar; }
    public JTextField getTxtNombre() { return txtNombre; }

    private void initComponents() {
        // Main content pane
        getContentPane().setLayout(new BorderLayout(10, 10));

        // --- Panel Superior (Formulario) ---
        JPanel panelFormulario = new JPanel(new BorderLayout(5, 5));
        panelFormulario.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelInput = new JPanel(new BorderLayout());
        panelInput.add(new JLabel("Nombre:"), BorderLayout.WEST);
        txtNombre = new JTextField();
        panelInput.add(txtNombre, BorderLayout.CENTER);
        
        panelFormulario.add(panelInput, BorderLayout.NORTH);

        // Panel para botones de acciones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAnadirRaiz = new JButton("Añadir Categoria");
        btnAnadirSub = new JButton("Añadir Sub");
        btnRenombrar = new JButton("Renombrar");
        btnEliminar = new JButton("Eliminar");
        
        panelBotones.add(btnAnadirRaiz);
        panelBotones.add(btnAnadirSub);
        panelBotones.add(btnRenombrar);
        panelBotones.add(btnEliminar);

        panelFormulario.add(panelBotones, BorderLayout.CENTER);
        
        getContentPane().add(panelFormulario, BorderLayout.NORTH);

        // --- Panel Central (Árbol) ---
        treeCategorias = new JTree();
        treeCategorias.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(treeCategorias);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        pack();
    }
}
