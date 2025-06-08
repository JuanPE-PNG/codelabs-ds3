package com.example.microservicio.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservicio.domain.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> listarProductos();

    @Override
    Optional<Producto> findById(Long id);

    @Override
    List<Producto> findAll();
}