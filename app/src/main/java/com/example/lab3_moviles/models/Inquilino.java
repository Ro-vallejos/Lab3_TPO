package com.example.lab3_moviles.models;

import java.io.Serializable;

public class Inquilino implements Serializable {
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String dni;
    private String email;

    public Inquilino() {
    }

    public Inquilino(String apellido, String dni, String email, int id, String nombre, String telefono) {
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombre + " " +apellido;
    }
}
