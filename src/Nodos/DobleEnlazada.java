/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nodos;

import Estructuras.ListaDobleEnlazada;
import Objetos.Bloque;
import Objetos.Libro;
import Objetos.Usuario;
import org.json.simple.JSONArray;

/**
 *
 * @author Isaac Maldonado
 */
public class DobleEnlazada {

    public DobleEnlazada siguiente;
    public DobleEnlazada anterior;

    //--------lista de usuarios---------//
    public Usuario usuario;

    public DobleEnlazada(Usuario usuario) {
        this.usuario = usuario;
        this.siguiente = null;
        this.anterior = null;
    }

    //-------lista de elementos en nodo arbol b----//
    public Bloque bloques;

    public DobleEnlazada(Bloque bloques) {
        this.bloques = bloques;
        this.siguiente = null;
        this.anterior = null;
    }

}
