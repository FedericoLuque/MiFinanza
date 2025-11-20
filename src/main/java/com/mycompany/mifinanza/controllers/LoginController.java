package com.mycompany.mifinanza.controllers;

import com.mycompany.mifinanza.dao.UsuarioDAO;
import com.mycompany.mifinanza.models.Usuario;
import com.mycompany.mifinanza.utils.Sesion;
import com.mycompany.mifinanza.views.LoginView;
import com.mycompany.mifinanza.views.MainView;
import com.mycompany.mifinanza.views.RegistroUsuarioView;
import javax.swing.JOptionPane;

public class LoginController {

    private final LoginView view;
    private final UsuarioDAO usuarioDAO;

    public LoginController(LoginView view, UsuarioDAO usuarioDAO) {
        this.view = view;
        this.usuarioDAO = usuarioDAO;
    }

    public void initController() {
        view.getBtnLogin().addActionListener(e -> login());
        view.getBtnNuevoUsuario().addActionListener(e -> openRegistroUsuario());
    }

    private void login() {
        String email = view.getTxtEmail().getText();
        String password = new String(view.getTxtPassword().getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, llena todos los campos.");
            return;
        }

        Usuario usuario = usuarioDAO.login(email, password);

        if (usuario != null) {
            Sesion.setUsuario(usuario);
            JOptionPane.showMessageDialog(view, "¡Bienvenido " + usuario.getNombre() + "!");
            
            // Abrir la ventana principal
            MainView mainView = new MainView();
            mainView.setLocationRelativeTo(null);
            mainView.setVisible(true);
            
            // Cerrar esta ventana de login
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Email o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openRegistroUsuario() {
        RegistroUsuarioView registroView = new RegistroUsuarioView(view);
        RegistroUsuarioController registroController = new RegistroUsuarioController(registroView, usuarioDAO);
        registroController.initController();
        registroView.setVisible(true);
    }
}
