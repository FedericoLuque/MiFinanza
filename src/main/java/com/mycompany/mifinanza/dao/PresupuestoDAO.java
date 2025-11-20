/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.dao;

/**
 *
 * @author federico
 */

import com.mycompany.mifinanza.database.DatabaseConnection;
import com.mycompany.mifinanza.models.Presupuesto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PresupuestoDAO {

    public boolean insertar(Presupuesto p, int idUsuario) {
        String sqlPresupuesto = "INSERT INTO Presupuesto(monto_total, fecha_inicio, fecha_fin, id_categoria, id_usuario) VALUES(?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        
        try {
            conn = DatabaseConnection.connect();
            
            // 1. Insertar Presupuesto
            pstmt1 = conn.prepareStatement(sqlPresupuesto);
            pstmt1.setDouble(1, p.getMontoTotal());
            pstmt1.setString(2, p.getFechaInicio().toString());
            pstmt1.setString(3, p.getFechaFin().toString());
            pstmt1.setInt(4, p.getIdCategoria());
            pstmt1.setInt(5, idUsuario);
            pstmt1.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al crear presupuesto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstmt1 != null) pstmt1.close();
            } catch (SQLException e) {
                System.out.println("Error cerrando pstmt1: " + e.getMessage());
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    // LISTAR PRESUPUESTOS CON PROGRESO
    public List<Presupuesto> listarConProgreso(int idUsuario) {
        List<Presupuesto> lista = new ArrayList<>();
        
        String sql = """
            SELECT p.id, p.monto_total, p.monto_actual, p.fecha_inicio, p.fecha_fin, p.id_categoria, c.nombre as nombre_cat
            FROM Presupuesto p
            JOIN Categoria c ON p.id_categoria = c.id
            WHERE p.id_usuario = ?
            ORDER BY p.fecha_fin DESC
        """;
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Presupuesto p = new Presupuesto();
                p.setId(rs.getInt("id"));
                p.setMontoTotal(rs.getDouble("monto_total"));
                p.setMontoActual(rs.getDouble("monto_actual"));
                p.setFechaInicio(LocalDate.parse(rs.getString("fecha_inicio")));
                p.setFechaFin(LocalDate.parse(rs.getString("fecha_fin")));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setNombreCategoria(rs.getString("nombre_cat"));
                
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar presupuestos: " + e.getMessage());
        }
        return lista;
    }
    public void actualizarMontoActual(Connection conn, int idCategoria, double montoGastado) throws SQLException {
        String sql = """
            UPDATE Presupuesto
            SET monto_actual = monto_actual + ?
            WHERE id_categoria = ? AND fecha_inicio <= CURRENT_DATE AND fecha_fin >= CURRENT_DATE
        """;
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, montoGastado);
            pstmt.setInt(2, idCategoria);
            pstmt.executeUpdate();
        }
    }
}
