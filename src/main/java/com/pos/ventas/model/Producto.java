package com.pos.ventas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String nombre;

    @Column(nullable = false)
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private double precio;

    @Column(nullable = false)
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}