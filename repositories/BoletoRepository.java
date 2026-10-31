package com.cine.repositories;

import com.cine.models.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring construye las consultas con los nombres reales de los atributos del modelo.
public interface BoletoRepository extends JpaRepository<Boleto, Long> {

    List<Boleto> findByFuncionId(Long funcionId);

    List<Boleto> findByClienteEmail(String email);

    long countByFuncionId(Long funcionId);

    // El asiento se identifica dentro de su función, no de todo el cine.
    boolean existsByFuncionIdAndFilaAndNumero(Long funcionId, String fila, int numero);
}
