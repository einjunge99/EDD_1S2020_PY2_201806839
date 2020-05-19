/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nodos;

import Estructuras.ArbolB;
import Objetos.Libro;

/**
 *
 * @author Isaac Maldonado
 */
public class B
{
     public B padre;
    public B [] hijos;
    public Libro [] libros;
    public boolean bandera = false;
    public String indice;  
    

    public B(int grado){
       hijos = new B [grado*2+3];
       libros = new Libro [grado*2+1];
    }
    
}
