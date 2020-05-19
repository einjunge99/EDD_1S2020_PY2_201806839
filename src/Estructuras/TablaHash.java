/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import Nodos.DobleEnlazada;
import Nodos.Hash;
import Objetos.Usuario;

/**
 *
 * @author Isaac Maldonado
 */
public class TablaHash {

    int M;
    Hash[] tabla;
    int numElementos;

    public TablaHash(int tamanio) {
        this.M = tamanio;
        tabla = new Hash[M];
        for (int i = 0; i < M; i++) {
            tabla[i] = new Hash();
        }
        numElementos = 0;

    }

//-----------METODOS PARA INSERTAR----------------------//    
    public void insertar(Usuario s) {
        int posicion;
        DobleEnlazada nodo = new DobleEnlazada(s);
        posicion = modulo(s.carnet);
        tabla[posicion].encadenado.insertarFin(nodo);
        this.numElementos++;
    }

    public void eliminar(int carnet) {
        int posicion;
        posicion = modulo(carnet);
        tabla[posicion].encadenado.eliminarUsuario(carnet);
        setTamanio();
    }

    int modulo(int entrada) {
        int salida;
        salida = entrada % M;
        return salida;
    }

    void setTamanio() {
        int cont = 0;
        for (int i = 0; i < M; i++) {
            cont += tabla[i].encadenado.getTamanio();
        }
        numElementos = cont;
    }

    public Usuario buscar(int carnet, String password) {
        Usuario temp = null;
        DobleEnlazada aux = null;
        for (int i = 0; i < M; i++) {
            aux = tabla[i].encadenado.buscar(carnet, password);
            if (aux != null) {
                temp = aux.usuario;
                if (temp != null) {
                    return temp;
                }
            }
        }

        return temp;
    }
    public Usuario buscarRegistrar(int carnet) {
        Usuario temp = null;
        DobleEnlazada aux = null;
        for (int i = 0; i < M; i++) {
            aux = tabla[i].encadenado.buscarRegistro(carnet);
            if (aux != null) {
                temp = aux.usuario;
                if (temp != null) {
                    return temp;
                }
            }
        }

        return temp;
    }

    public String generarDot() {
        String concatenar = "";
        String listas = "";
        String relaciones = "";
        concatenar += "digraph G {\n"
                + "rankdir = LR;\n"
                + "node [shape=record];\n"
                + "hashTable [label=\"";

        for (int i = 0; i < M; i++) {

            if (i == M - 1) {
                concatenar += "<f" + i + ">" + i;
            } else {
                concatenar += "<f" + i + ">" + i + "|";
            }
            if (tabla[i].encadenado.getTamanio() != 0) {

                relaciones += "hashTable:f" + i + " -> nodo_" + i + "_0\n";
                listas += tabla[i].encadenado.listaHash(i);
            }
        }
        concatenar += "\" height=40];\n";
        concatenar += listas;
        concatenar += relaciones;
        concatenar += "}\n";
        return concatenar;
    }

}
