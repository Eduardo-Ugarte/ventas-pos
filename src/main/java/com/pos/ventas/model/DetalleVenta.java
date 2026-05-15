package com.pos.ventas.model;

import jakarta.persistence.*;

@Entity
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre guardado como texto para conservar historial aunque el producto sea eliminado
    @Column(nullable = false)
    private String producto;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private double subtotal;

    // getters y setters
    public Long getId() { return id; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}