package com.tecsup.controller;

import com.tecsup.dto.ClienteDTO;
import com.tecsup.model.Cliente;
import com.tecsup.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con id: " + id));
    }

    @PostMapping
    public ResponseEntity<Cliente> guardar(@Valid @RequestBody ClienteDTO dto) {
        Cliente cliente = new Cliente(
                null,
                dto.getNombre(),
                dto.getApellidos(),
                dto.getDireccion(),
                dto.getTelefono(),
                dto.getNroDni(),
                dto.getEstado()
        );
        Cliente guardado = service.guardar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        try {
            Cliente cliente = new Cliente(
                    id,
                    dto.getNombre(),
                    dto.getApellidos(),
                    dto.getDireccion(),
                    dto.getTelefono(),
                    dto.getNroDni(),
                    dto.getEstado()
            );
            return ResponseEntity.ok(service.actualizar(id, cliente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }
}
