/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mifinanza.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "nombre_cuenta")
    private String nombre;
    
    private double saldo;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    private Usuario usuario;

    public Cuenta() {
    }
    
    public Cuenta(int id, String nombre, double saldo, Usuario usuario) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.usuario = usuario;
    }

    public Cuenta(int id, String nombre, Usuario usuario) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
    }
    
    // Constructor de compatibilidad (int idUsuario)
    public Cuenta(int id, String nombre, double saldo, int idUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.usuario = new Usuario();
        this.usuario.setId(idUsuario);
    }
    
    public Cuenta(String nombre, int idUsuario) {
        this.nombre = nombre;
        this.usuario = new Usuario();
        this.usuario.setId(idUsuario);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    // Métodos de compatibilidad para código legacy
    public int getIdUsuario() {
        return (usuario != null) ? usuario.getId() : 0;
    }
    
    public void setIdUsuario(int idUsuario) {
        if (this.usuario == null) {
            this.usuario = new Usuario();
        }
        this.usuario.setId(idUsuario);
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
