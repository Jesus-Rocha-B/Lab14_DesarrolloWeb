package com.tecsup.services;

import com.tecsup.dto.CategoriaDTO;
import com.tecsup.model.Producto;
import com.tecsup.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private CategoriaCircuitService categoriaCircuitService;

    // ==========================
    // LISTAR PRODUCTOS
    // ==========================
    public List<Producto> listar() {
        List<Producto> productos = repository.findAll();
        productos.forEach(p -> {
            if (p.getCategoriaId() != null) {
                CategoriaDTO categoria = categoriaCircuitService.obtenerCategoria(p.getCategoriaId());
                p.setCategoria(categoria);
            }
        });
        return productos;
    }

    // ==========================
    // BUSCAR PRODUCTO POR ID
    // ==========================
    public Optional<Producto> buscarPorId(Long id) {
        Optional<Producto> opt = repository.findById(id);
        opt.ifPresent(p -> {
            if (p.getCategoriaId() != null) {
                CategoriaDTO categoria = categoriaCircuitService.obtenerCategoria(p.getCategoriaId());
                p.setCategoria(categoria);
            }
        });
        return opt;
    }

    public Producto obtener(Long id) {
        return buscarPorId(id).orElse(null);
    }

    // ==========================
    // GUARDAR PRODUCTO
    // ==========================
    public Producto guardar(Producto p) {
        Producto producto = repository.save(p);
        if (producto.getCategoriaId() != null) {
            CategoriaDTO categoria = categoriaCircuitService.obtenerCategoria(producto.getCategoriaId());
            producto.setCategoria(categoria);
        }
        return producto;
    }

    // ==========================
    // ACTUALIZAR PRODUCTO
    // ==========================
    public Producto actualizar(Long id, Producto p) {
        Producto producto = repository.findById(id).orElse(null);
        if (producto != null) {
            producto.setNombre(p.getNombre());
            producto.setPrecio(p.getPrecio());
            producto.setStock(p.getStock());
            producto.setCategoriaId(p.getCategoriaId());
            producto = repository.save(producto);
            if (producto.getCategoriaId() != null) {
                CategoriaDTO categoria = categoriaCircuitService.obtenerCategoria(producto.getCategoriaId());
                producto.setCategoria(categoria);
            }
            return producto;
        }
        return null;
    }

    // ==========================
    // ELIMINAR PRODUCTO
    // ==========================
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
