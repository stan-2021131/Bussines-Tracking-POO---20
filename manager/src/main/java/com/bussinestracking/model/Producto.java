package com.bussinestracking.model;

public class Producto {
    private String idProducto;
    private String nombre;
    private String descripcion;
    private Short cantidad;
    private float precioVenta;
    private float precioOriginal;
    private Categoria idCategoria;

    public Producto() {}
    public Producto(String idProducto, String nombre, String descripcion, short cantidad,
                    float precioVenta, float precioOriginal, Categoria idCategoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.precioOriginal = precioOriginal;
        this.idCategoria = idCategoria;
    }
    public Producto(String idProducto, String nombre, Short cantidad, float precioVenta, float precioOriginal) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.precioOriginal = precioOriginal;
    }
    public Producto(String a, String b, String c) {}

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
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

    public Categoria getCategoria() {
        return idCategoria;
    }

    public void setCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return nombre.equals(producto.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}
