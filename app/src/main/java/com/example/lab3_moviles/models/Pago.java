package com.example.lab3_moviles.models;

import com.google.gson.Gson;

import java.io.Serializable;

public class Pago implements Serializable {

    private int id;
    private int idContrato;
    private int nroPago;
    private String fechaPago;
    private String estado;
    private String concepto;
    private double importe;
    public Pago() {
    }


    public Pago(String concepto, String estado, String fechaPago, int id, int idContrato, int nroPago, double importe) {
        this.concepto = concepto;
        this.estado = estado;
        this.fechaPago = fechaPago;
        this.id = id;
        this.idContrato = idContrato;
        this.nroPago = nroPago;
        this.importe = importe;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public int getNroPago() {
        return nroPago;
    }

    public void setNroPago(int nroPago) {
        this.nroPago = nroPago;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
    @Override
    public String toString() {
        // Serializa el objeto actual a una cadena JSON
        return new Gson().toJson(this);
    }
}
