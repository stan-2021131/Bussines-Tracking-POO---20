package com.bussinestracking.model;

import java.util.Date;

public class ResumenContable {
    private int idResumen;
    private float totalCompras;
    private float totalVentas;


    public ResumenContable() {}
    public ResumenContable(int idResumen, float totalCompras, float totalVentas ) {
        this.idResumen = idResumen;
        this.totalCompras = totalCompras;
        this.totalVentas = totalVentas;

    }

    public int getIdResumen() {
        return idResumen;
    }

    public void setIdResumen(int idResumen) {
        this.idResumen = idResumen;
    }

    public float getTotalCompras() {
        return totalCompras;
    }

    public void setTotalCompras(float totalCompras) {
        this.totalCompras = totalCompras;
    }

    public float getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(float totalVentas) {
        this.totalVentas = totalVentas;
    }
    }
}
