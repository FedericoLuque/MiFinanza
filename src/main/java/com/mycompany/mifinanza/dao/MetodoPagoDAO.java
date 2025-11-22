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
import com.mycompany.mifinanza.models.MetodoPago;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAO {

    public List<MetodoPago> listar() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM MetodoPago";
        
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                lista.add(new MetodoPago(
                    rs.getInt("id"),
                    rs.getString("nombre_metodo")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al listar métodos de pago: " + e.getMessage());
        }
        return lista;
    }
}
