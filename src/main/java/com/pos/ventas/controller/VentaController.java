package com.pos.ventas.controller;

import com.pos.ventas.model.Venta;
import com.pos.ventas.model.DetalleVenta;
import com.pos.ventas.model.Producto;
import com.pos.ventas.repository.VentaRepository;
import com.pos.ventas.repository.ProductoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaRepository repo;
    private final ProductoRepository productoRepo;

    public VentaController(VentaRepository repo, ProductoRepository productoRepo) {
        this.repo = repo;
        this.productoRepo = productoRepo;
    }

    @GetMapping
    public List<Venta> listar() {
        return repo.findAll();
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Venta venta) {
        double subtotal = 0;

        for (DetalleVenta d : venta.getDetalles()) {

            Optional<Producto> opt = productoRepo.findByNombreIgnoreCase(d.getProducto());

            if (opt.isPresent()) {
                Producto p = opt.get();

                if (p.getStock() < d.getCantidad()) {
                    return ResponseEntity.badRequest()
                        .body("Stock insuficiente para: " + p.getNombre()
                            + ". Disponible: " + p.getStock());
                }

                p.setStock(p.getStock() - d.getCantidad());
                productoRepo.save(p);
            }

            double sub = d.getCantidad() * d.getPrecio();
            d.setSubtotal(sub);
            subtotal += sub;
        }

        double igv   = subtotal * 0.18;
        double total = subtotal + igv;

        venta.setSubtotal(subtotal);
        venta.setIgv(igv);
        venta.setTotal(total);

        return ResponseEntity.ok(repo.save(venta));
    }
}
