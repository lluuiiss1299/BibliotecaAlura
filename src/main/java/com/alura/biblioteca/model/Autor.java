package com.alura.biblioteca.model;

import jakarta.persistence.*;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private Long anoNacimiento;
    private Long anoMuerte;
    @ManyToOne
    private Libro libro;


    public Autor(){

    }

    public Autor(DatosAutor autor){
        this.nombre = autor.nombre();
        this.anoNacimiento = autor.anoNacimiento();
        this.anoMuerte = autor.anoMuerte();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Long anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Long getAnoMuerte() {
        return anoMuerte;
    }

    public void setAnoMuerte(Long anoMuerte) {
        this.anoMuerte = anoMuerte;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
