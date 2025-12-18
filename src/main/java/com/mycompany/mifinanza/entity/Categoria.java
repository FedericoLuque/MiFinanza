/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nombre;
    
    @Column(name = "parent_id")
    private Integer parentId;
    
    // Auto-relación para subcategorías (Opcional, pero útil si quisieramos navegar)
    // Por ahora lo mantengo simple como en el modelo original, solo la lista.
    @Transient // No lo persistimos con JPA complejo para no complicar el ejemplo, o usamos @OneToMany
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
    
    @Override
    public String toString() {
        return nombre;
    }
}
