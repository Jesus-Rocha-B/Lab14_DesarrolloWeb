package com.tecsup.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ClienteDTO {

    private Long id;
    private String nombre;

    @JsonAlias({"apellidos", "apellido"})
    private String apellido;

    private String direccion;
    private String telefono;
    private String nroDni;
    private String estado;
}
