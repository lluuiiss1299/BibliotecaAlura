package com.alura.biblioteca.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titulo;
    private Lenguaje idioma;
    private Long descargas;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores;
    public Libro(){

    }

    public Libro(DatosLibro datoslibro){
        this.titulo = datoslibro.titulo();
        this.idioma = Lenguaje.fromString(datoslibro.idioma().get(0));
        this.descargas = datoslibro.descargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    public Lenguaje getIdioma() {
        return idioma;
    }

    public void setIdioma(Lenguaje idioma) {
        this.idioma = idioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        autores.forEach(a->a.setLibro(this));
        this.autores = autores;
    }

    @Override
    public String toString() {
        return
                "\ntitulo= " + titulo +
                "\nidioma= " + idioma +
                "\ndescargas= " + descargas +
                "\nautor= " + autores.get(0).getNombre();
    }
}
