/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.utils;

import com.mycompany.mifinanza.models.Usuario;

/**
 *
 * @author alumnadotarde
 */
public class Sesion {
    private static Usuario usuarioLogueado;

    // Guardar el usuario cuando hace login
    public static void setUsuario(Usuario usuario) {
        usuarioLogueado = usuario;
    }

    // Recuperar el usuario desde cualquier parte de la app
    public static Usuario getUsuario() {
        return usuarioLogueado;
    }
    
    // Cerrar sesión
    public static void logout() {
        usuarioLogueado = null;
    }
}
