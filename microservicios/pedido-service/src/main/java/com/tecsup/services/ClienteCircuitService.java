package com.tecsup.services;

import com.tecsup.client.ClienteClient;
import com.tecsup.dto.ClienteDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteCircuitService {

    @Autowired
    private ClienteClient clienteClient;

    @Retry(name = "clienteRetry", fallbackMethod = "clienteFallback")
    @CircuitBreaker(name = "clienteService", fallbackMethod = "clienteFallback")
    public ClienteDTO obtenerCliente(Long id) {
        System.out.println("Llamando a cliente-service...");
        return clienteClient.obtenerCliente(id);
    }

    public ClienteDTO clienteFallback(Long id, Exception ex) {
        System.out.println("Fallback Cliente ejecutado: " + ex.getMessage());
        ClienteDTO dto = new ClienteDTO();
        dto.setId(id);
        dto.setNombre("Cliente temporal no disponible");
        dto.setApellido("-");
        return dto;
    }
}
