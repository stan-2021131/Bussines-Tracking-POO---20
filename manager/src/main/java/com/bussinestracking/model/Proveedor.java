package com.bussinestracking.model;

public class Proveedor {
    private int idproveedor;
    private String nombre;
    private String descripcion;

    public Proveedor() {}
    public Proveedor(int idproveedor, String nombre, String descripcion) {
        this.idproveedor = idproveedor;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
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
}
