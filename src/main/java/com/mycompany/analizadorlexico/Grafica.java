/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;
import java.awt.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author DAVID
 */
public class Grafica {
    private MutableGraph grafo;
    private ArrayList<Token> tokens;
    private Token token;
    
    public Grafica(){
    }

    public String crearGrafos(Token token){
        String lexema = token.getCadena();
        ArrayList<Character> caracteres = new ArrayList<>();
        int a = 0;
        while (a < lexema.length()){
            caracteres.add(lexema.charAt(a));
            a++;
        }
        String dotCode =    "digraph G {\n"+
                            "   node [shape = circle]\n"
                           +"   node [style = filled]\n"
                           +"   rankdir=LR\n";
        int i = 0;
        String grafo = "";
        for (Character character : caracteres) {
            if(lexema.length() - 1 == i){
                if(character == '.'){
                    grafo+= "\\.";
                } else{
                    grafo+= character;
                }
            } else if(i < lexema.length()){
                if(character == '.'){
                    grafo+= ""+"\\." +"->";
                } else {
                    grafo+= ""+character +"->";
                }
                
            }
            i++;
        }
        dotCode+= grafo+"}";

        try {
            FileWriter writer = new FileWriter("graph.dot");
            writer.write(dotCode);
            writer.close();
            System.out.println("El grafo se escribió correctamente");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dotCode);
        return dotCode;
    }
    
    public void crearImagen(String dotCode) {
        String formato = "png"; // Puedes usar otros formatos como svg, pdf, etc.
        String nombreImagen = "grafica." + formato;

        try {
            Process process = new ProcessBuilder("dot", "-T" + formato, "-o", nombreImagen)
                    .redirectErrorStream(true)
                    .start();

            process.getOutputStream().write(dotCode.getBytes());
            process.getOutputStream().close();
            process.waitFor();
            
            System.out.println("Imagen generada: " + nombreImagen);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
