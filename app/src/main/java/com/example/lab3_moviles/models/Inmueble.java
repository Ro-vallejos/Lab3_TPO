package com.example.lab3_moviles.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Inmueble implements Serializable {
    private int idInmueble;
    private String direccion;
    private String tipo;
    private String uso;
    private int ambientes;
    private int superficie;
    private double latitud;
    private double longitud;
    private double valor;
    private String imagen;
    private boolean disponible;
    private int idPropietario;
    private Propietario duenio;

    public Inmueble() {
    }

    public Inmueble(int ambientes, String direccion, boolean disponible, Propietario duenio, int idInmueble, int idPropietario, String imagen, double latitud, double longitud, int superficie, String tipo, String uso, double valor) {
        this.ambientes = ambientes;
        this.direccion = direccion;
        this.disponible = disponible;
        this.duenio = duenio;
        this.idInmueble = idInmueble;
        this.idPropietario = idPropietario;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.superficie = superficie;
        this.tipo = tipo;
        this.uso = uso;
        this.valor = valor;
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

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Propietario getDuenio() {
        return duenio;
    }

    public void setDuenio(Propietario duenio) {
        this.duenio = duenio;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
