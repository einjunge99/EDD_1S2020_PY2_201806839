/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import org.json.simple.JSONArray;

/**
 *
 * @author Isaac Maldonado
 */
public class Bloque {

    public int index;
    public String timeStamp;
    public int nonce;
    public JSONArray data;
    public String prevHash;
    public String hash;

    public Bloque(int index, String timeStamp, int nonce, JSONArray data, String prevHash, String hash) {
        this.index = index;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.data = data;
        this.prevHash = prevHash;
        this.hash = hash;
    }
    /*---------lo que tengo que hacer es, cargar los bloques a la lista, la data como json array, y cada
     que se vaya a agregar uno ya sera mas facil el acceso y volver a reescribir el archivo.
     */

}
