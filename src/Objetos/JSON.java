/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Nodos.AVL;
import Nodos.DobleEnlazada;
import edd.proyectofinal.EDDProyectoFinal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Isaac Maldonado
 */
public class JSON {

    public void cargaUsuarios() throws FileNotFoundException, IOException, ParseException {

        String archivo = abrirArchivo();
        if (!archivo.equals("")) {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(new FileReader(archivo));
            JSONArray usuarios = (JSONArray) json.get("Usuarios");

            for (int i = 0; i < usuarios.size(); i++) {
                JSONObject jsonO = (JSONObject) usuarios.get(i);

                int carnet = Integer.parseInt(jsonO.get("Carnet").toString());
                String nombre = jsonO.get("Nombre").toString();
                String apellido = jsonO.get("Apellido").toString();
                String carrera = jsonO.get("Carrera").toString();
                String password = jsonO.get("Password").toString();

                Usuario usuario = new Usuario(carnet, nombre, apellido, carrera, password);
                Usuario temp = EDDProyectoFinal.hash.buscarRegistrar(carnet);
                if (temp == null) {
                    EDDProyectoFinal.hash.insertar(usuario);
                    EDDProyectoFinal.contenidoBloque.crearUsuario(usuario);
                }

            }

        }

    }

    public void cargarLibros(int usuario) throws IOException, ParseException {

        String archivo = abrirArchivo();
        if (!archivo.equals("")) {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(archivo),"UTF8"));
            JSONArray libros = (JSONArray) json.get("libros");

            for (int i = 0; i < libros.size(); i++) {
                JSONObject jsonO = (JSONObject) libros.get(i);

                Long ISBN = Long.parseLong(jsonO.get("ISBN").toString());
                String anio = jsonO.get("Año").toString();
                String idioma = jsonO.get("Idioma").toString();
                String titulo = jsonO.get("Titulo").toString();
                String editorial = jsonO.get("Editorial").toString();
                String autor = jsonO.get("Autor").toString();
                String edicion = jsonO.get("Edicion").toString();
                String categoria = jsonO.get("Categoria").toString();

                AVL temp = EDDProyectoFinal.arbolAVL.buscar(categoria);
                if (temp == null) {
                    Categoria _categoria = new Categoria(categoria, usuario);
                    EDDProyectoFinal.arbolAVL.insertar(_categoria);
                    EDDProyectoFinal.contenidoBloque.crearCategoria(_categoria);
                }
                Libro libro = new Libro(ISBN, titulo, autor, editorial, anio, edicion, categoria, idioma, usuario);
                Libro aux = EDDProyectoFinal.arbolAVL.buscarLibro(ISBN);
                if (aux == null) {
                    EDDProyectoFinal.arbolAVL.buscar(categoria).categoria.libros.insertarLibro(libro);
                    EDDProyectoFinal.contenidoBloque.crearLibro(libro);
                }

            }
        }
    }

    public void cargarBloque() throws FileNotFoundException, IOException, ParseException {

        String path = System.getProperty("user.home") + File.separator + "Documents";
        path += File.separator + "Your Custom Folder";
        path += File.separator + "salida.JSON";
        JSONParser parser = new JSONParser();
        File customDir = new File(path);
        if (customDir.exists()) {
            JSONObject json = (JSONObject) parser.parse(new FileReader(path));
            JSONArray bloques = (JSONArray) json.get("BLOQUE");
            //System.out.println("TAMANIO: "+bloques.size());
            for (int i = 0; i < bloques.size(); i++) {
                JSONObject jsonO = (JSONObject) bloques.get(i);
                int index = Integer.parseInt(jsonO.get("INDEX").toString());
                System.out.println("INDEX: "+index);
                String timeStamp = jsonO.get("TIMESTAMP").toString();
                int nonce = Integer.parseInt(jsonO.get("NONCE").toString());
                JSONArray data = (JSONArray) jsonO.get("DATA");

                for (int j = 0; j < data.size(); j++) {
                    JSONObject dentro = (JSONObject) data.get(j);
                    //System.out.println(dentro.keySet().toString());
                    switch (dentro.keySet().toString()) {
                        case "[CREAR_USUARIO]":
                            //System.out.println("heey!");
                            crearUsuario(dentro);
                            break;
                        case "[EDITAR_USUARIO]":
                            editarUsuario(dentro);
                            break;
                        case "[ELIMINAR_USUARIO]":
                            eliminarUsuario(dentro);
                            break;
                        case "[CREAR_LIBRO]":
                            crearLibro(dentro);
                            break;
                        case "[ELIMINAR_LIBRO]":
                            eliminarLibro(dentro);
                            break;
                        case "[CREAR_CATEGORIA]":
                            crearCategoria(dentro);
                            break;
                        case "[ELIMINAR_CATEGORIA]":
                            eliminarCategoria(dentro);
                            break;
                        default:
                            System.out.println("error!");
                            break;
                    }

                }
                String prevHash = jsonO.get("PREVIOUSHASH").toString();
                String hash = jsonO.get("HASH").toString();

                Bloque bloque = new Bloque(index, timeStamp, nonce, data, prevHash, hash);
                DobleEnlazada temp = new DobleEnlazada(bloque);
                EDDProyectoFinal.blockchain.insertarFin(temp);
            }

        }

    }

    private void crearUsuario(JSONObject entrada) {
        JSONObject o = (JSONObject) entrada.get("CREAR_USUARIO");
        int carnet = Integer.parseInt(o.get("Carnet").toString());
        String nombre = o.get("Nombre").toString();
        String apellido = o.get("Apellido").toString();
        String carrera = o.get("Carrera").toString();
        String password = o.get("Password").toString();

        Usuario usuario = new Usuario(carnet, nombre, apellido, carrera, password);
        EDDProyectoFinal.hash.insertar(usuario);
    }

    private void editarUsuario(JSONObject entrada) {
        JSONObject o = (JSONObject) entrada.get("EDITAR_USUARIO");
        int carnet = Integer.parseInt(o.get("Carnet").toString());
        String nombre = o.get("Nombre").toString();
        String apellido = o.get("Apellido").toString();
        String carrera = o.get("Carrera").toString();
        String password = o.get("Password").toString();

        Usuario usuario = new Usuario(carnet, nombre, apellido, carrera, password);
        EDDProyectoFinal.hash.eliminar(carnet);
        EDDProyectoFinal.hash.insertar(usuario);
    }

    private void eliminarUsuario(JSONObject entrada) {
        JSONObject o = (JSONObject) entrada.get("ELIMINAR_USUARIO");
        int carnet = Integer.parseInt(o.get("Carnet").toString());
        EDDProyectoFinal.hash.eliminar(carnet);
    }

    private void crearLibro(JSONObject entrada) {
        JSONObject o = (JSONObject) entrada.get("CREAR_LIBRO");

        long ISBN = Long.parseLong(o.get("ISBN").toString());
        String anio = o.get("Anio").toString();
        String idioma = o.get("Idioma").toString();
        String titulo = o.get("Titulo").toString();
        String editorial = o.get("Editorial").toString();
        String autor = o.get("Autor").toString();
        String edicion = o.get("Edicion").toString();
        String categoria = o.get("Categoria").toString();
        int usuario = Integer.parseInt(o.get("Usuario").toString());

        Libro libro = new Libro(ISBN, titulo, autor, editorial, anio, edicion, categoria, idioma, usuario);
        EDDProyectoFinal.arbolAVL.buscar(categoria).categoria.libros.insertarLibro(libro);
        EDDProyectoFinal.contenidoBloque.crearLibro(libro);
    }

    private void eliminarLibro(JSONObject entrada) {
        JSONObject o = (JSONObject) entrada.get("ELIMINAR_LIBRO");

        long ISBN = Long.parseLong(o.get("ISBN").toString());
        String categoria = o.get("Categoria").toString();

        AVL aux = EDDProyectoFinal.arbolAVL.buscar(categoria);
        aux.categoria.libros.eliminar(ISBN);
    }

    private void crearCategoria(JSONObject entrada) {
        JSONObject o = (JSONObject) entrada.get("CREAR_CATEGORIA");
        String categoria = o.get("Nombre").toString();
        int carnet = Integer.parseInt(o.get("Usuario").toString());
        Categoria temp = new Categoria(categoria, carnet);
        EDDProyectoFinal.arbolAVL.insertar(temp);
    }
    
    private void eliminarCategoria(JSONObject entrada){
         JSONObject o = (JSONObject) entrada.get("ELIMINAR_CATEGORIA");
         String valor=o.get("Nombre").toString();
         EDDProyectoFinal.arbolAVL.eliminar(valor);
    }

    private String abrirArchivo() {

        JFileChooser jf = new JFileChooser();
        jf.setAcceptAllFileFilterUsed(false);
        jf.setFileFilter(new FileNameExtensionFilter("Archivos *.json", "json"));
        if (jf.showOpenDialog(jf.getParent()) == JFileChooser.APPROVE_OPTION) {
            String route = jf.getSelectedFile().getAbsolutePath();
            return route;
        }

        return "";
    }

}
