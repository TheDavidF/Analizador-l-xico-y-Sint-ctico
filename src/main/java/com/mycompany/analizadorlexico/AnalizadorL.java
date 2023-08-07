/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico;

import java.util.ArrayList;

/**
 *
 * @author DAVID
 */
public class AnalizadorL {

    private Expresion expresion;
    private int columnaActual;
    private int lineaActual;
    private String cadena;

    public AnalizadorL() {
        expresion = new Expresion();
    }

    public ArrayList<Token> listarTokens() {
        ArrayList<Token> tokens = new ArrayList<>();
        while (columnaActual < cadena.length()) {
            char charActual = cadena.charAt(columnaActual);
            if (Character.isWhitespace(charActual)) {
                columnaActual++;
            } else if (Character.isLetter(columnaActual)) {
                // Capturar palabra
                String identificador = "";
                while (columnaActual < cadena.length() && Character.isLetterOrDigit(cadena.charAt(columnaActual))) {
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                if (expresion.validarLogico(identificador)) {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.OPERADOR_LOGICO, identificador));
                } else if (expresion.validarKeywords(identificador)) {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.PALABRA_RESERVADA, identificador));
                } else if (expresion.validarIdentificador(identificador)) {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.IDENTIFICADOR, identificador));
                } else {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.ERROR_LEXICO, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '#') {
                String identificador = "";
                while (columnaActual < cadena.length() && cadena.charAt(columnaActual) != '\n') {
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                lineaActual++;
                if (expresion.validarComentario(identificador)) {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.COMENTARIO, identificador));
                } else {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.ERROR_LEXICO, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '"') {
                String identificador = "";
                while (columnaActual < cadena.length() && charActual != '"') {
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                identificador += cadena.charAt(columnaActual);
                columnaActual++;
                if (expresion.validarIdentificador(identificador)) {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.CADENA, identificador));
                } else {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.ERROR_LEXICO, identificador));
                }
            } else if (Character.isDigit(charActual)) {
                String identificador = "";
                while (columnaActual < cadena.length() && Character.isDigit(charActual)
                        || columnaActual < cadena.length() && cadena.charAt(columnaActual) == '.') {
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                if (expresion.validarEntero(identificador)) {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.ENTERO, identificador));
                } else if (expresion.validarDecimal(identificador)) {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.DECIMAL, identificador));
                } else {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.ERROR_LEXICO, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '=' && cadena.charAt((columnaActual + 1)) != '=') {
                String identificador = "" + cadena.charAt(columnaActual);
                columnaActual++;
                if (expresion.validarAsignacion(identificador)) {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.OPERADOR_ASIGNADOR, identificador));
                } else {
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.ERROR_LEXICO, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '=' || cadena.charAt(columnaActual) == '!' || cadena.charAt(columnaActual) == '>'
                    || cadena.charAt(columnaActual) == '<' && cadena.charAt((columnaActual + 1)) == '=') {
                String identificador = ""+cadena.charAt(columnaActual);
                columnaActual++;
                identificador+= cadena.charAt(columnaActual);
                columnaActual++;
                if(expresion.validarComparador(identificador)){
                    tokens.add(new Token(lineaActual, columnaActual, TokenId.OPERADOR_COMPARACION, identificador));
                }
            } else if(cadena.charAt(columnaActual) == '\n'){
                columnaActual++;
                lineaActual++;
            } else {
                String identificador = "";
                while(cadena.charAt(columnaActual) < cadena.length()){
                    if(Character.isWhitespace(charActual)){
                        columnaActual++;
                        tokens.add(new Token(lineaActual, columnaActual, TokenId.ERROR_LEXICO, identificador));
                        identificador = "";
                    } else {
                        identificador += cadena.charAt(columnaActual);
                         columnaActual++;
                    }  
                }
                tokens.add(new Token(lineaActual, columnaActual, TokenId.ERROR_LEXICO, identificador));
            }
        }
        return tokens;
    }

}
