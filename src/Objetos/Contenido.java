/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Nodos.DobleEnlazada;
import edd.proyectofinal.EDDProyectoFinal;
import static edd.proyectofinal.EDDProyectoFinal.blockchain;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Isaac Maldonado
 */
public class Contenido {

    JSONArray data = new JSONArray();
    JSONArray bloques = new JSONArray();

    public Contenido() {

    }

    public void crearUsuario(Usuario usuario) {

        JSONObject detalles = new JSONObject();
        detalles.put("Carnet", usuario.carnet);
        detalles.put("Nombre", usuario.nombre);
        detalles.put("Apellido", usuario.apellido);
        detalles.put("Carrera", usuario.carrera);
        detalles.put("Password", usuario.pass);

        JSONObject objeto = new JSONObject();
        objeto.put("CREAR_USUARIO", detalles);

        data.add(objeto);

    }

    public void editarUsuario(Usuario usuario) {

        JSONObject detalles = new JSONObject();
        detalles.put("Carnet", usuario.carnet);
        detalles.put("Nombre", usuario.nombre);
        detalles.put("Apellido", usuario.apellido);
        detalles.put("Carrera", usuario.carrera);
        detalles.put("Password", usuario.pass);

        JSONObject objeto = new JSONObject();
        objeto.put("EDITAR_USUARIO", detalles);

        data.add(objeto);

    }

    public void eliminarUsuario(int carnet) {

        JSONObject detalles = new JSONObject();
        detalles.put("Carnet", carnet);

        JSONObject objeto = new JSONObject();
        objeto.put("ELIMINAR_USUARIO", detalles);

        data.add(objeto);
    }

    public void crearLibro(Libro libro) {

        JSONObject detalles = new JSONObject();
        detalles.put("ISBN", libro.ISBN);
        detalles.put("Anio", Integer.parseInt(libro.anio));
        detalles.put("Idioma", libro.idioma);
        detalles.put("Titulo", libro.titulo);
        detalles.put("Editorial", libro.editorial);
        detalles.put("Autor", libro.autor);
        detalles.put("Edicion", Integer.parseInt(libro.edicion));
        detalles.put("Categoria", libro.categoria);
        detalles.put("Usuario", libro.usuario);

        JSONObject objeto = new JSONObject();
        objeto.put("CREAR_LIBRO", detalles);

        data.add(objeto);

    }

    public void eliminarLibro(Libro libro) {

        JSONObject detalles = new JSONObject();
        detalles.put("ISBN", libro.ISBN);
        detalles.put("Titulo", libro.titulo);
        detalles.put("Categoria", libro.categoria);

        JSONObject objeto = new JSONObject();
        objeto.put("ELIMINAR_LIBRO", detalles);

        data.add(objeto);

    }

    public void crearCategoria(Categoria categoria) {

        JSONObject detalles = new JSONObject();
        detalles.put("Nombre", categoria.categoria);
        detalles.put("Usuario", categoria.usuario);

        JSONObject objeto = new JSONObject();
        objeto.put("CREAR_CATEGORIA", detalles);

        data.add(objeto);

    }

    public void eliminarCategoria(String categoria) {

        JSONObject detalles = new JSONObject();
        detalles.put("Nombre", categoria);

        JSONObject objeto = new JSONObject();
        objeto.put("ELIMINAR_CATEGORIA", detalles);

        data.add(objeto);

    }

    private void agregarBloques() {
        DobleEnlazada temp = blockchain.getPrimero();
        while (temp != null) {
            JSONObject detalles = new JSONObject();
            detalles.put("INDEX", temp.bloques.index);
            detalles.put("TIMESTAMP", temp.bloques.timeStamp);
            detalles.put("NONCE", temp.bloques.nonce);
            System.out.println("DENTRO: " + temp.bloques.data);
            detalles.put("DATA", temp.bloques.data);
            detalles.put("PREVIOUSHASH", temp.bloques.prevHash);
            detalles.put("HASH", temp.bloques.hash);
            bloques.add(detalles);
            temp = temp.siguiente;
        }

    }

    public void generarBloque() throws IOException {
        System.out.println("-----------------------INICIO DE ANALISIS-----------------------");
        agregarBloques();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy::HH:mm:ss");
        Date dateObject = new Date();

        int tamanio = blockchain.getTamanio();

        String prevHash = "0000";
        int index = 0;
        if (tamanio != 0) {
            DobleEnlazada temp = blockchain.getUltimo();
            prevHash = temp.bloques.hash;
            index = temp.bloques.index + 1;
        }

        String fecha = formatter.format(dateObject);

        hashFinal = Integer.valueOf(index) + fecha + prevHash + this.data;

        int nonce = getNonce(hashFinal);
        JOptionPane.showMessageDialog(null, "Minado completado!");

        JSONObject detalles = new JSONObject();
        detalles.put("INDEX", index);
        detalles.put("TIMESTAMP", fecha);
        detalles.put("NONCE", nonce);
        detalles.put("DATA", this.data);
        detalles.put("PREVIOUSHASH", prevHash);
        detalles.put("HASH", hashFinal);
        bloques.add(detalles);

        JSONObject objeto = new JSONObject();
        objeto.put("BLOQUE", bloques);

        String path = System.getProperty("user.home") + File.separator + "Documents";
        path += File.separator + "Your Custom Folder";
        path += File.separator + "salida.JSON";
        File myFoo = new File(path);
        FileWriter fooWriter = new FileWriter(myFoo, false); // true to append
        // false to overwrite.
        fooWriter.write(objeto.toJSONString());
        fooWriter.close();

        JSONArray dataAux = new JSONArray();
        for (int i = 0; i < this.data.size(); i++) {
            dataAux.add(this.data.get(i));
        }
        Bloque bloque = new Bloque(index, fecha, nonce, dataAux, prevHash, hashFinal);
        DobleEnlazada temp = new DobleEnlazada(bloque);
        EDDProyectoFinal.blockchain.insertarFin(temp);

        this.bloques.clear();
        this.data.clear();
        System.out.println("VACIO: " + data);

    }

    public static String sha256(String input) {
        try {
            MessageDigest sha
                    = MessageDigest
                    .getInstance(
                            "SHA-256");
            int i = 0;

            byte[] hash
                    = sha.digest(
                            input.getBytes("UTF-8"));

            StringBuffer hexHash
                    = new StringBuffer();

            while (i < hash.length) {
                String hex
                        = Integer.toHexString(
                                0xff & hash[i]);
                if (hex.length() == 1) {
                    hexHash.append('0');
                }
                hexHash.append(hex);
                i++;
            }

            return hexHash.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static String hashFinal;

    public static int getNonce(String entrada) {
        int nonce = 0;
        while (true) {
            hashFinal = sha256(entrada + String.valueOf(nonce));
            if (hashFinal.substring(0, 4).equals("0000")) {
                System.out.println(hashFinal);
                break;
            }
            nonce++;
        }
        return nonce;
    }

}
