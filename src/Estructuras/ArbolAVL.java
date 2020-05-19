/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

import Nodos.AVL;
import Objetos.Categoria;
import Objetos.Libro;
import Objetos.Logico;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Isaac Maldonado
 */
public class ArbolAVL {

    public AVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }
    /*
     -----------------CASOS INSERCION----------------------
     ----S0: Inserción subarbol izquierdo, rama izquierda
     ----S1: Inserción subarbol derecho, rama derecha
     ----S2: Inserción subarbol izquierdo, rama derecha
     ----S3: Inserción subarbol derecho, rama izquierda
    
     ----Referencia de libro Estructuras de Datos en Java, Joyanes(1ra edición)
     */

    private AVL rotacionS0(AVL n0, AVL n1) {
        n0.izquierda = n1.derecha;
        n1.derecha = n0;
        if (n1.fe == -1) {
            n0.fe = 0;
            n1.fe = 0;
        } else {
            n0.fe = -1;
            n1.fe = 1;
        }
        return n1;
    }

    private AVL rotacionS1(AVL n0, AVL n1) {
        n0.derecha = n1.izquierda;
        n1.izquierda = n0;
        if (n1.fe == 1) {
            n0.fe = 0;
            n1.fe = 0;
        } else {
            n0.fe = 1;
            n1.fe = -1;
        }
        return n1;
    }

    private AVL rotacionS2(AVL n0, AVL n1) {
        AVL n2;

        n2 = (AVL) n1.derecha;
        n0.izquierda = n2.derecha;
        n2.derecha = n0;
        n1.derecha = n2.izquierda;
        n2.izquierda = n1;

        if (n2.fe == 1) {
            n1.fe = -1;
        } else {
            n1.fe = 0;
        }
        if (n2.fe == -1) {
            n0.fe = 1;
        } else {
            n0.fe = 0;
        }
        n2.fe = 0;
        return n2;
    }

    private AVL rotacionS3(AVL n0, AVL n1) {
        AVL n2;

        n2 = (AVL) n1.izquierda;
        n0.derecha = n2.izquierda;
        n2.izquierda = n0;
        n1.izquierda = n2.derecha;
        n2.derecha = n1;

        if (n2.fe == 1) {
            n0.fe = -1;
        } else {
            n0.fe = 0;
        }
        if (n2.fe == -1) {
            n1.fe = 1;
        } else {
            n1.fe = 0;
        }
        n2.fe = 0;
        return n2;
    }

    public void insertar(Categoria categoria) {
        Logico bandera = new Logico(false);
        //boolean bandera = false;
        this.raiz = insertarRecursivo(this.raiz, categoria, bandera);
    }

    private AVL insertarRecursivo(AVL nodo, Categoria info, Logico bandera) {
        AVL n1;
        if (nodo == null) {
            nodo = new AVL(info);
            bandera.v = true;
        } else if (info.categoria.compareTo(nodo.categoria.categoria) < 0) {
            AVL izquierda;
            izquierda = insertarRecursivo(nodo.izquierda, info, bandera);
            nodo.izquierda = izquierda;

            if (bandera.v) {

                switch (nodo.fe) {
                    case 1:
                        nodo.fe = 0;
                        bandera.v = false;
                        break;
                    case 0:
                        nodo.fe = -1;
                        break;
                    case -1:
                        n1 = nodo.izquierda;
                        if (n1.fe == -1) {
                            nodo = rotacionS0(nodo, n1);
                        } else {
                            nodo = rotacionS2(nodo, n1);
                        }
                        bandera.v = false;
                }

            }
        } else if (info.categoria.compareTo(nodo.categoria.categoria) > 0) {
            AVL derecha;
            derecha = insertarRecursivo(nodo.derecha, info, bandera);
            nodo.derecha = derecha;

            if (bandera.v) {

                switch (nodo.fe) {
                    case 1:
                        n1 = nodo.derecha;
                        if (n1.fe == 1) {
                            nodo = rotacionS1(nodo, n1);
                        } else {
                            nodo = rotacionS3(nodo, n1);
                        }
                        bandera.v = false;
                        break;
                    case 0:
                        nodo.fe = 1;
                        break;
                    case -1:
                        nodo.fe = 0;
                        bandera.v = false;
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Categoria existente!");
        }
        return nodo;
    }

    public void eliminar(String categoria) {
        Logico bandera = new Logico(false);
        this.raiz = eliminarRecursivo(this.raiz, categoria, bandera);
    }

    private AVL eliminarRecursivo(AVL nodo, String info, Logico bandera) {
        if (nodo == null) {
            System.out.println("No encontrado");
        } else if (info.compareTo(nodo.categoria.categoria) < 0) {
            AVL izquierdo;
            izquierdo = eliminarRecursivo(nodo.izquierda, info, bandera);
            nodo.izquierda = izquierdo;
            if (bandera.v) {
                nodo = equilibrarS0(nodo, bandera);
            }
        } else if (info.compareTo(nodo.categoria.categoria) > 0) {
            AVL derecha;
            derecha = eliminarRecursivo(nodo.derecha, info, bandera);
            nodo.derecha = derecha;
            if (bandera.v) {
                nodo = equilibrarS1(nodo, bandera);
            }
        } else {
            AVL aux;
            aux = nodo;
            if (aux.izquierda == null) {
                nodo = aux.derecha;
                bandera.v = true;
            } else if (aux.derecha == null) {
                nodo = aux.izquierda;
                bandera.v = true;
            } else {
                AVL izquierda;
                izquierda = remplazar(nodo, nodo.izquierda, bandera);
                nodo.izquierda = izquierda;
                if (bandera.v) {
                    nodo = equilibrarS0(nodo, bandera);
                }
            }
            aux = null;

        }
        return nodo;
    }

    private AVL remplazar(AVL nodo, AVL temp, Logico bandera) {
        if (temp.derecha != null) {
            AVL aux;
            aux = remplazar(nodo, temp.derecha, bandera);
            temp.derecha = aux;
            if (bandera.v) {
                temp = equilibrarS1(temp, bandera);
            }
        } else {
            nodo.categoria = temp.categoria;
            nodo = temp;
            temp = temp.izquierda;
            nodo = null;
            bandera.v = true;
        }
        return temp;
    }

    private AVL equilibrarS0(AVL nodo, Logico bandera) {
        AVL n1;
        switch (nodo.fe) {
            case -1:
                nodo.fe = 0;
                break;
            case 0:
                nodo.fe = 1;
                bandera.v = false;
                break;
            case 1:
                n1 = nodo.derecha;
                if (n1.fe >= 0) {
                    if (n1.fe == 0) {
                        bandera.v = false;
                    }
                    nodo = rotacionS1(nodo, n1);
                } else {
                    nodo = rotacionS3(nodo, n1);
                }
                break;

        }
        return nodo;
    }

    private AVL equilibrarS1(AVL nodo, Logico bandera) {
        AVL n1;
        switch (nodo.fe) {
            case -1:
                n1 = nodo.izquierda;
                if (n1.fe <= 0) {
                    if (n1.fe == 0) {
                        bandera.v = false;
                    }
                    nodo = rotacionS0(nodo, n1);
                } else {
                    nodo = rotacionS2(nodo, n1);
                }

                break;
            case 0:
                nodo.fe = -1;
                bandera.v = false;
                break;
            case 1:
                nodo.fe = 0;
                break;

        }
        return nodo;
    }

    public AVL buscar(String categoria) {
        AVL temp = buscarRecursivo(this.raiz, categoria);
        return temp;
    }

    public AVL buscarRecursivo(AVL nodo, String categoria) {
        if (nodo != null) {
            if (nodo.categoria.categoria.equals(categoria)) {
                return nodo;
            } else {
                AVL temp = buscarRecursivo(nodo.izquierda, categoria);
                if (temp == null) {
                    temp = buscarRecursivo(nodo.derecha, categoria);
                }
                return temp;
            }
        } else {
            return null;
        }
    }

    public ArrayList<Libro> buscarSimilar(String titulo) {
        ArrayList<Libro> similar = new ArrayList<>();
        similar = similarRecursivo(this.raiz, titulo, similar);
        return similar;
    }

    public ArrayList<Libro> similarRecursivo(AVL nodo, String titulo, ArrayList<Libro> similar) {
        ArrayList<Libro> temp = similar;
        if (nodo != null) {
            temp = nodo.categoria.libros.buscarSimilar(titulo);
            temp = similarRecursivo(nodo.izquierda, titulo, temp);
            temp = similarRecursivo(nodo.derecha, titulo, temp);
        }
        return temp;

    }

    public Libro buscarLibro(Long ISBN) {
        Libro temp = buscarRecursivo(this.raiz, ISBN);
        return temp;
    }

    public Libro buscarRecursivo(AVL nodo, Long ISBN) {
        if (nodo != null) {
            Libro libro = nodo.categoria.libros.buscar(ISBN);
            if (libro != null) {
                return libro;
            } else {
                Libro temp = buscarRecursivo(nodo.izquierda, ISBN);
                if (temp == null) {
                    temp = buscarRecursivo(nodo.derecha, ISBN);
                }
                return temp;
            }
        } else {
            return null;
        }
    }

    int contadorIndice = 0;

    private String llenarDot(AVL nodo) {
        String concatenar = "";
        if (nodo != null) {
            nodo.indice = "nodo_" + contadorIndice;
            contadorIndice++;
            concatenar += nodo.indice + " [label=\"" + nodo.categoria.categoria + "\"];\n";
            if (nodo.izquierda != null) {
                int aux = contadorIndice;
                concatenar += "R_" + contadorIndice + " [style=invis];\n";
                concatenar += llenarDot(nodo.izquierda) + nodo.indice + " -> " + nodo.izquierda.indice + "\n " + nodo.indice + " -> R_" + aux + "[ style = invis ];\n";

            }

            if (nodo.derecha != null) {
                int aux = contadorIndice;
                concatenar += "L_" + contadorIndice + " [style=invis];\n";
                concatenar += llenarDot(nodo.derecha) +nodo.indice + " -> L_" + aux + "[ style = invis ];\n"+nodo.indice + " -> " + nodo.derecha.indice + "\n";
            }

        }
        return concatenar;
    }

    public String generarDot() {
        contadorIndice = 0;
        String concatenar = "";
        concatenar += "digraph structs {\n";
        concatenar+=" ordering=out ;\n";
        concatenar += llenarDot(this.raiz);
        concatenar += " }";
        return concatenar;
    }

    private ArrayList llenarPreOrden(AVL nodo, ArrayList<String> temp) {
        ArrayList<String> lista = temp;
        if (nodo != null) {
            lista.add(nodo.categoria.categoria);
            llenarPreOrden(nodo.izquierda, lista);
            llenarPreOrden(nodo.derecha, lista);

        }
        return lista;
    }

    private ArrayList llenarInOrden(AVL nodo, ArrayList<String> temp) {
        ArrayList<String> lista = temp;
        if (nodo != null) {

            llenarPreOrden(nodo.izquierda, lista);
            lista.add(nodo.categoria.categoria);
            llenarPreOrden(nodo.derecha, lista);

        }
        return lista;
    }

    private ArrayList llenarPostOrden(AVL nodo, ArrayList<String> temp) {
        ArrayList<String> lista = temp;
        if (nodo != null) {

            llenarPreOrden(nodo.izquierda, lista);
            llenarPreOrden(nodo.derecha, lista);
            lista.add(nodo.categoria.categoria);

        }
        return lista;
    }

    public String generarOrden(int tipo) {
        ArrayList<String> temp = new ArrayList<String>();
        String concat = "";
        concat += "digraph D {\n"
                + "  rankdir=LR;";
        switch (tipo) {
            case 1:
                temp = llenarPreOrden(this.raiz, temp);
                break;
            case 2:
                temp = llenarInOrden(this.raiz, temp);
                break;
            case 3:
                temp = llenarPostOrden(this.raiz, temp);
                break;
        }

        for (int i = 0; i < temp.size(); i++) {
            concat += "N" + i + "[label=\"" + temp.get(i) + "\"] \n";
        }

        for (int i = 0; i < temp.size() - 1; i++) {
            int aux = i + 1;
            concat += "N" + i + " -> " + "N" + aux + "\n";
        }
        concat += " }";
        return concat;
    }

}
