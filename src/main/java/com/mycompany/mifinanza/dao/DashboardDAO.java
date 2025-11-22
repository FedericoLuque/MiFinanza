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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DashboardDAO {

    // Para el Gráfico de Pastel: Gastos por Categoría
    public Map<String, Double> obtenerGastosPorCategoria(int idUsuario) {
        Map<String, Double> datos = new HashMap<>();
        
        String sql = """
            SELECT c.nombre, SUM(g.monto) as total
            FROM Gasto g
            JOIN Categoria c ON g.id_categoria = c.id
            WHERE g.id_usuario = ?
            GROUP BY c.nombre
        """;
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                datos.put(rs.getString("nombre"), rs.getDouble("total"));
            }
            
        } catch (SQLException e) {
            System.out.println("Error dashboard categorías: " + e.getMessage());
        }
        return datos;
    }

    // Para el Gráfico de Barras: Ingresos vs Gastos
    // Retorna un array donde [0] = Total Ingresos, [1] = Total Gastos
    public double[] obtenerBalanceTotal(int idUsuario) {
        double[] balance = {0.0, 0.0};
        
        String sqlIngresos = "SELECT SUM(monto) FROM Ingreso WHERE id_usuario = ?";
        String sqlGastos = "SELECT SUM(monto) FROM Gasto WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConnection.connect()) {
            
            // 1. Sumar Ingresos
            try (PreparedStatement pstmt = conn.prepareStatement(sqlIngresos)) {
                pstmt.setInt(1, idUsuario);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    balance[0] = rs.getDouble(1);
                }
            }
            
            // 2. Sumar Gastos
            try (PreparedStatement pstmt = conn.prepareStatement(sqlGastos)) {
                pstmt.setInt(1, idUsuario);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    balance[1] = rs.getDouble(1);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error dashboard balance: " + e.getMessage());
        }
        return balance;
    }
}
