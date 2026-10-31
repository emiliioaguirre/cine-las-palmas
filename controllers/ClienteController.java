package com.cine.controllers;

import com.cine.models.Cliente;
import com.cine.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Recibe solicitudes JSON y delega las reglas de negocio en los servicios.
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listar();
    }

    // POST registra un nuevo cliente y activa su cuenta de puntos.
    @PostMapping
    public Cliente registrar(@RequestBody Cliente cliente) {
        return clienteService.registrar(cliente);
    }
    @GetMapping("/{id}/puntos")
    public Integer puntos(@PathVariable("id") Long id) { return clienteService.puntos(id); }
}
