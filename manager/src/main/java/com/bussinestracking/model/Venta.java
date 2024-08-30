package com.bussinestracking.model;

import java.util.ArrayList;
import java.util.Date;

public class Venta {
    private int idVenta;
    private String cliente;
    private String nit;
    private ArrayList<Integer> idProductos;
    private float total;
    private String formaPago;
    private Date fecha;

    public Venta() {}
    public Venta(int idVenta, String cliente, String nit, ArrayList<Integer> idProductos, float total, String formaPago, Date fecha) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.nit = nit;
        this.idProductos = idProductos;
        this.total = total;
        this.formaPago = formaPago;
        this.fecha = fecha;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public ArrayList<Integer> getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(ArrayList<Integer> idProductos) {
        this.idProductos = idProductos;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
