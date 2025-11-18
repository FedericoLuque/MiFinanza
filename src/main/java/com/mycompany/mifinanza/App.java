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
import com.mycompany.mifinanza.views.MainView;
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

        // 2. Iniciar la ventana principal
        SwingUtilities.invokeLater(() -> {
            MainView view = new MainView();
            // Centrar la ventana en la pantalla
            view.setLocationRelativeTo(null);
            view.setVisible(true);
        });
    }
}