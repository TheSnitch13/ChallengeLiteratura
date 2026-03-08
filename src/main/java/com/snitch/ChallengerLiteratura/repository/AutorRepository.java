package com.snitch.ChallengerLiteratura.repository;

import com.snitch.ChallengerLiteratura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreContainsIgnoreCase(String nombre);

    @Query("""
       SELECT DISTINCT a
       FROM Autor a
       LEFT JOIN FETCH a.libros
       WHERE a.fechaDeNacimiento <= :anio
       AND (a.fechaDeFallecimiento IS NULL OR a.fechaDeFallecimiento >= :anio)
       """)
    List<Autor> autoresVivosConLibrosEnDeterminadoAnio(Integer anio);

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> listarAutoresConLibros();
}