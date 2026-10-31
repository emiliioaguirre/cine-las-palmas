package com.cine.repositories;

import com.cine.models.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

// Spring construye las consultas con los nombres reales de los atributos del modelo.
public interface FuncionRepository extends JpaRepository<Funcion, Long> {

    List<Funcion> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Funcion> findByPeliculaId(Long peliculaId);

    List<Funcion> findBySalaNumero(Integer numero);

    List<Funcion> findByFechaHora(LocalDateTime fechaHora);

    List<Funcion> findBySalaId(Long salaId);

    // La duración de la película se considera al comparar los intervalos en el servicio.
    // El bloqueo se mantiene durante la transacción que modifica este recurso.
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select x from Funcion x where x.id = :id")
    java.util.Optional<Funcion> bloquear(@org.springframework.data.repository.query.Param("id") Long id);
}
