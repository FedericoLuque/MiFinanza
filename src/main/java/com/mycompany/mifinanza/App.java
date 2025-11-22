/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza;

/**
 *
 * @author alumnadotarde
 */

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.mifinanza.controllers.LoginController;
import com.mycompany.mifinanza.dao.UsuarioDAO;
import com.mycompany.mifinanza.views.LoginView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {

    public static void main(String[] args) {
        // 1. Configurar el tema visual (FlatLaf) para que se vea moderno
        try {
            // Puedes cambiar FlatLightLaf por FlatDarkLaf si prefieres modo oscuro
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Error al iniciar el tema visual");
        }

        // 2. Iniciar la aplicación con el patrón MVC
        SwingUtilities.invokeLater(() -> {
            // Crear instancias del Modelo, Vista y Controlador
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView, usuarioDAO);

            // Iniciar el controlador para configurar los listeners
            loginController.initController();
            
            // Centrar y mostrar la vista
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
        });
    }
}