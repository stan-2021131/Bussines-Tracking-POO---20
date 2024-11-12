package com.bussinestracking.model;

import java.util.ArrayList;

public class Compra {
    private String idCompra;
    private Proveedor idProveedor;
    private String productos;
    private int cantidad;
    private float precio;

    public Compra() {}
    public Compra(String idCompra, Proveedor idProveedor,String productos, int cantidad, float precio) {
        this.idCompra = idCompra;
        this.idProveedor = idProveedor;
        this.productos = productos;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public Proveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public float getPrecio() {return precio;}
    public void setPrecio(float precio) {this.precio = precio;}
}
