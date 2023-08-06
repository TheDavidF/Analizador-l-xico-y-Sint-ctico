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
    
    //patron para identificado
    private final String identificador = "[^\\d][a-zA-Z\\_\\d]+";
    //patron para keyWords
    private final String keyWords = "\\b(and|as|assert|break|class|continue|def|del|elif|else|except|False|finally|for|from|global|if|import|in|is|lambda|None|nonlocal|not|or|pass|raise|return|True|try|while|with|yield)\\b";
    private Pattern patternid;
    private Pattern pKeywords;
    
    
    public Expresion(){
        patternid = Pattern.compile(identificador);
        pKeywords = Pattern.compile(keyWords);
    }
    
    
    public boolean validarIdentificador(String id){
        //patrón para identificadores "[^\\d][a-zA-Z\\_\\d]*"
        return id.matches(patternid.toString());
    }
    
    public boolean validarKeywords(String txt){
        //patron para validar palabras reservadas
        return txt.matches(pKeywords.toString());
    }
                                                
}
