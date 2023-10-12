/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorsintactico;

/**
 *
 * @author DAVID
 */
public class Variable{
    
    private String id;
    private int llamadas;
    private int linea;
    
    public Variable (String variable, int linea){
        this.id = variable;
        this.llamadas = 0;
        this.linea = linea;
            
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLlamadas() {
        return llamadas;
    }

    public void setLlamadas(int llamadas) {
        this.llamadas = llamadas;
    }
    
    
}
