package com.tecsup.controller;

import com.tecsup.dto.PedidoRequestDTO;
import com.tecsup.model.Pedido;
import com.tecsup.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Pedido> guardar(@Valid @RequestBody PedidoRequestDTO dto) {
        Pedido guardado = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            return ResponseEntity.ok(service.cambiarEstado(id, estado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<?> anular(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.anular(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
