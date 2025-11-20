/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.models;

/**
 *
 * @author federico
 */
import java.sql.Date;

public class Ingreso {
    private int id;
    private double monto;
    private Date fecha;
    private String descripcion;
    private String fuente;
    private int idCuenta;
    private int idCategoria;
    private int idUsuario;
    private String categoria;
    private String cuenta;

    public Ingreso() {
    }

    // Constructor para la inserción
    public Ingreso(double monto, Date fecha, String descripcion, String fuente, int idCuenta, int idCategoria, int idUsuario) {
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.fuente = fuente;
        this.idCuenta = idCuenta;
        this.idCategoria = idCategoria;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFuente() { return fuente; }
    public void setFuente(String fuente) { this.fuente = fuente; }

    public int getIdCuenta() { return idCuenta; }
    public void setIdCuenta(int idCuenta) { this.idCuenta = idCuenta; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public String getCuenta() { return cuenta; }
    public void setCuenta(String cuenta) { this.cuenta = cuenta; }
}
