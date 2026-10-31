package com.cine.services;
import com.cine.models.*;
import com.cine.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
@Service
public class BoletoService {
    @Autowired private BoletoRepository boletoRepository;
    @Autowired private FuncionRepository funcionRepository;
    @Autowired private ClienteRepository clienteRepository;
    public List<Boleto> porFuncion(Long funcionId) {
        if (!funcionRepository.existsById(funcionId)) throw new java.util.NoSuchElementException();
        return boletoRepository.findByFuncionId(funcionId);
    }
    @Transactional
    public Boleto comprar(Long funcionId, Long clienteId, String fila, int numero) {
        // Serializa compras de la misma función para comprobar cupo y asiento sin carreras.
        Funcion funcion = funcionRepository.bloquear(funcionId).orElseThrow();
        if (!funcion.getFechaHora().isAfter(java.time.LocalDateTime.now())) throw new IllegalStateException("La función ya comenzó");
        if (fila == null) throw new IllegalArgumentException("La fila es obligatoria");
        fila = fila.trim().toUpperCase(java.util.Locale.ROOT);
        // Decisión de distribución: filas A-Z de hasta diez asientos. La última puede ser parcial.
        if (!fila.matches("[A-Z]") || numero < 1 || numero > 10 ||
                (fila.charAt(0) - 'A') * 10 + numero > funcion.getSala().getCapacidad())
            throw new IllegalArgumentException("El asiento no existe en esta sala");
        if (boletoRepository.existsByFuncionIdAndFilaAndNumero(funcionId, fila, numero)) throw new IllegalStateException("El asiento ya está ocupado");
        if (boletoRepository.countByFuncionId(funcionId) >= funcion.getSala().getCapacidad()) throw new IllegalStateException("La sala no tiene cupo");
        Boleto boleto = new Boleto();
        boleto.setFolio(UUID.randomUUID().toString());
        boleto.setFuncion(funcion); boleto.setFila(fila); boleto.setNumero(numero);
        if (clienteId != null) {
            Cliente cliente = clienteRepository.bloquear(clienteId).orElseThrow();
            boleto.setCliente(cliente);
            // Decisión del ejercicio: un punto por cada diez pesos completos, solo para registrados.
            if (cliente.isRegistrado()) {
                int ganados = java.math.BigDecimal.valueOf(funcion.getPrecio()).divideToIntegralValue(java.math.BigDecimal.TEN).intValueExact();
                cliente.setPuntos(Math.addExact(cliente.getPuntos(), ganados));
            }
        }
        return boletoRepository.save(boleto);
    }
}
