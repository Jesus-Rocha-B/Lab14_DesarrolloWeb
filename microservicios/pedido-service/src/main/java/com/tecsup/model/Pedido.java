package com.tecsup.model;

import com.tecsup.dto.ClienteDTO;
import com.tecsup.dto.ProductoDTO;
import jakarta.persistence.*;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;
    private Long productoId;
    private Integer cantidad;
    private String estado; // PENDIENTE, ATENDIDO, ANULADO

    @Transient
    private ClienteDTO cliente;

    @Transient
    private ProductoDTO producto;

    public Pedido() {
    }

    public Pedido(Long id, Long clienteId, Long productoId, Integer cantidad, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }
}
