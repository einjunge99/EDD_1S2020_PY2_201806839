/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

/**
 *
 * @author Isaac Maldonado
 */
public class Usuario {

    public int carnet;
    public String nombre;
    public String apellido;
    public String carrera;
    public String pass;
    public Usuario(int carnet, String nombre, String apellido, String carrera, String pass) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.apellido = apellido;
        this.carrera = carrera;
        this.pass = pass;
    }

    
    
}
