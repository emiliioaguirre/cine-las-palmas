package com.cine.controllers;

import com.cine.models.Clasificacion;
import com.cine.models.Pelicula;
import com.cine.services.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Recibe solicitudes JSON y delega las reglas de negocio en los servicios.
@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public List<Pelicula> listar(@RequestParam(required = false) Clasificacion clasificacion) {
        if (clasificacion != null) {
            return peliculaService.porClasificacion(clasificacion);
        }
        return peliculaService.listar();
    }

    // El identificador está dentro de la ruta, por eso se utiliza @PathVariable.
    @GetMapping("/{id}")
    public Pelicula obtener(@PathVariable("id") Long id) {
        return peliculaService.obtener(id);
    }

    // @RequestBody convierte el JSON recibido en el objeto Pelicula.
    @PostMapping
    public Pelicula crear(@RequestBody Pelicula pelicula) {
        return peliculaService.crear(pelicula);
    }
}
