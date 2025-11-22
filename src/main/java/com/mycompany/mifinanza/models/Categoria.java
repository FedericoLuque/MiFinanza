/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumnadotarde
 */
public class Categoria {
    private int id;
    private String nombre;
    private Integer parentId; // Null si es categoría padre
    private final List<Categoria> subCategorias = new ArrayList<>();

    public Categoria() {}

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }

    public List<Categoria> getSubCategorias() { return subCategorias; }
    
    public void addSubCategoria(Categoria sub) {
        this.subCategorias.add(sub);
    }
    
    // El toString se usará para mostrar en JTree y JComboBox
    @Override
    public String toString() {
        return nombre;
    }
}
