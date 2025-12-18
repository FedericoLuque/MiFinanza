/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDate;

@Entity
@Table(name = "Presupuesto")
public class Presupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "monto_total")
    private double montoTotal;
    
    @Column(name = "monto_actual")
    private double montoActual;
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    private Usuario usuario;

    @Transient
    private String nombreCategoria; // Compatibilidad legacy

    public Presupuesto() {
    }

    public Presupuesto(double montoTotal, LocalDate fechaInicio, LocalDate fechaFin, int idCategoria, int idUsuario) {
        this.montoTotal = montoTotal;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.categoria = new Categoria();
        this.categoria.setId(idCategoria);
        this.usuario = new Usuario();
        this.usuario.setId(idUsuario);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public double getMontoActual() { return montoActual; }
    public void setMontoActual(double montoActual) { this.montoActual = montoActual; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    // Métodos de compatibilidad
    public int getIdCategoria() {
        return (categoria != null) ? categoria.getId() : 0;
    }

    public void setIdCategoria(int idCategoria) {
        if (this.categoria == null) {
            this.categoria = new Categoria();
        }
        this.categoria.setId(idCategoria);
    }

    public int getIdUsuario() {
        return (usuario != null) ? usuario.getId() : 0;
    }

    public void setIdUsuario(int idUsuario) {
        if (this.usuario == null) {
            this.usuario = new Usuario();
        }
        this.usuario.setId(idUsuario);
    }

    public String getNombreCategoria() { 
        if (categoria != null && categoria.getNombre() != null) return categoria.getNombre();
        return nombreCategoria; 
    }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }
}