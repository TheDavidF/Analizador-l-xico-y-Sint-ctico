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
        lineaActual = 0;
        columnaActual = 0;
    }

    public ArrayList<Token> listarTokens() {
        ArrayList<Token> tokens = new ArrayList<>();
        while (columnaActual < cadena.length()) {
            char charActual = cadena.charAt(columnaActual);
            if (Character.isWhitespace(charActual)) {
                columnaActual++;
            } else if (Character.isLetter(cadena.charAt(columnaActual))) {
                // Capturar palabra
                String identificador = "";
                while (columnaActual < cadena.length() && Character.isLetterOrDigit(cadena.charAt(columnaActual))) {
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                if (expresion.validarLogico(identificador)) {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.OPERADOR_LOGICO, identificador));
                } else if (expresion.validarKeywords(identificador)) {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.PALABRA_RESERVADA, identificador));
                } else if (expresion.validarIdentificador(identificador)) {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.IDENTIFICADOR, identificador));
                } else {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '#') {
                String identificador = "";
                while (columnaActual < cadena.length() && cadena.charAt(columnaActual) != '\n') {
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                lineaActual++;
                if (expresion.validarComentario(identificador)) {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.COMENTARIO, identificador));
                } else {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '"' || cadena.charAt(columnaActual) == '\'') {
                String identificador = "";
                if (cadena.charAt(columnaActual) == '"') {
                    identificador = "\"";
                } else {
                    identificador = "'";
                }
                columnaActual++;
                while (columnaActual < cadena.length() && cadena.charAt(columnaActual) != '\"') {
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                if (cadena.charAt(columnaActual) == '"') {
                    identificador = "\"";
                } else {
                    identificador = "'";
                }
                columnaActual++;
                if (expresion.validarCadena(identificador)) {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.CADENA, identificador));
                } else {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                }
            } else if (Character.isDigit(cadena.charAt(columnaActual))) {
                String identificador = "";
                while (columnaActual < cadena.length() && Character.isDigit(cadena.charAt(columnaActual))
                        || columnaActual < cadena.length() && cadena.charAt(columnaActual) == '.') {
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                if (expresion.validarEntero(identificador)) {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.ENTERO, identificador));
                } else if (expresion.validarDecimal(identificador)) {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.DECIMAL, identificador));
                } else {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '=' && cadena.charAt((columnaActual + 1)) != '=') {
                String identificador = "" + cadena.charAt(columnaActual);
                columnaActual++;
                if (expresion.validarAsignacion(identificador)) {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.OPERADOR_ASIGNADOR, identificador));
                } else {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '=' || cadena.charAt(columnaActual) == '!' || cadena.charAt(columnaActual) == '>'
                    || cadena.charAt(columnaActual) == '<' && cadena.charAt((columnaActual + 1)) == '=') {
                String identificador = "" + cadena.charAt(columnaActual);
                columnaActual++;
                identificador += cadena.charAt(columnaActual);
                columnaActual++;
                if (expresion.validarComparador(identificador)) {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.OPERADOR_COMPARACION, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '\n') {
                columnaActual++;
                lineaActual++;
            } else {
                String identificador = "";
                identificador += cadena.charAt(columnaActual);
                if (expresion.validarSignos(identificador)) {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.OTROS_OPERADORES, identificador));
                } else {
                    tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                }
                columnaActual++;
                /*while(cadena.charAt(columnaActual) < cadena.length()){
                    if(Character.isWhitespace(charActual)){
                        columnaActual++;
                        tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                        identificador = "";
                    } else {
                        identificador += cadena.charAt(columnaActual);
                         columnaActual++;
                    }  
                }
                tokens.add(new Token((lineaActual + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                 */
            }
        }
        return tokens;
    }

    public Expresion getExpresion() {
        return expresion;
    }

    public void setExpresion(Expresion expresion) {
        this.expresion = expresion;
    }

    public int getColumnaActual() {
        return columnaActual;
    }

    public void setColumnaActual(int columnaActual) {
        this.columnaActual = columnaActual;
    }

    public int getLineaActual() {
        return lineaActual;
    }

    public void setLineaActual(int lineaActual) {
        this.lineaActual = lineaActual;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

}
