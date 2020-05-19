/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edd.proyectofinal;

import Estructuras.ArbolAVL;
import Estructuras.ListaDobleEnlazada;
import Estructuras.TablaHash;
import Interfaz.Login;
import Objetos.Contenido;
import Objetos.JSON;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;


/**
 *
 * @author Isaac Maldonado
 */
public class EDDProyectoFinal {

    /**
     * @param args the command line arguments
     */
    public static TablaHash hash = new TablaHash(45);
    public static ArbolAVL arbolAVL = new ArbolAVL();
    public static Contenido contenidoBloque = new Contenido();
    public static ListaDobleEnlazada blockchain = new ListaDobleEnlazada();

   




    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {

        Login prueba = new Login();
        prueba.setVisible(true);

        String path = System.getProperty("user.home") + File.separator + "Documents";
        path += File.separator + "Your Custom Folder";
        File customDir = new File(path);

        if (customDir.exists()) {
            System.out.println(customDir + " already exists");
            JSON carga = new JSON();
            carga.cargarBloque();
            System.out.println("Bloques en el sistema: " + blockchain.getTamanio());
        } else if (customDir.mkdirs()) {
            System.out.println(customDir + " was created");
        } else {
            System.out.println(customDir + " was not created");
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              
            }
        });

    }

}
