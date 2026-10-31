package com.cine.controllers;

import com.cine.models.Sala;
import com.cine.repositories.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Conserva el controlador del código base y consulta las salas existentes.
@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaRepository salaRepository;

    @GetMapping
    public List<Sala> listar() {
        return salaRepository.findAll();
    }

    @PostMapping
    public Sala crear(@RequestBody Sala sala) {
        // El caso solo contempla las salas 1 a 6, sin números repetidos.
        if (sala.getId() != null || sala.getNumero() == null || sala.getNumero() < 1 || sala.getNumero() > 6)
            throw new IllegalArgumentException("El número de sala debe estar entre 1 y 6 y no indicar id");
        if (sala.getCapacidad() == null || sala.getCapacidad() < 1 || sala.getCapacidad() > 260)
            throw new IllegalArgumentException("La capacidad debe estar entre 1 y 260");
        if (salaRepository.findByNumero(sala.getNumero()) != null)
            throw new IllegalStateException("El número de sala ya existe");
        return salaRepository.save(sala);
    }
}
