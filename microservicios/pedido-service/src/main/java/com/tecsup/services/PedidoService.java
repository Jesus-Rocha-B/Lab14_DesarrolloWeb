package com.tecsup.services;

import com.tecsup.dto.ClienteDTO;
import com.tecsup.dto.PedidoRequestDTO;
import com.tecsup.dto.ProductoDTO;
import com.tecsup.model.Pedido;
import com.tecsup.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ClienteCircuitService clienteCircuitService;

    @Autowired
    private ProductoCircuitService productoCircuitService;

    public List<Pedido> listar() {
        List<Pedido> pedidos = repository.findAll();
        for (Pedido p : pedidos) {
            completarInformacion(p);
        }
        return pedidos;
    }

    public Optional<Pedido> buscarPorId(Long id) {
        Optional<Pedido> opt = repository.findById(id);
        opt.ifPresent(this::completarInformacion);
        return opt;
    }

    public Pedido guardar(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setClienteId(dto.getClienteId());
        pedido.setProductoId(dto.getProductoId());
        pedido.setCantidad(dto.getCantidad());
        pedido.setEstado(dto.getEstado() != null && !dto.getEstado().isBlank() ? dto.getEstado().toUpperCase() : "PENDIENTE");

        Pedido guardado = repository.save(pedido);
        completarInformacion(guardado);
        return guardado;
    }

    public Pedido cambiarEstado(Long id, String nuevoEstado) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        String estadoNormalizado = nuevoEstado.toUpperCase();
        if (!estadoNormalizado.equals("PENDIENTE") && !estadoNormalizado.equals("ATENDIDO") && !estadoNormalizado.equals("ANULADO")) {
            throw new IllegalArgumentException("Estado no permitido. Debe ser: PENDIENTE, ATENDIDO o ANULADO");
        }

        pedido.setEstado(estadoNormalizado);
        Pedido actualizado = repository.save(pedido);
        completarInformacion(actualizado);
        return actualizado;
    }

    public Pedido anular(Long id) {
        return cambiarEstado(id, "ANULADO");
    }

    private void completarInformacion(Pedido pedido) {
        if (pedido.getClienteId() != null) {
            ClienteDTO cliente = clienteCircuitService.obtenerCliente(pedido.getClienteId());
            pedido.setCliente(cliente);
        }

        if (pedido.getProductoId() != null) {
            ProductoDTO producto = productoCircuitService.obtenerProducto(pedido.getProductoId());
            pedido.setProducto(producto);
        }
    }
}
