package com.alura.biblioteca.repositorio;

import com.alura.biblioteca.model.Autor;
import com.alura.biblioteca.model.Lenguaje;
import com.alura.biblioteca.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String nombre);
    @Query("SELECT a FROM Libro l JOIN l.autores a ORDER BY a.nombre DESC")
    List<Autor> findAutors();
    @Query("SELECT a FROM Libro l JOIN l.autores a WHERE a.anoNacimiento <= :anio AND a.anoMuerte >= :anio")
    Optional<List<Autor>> findAutoresPorAnio(Long anio);

    List<Libro> findByIdioma(Lenguaje idioma);
}
