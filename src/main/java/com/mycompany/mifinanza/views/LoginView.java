package com.mycompany.mifinanza.views;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class LoginView extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnNuevoUsuario;

    public LoginView() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Iniciar Sesión");
        setResizable(false);

        JPanel panel = new JPanel(new MigLayout("wrap 2, fillx, insets 35 45 35 45", "[sg 1, 100]10[fill, sg 1]"));
        panel.setBackground(new Color(245, 245, 245));
        
        JLabel lblTitulo = new JLabel("Bienvenido a MiFinanza");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, "span 2, growx, wrap 30");

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();
        panel.add(lblEmail);
        panel.add(txtEmail, "growx, wrap 15");

        JLabel lblPassword = new JLabel("Contraseña:");
        txtPassword = new JPasswordField();
        panel.add(lblPassword);
        panel.add(txtPassword, "growx, wrap 30");

        btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(new Color(59, 89, 182));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(btnLogin, "span 2, split 2, center, sg 2");

        btnNuevoUsuario = new JButton("Crear cuenta nueva");
        btnNuevoUsuario.setBackground(new Color(66, 183, 42));
        btnNuevoUsuario.setForeground(Color.WHITE);
        btnNuevoUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(btnNuevoUsuario, "sg 2");

        add(panel);
        pack();
        setLocationRelativeTo(null); 
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JButton getBtnNuevoUsuario() {
        return btnNuevoUsuario;
    }
}

