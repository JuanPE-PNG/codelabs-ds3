package com.example.microservicio.domain.repository;

import com.example.microservicio.domain.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    List<Producto> listarProductos();
    Optional<Producto> findById(Long id);
    List<Producto> findAll();
}