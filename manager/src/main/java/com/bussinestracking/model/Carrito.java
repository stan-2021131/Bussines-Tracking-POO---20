package com.bussinestracking.model;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    public static List<Producto> carrito = new ArrayList<>();

    public static List<Producto> getCarrito() {
        return carrito;
    }
    public static void agregarProducto(Producto producto,short cantidad) {
        if(carrito.contains(producto)) {
            Producto p = carrito.get(carrito.indexOf(producto));
            p.setCantidad((short) (p.getCantidad() + cantidad));
        }else {
            producto.setCantidad(cantidad);
            carrito.add(producto);
        }
    }

    public static void eliminarProducto(Producto producto) {
        if(carrito.contains(producto)) {
            carrito.remove(producto);
        }
    }

}
