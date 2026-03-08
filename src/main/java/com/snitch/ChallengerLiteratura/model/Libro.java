package com.snitch.ChallengerLiteratura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String titulo;

    private Double numeroDeDescargas;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idioma")
    private List<String> idiomas = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();

    public Libro() {
    }

    public Libro(DatosLibros datos) {
        this.titulo = datos.titulo();
        this.numeroDeDescargas = datos.numeroDeDescargas();
        this.idiomas = datos.idiomas();
    }

    @Override
    public String toString() {
        String nombreAutor = autores.isEmpty() ? "Sin autor" : autores.get(0).getNombre();
        return "\n*********************" +
                "\n-------Libro--------:" +
                "\nTitulo: " + titulo +
                "\nAutor: " + nombreAutor +
                "\nIdioma: " + idiomas +
                "\nNumero de descargas: " +numeroDeDescargas;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }
}