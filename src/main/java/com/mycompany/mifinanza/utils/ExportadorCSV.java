/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.utils;

import com.mycompany.mifinanza.database.DatabaseConnection;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author federico
 */
public class ExportadorCSV {
    
     public static void exportarHistorial(int idUsuario) {
        // 1. Preguntar dónde guardar el archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte CSV");
        fileChooser.setSelectedFile(new java.io.File("reporte_financiero_" + LocalDate.now() + ".csv"));
        
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            // Asegurar extensión .csv
            if (!rutaArchivo.toLowerCase().endsWith(".csv")) {
                rutaArchivo += ".csv";
            }
            
            generarArchivo(idUsuario, rutaArchivo);
        }
    }

    private static void generarArchivo(int idUsuario, String ruta) {
        String sql = """
            SELECT 'GASTO' as tipo, fecha, monto, descripcion, comercio as entidad 
            FROM Gasto WHERE id_usuario = ?
            UNION ALL
            SELECT 'INGRESO' as tipo, fecha, monto, descripcion, fuente as entidad 
            FROM Ingreso WHERE id_usuario = ?
            ORDER BY fecha DESC
        """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             FileWriter fw = new FileWriter(ruta);
             PrintWriter pw = new PrintWriter(fw)) {

            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idUsuario);
            
            ResultSet rs = pstmt.executeQuery();

            // Escribir Encabezados CSV
            pw.println("TIPO,FECHA,MONTO,DESCRIPCION,ENTIDAD");

            // Escribir filas
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String fecha = rs.getString("fecha");
                double monto = rs.getDouble("monto");
                // Escapar comas en descripciones para no romper el CSV
                String desc = rs.getString("descripcion").replace(",", " "); 
                String entidad = rs.getString("entidad").replace(",", " ");

                pw.printf("%s,%s,%.2f,%s,%s%n", tipo, fecha, monto, desc, entidad);
            }

            JOptionPane.showMessageDialog(null, "¡Exportación exitosa!\nArchivo guardado en: " + ruta);

        } catch (IOException | java.sql.SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al exportar: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
