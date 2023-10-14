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
    
    private ArrayList<Instruccion> instrucciones;
    private String nombre;
    private int linea;
    private int columna;
    
    public BloqueDeCodigo(String nombre, int linea, int columna){
        instrucciones = new ArrayList<>();
        this.nombre = nombre;
        this.linea = linea;
        this.columna = columna;
    }
    
    public void agregarInstruccion(Instruccion instruccion) {
        instrucciones.add(instruccion);
    }

    public ArrayList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(ArrayList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "Nombre: "+nombre+"   linea: " +linea+"   column: "+columna +"\n";
    }
    
    
}
