/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.dao;

/**
 *
 * @author alumnadotarde
 */

import com.mycompany.mifinanza.database.DatabaseConnection;
import com.mycompany.mifinanza.models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Registrar un nuevo usuario
    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO Usuario(nombre, email, password) VALUES(?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPassword()); // OJO: Aquí deberíamos encriptar en el futuro
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    // Validar login
    public Usuario login(String email, String password) {
        String sql = "SELECT * FROM Usuario WHERE email = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("password")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return null; // Retorna null si falla el login
    }
    
    // ACTUALIZAR CONTRASEÑA
    public boolean cambiarPassword(int idUsuario, String nuevaPassword) {
        String sql = "UPDATE Usuario SET password = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nuevaPassword);
            pstmt.setInt(2, idUsuario);
            
            int filas = pstmt.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al cambiar contraseña: " + e.getMessage());
            return false;
        }
    }
}