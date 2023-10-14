/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorsintactico;

import java.util.ArrayList;

/**
 *
 * @author DAVID
 */
public class Instruccion  {
    
    private ArrayList<String> cadena;
    private BloqueDeCodigo bloque;
    
    public Instruccion (){
        this.bloque = new BloqueDeCodigo("", 1, 1);
        cadena = new ArrayList<>();
    }
    
    public void agregarCadena(String string){
        cadena.add(string);
    }
    
    public void vaciarInstruccion (){
        this.bloque.agregarInstruccion(this);
        cadena.clear();
    }

    public ArrayList<String> getCadena() {
        return cadena;
    }

    public void setCadena(ArrayList<String> cadena) {
        this.cadena = cadena;
    }

    public BloqueDeCodigo getBloque() {
        return bloque;
    }

    public void setBloque(BloqueDeCodigo bloque) {
        this.bloque = bloque;
    }
    
    
}
