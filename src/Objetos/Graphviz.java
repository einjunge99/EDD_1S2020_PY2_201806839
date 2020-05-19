/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.File;
import java.io.FileWriter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Isaac Maldonado
 */

/*
 Con referecia al post de Overflow:
 https://stackoverflow.com/questions/570401/in-java-under-windows-how-do-i-find-a-redirected-desktop-folder

 */
public class Graphviz {

    public Graphviz(String direccionDot, String direccionPng, String contenido) {
        dibujar(direccionDot, direccionPng, contenido);
    }

    public void dibujar(String dot, String png, String contenido) {
        try {

            File home = FileSystemView.getFileSystemView().getHomeDirectory();
            String direccionDot=home.toString()+"\\"+dot;
            String direccionPng=home.toString()+"\\"+png;
            FileWriter archivoDot = new FileWriter(direccionDot);
            archivoDot.write(contenido);
            archivoDot.close();

            ProcessBuilder pbuilder;
            pbuilder = new ProcessBuilder("dot", "-Tpng", "-o", direccionPng, direccionDot);
            pbuilder.start();
            pbuilder = new ProcessBuilder("cmd.exe", "/c", "cd " + home.toString() + " && start salida.png");
            pbuilder.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
