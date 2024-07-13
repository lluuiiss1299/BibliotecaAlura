package com.alura.biblioteca.model;

public enum Lenguaje {
    ESPANOL("es"),
    INGLES("en"),
    FRANCES("fr"),
    PORTUGUES("pt");

    public String codigoIdioma;

    Lenguaje(String codigoIdioma){
        this.codigoIdioma = codigoIdioma;
    }

    public static Lenguaje fromString(String text) {
        for (Lenguaje lengua : Lenguaje.values()) {
            if (lengua.codigoIdioma.equalsIgnoreCase(text.replace("[","").replace("]",""))) {
                return lengua;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

}
