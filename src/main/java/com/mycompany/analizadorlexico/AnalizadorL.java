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
    private int columnaT;
    private int lineaT;
    private String cadena;
    private ArrayList<Token> tokens;
    private int indent;

    public AnalizadorL() {
        expresion = new Expresion();
        lineaT = 0;
        columnaT = 0;
        tokens = new ArrayList<>();
        this.indent = 0;

    }

    public ArrayList<Token> listarTokens() {
        lineaT = 0;
        columnaT = 0;
        String cadenaa;
        int columnaActual = 0;
        try {
            while (columnaActual < cadena.length()) {
                char charActual = cadena.charAt(columnaActual);
                String saltoLinea = "";
                if (cadena.charAt(columnaActual) == '\n' || cadena.charAt(columnaActual) == '\r') {
                    columnaT = 0;
                    int columna = columnaActual;
                    columnaActual++;
                    if (cadena.charAt(columna) == '\n') {
                        lineaT++;
                    }
                } else if (Character.isWhitespace(charActual)) {
                    int columna = columnaActual;
                    int comumaTemp = columnaT;
                    while (indent < 4 && Character.isWhitespace(cadena.charAt(columnaActual))) {
                        indent++;
                        columnaActual++;
                        columnaT++;
                    }
                    if (indent == 4) {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.INDENT, "    ", "Indentacion"));
                        indent = 0;
                    } else {
                        columnaT = comumaTemp;
                        columnaActual = columna;
                        columnaActual++;
                        columnaT++;
                    }

                } else if (Character.isLetter(cadena.charAt(columnaActual)) || cadena.charAt(columnaActual) == '_') {

                    // Capturar palabra
                    String identificador = "";
                    while (columnaActual < cadena.length() && Character.isLetterOrDigit(cadena.charAt(columnaActual))
                            || columnaActual < cadena.length() && cadena.charAt(columnaActual) == '_') {
                        if (columnaActual == (cadena.length() - 1)) {
                            identificador += cadena.charAt(columnaActual);
                            columnaActual++;
                            columnaT++;
                            break;
                        }
                        identificador += cadena.charAt(columnaActual);
                        columnaActual++;
                        columnaT++;
                    }
                    if (expresion.validarBoleano(identificador)) {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.BOOLEANO, identificador, identificador));
                    } else if (expresion.validarLogico(identificador)) {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.OPERADOR_LOGICO, identificador, identificador));
                    } else if (expresion.validarKeywords(identificador)) {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.PALABRA_RESERVADA, identificador, identificador));
                    } else if (expresion.validarIdentificador(identificador)) {
                        tokens.add(new Token((lineaT + 1), (columnaT), TokenId.IDENTIFICADOR, identificador, identificador));
                    } else {
                        // Identificador inválido
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.ERROR_LEXICO, identificador, identificador));
                    }

                } else if (cadena.charAt(columnaActual) == '#') {
                    String identificador = "";
                    while (columnaActual < cadena.length() && cadena.charAt(columnaActual) != '\n') {
                        if (Character.isWhitespace(cadena.charAt(columnaActual)) && columnaActual == cadena.length()
                                || Character.isWhitespace(cadena.charAt(columnaActual)) && charSiguiente(cadena, columnaActual)) {
                            break;
                        }
                        identificador += cadena.charAt(columnaActual);
                        columnaActual++;
                        columnaT++;
                    }
                    System.out.println(identificador + identificador.length());
                    lineaT++;
                    columnaActual++;
                    columnaActual++;
                    columnaT = 0;
                    if (expresion.validarComentario(identificador)) {
                        tokens.add(new Token((lineaT), (columnaT + 1), TokenId.COMENTARIO, identificador, expresion.getComentario()));
                    } else {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.ERROR_LEXICO, identificador, identificador));
                    }
                } else if (cadena.charAt(columnaActual) == '"' || cadena.charAt(columnaActual) == '\'') {
                    String identificador = "";
                    identificador += cadena.charAt(columnaActual);
                    if (columnaActual < (cadena.length() - 1)) {
                        columnaActual++;
                        columnaT++;
                    }
                    while (columnaActual < cadena.length() && cadena.charAt(columnaActual) != '"' && cadena.charAt(columnaActual) != '\'') {
                        if (columnaActual == (cadena.length() - 1) || cadena.charAt(columnaActual) == '\n' || cadena.charAt(columnaActual) == '\r') {
                            break;
                        }
                        identificador += cadena.charAt(columnaActual);
                        columnaActual++;
                        columnaT++;
                    }
                    if (identificador.length() > 1 && cadena.charAt(columnaActual) != '\n' && cadena.charAt(columnaActual) != '\r') {
                        identificador += cadena.charAt(columnaActual);
                    }
                    columnaActual++;
                    columnaT++;

                    if (expresion.validarCadena(identificador)) {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.CONSTANTE, identificador, expresion.getCadena()));
                    } else {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.ERROR_LEXICO, identificador, identificador));
                    }
                } else if (Character.isDigit(cadena.charAt(columnaActual))) {
                    String identificador = "";
                    while (columnaActual < cadena.length() && Character.isDigit(cadena.charAt(columnaActual))
                            || columnaActual < cadena.length() && cadena.charAt(columnaActual) == '.' || columnaActual < cadena.length() && Character.isLetter(cadena.charAt(columnaActual))) {
                        identificador += cadena.charAt(columnaActual);
                        columnaActual++;
                        columnaT++;
                    }
                    if (expresion.validarEntero(identificador)) {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.CONSTANTE, identificador, expresion.getEntero()));
                    } else if (expresion.validarDecimal(identificador)) {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.CONSTANTE, identificador, expresion.getDecimal()));
                    } else {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.ERROR_LEXICO, identificador, identificador));
                    }
                } else if (cadena.charAt(columnaActual) == '=' && charSiguiente(cadena, columnaActual) || cadena.charAt(columnaActual) == '!' && charSiguiente(cadena, columnaActual)
                        || cadena.charAt(columnaActual) == '>' && charSiguiente(cadena, columnaActual)
                        || cadena.charAt(columnaActual) == '<' && charSiguiente(cadena, columnaActual)) {
                    String identificador = "";
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                    columnaT++;
                    identificador += cadena.charAt(columnaActual);
                    if (expresion.validarComparador(identificador)) {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.OPERADOR_COMPARACION, identificador, identificador));
                    }
                    columnaActual++;
                    columnaT++;
                } else if (cadena.charAt(columnaActual) == '=') {
                    String identificador = "" + cadena.charAt(columnaActual);
                    columnaActual++;
                    columnaT++;
                    if (expresion.validarAsignacion(identificador)) {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.OPERADOR_ASIGNADOR, identificador, identificador));
                    } else {
                        tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.ERROR_LEXICO, identificador, identificador));
                    }
                } else if (expresion.validarComparador("" + cadena.charAt(columnaActual))) {
                    String identificador = "" + cadena.charAt(columnaActual);
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.OPERADOR_COMPARACION, identificador, identificador));
                    columnaActual++;
                    columnaT++;
                } else {
                    String identificador = "";
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                    columnaT++;
                    if (identificador.equals("*") && cadena.charAt(columnaActual) == '*' || identificador.equals("/") && cadena.charAt(columnaActual) == '/') {
                        identificador += cadena.charAt(columnaActual);
                        columnaActual++;
                        columnaT++;
                        if (expresion.validarSignos(identificador)) {
                            tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.OTROS_OPERADORES, identificador, identificador));
                        } else if (expresion.validarOperador(identificador)) {
                            tokens.add(new Token((lineaT + 1), columnaT, TokenId.OPERADOR_ARITMETICO, identificador, identificador));
                        } else {
                            tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.ERROR_LEXICO, identificador, identificador));
                        }
                    } else {

                        if (expresion.validarSignos(identificador)) {
                            tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.OTROS_OPERADORES, identificador, identificador));
                        } else if (expresion.validarOperador(identificador)) {
                            tokens.add(new Token((lineaT + 1), columnaT, TokenId.OPERADOR_ARITMETICO, identificador, identificador));
                        } else {
                            tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.ERROR_LEXICO, identificador, identificador));
                        }
                    }

                }
            }
            return tokens;
        } catch (Exception e) {
            System.out.println("no hay mas tokens");
            return tokens;
        }

    }

    // me ayuda a verificar si hay un siguiente char sin que me salte una exception
    private Boolean charSiguiente(String cadena, int columnaActual) {
        try {
            if (cadena.charAt((columnaActual + 1)) == '=' || cadena.charAt((columnaActual + 1)) == '\n') {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public Expresion getExpresion() {
        return expresion;
    }

    public void setExpresion(Expresion expresion) {
        this.expresion = expresion;
    }

    public int getColumnaActual() {
        return columnaT;
    }

    public void setColumnaActual(int columnaActual) {
        this.columnaT = columnaActual;
    }

    public int getLineaActual() {
        return lineaT;
    }

    public void setLineaActual(int lineaActual) {
        this.lineaT = lineaActual;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

}
