package com.cine.controllers;

import com.cine.models.Funcion;
import com.cine.services.FuncionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Recibe solicitudes JSON y delega las reglas de negocio en los servicios.
@RestController
@RequestMapping("/funciones")
public class FuncionController {

    @Autowired
    private FuncionService funcionService;

    @GetMapping
    public List<Funcion> listar() {
        return funcionService.listar();
    }

    // El identificador está dentro de la ruta, por eso se utiliza @PathVariable.
    @GetMapping("/{id}")
    public Funcion obtener(@PathVariable("id") Long id) {
        return funcionService.obtener(id);
    }

    @PostMapping
    public Funcion programar(@RequestParam Long peliculaId,
                             @RequestParam Long salaId,
                             @RequestBody Funcion funcion) {
        return funcionService.programar(peliculaId, salaId, funcion);
    }
}
