/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.models;

/**
 *
 * @author federico
 */
import java.time.LocalDate;

public class Presupuesto {
    private int id;
    private double montoAsignado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int idCategoria;
    
    
    private double montoGastado; // Para la barra de progreso
    private String nombreCategoria; // Para mostrar en la lista

    public Presupuesto() {
    }

    public Presupuesto(double montoAsignado, LocalDate fechaInicio, LocalDate fechaFin, int idCategoria) {
        this.montoAsignado = montoAsignado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idCategoria = idCategoria;
    }

    // Getters y Setters básicos
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getMontoAsignado() { return montoAsignado; }
    public void setMontoAsignado(double montoAsignado) { this.montoAsignado = montoAsignado; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }
    
    // Getters y Setters auxiliares
    public double getMontoGastado() { return montoGastado; }
    public void setMontoGastado(double montoGastado) { this.montoGastado = montoGastado; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }
}
