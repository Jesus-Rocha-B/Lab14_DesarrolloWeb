package com.tecsup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClienteDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    private String telefono;

    @NotBlank(message = "El nro_dni es obligatorio")
    @Pattern(regexp = "^\\d{8}$", message = "El nro_dni debe contener exactamente 8 dígitos")
    private String nroDni;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
