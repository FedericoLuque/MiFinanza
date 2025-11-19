/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.database;

/**
 *
 * @author alumnadotarde
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // La URL de conexión. "jdbc:sqlite:" es el protocolo y "mifinanza.db" es el archivo.
    // Al no poner ruta absoluta, se creará en la raíz de tu proyecto NetBeans.
    private static final String URL = "jdbc:sqlite:mifinanza.db";

    /**
     * Método estático para obtener una conexión a la base de datos.
     * @return Objeto Connection o null si falla.
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("¡Conexión a SQLite establecida con éxito!");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conn;
    }
    
    // Método MAIN temporal para probar la conexión
    public static void main(String[] args) {
        connect();
    }
}