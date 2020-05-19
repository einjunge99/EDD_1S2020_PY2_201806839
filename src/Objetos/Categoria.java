/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Estructuras.ArbolB;

/**
 *
 * @author Isaac Maldonado
 */
public class Categoria {
    public String categoria;
    public int usuario;
    public ArbolB libros;
    public Categoria(String categoria, int usuario){
       this.categoria=categoria;
       this.usuario=usuario;
       this.libros=new ArbolB(2);
    }
}
