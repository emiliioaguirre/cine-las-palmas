package com.cine.services;
import com.cine.models.*;
import com.cine.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PeliculaService {
    @Autowired private PeliculaRepository peliculaRepository;
    public List<Pelicula> listar() { return peliculaRepository.findAll(); }
    public Pelicula obtener(Long id) { return peliculaRepository.findById(id).orElseThrow(); }
    public List<Pelicula> porClasificacion(Clasificacion clasificacion) {
        // Se respeta el filtro recibido; no se fuerza la clasificación A.
        return peliculaRepository.findByClasificacion(clasificacion);
    }
    public Pelicula crear(Pelicula pelicula) {
        if (pelicula.getId() != null) throw new IllegalArgumentException("El id se genera al crear la película");
        if (pelicula.getTitulo() == null || pelicula.getTitulo().isBlank()) throw new IllegalArgumentException("El título es obligatorio");
        if (pelicula.getDuracion() <= 0) throw new IllegalArgumentException("La duración debe ser positiva y estar en minutos");
        if (pelicula.getClasificacion() == null) throw new IllegalArgumentException("La clasificación es obligatoria");
        if (pelicula.getGenero() == null || pelicula.getGenero().isBlank()) throw new IllegalArgumentException("El género es obligatorio");
        return peliculaRepository.save(pelicula);
    }
}
