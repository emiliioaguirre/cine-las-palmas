package com.cine.services;
import com.cine.models.*;
import com.cine.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class FuncionService {
    @Autowired private FuncionRepository funcionRepository;
    @Autowired private PeliculaRepository peliculaRepository;
    @Autowired private SalaRepository salaRepository;
    public List<Funcion> listar() { return funcionRepository.findAll(); }
    public Funcion obtener(Long id) { return funcionRepository.findById(id).orElseThrow(); }
    @Transactional
    public Funcion programar(Long peliculaId, Long salaId, Funcion funcion) {
        if (funcion.getId() != null) throw new IllegalArgumentException("El id se genera al programar");
        if (funcion.getFechaHora() == null) throw new IllegalArgumentException("La fecha y hora son obligatorias");
        if (!Double.isFinite(funcion.getPrecio()) || funcion.getPrecio() < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
        Pelicula pelicula = peliculaRepository.findById(peliculaId).orElseThrow();
        // Bloquear la sala impide programaciones simultáneas que se traslapen.
        Sala sala = salaRepository.bloquear(salaId).orElseThrow();
        var inicio = funcion.getFechaHora();
        var fin = inicio.plusMinutes(pelicula.getDuracion());
        for (Funcion existente : funcionRepository.findBySalaId(salaId)) {
            var inicioExistente = existente.getFechaHora();
            var finExistente = inicioExistente.plusMinutes(existente.getPelicula().getDuracion());
            if (inicio.isBefore(finExistente) && inicioExistente.isBefore(fin))
                throw new IllegalStateException("La sala ya tiene una función en ese horario");
        }
        funcion.setPelicula(pelicula); funcion.setSala(sala);
        // El precio recibido se conserva; no se reemplaza por cero.
        funcion.setBoletos(new java.util.ArrayList<>());
        return funcionRepository.save(funcion);
    }
}
