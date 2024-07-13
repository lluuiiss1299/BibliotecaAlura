package com.alura.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Respuesta(
        @JsonAlias("count") Long numeroLibros,
        @JsonAlias("results") List<DatosLibro> libros
        ) {
}
