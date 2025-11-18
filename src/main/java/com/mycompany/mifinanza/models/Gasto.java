/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.models;

/**
 *
 * @author federico
 */

import java.sql.Timestamp;

public class Gasto {
    private int id;
    private double monto;
    private Timestamp fecha;
    private String descripcion;
    private String comercio;
    private int idCuenta;
    private int idMetodoPago; // Ahora obligatorio
    private int idCategoria;
    private int idUsuario;

    public Gasto() {
    }

    // Constructor actualizado: incluye idMetodoPago
    public Gasto(double monto, Timestamp fecha, String descripcion, String comercio, int idCuenta, int idMetodoPago, int idCategoria, int idUsuario) {
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.comercio = comercio;
        this.idCuenta = idCuenta;
        this.idMetodoPago = idMetodoPago;
        this.idCategoria = idCategoria;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getComercio() { return comercio; }
    public void setComercio(String comercio) { this.comercio = comercio; }

    public int getIdCuenta() { return idCuenta; }
    public void setIdCuenta(int idCuenta) { this.idCuenta = idCuenta; }

    public int getIdMetodoPago() { return idMetodoPago; }
    public void setIdMetodoPago(int idMetodoPago) { this.idMetodoPago = idMetodoPago; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
}