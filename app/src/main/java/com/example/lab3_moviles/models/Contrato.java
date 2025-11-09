package com.example.lab3_moviles.models;

import com.google.gson.Gson;

import java.io.Serializable;

public class Contrato implements Serializable {

    private int id;
    private String fechaInicio;
    private String fechaFin;
    private int estado;
    private double montoMensual;
    private Inquilino inquilino;
    private Inmueble inmueble;

    public Contrato() {
    }

    public Contrato(int estado, String fechaFin, String fechaInicio, int id, Inmueble inmueble, Inquilino inquilino, double montoMensual) {
        this.estado = estado;
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
        this.id = id;
        this.inmueble = inmueble;
        this.inquilino = inquilino;
        this.montoMensual = montoMensual;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(double montoMensual) {
        this.montoMensual = montoMensual;
    }
    @Override
    public String toString() {
        // Serializa el objeto actual a una cadena JSON
        return new Gson().toJson(this);
    }
}

