package com.bussinestracking.model;

public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private Short cantidad;
    private float precioVenta;
    private float precioOriginal;
    private int idCategoria;

    public Producto() {}
    public Producto(int idProducto, String nombre, String descripcion, short cantidad,
                    float precioVenta, float precioOriginal, int idCategoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.precioOriginal = precioOriginal;
        this.idCategoria = idCategoria;
    }
    public Producto(String a, String b, String c) {}

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getCantidad() {
        return cantidad;
    }

    public void setCantidad(Short cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getPrecioOriginal() {
        return precioOriginal;
    }

    public void setPrecioOriginal(float precioOriginal) {
        this.precioOriginal = precioOriginal;
    }

    public int getCategoria() {
        return idCategoria;
    }

    public void setCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
