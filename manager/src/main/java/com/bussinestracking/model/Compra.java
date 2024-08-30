package com.bussinestracking.model;

import java.util.ArrayList;

public class Compra {
    private int idCompra;
    private int idProveedor;
    private ArrayList<Integer> productos;
    private float total;

    public Compra() {}
    public Compra(int idCompra, int idProveedor, ArrayList<Integer> productos, float total) {
        this.idCompra = idCompra;
        this.idProveedor = idProveedor;
        this.productos = productos;
        this.total = total;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public ArrayList<Integer> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Integer> productos) {
        this.productos = productos;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
