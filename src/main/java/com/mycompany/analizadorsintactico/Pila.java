/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorsintactico;


/**
 *
 * @author DAVID
 */
public class Pila {
    
    private Nodo raiz;
    
    public Pila () {
        raiz=null;
    }
    
    public void insertar(String token) {
    	Nodo nuevo;
        nuevo = new Nodo();
        nuevo.setToken(token);
        if (raiz==null)
        {
            nuevo.setSiguiente(null);
            raiz = nuevo;
        }
        else
        {
            nuevo.setSiguiente(raiz); 
            raiz = nuevo;
        }
    }
    
        public String extraer ()
    {
        if (raiz!=null)
        {
            String token = raiz.getToken();
            raiz = raiz.getSiguiente();
            return token;
        }
        else
        {
            return null;
        }
    }
    
    public boolean estaVacia(){
       if(raiz != null){
           return false;
       } else {
           return true;
       }
    }
}
