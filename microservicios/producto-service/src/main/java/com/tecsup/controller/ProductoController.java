package com.tecsup.controller;

import com.tecsup.model.Producto;
import com.tecsup.services.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con id: " + id));
    }

    @PostMapping
    public ResponseEntity<Producto> guardar(@Valid @RequestBody Producto producto) {
        Producto guardado = service.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        try {
            return ResponseEntity.ok(service.actualizar(id, producto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }
}
