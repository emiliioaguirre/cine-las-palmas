package com.cine.controllers;

import com.cine.models.Boleto;
import com.cine.services.BoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Recibe solicitudes JSON y delega las reglas de negocio en los servicios.
@RestController
@RequestMapping("/boletos")
public class BoletoController {

    @Autowired
    private BoletoService boletoService;

    @GetMapping("/funcion/{funcionId}")
    public List<Boleto> porFuncion(@PathVariable("funcionId") Long funcionId) {
        return boletoService.porFuncion(funcionId);
    }

    // Comprar modifica datos: se utiliza POST, no GET.
    @PostMapping
    public Boleto comprar(@RequestParam Long funcionId,
                          @RequestParam(required = false) Long clienteId,
                          @RequestParam String fila,
                          @RequestParam int numero) {
        return boletoService.comprar(funcionId, clienteId, fila, numero);
    }
}
