package com.mycompany.mifinanza.controllers;

import com.mycompany.mifinanza.dao.UsuarioDAO;
import com.mycompany.mifinanza.models.Usuario;
import com.mycompany.mifinanza.views.RegistroUsuarioView;
import javax.swing.JOptionPane;

public class RegistroUsuarioController {
    
    private final RegistroUsuarioView view;
    private final UsuarioDAO usuarioDAO;

    public RegistroUsuarioController(RegistroUsuarioView view, UsuarioDAO usuarioDAO) {
        this.view = view;
        this.usuarioDAO = usuarioDAO;
    }
    
    public void initController() {
        view.getBtnRegistrar().addActionListener(e -> registrarUsuario());
        view.getBtnCancelar().addActionListener(e -> view.dispose());
    }
    
    private void registrarUsuario() {
        String nombre = view.getTxtNombre().getText();
        String email = view.getTxtEmail().getText();
        String password = new String(view.getTxtPassword().getPassword());
        
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, llena todos los campos.");
            return;
        }
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(password);
        
        if (usuarioDAO.registrar(nuevoUsuario)) {
            JOptionPane.showMessageDialog(view, "Usuario creado exitosamente.");
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Error al crear el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
