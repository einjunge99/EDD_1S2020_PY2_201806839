/*
 * To change this license cabeza, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import Nodos.DobleEnlazada;
import Objetos.Usuario;

/**
 *
 * @author Isaac Maldonado
 */
public class ListaDobleEnlazada {

    DobleEnlazada cabeza;
    DobleEnlazada cola;
    int tamanio;

    public ListaDobleEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

//-----------METODOS PARA INSERTAR----------------------//    
    public void insertarFin(DobleEnlazada info) {
        if (cabeza == null) {
            cabeza = info;
            cola = info;
        } else {
            cola.siguiente = info;
            info.anterior = cola;
            cola = info;

        }
        tamanio++;
    }

    void insertarInicio(DobleEnlazada info) {
        if (cabeza == null) {
            cabeza = info;
            cola = info;
        } else {
            cabeza.anterior = info;
            info.siguiente = cabeza;
            cabeza = info;

        }
        tamanio++;
    }

//----------METODOS PARA ELIMINAR-----------------------//    
    void eliminarPrimero() {
        if (cabeza.siguiente == null) {
            cabeza = null;
            cola = null;
        } else if (cabeza != null) {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        }
    }

    void eliminarUltimo() {
        if (cabeza.siguiente == null) {
            cabeza = null;
            cola = null;

        } else if (cabeza != null) {
            cola = cola.anterior;
            cola.siguiente = null;

        }
    }
    /*lo que se puede hacer es que en la posicion del arreglo se meta la lista
     y no que el objeto contenga la lista en si, asi en caso se elimie el primero de la lista
     se pueda desplazar el resto sin problema
     */

    void eliminarUsuario(int carnet) {
        int cont = 0;
        boolean bandera = false;
        DobleEnlazada actual = cabeza;
        while (actual != null) {
            if (actual.usuario.carnet == carnet) {
                bandera = true;
                break;
            } else {
                actual = actual.siguiente;
                cont++;
            }
        }
        if (bandera) {
            if (cont == 0) {
                eliminarPrimero();
            } else if (cont + 1 == tamanio) {
                eliminarUltimo();
            } else {
                actual.anterior.siguiente = actual.siguiente;
                actual.siguiente.anterior = actual.anterior;
            }
            tamanio--;
        }

    }

//----------METODOS DE ORDENAMIENTO-------------------------------// 
    public DobleEnlazada buscar(int carnet, String password) {
        DobleEnlazada temp = cabeza;
        while (temp != null && temp.usuario.carnet != carnet && !temp.usuario.pass.equals(password)) {
            temp = temp.siguiente;
        }
        return temp;
    }

    public DobleEnlazada buscarRegistro(int carnet) {
        DobleEnlazada temp = cabeza;
        while (temp != null && temp.usuario.carnet != carnet) {
            temp = temp.siguiente;
        }
        return temp;
    }

//----------METODOS PARA RETORNO INFORMACION-----------------------//   
    public int getTamanio() {
        return this.tamanio;
    }

    public DobleEnlazada getUltimo() {
        return this.cola;
    }

    public DobleEnlazada getPrimero() {
        return this.cabeza;
    }

    public void mostrarUsuarios() {
        DobleEnlazada temp = cabeza;
        while (temp != null) {
            System.out.print(temp.usuario.nombre + " . ");
            temp = temp.siguiente;
        }
        System.out.println();

    }

    public String listaHash(int indice) {
        String concatenar = "";
        int cont = -1;
        DobleEnlazada temp = cabeza;
        while (temp != null) {
            cont++;
            concatenar += "nodo_" + indice + "_" + cont + " [label=\"{" + temp.usuario.nombre +"|"+temp.usuario.carnet+"|"+temp.usuario.pass+"}\"];\n";
            temp = temp.siguiente;
        }
        if (cont > 0) {
            int aux = 0;
            for (int i = 0; i < cont; i++) {
                aux = i + 1;
                concatenar += "nodo_" + indice + "_" + i + " -> nodo_" + indice + "_" + aux + "\n";
            }
        }
        return concatenar;
    }

    public String listaBloques() {
        int cont = 0;
        String concat = "";
        concat += "digraph D {\n"
                + "  node [shape=record fontname=Arial];\n";
        DobleEnlazada temp = cabeza;
        while (temp != null) {
            concat += "N" + cont + "[label=\"{Index:" + temp.bloques.index + "|Nonce: " + temp.bloques.nonce + "}|{ Timestamp: " + temp.bloques.timeStamp + "| Hash: " + temp.bloques.hash + "| Prev: " + temp.bloques.prevHash + "}\"] \n";
            temp = temp.siguiente;
            cont++;
        }

        for (int i = 0; i < cont - 1; i++) {
            int k=i+1;
            concat += "N" + i + " -> " + "N" + k + " \n";
        }
        for (int i = cont - 1; i > 0; i--) {
            int k = i - 1;
            concat += "N" + i + " -> " + "N" + k + " \n";
        }
        concat+="}";
        return concat;
    }

}
