package com.fabio.plantas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "plantas")
public class Planta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String ubicacion;

    private boolean necesitaSolDirecto;

    public Planta() {}

    public Planta(String nombre, String tipo, String ubicacion, boolean necesitaSolDirecto) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.necesitaSolDirecto = necesitaSolDirecto;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public boolean isNecesitaSolDirecto() { return necesitaSolDirecto; }
    public void setNecesitaSolDirecto(boolean necesitaSolDirecto) { this.necesitaSolDirecto = necesitaSolDirecto; }
}
