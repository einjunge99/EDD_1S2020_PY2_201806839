/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

/**
 *
 * @author Isaac Maldonado
 */
public class Libro {

    public long ISBN;
    public String titulo;
    public String autor;
    public String editorial;
    public String anio;
    public String edicion;
    public String categoria;
    public String idioma;
    public int usuario;
    

    public Libro(long ISBN, String titulo, String autor, String editorial, String anio, String edicion, String categoria, String idioma, int usuario) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.edicion = edicion;
        this.categoria = categoria;
        this.idioma = idioma;
        this.usuario =usuario;
    }
    
}
