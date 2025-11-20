package com.mycompany.mifinanza.views;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class RegistroUsuarioView extends JDialog {
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    public RegistroUsuarioView(Frame owner) {
        super(owner, "Registro de Nuevo Usuario", true);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new MigLayout("wrap 2, fillx, insets 20", "[sg 1, 100]10[fill, sg 1]"));
        
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();
        panel.add(lblNombre);
        panel.add(txtNombre, "growx, wrap 15");

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();
        panel.add(lblEmail);
        panel.add(txtEmail, "growx, wrap 15");

        JLabel lblPassword = new JLabel("Contraseña:");
        txtPassword = new JPasswordField();
        panel.add(lblPassword);
        panel.add(txtPassword, "growx, wrap 30");

        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");
        
        panel.add(btnRegistrar, "span 2, split 2, center, sg 2");
        panel.add(btnCancelar, "sg 2");

        add(panel);
        pack();
        setLocationRelativeTo(getOwner());
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }
}
