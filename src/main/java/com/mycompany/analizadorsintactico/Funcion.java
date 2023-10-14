/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorsintactico;

/**
 *
 * @author DAVID
 */
public class Funcion{
    
    private String nombre;
    private int llamadas;
    private int linea;
    private int columna;
    
    public Funcion (String nombre, int linea, int columna){
        this.nombre = nombre;
        this.llamadas = 0;
        this.linea = linea;
        this.columna = columna;
            
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String id) {
        this.nombre = id;
    }

    public int getLlamadas() {
        return llamadas;
    }

    public void setLlamadas(int llamadas) {
        this.llamadas = llamadas;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public Object getNomre() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
}
