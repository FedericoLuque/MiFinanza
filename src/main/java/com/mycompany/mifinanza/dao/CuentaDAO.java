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
import com.mycompany.mifinanza.models.Cuenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO {

    // INSERTAR
    public boolean insertar(Cuenta cuenta) {
        String sql = "INSERT INTO Cuenta(nombre_cuenta, saldo, id_usuario) VALUES(?, 0, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cuenta.getNombre());
            pstmt.setInt(2, cuenta.getIdUsuario());
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al insertar cuenta: " + e.getMessage());
            return false;
        }
    }

    // LISTAR CUENTAS POR USUARIO CON SALDO REAL
    public List<Cuenta> listarPorUsuario(int idUsuario) {
        List<Cuenta> lista = new ArrayList<>();
        String sql = """
            SELECT 
                c.id, 
                c.nombre_cuenta, 
                c.id_usuario,
                c.saldo
            FROM Cuenta c
            WHERE c.id_usuario = ?
        """;
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lista.add(new Cuenta(
                    rs.getInt("id"),
                    rs.getString("nombre_cuenta"),
                    rs.getDouble("saldo"),
                    rs.getInt("id_usuario")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar cuentas: " + e.getMessage());
        }
        return lista;
    }
}
