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
public class BloqueDeCodigo {
    
    private ArrayList<Pila> instrucciones;
    private String nombre;
    private int linea;
    private int columna;
    
    public BloqueDeCodigo(String nombre, int linea, int columna){
        instrucciones = new ArrayList<>();
        this.nombre = nombre;
        this.linea = linea;
        this.columna = columna;
    }
    
    public void agregarInstruccion(Pila pila) {
        instrucciones.add(pila);
    }
}
