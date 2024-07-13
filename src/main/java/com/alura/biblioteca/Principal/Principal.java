package com.alura.biblioteca.Principal;

import com.alura.biblioteca.model.*;
import com.alura.biblioteca.repositorio.LibroRepository;
import com.alura.biblioteca.service.ConsumoAPI;
import com.alura.biblioteca.service.ConvierteDatos;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    // Variables
    private LibroRepository repositorio;
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;
    private List<Autor> autores;


    public Principal(LibroRepository repository) {
        this.repositorio=repository;
    }

    //Metodos
    public void  mostrarMenu(){
        int opcion = 1;
        while (opcion != 0) {
            System.out.println(
                    """
                            
                            Elija la opción que desee realizar
                            1.- Buscar libro por titulo
                            2.- Lista de libros registrados
                            3.- Lista de autores registrados
                            4.- Lista de autores vivos en determinados años
                            5.- Lista de libros por idioma
                                                    
                            0.- salir
                            """
            );
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosPorAno();
                    break;
                case 5:
                    mostrarLibrosPorIdiomas();
                    break;
                case 0:
                    System.out.println("Cerrando apicación...");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }

    }

    private void mostrarLibrosPorIdiomas() {
        System.out.println("""
                        Escriba el idioma que desea ver
                        es - Español
                        en - Inglés
                        fr - Frances
                        pt - Portugués
                    """);
        var idioma = teclado.nextLine();
        var res = Lenguaje.fromString(idioma);
        libros = repositorio.findByIdioma(res);
        if(libros.isEmpty())
            System.out.println("No se encontraron libros");
        libros.stream()
                .sorted(Comparator.comparing(Libro::getDescargas).reversed())
                .forEach(System.out::println);
    }

    private void mostrarAutoresVivosPorAno() {
        System.out.println("Escriba el año que desea buscar");
        var year = teclado.nextLong();
        teclado.nextLine();
        var resultado = repositorio.findAutoresPorAnio(year);
        if(resultado.isPresent()){
            autores = resultado.get();
            autores.forEach(a-> System.out.println("Autor: "+ a.getNombre() + " Fecha de nacimiento: "+ a.getAnoNacimiento() + " Fecha de muerte: "+ a.getAnoMuerte()));
        }else{
            System.out.println("No hay autores registrados en ese año");
        }
    }

    private void mostrarAutoresRegistrados() {
        autores = repositorio.findAutors();
        autores.forEach(a-> System.out.println("Nombre: "+a.getNombre()));
    }

    private void mostrarLibrosRegistrados() {
        libros = repositorio.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getDescargas).reversed())
                .forEach(System.out::println);
    }

    private void buscarLibroPorTitulo() {
        DatosLibro datos = getDatosLibro();
        try {
            Libro libro = new Libro(datos);
            List<Autor> listaA = datos.autores().stream()
                    .map(Autor::new)
                    .collect(Collectors.toList());
            libro.setAutores(listaA);
            repositorio.save(libro);
            System.out.println(libro);
        }catch (NullPointerException | IllegalArgumentException e){
            System.out.println("Libro no encontrado");
        }


    }

    private DatosLibro getDatosLibro() {
        System.out.println("Escriba el titulo del libro que desea ver");
        var nombreLibro = teclado.nextLine();
        try{
            var json = consumo.obtenerDatos(URL_BASE+nombreLibro.replace(" ", "%20"));
            Respuesta respuesta = conversor.obtenerDatos(json, Respuesta.class);
            var primerLibro = respuesta.libros().get(0);
            return new DatosLibro(primerLibro.titulo(),primerLibro.idioma(),primerLibro.descargas(),primerLibro.autores());

        }catch (Exception e){
            System.out.println("Libro no encontrado");
        }
        return null;
    }

}
