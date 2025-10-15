package com.example.lab3_moviles.models;

import java.io.Serializable;

public class Propietario implements Serializable {
    private int idPropietario;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String clave;
    private String email;

    public Propietario() {
    }

    public Propietario(String apellido, String clave, String dni, int idPropietario, String email, String nombre, String telefono) {
        this.apellido = apellido;
        this.clave = clave;
        this.dni = dni;
        this.idPropietario = idPropietario;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
