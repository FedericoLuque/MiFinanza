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
import com.mycompany.mifinanza.models.Gasto;
import com.mycompany.mifinanza.models.Ingreso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransaccionDAO {

    /**
     * Registra un gasto y descuenta el saldo de la cuenta asociada de forma atómica.
     */
    public boolean registrarGasto(Gasto gasto) {
        String sqlInsert = "INSERT INTO Gasto(monto, fecha, descripcion, comercio, id_cuenta, id_metodopago, id_categoria, id_usuario) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlUpdateCuenta = "UPDATE Cuenta SET saldo = saldo - ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmtInsert = null;
        PreparedStatement pstmtUpdate = null;

        try {
            conn = DatabaseConnection.connect();
            // 1. Desactivar auto-commit para iniciar una transacción manual
            conn.setAutoCommit(false);

            // 2. Insertar el Gasto
            pstmtInsert = conn.prepareStatement(sqlInsert);
            pstmtInsert.setDouble(1, gasto.getMonto());
            pstmtInsert.setString(2, gasto.getFecha().toString()); // Guardamos Timestamp como texto ISO para SQLite
            pstmtInsert.setString(3, gasto.getDescripcion());
            pstmtInsert.setString(4, gasto.getComercio());
            pstmtInsert.setInt(5, gasto.getIdCuenta());
            pstmtInsert.setInt(6, gasto.getIdMetodoPago());
            pstmtInsert.setInt(7, gasto.getIdCategoria());
            pstmtInsert.setInt(8, gasto.getIdUsuario());
            pstmtInsert.executeUpdate();

            // 3. Restar saldo a la Cuenta
            pstmtUpdate = conn.prepareStatement(sqlUpdateCuenta);
            pstmtUpdate.setDouble(1, gasto.getMonto());
            pstmtUpdate.setInt(2, gasto.getIdCuenta());
            pstmtUpdate.executeUpdate();

            // 4. Si todo salió bien, confirmamos los cambios (Commit)
            conn.commit();
            return true;

        } catch (SQLException e) {
            // Si algo falló, deshacemos todo (Rollback)
            if (conn != null) {
                try {
                    System.err.println("Transacción fallida. Deshaciendo cambios...");
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error en rollback: " + ex.getMessage());
                }
            }
            System.out.println("Error al registrar gasto: " + e.getMessage());
            return false;
        } finally {
            // Cerrar recursos manualmente
            try {
                if (pstmtInsert != null) pstmtInsert.close();
                if (pstmtUpdate != null) pstmtUpdate.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error cerrando recursos: " + e.getMessage());
            }
        }
    }

    /**
     * Registra un ingreso y aumenta el saldo de la cuenta asociada.
     */
    public boolean registrarIngreso(Ingreso ingreso) {
        String sqlInsert = "INSERT INTO Ingreso(monto, fecha, descripcion, fuente, id_cuenta, id_categoria, id_usuario) VALUES(?, ?, ?, ?, ?, ?, ?)";
        String sqlUpdateCuenta = "UPDATE Cuenta SET saldo = saldo + ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmtInsert = null;
        PreparedStatement pstmtUpdate = null;

        try {
            conn = DatabaseConnection.connect();
            conn.setAutoCommit(false); // Iniciar transacción

            // Insertar Ingreso
            pstmtInsert = conn.prepareStatement(sqlInsert);
            pstmtInsert.setDouble(1, ingreso.getMonto());
            pstmtInsert.setString(2, ingreso.getFecha().toString());
            pstmtInsert.setString(3, ingreso.getDescripcion());
            pstmtInsert.setString(4, ingreso.getFuente());
            pstmtInsert.setInt(5, ingreso.getIdCuenta());
            pstmtInsert.setInt(6, ingreso.getIdCategoria());
            pstmtInsert.setInt(7, ingreso.getIdUsuario());
            pstmtInsert.executeUpdate();

            // Sumar saldo a la Cuenta
            pstmtUpdate = conn.prepareStatement(sqlUpdateCuenta);
            pstmtUpdate.setDouble(1, ingreso.getMonto());
            pstmtUpdate.setInt(2, ingreso.getIdCuenta());
            pstmtUpdate.executeUpdate();

            conn.commit(); // Confirmar
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error en rollback: " + ex.getMessage());
                }
            }
            System.out.println("Error al registrar ingreso: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstmtInsert != null) pstmtInsert.close();
                if (pstmtUpdate != null) pstmtUpdate.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error cerrando recursos: " + e.getMessage());
            }
        }
    }
}