package com.bussinestracking.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {
    private String idVenta;
    private String cliente;
    private String nit;
    private List<Producto> idProductos;
    private float total;
    private String formaPago;
    private Date fecha;

    public Venta() {}
    public Venta(String idVenta, String cliente, String nit, List<Producto> idProductos, float total, String formaPago, Date fecha) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.nit = nit;
        this.idProductos = idProductos;
        this.total = total;
        this.formaPago = formaPago;
        this.fecha = fecha;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
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

    public List<Producto> getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(List<Producto> idProductos) {
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
