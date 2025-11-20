/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.dao;

import com.mycompany.mifinanza.database.DatabaseConnection;
import com.mycompany.mifinanza.models.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author alumnadotarde
 */
public class CategoriaDAO {

    public boolean insertar(Categoria cat, Integer parentId) {
        String sqlCat = "INSERT INTO Categoria(nombre, parent_id) VALUES(?, ?)";
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.connect();
            conn.setAutoCommit(false);

            // 1. Insertar en la tabla Categoria
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCat, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, cat.getNombre());
                if (parentId != null) {
                    pstmt.setInt(2, parentId);
                } else {
                    pstmt.setNull(2, java.sql.Types.INTEGER);
                }
                pstmt.executeUpdate();
                
                // Obtener el ID generado para la nueva categoría
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cat.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Error al obtener ID de categoría nueva.");
                    }
                }
            }
            
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar categoría: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error en rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error cerrando conexión: " + e.getMessage());
                }
            }
        }
    }

    public List<Categoria> listarJerarquia() {
        List<Categoria> todasLasCategorias = new ArrayList<>();
        // 1. Obtener todas las categorías y sus relaciones de padre
        String sql = "SELECT id, nombre, parent_id FROM Categoria";
        
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Categoria cat = new Categoria(rs.getInt("id"), rs.getString("nombre"));
                // El getInt() devuelve 0 si el valor es SQL NULL, lo cual es un problema.
                // Usamos getObject para poder chequear si es null.
                Integer parentId = (Integer) rs.getObject("parent_id");
                cat.setParentId(parentId);
                todasLasCategorias.add(cat);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar jerarquía de categorías: " + e.getMessage());
            return new ArrayList<>(); // Devolver lista vacía en caso de error
        }

        // Construir la jerarquía en Java
        Map<Integer, Categoria> mapaCategorias = todasLasCategorias.stream()
                .collect(Collectors.toMap(Categoria::getId, cat -> cat));

        // Lista que contendrá solo las categorías de nivel superior (padres)
        List<Categoria> categoriasPadre = new ArrayList<>();

        for (Categoria cat : todasLasCategorias) {
            if (cat.getParentId() != null) {
                // Es una subcategoría, encontrar a su padre y añadirla
                Categoria padre = mapaCategorias.get(cat.getParentId());
                if (padre != null) {
                    padre.addSubCategoria(cat);
                }
            } else {
                // Es una categoría de nivel superior
                categoriasPadre.add(cat);
            }
        }
        
        return categoriasPadre;
    }

    public boolean actualizar(Categoria cat) {
        String sql = "UPDATE Categoria SET nombre = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cat.getNombre());
            pstmt.setInt(2, cat.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar categoría: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Categoria WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar categoría: " + e.getMessage());
            return false;
        }
    }
}
