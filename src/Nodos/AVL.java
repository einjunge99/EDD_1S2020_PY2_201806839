/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nodos;

import Objetos.Categoria;

/**
 *
 * @author Isaac Maldonado
 */
public class AVL {
    
    public Categoria categoria;
    public String indice;
    //public Categoria categoria;
    public AVL izquierda;
    public AVL derecha;
    public int fe;
    
    
    public AVL(Categoria categoria){
        this.categoria=categoria;
        this.izquierda=null;
        this.derecha=null;
        this.fe=0;
    }
    
}
