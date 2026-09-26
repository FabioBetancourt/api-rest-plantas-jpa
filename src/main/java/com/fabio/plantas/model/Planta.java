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
    private String ubicacion;

    private boolean necesitaSolDirecto;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Planta() {}
    public Planta(String nombre, String ubicacion, boolean necesitaSolDirecto, Categoria categoria) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.necesitaSolDirecto = necesitaSolDirecto;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public boolean isNecesitaSolDirecto() { return necesitaSolDirecto; }
    public void setNecesitaSolDirecto(boolean necesitaSolDirecto) { this.necesitaSolDirecto = necesitaSolDirecto; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
