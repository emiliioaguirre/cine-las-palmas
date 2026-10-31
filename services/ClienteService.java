package com.cine.services;
import com.cine.models.Cliente;
import com.cine.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ClienteService {
    @Autowired private ClienteRepository clienteRepository;
    public List<Cliente> listar() { return clienteRepository.findAll(); }
    public Cliente registrar(Cliente cliente) {
        if (cliente.getId() != null) throw new IllegalArgumentException("El id se genera al registrar");
        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        if (cliente.getEmail() == null || !cliente.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))
            throw new IllegalArgumentException("El correo no es válido");
        cliente.setEmail(cliente.getEmail().trim().toLowerCase(java.util.Locale.ROOT));
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) throw new IllegalStateException("El correo ya está registrado");
        // Registrar habilita la acumulación de puntos; el cliente no elige su saldo inicial.
        cliente.setRegistrado(true); cliente.setPuntos(0);
        return clienteRepository.save(cliente);
    }
    public Integer puntos(Long id) { return clienteRepository.findById(id).orElseThrow().getPuntos(); }
}
