/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorsintactico;


/**
 *
 * @author DAVID
 */
public class Nodo {
    
    private String cadena;
    private Nodo siguiente;
    
    public Nodo (){
    }

    public String getToken() {
        return cadena;
    }

    public void setToken(String token) {
        this.cadena = token;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    
    
}
