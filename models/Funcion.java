package com.cine.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "funciones")
public class Funcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    private LocalDateTime fechaHora;

    private double precio;

    @OneToMany(mappedBy = "funcion", cascade = CascadeType.ALL)
    private List<Boleto> boletos = new ArrayList<>();

    public Funcion() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Pelicula getPelicula() { return pelicula; }
    public void setPelicula(Pelicula pelicula) { this.pelicula = pelicula; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    // Se consulta el horario programado y el precio guardado, sin usar now() ni multiplicar.
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public List<Boleto> getBoletos() { return boletos; }
    public void setBoletos(List<Boleto> boletos) { this.boletos = boletos; }
}
