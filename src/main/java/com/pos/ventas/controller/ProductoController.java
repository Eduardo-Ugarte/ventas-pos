package com.pos.ventas.controller;

import com.pos.ventas.model.Producto;
import com.pos.ventas.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoRepository repo;

    public ProductoController(ProductoRepository repo) {
        this.repo = repo;
    }

    // LISTAR
    @GetMapping
    public List<Producto> listar() {
        return repo.findAll();
    }

    // GUARDAR — @Valid activa las validaciones del modelo
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Producto p) {
        Producto guardado = repo.save(p);
        return ResponseEntity.ok(guardado);
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}