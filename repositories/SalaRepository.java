package com.cine.repositories;

import com.cine.models.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring construye las consultas con los nombres reales de los atributos del modelo.
public interface SalaRepository extends JpaRepository<Sala, Long> {

    Sala findByNumero(Integer numero);

    List<Sala> findByCapacidad(Integer capacidad);
    // El bloqueo se mantiene durante la transacción que modifica este recurso.
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select x from Sala x where x.id = :id")
    java.util.Optional<Sala> bloquear(@org.springframework.data.repository.query.Param("id") Long id);
}
