/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author DAVID
 */
public class Expresion {
    
    //patron para otros operadores 
    private final String signos = "[\\.\\(\\)\\{\\}\\[\\]\\,\\;\\:]{1}";
    private Pattern pSignos;
    //patron para un booleano
    private final String boleano = "(True|False)";
    private Pattern pBoleano;
    //patron para un comentario de una linea
    private final String comentario ="^\\s*#[^#]*$";
    private Pattern pComentario;
    //patron para cadena o string
    private final String cadena = "\\\"[^\\\"]*\\\"|\\'[^\\']*\\'";
    private Pattern pCadena;
    //patron para operadores de asignacion
    private final String asignacion = "(=|\\+=|\\*=|\\/=|-=)";
    private Pattern pAsignacion;
    //patron para operadores logicos
    private final String logico = "(and|or|not)";
    private Pattern pLogico;
    //patron para operadores de comparación
    private final String comparador = "[=]{2}|(!=|<=|>=|<|>)";
    private Pattern pComparador;
    //patron para un entero
    private final String entero = "[0-9]+";
    private Pattern pEntero;
    //patron para decimales
    private final String decimal = "[0-9]+[.]{1}[0-9]+";
    private Pattern pDecimal;
    //patron para operadores aritmeticos
    private final String operador = "[-+*/]|[/]{1,2}|[*]{1,2}";
    private Pattern pOperador;
    //patron para identificado
    private final String identificador = "[a-zA-Z_][a-zA-Z\\d_]*";
    private Pattern patternid;
    //patron para keyWords
    private final String keyWords = "\\b(print|and|as|assert|break|class|continue|def|del|elif|else|except|False|finally|for|from|global|if|import|in|is|lambda|None|nonlocal|not|or|pass|raise|return|True|try|while|with|yield)\\b";
    private Pattern pKeywords;
    
    
    
    
    public Expresion(){
        patternid = Pattern.compile(identificador);
        pKeywords = Pattern.compile(keyWords);
        pOperador = Pattern.compile(operador);
        pEntero = Pattern.compile(entero);
        pDecimal = Pattern.compile(decimal);
        pComparador =Pattern.compile(comparador);
        pLogico = Pattern.compile(logico);
        pAsignacion = Pattern.compile(asignacion);
        pCadena = Pattern.compile(cadena);
        pComentario = Pattern.compile(comentario);
        pBoleano = Pattern.compile(boleano);
        pSignos = Pattern.compile(signos);
    }
    
    
    public boolean validarIdentificador(String txt){
        //patrón para identificadores "[^\\d][a-zA-Z\\_\\d]*"
        return txt.matches(patternid.toString());
    }
    
    public boolean validarKeywords(String txt){
        //patron para validar palabras reservadas
        return txt.matches(pKeywords.toString());
    }
    
    public boolean validarOperador(String txt){
        return txt.matches(pOperador.toString());
    }
                                     
    public boolean validarEntero(String txt){
        return txt.matches(pEntero.toString());
    }
    
    public boolean validarComparador(String txt){
        return txt.matches(pComparador.toString());
    }
    
    public boolean validarLogico(String txt){
        return txt.matches(pLogico.toString());
    }
    
    public boolean validarAsignacion(String txt){
        return txt.matches(pAsignacion.toString());
    }
    
    public boolean validarDecimal(String txt){
        return txt.matches(pDecimal.toString());
    }
    
    public boolean validarCadena(String txt){
        return txt.matches(pCadena.toString());
    }
    
    public boolean validarComentario(String txt){
        return txt.matches(pComentario.toString());
    }
     
    public boolean validarBoleano(String txt){
        return txt.matches(pBoleano.toString());
    } 
    
    public boolean validarSignos(String txt){
        return txt.matches(pSignos.toString());
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getComentario() {
        return comentario;
    }

    public String getCadena() {
        return cadena;
    }

    public String getEntero() {
        return entero;
    }

    public String getDecimal() {
        return decimal;
    }
    
    
}
