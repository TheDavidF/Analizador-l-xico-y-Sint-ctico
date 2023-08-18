/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;
import java.awt.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author DAVID
 */
public class Graficador {
    private ArrayList<Token> lexemas;
    private MutableGraph grafo;
    
    public Graficador(ArrayList<Token> tokens){
        this.lexemas = tokens;   
    }
    
    public void crearGrafos(){
        
    }
}
