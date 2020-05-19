/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nodos;

import Estructuras.ListaDobleEnlazada;
import Objetos.Usuario;

/**
 *
 * @author Isaac Maldonado
 */
public class Hash {
   public ListaDobleEnlazada encadenado;
   public Hash(){
       this.encadenado=new ListaDobleEnlazada();
   }
   
//----------METODOS PARA RETORNO INFORMACION-----------------------// 
   int getTamanio(){
      return this.encadenado.getTamanio();
   }
}
