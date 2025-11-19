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
        // Insertamos el presupuesto y también lo vinculamos al usuario en la tabla intermedia
        String sqlPresupuesto = "INSERT INTO Presupuesto(monto_asignado, fecha_inicio, fecha_fin, id_categoria) VALUES(?, ?, ?, ?)";
        String sqlVinculo = "INSERT INTO Presupuesto_Usuario(id_presupuesto, id_usuario) VALUES(?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        
        try {
            conn = DatabaseConnection.connect();
            conn.setAutoCommit(false); // Transacción
            
            // 1. Insertar Presupuesto
            pstmt1 = conn.prepareStatement(sqlPresupuesto, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt1.setDouble(1, p.getMontoAsignado());
            pstmt1.setString(2, p.getFechaInicio().toString());
            pstmt1.setString(3, p.getFechaFin().toString());
            pstmt1.setInt(4, p.getIdCategoria());
            pstmt1.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = pstmt1.getGeneratedKeys();
            int idPresupuesto = 0;
            if (rs.next()) {
                idPresupuesto = rs.getInt(1);
            }
            
            // 2. Vincular con Usuario
            pstmt2 = conn.prepareStatement(sqlVinculo);
            pstmt2.setInt(1, idPresupuesto);
            pstmt2.setInt(2, idUsuario);
            pstmt2.executeUpdate();
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) {}
            System.out.println("Error al crear presupuesto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstmt1 != null) pstmt1.close();
            } catch (SQLException e) {
                System.out.println("Error cerrando pstmt1: " + e.getMessage());
            }
            try {
                if (pstmt2 != null) pstmt2.close();
            } catch (SQLException e) {
                System.out.println("Error cerrando pstmt2: " + e.getMessage());
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
            SELECT p.id, p.monto_asignado, p.fecha_inicio, p.fecha_fin, p.id_categoria, c.nombre as nombre_cat,
            (
                SELECT IFNULL(SUM(g.monto), 0) 
                FROM Gasto g 
                WHERE g.id_usuario = pu.id_usuario 
                AND g.id_categoria = p.id_categoria 
                AND g.fecha >= p.fecha_inicio 
                AND g.fecha <= p.fecha_fin
            ) as total_gastado
            FROM Presupuesto p
            JOIN Presupuesto_Usuario pu ON p.id = pu.id_presupuesto
            JOIN Categoria c ON p.id_categoria = c.id
            WHERE pu.id_usuario = ?
        """;
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Presupuesto p = new Presupuesto();
                p.setId(rs.getInt("id"));
                p.setMontoAsignado(rs.getDouble("monto_asignado"));
                p.setFechaInicio(LocalDate.parse(rs.getString("fecha_inicio")));
                p.setFechaFin(LocalDate.parse(rs.getString("fecha_fin")));
                p.setIdCategoria(rs.getInt("id_categoria"));
                
                // Datos calculados
                p.setNombreCategoria(rs.getString("nombre_cat"));
                p.setMontoGastado(rs.getDouble("total_gastado"));
                
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar presupuestos: " + e.getMessage());
        }
        return lista;
    }
}
