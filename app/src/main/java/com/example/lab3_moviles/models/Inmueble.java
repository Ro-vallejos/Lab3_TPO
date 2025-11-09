package com.example.lab3_moviles.models;

import com.google.gson.Gson;

import java.io.Serializable;

public class Inmueble implements Serializable {
    private int id;
    private String direccion;
    private String tipo;
    private int estado;
    private String uso;
    private int ambientes;
    private int superficie;
    private double precio;
    private String imagen;
    private int idPropietario;
    private Propietario duenio;

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Inmueble() {
    }

    public Inmueble(int ambientes, String direccion, Propietario duenio, int estado, int id, int idPropietario, String imagen, double precio, int superficie, String tipo, String uso) {
        this.ambientes = ambientes;
        this.direccion = direccion;
        this.duenio = duenio;
        this.estado = estado;
        this.id = id;
        this.idPropietario = idPropietario;
        this.imagen = imagen;
        this.precio = precio;
        this.superficie = superficie;
        this.tipo = tipo;
        this.uso = uso;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public Propietario getDuenio() {
        return duenio;
    }

    public void setDuenio(Propietario duenio) {
        this.duenio = duenio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
