package com.example.rest.controller;

import com.example.rest.model.Pais;
import com.example.rest.service.PaisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paises")
public class PaisController {
    private final PaisService paisService;

    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping
    public List<Pais> listarPaises() {
        return paisService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pais> obtenerPais(@PathVariable Long id) {
        return paisService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Pais> obtenerPaisPorNombre(@PathVariable String nombre) {
        return paisService.obtenerPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pais crearPais(@RequestBody Pais pais) {
        return paisService.guardarPais(pais);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPais(@PathVariable Long id) {
        paisService.eliminarPais(id);
        return ResponseEntity.noContent().build();
    }
}