package com.cine.repositories;

import com.cine.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

// Spring construye las consultas con los nombres reales de los atributos del modelo.
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByPuntos(Integer puntos);
    // El bloqueo se mantiene durante la transacción que modifica este recurso.
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select x from Cliente x where x.id = :id")
    java.util.Optional<Cliente> bloquear(@org.springframework.data.repository.query.Param("id") Long id);
}
