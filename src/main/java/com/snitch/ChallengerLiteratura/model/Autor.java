package com.snitch.ChallengerLiteratura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;

    @ManyToMany(mappedBy = "autores")
    private List<Libro> libros = new ArrayList<>();

    public Autor() {
    }

    public Autor(DatosAutor datos) {
        this.nombre = datos.nombre();
        this.fechaDeNacimiento = datos.fechaDeNacimiento();
        this.fechaDeFallecimiento = datos.fechaDeFallecimiento();
    }

    @Override
    public String toString() {
        return "\nAutor: " + nombre +
                "\nFecha de nacimiento: " + fechaDeNacimiento +
                "\nFecha de fallecimiento:" + fechaDeFallecimiento +
                "\nLibros: " + libros;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaMuerte) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}