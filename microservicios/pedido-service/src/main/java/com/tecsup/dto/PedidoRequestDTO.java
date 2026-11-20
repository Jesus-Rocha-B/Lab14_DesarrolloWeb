package com.tecsup.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoRequestDTO {

    @NotNull(message = "El clienteId es obligatorio")
    private Long clienteId;

    @NotNull(message = "El productoId es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    private String estado;
}
