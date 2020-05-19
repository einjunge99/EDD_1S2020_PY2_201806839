/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import Nodos.DobleEnlazada;
import Nodos.B;
import Objetos.Libro;
import java.util.ArrayList;

/**
 *
 * @author Isaac Maldonado
 */
public class ArbolB {

    public int grado;
    public B raiz;
    public ArrayList<Libro> ingresados;

    public ArbolB(int grado) {
        this.grado = grado;
        this.raiz = new B(this.grado);
        this.ingresados = new ArrayList<Libro>();
    }
    //-----------METODOS PARA INSERTAR----------------------//    

    public void insertarLibro(Libro info) {
        if (!raiz.bandera) {
            insertar(raiz, info);
        } else {
            recursivoHijos(raiz);
            recursivoInsertar(raiz, info);
        }
    }

    public void insertar(B nodo, Libro info) {

        int actual = 0;
        int cantidad = nodo.libros.length;
        for (int i = 0; i < cantidad; i++) {
            if (nodo.libros[i] == null) {

                nodo.libros[i] = info;
                //int n=contarElementos(raiz.libros);
                //quickSort(raiz.libros, 0, n-1);
                ordenar(nodo.libros);
                actual = i;
                ingresados.add(info);
                break;
            }
        }
        if (actual == 2 * grado) {
            split(nodo);
        }

    }

    public void recursivoInsertar(B nodo, Libro info) {
        if (nodo != null) {
            boolean entro = false;
            if (!nodo.bandera) {
                insertar(nodo, info);
                entro = true;
            }
            if (!entro) {
                for (int i = 0; i < 2 * grado + 1; i++) {

                    if (nodo.libros[i] == null || info.ISBN < nodo.libros[i].ISBN) {
                        entro = true;
                        recursivoInsertar(nodo.hijos[i], info);
                        i = 2 * grado;
                    }
                }
            }
        }

    }

    public void recursivoHijos(B nodo) {
        if (nodo == raiz) {
            if (raiz.hijos[0] != null) {
                raiz.bandera = true;
            }
        }
        for (int i = 0; i < nodo.hijos.length; i++) {
            if (nodo.hijos[i] != null) {
                nodo.bandera = true;
                recursivoHijos(nodo.hijos[i]);
            }
        }
    }

    //-----------METODOS DE ORDENAMIENTO----------------------// 
    int particion(Libro arr[], int inicio, int fin) {
        Libro pivote = arr[fin];
        int i = (inicio - 1);
        for (int j = inicio; j < fin; j++) {

            if (arr[j].ISBN < pivote.ISBN) {
                i++;

                Libro temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Libro temp = arr[i + 1];
        arr[i + 1] = arr[fin];
        arr[fin] = temp;

        return i + 1;
    }

    int contarLibros(Libro arr[]) {
        int longitud = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                longitud++;
            } else {
                break;
            }
        }

        return longitud;

    }

    int contarHijos(B arr[]) {
        int longitud = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                longitud++;
            } else {
                break;
            }
        }

        return longitud;

    }

    void quickSort(Libro arr[], int inicio, int fin) {

        if (inicio < fin) {
            int pi = particion(arr, inicio, fin);
            quickSort(arr, inicio, pi - 1);
            quickSort(arr, pi + 1, fin);
        }
    }

    public void ordenar(Libro arr[]) {
        int longitud = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                longitud++;
            } else {
                break;
            }
        }
        for (int ord = 0; ord < longitud; ord++) {
            for (int ord1 = 0; ord1 < longitud - 1; ord1++) {
                if (arr[ord1].ISBN > arr[ord1 + 1].ISBN) {
                    Libro tmp = arr[ord1];
                    arr[ord1] = arr[ord1 + 1];
                    arr[ord1 + 1] = tmp;

                }
            }
        }
    }

    public void ordenarNodos(B aOrdenar) {
        int i, j;
        i = 0;
        B tmp;

        while (i < 2 * grado + 3 && aOrdenar.hijos[i] != null) {
            j = 0;
            while (j < 2 * grado + 2 && aOrdenar.hijos[j] != null && aOrdenar.hijos[j + 1] != null) {
                if (aOrdenar.hijos[j].libros[0].ISBN > aOrdenar.hijos[j + 1].libros[0].ISBN) {
                    tmp = aOrdenar.hijos[j];
                    aOrdenar.hijos[j] = aOrdenar.hijos[j + 1];
                    aOrdenar.hijos[j + 1] = tmp;
                }
                j++;
            }
            i++;
        }
    }

    //----------METODO DESBORDAMIENTO----------------------------//
    public void split(B nodo) {

        B izquierda = new B(this.grado);
        B derecha = new B(this.grado);

        if (nodo.bandera) {
            for (int i = 0; i < grado + 1; i++) {
                izquierda.hijos[i] = nodo.hijos[i];
                izquierda.hijos[i].padre = izquierda;

                nodo.hijos[i] = null;

                derecha.hijos[i] = nodo.hijos[grado + 1 + i];
                derecha.hijos[i].padre = derecha;

                nodo.hijos[grado + 1 + i] = null;
            }
        }

        for (int i = 0; i < grado; i++) {
            izquierda.libros[i] = nodo.libros[i];

            nodo.libros[i] = null;

            derecha.libros[i] = nodo.libros[grado + 1 + i];

            nodo.libros[grado + 1 + i] = null;
        }

        nodo.libros[0] = nodo.libros[grado];
        nodo.libros[grado] = null;

        nodo.hijos[0] = izquierda;
        nodo.hijos[1] = derecha;
        nodo.hijos[0].padre = nodo;
        nodo.hijos[1].padre = nodo;

        recursivoHijos(raiz);
        ordenarNodos(nodo);

        if (nodo.padre != null) {
            for (int i = 0; i < nodo.padre.libros.length; i++) {
                if (nodo.padre.libros[i] == null) {
                    nodo.padre.libros[i] = nodo.libros[0];
                    nodo.libros[0] = null;
                    //int n=contarElementos(nodo.padre.libros);
                    //quickSort(nodo.padre.libros, 0, n-1);
                    ordenar(nodo.padre.libros);
                    break;
                }
            }
            int cont = contarHijos(nodo.padre.hijos);

            nodo.padre.hijos[cont] = nodo.hijos[0];
            nodo.padre.hijos[cont + 1] = nodo.hijos[1];
            nodo.padre.hijos[cont].padre = nodo.padre;
            nodo.padre.hijos[cont + 1].padre = nodo.padre;

            int actual = 0;
            for (int i = 0; i < 2 * grado + 3; i++) {
                if (nodo.padre.hijos[i] != null) {
                    if (nodo.padre.hijos[i].libros[0] == nodo.libros[0]) {
                        actual = i;
                        break;
                    }
                }
            }
            for (int j = actual; j < 2 * grado + 2; j++) {
                if (nodo.padre.hijos[j] != null && nodo.padre.hijos[j + 1] != null) {
                    nodo.padre.hijos[j] = nodo.padre.hijos[j + 1];
                    actual = j + 1;
                }
            }

            nodo.padre.hijos[actual] = null;
            //int n=contarElementos(papa.libros);
            //quickSort(nodo.padre.libros, 0, n-1);
            ordenar(nodo.padre.libros);
            ordenarNodos(nodo.padre);

            if (nodo.padre.libros[2 * grado] != null) {
                split(nodo.padre);
            }
        }
    }

    public void eliminar(long clave) {
        boolean bandera = false;
        for (int i = 0; i < ingresados.size(); i++) {
            if (ingresados.get(i).ISBN == clave) {
                bandera = true;
                ingresados.remove(i);
                break;
            }
        }
        if (bandera) {
            ArrayList<Libro> auxiliar = ingresados;
            ingresados = new ArrayList<Libro>();
            raiz = new B(this.grado);
            raiz.bandera = false;
            for (int k = 0; k < auxiliar.size(); k++) {
                Libro y = auxiliar.get(k);
                insertarLibro(y);
            }
        }
    }

    public Libro buscar(long clave) {
        for (int i = 0; i < ingresados.size(); i++) {
            if (ingresados.get(i).ISBN == clave) {
                return ingresados.get(i);
            }
        }
        return null;
    }

    public ArrayList<Libro> buscarSimilar(String titulo) {
        ArrayList<Libro> similares = new ArrayList<>();
        for (int i = 0; i < ingresados.size(); i++) {
            if (ingresados.get(i).titulo.contains(titulo)) {
                similares.add(ingresados.get(i));
            }
        }
        return similares;
    }

    int contadorNodos = 0;

    public String generarDot() {
        String concatenar = "";
        contadorNodos = 0;
        concatenar = "";
        concatenar += "digraph g {\n"
                + "node [shape = record,height=.1];";
        concatenar += graficar(raiz);
        concatenar += indexar(raiz);
        concatenar += "}\n";
        return concatenar;
    }

    public String graficar(B actual) {
        String concatenar = "";
        concatenar += "node" + contadorNodos + "[label=\"";
        actual.indice = "node" + contadorNodos;
        for (int i = 0; i < grado * 2 + 1; i++) {
            if (actual.libros[i] != null) {
                concatenar += "<f" + i + ">|{ISBN: " + actual.libros[i].ISBN + "|" + actual.libros[i].titulo + "}| ";
            }
        }
        concatenar += "\"];\n";
        contadorNodos++;
        for (int i = 0; i < 5; i++) {
            if (actual.hijos[i] != null) {
                concatenar += graficar(actual.hijos[i]);
            }
        }
        return concatenar;
    }

    public String indexar(B actual) {
        String concatenar = "";
        for (int i = 0; i < 5; i++) {
            if (actual.hijos[i] != null) {
                concatenar += actual.indice + "->" + actual.hijos[i].indice + ";\n";
                concatenar+=indexar(actual.hijos[i]);
            }
        }
        return concatenar;
    }

}
