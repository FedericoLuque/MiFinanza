/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.models;

/**
 *
 * @author alumnadotarde
 */

public class Cuenta {
    private int id;
    private String nombre;
    private double saldo;
    private int idUsuario;

    public Cuenta() {
    }
    
    public Cuenta(int id, String nombre, double saldo, int idUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.idUsuario = idUsuario;
    }

    public Cuenta(int id, String nombre, int idUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.idUsuario = idUsuario;
    }
    
    // Constructor para crear nuevas (sin ID)
    public Cuenta(String nombre, int idUsuario) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    @Override
    public String toString() {
        return nombre;
    }
}