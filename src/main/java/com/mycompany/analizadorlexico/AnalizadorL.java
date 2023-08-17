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

    public AnalizadorL() {
        expresion = new Expresion();
        lineaT = 0;
        columnaT = 0;

    }

    public ArrayList<Token> listarTokens() {
        lineaT = 0;
        columnaT = 0;
        ArrayList<Token> tokens = new ArrayList<>();
        int columnaActual = 0;
        while (columnaActual < cadena.length()) {
            char charActual = cadena.charAt(columnaActual);
            String saltoLinea = "";
            if (cadena.charAt(columnaActual) == '\n') {
                columnaT = 0;
                columnaActual++;
                lineaT++;
            } else if (Character.isWhitespace(charActual)) {
                columnaActual++;
            } else if (Character.isLetter(cadena.charAt(columnaActual)) || cadena.charAt(columnaActual) == '_') {
                // Capturar palabra
                String identificador = "";
                while (columnaActual < cadena.length() && Character.isLetterOrDigit(cadena.charAt(columnaActual))
                        || columnaActual < cadena.length() && cadena.charAt(columnaActual) == '_') {
                    if (columnaActual == (cadena.length() - 1)) {
                        identificador += cadena.charAt(columnaActual);
                        columnaActual++;
                        break;
                    }
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                if (expresion.validarIdentificador(identificador)) {
                    // Verificar símbolo de comparación o asignación
                    if (columnaActual < cadena.length()) {
                        char siguienteCaracter = cadena.charAt(columnaActual);
                        if (validarSigno(siguienteCaracter)) {
                            // Símbolo de comparación o asignación válido
                            if (expresion.validarLogico(identificador)) {
                                tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.OPERADOR_LOGICO, identificador));
                            } else if (expresion.validarKeywords(identificador)) {
                                tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.PALABRA_RESERVADA, identificador));
                            } else if (expresion.validarIdentificador(identificador)) {
                                tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.IDENTIFICADOR, identificador));
                            } else {
                                tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.ERROR_LEXICO, identificador));
                            }
                        } else {
                            // Símbolo inválido después del identificador
                            tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador + siguienteCaracter));
                            columnaActual++;
                        }
                    } else {
                        // Fin de cadena después del identificador
                        if (expresion.validarLogico(identificador)) {
                            tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.OPERADOR_LOGICO, identificador));
                        } else if (expresion.validarKeywords(identificador)) {
                            tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.PALABRA_RESERVADA, identificador));
                        } else if (expresion.validarIdentificador(identificador)) {
                            tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.IDENTIFICADOR, identificador));
                        } else {
                            tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.ERROR_LEXICO, identificador));
                        }
                    }
                } else {
                    // Identificador inválido
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '#') {
                String identificador = "";
                while (columnaActual < cadena.length() && cadena.charAt(columnaActual) != '\n') {
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                lineaT++;
                if (expresion.validarComentario(identificador)) {
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.COMENTARIO, identificador));
                } else {
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '"' || cadena.charAt(columnaActual) == '\'') {
                String identificador = "";
                identificador += cadena.charAt(columnaActual);
                if (columnaActual < (cadena.length() - 1)) {
                    columnaActual++;
                }
                while (columnaActual < cadena.length() && cadena.charAt(columnaActual) != '"' && cadena.charAt(columnaActual) != '\'') {
                    if (columnaActual == (cadena.length() - 1)) {
                        break;
                    }
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                if (identificador.length() > 1) {
                    identificador += cadena.charAt(columnaActual);
                }
                columnaActual++;
                if (expresion.validarCadena(identificador)) {
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.CADENA, identificador));
                } else {
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                }
            } else if (Character.isDigit(cadena.charAt(columnaActual))) {
                String identificador = "";
                while (columnaActual < cadena.length() && Character.isDigit(cadena.charAt(columnaActual))
                        || columnaActual < cadena.length() && cadena.charAt(columnaActual) == '.' || columnaActual < cadena.length() && Character.isLetter(cadena.charAt(columnaActual))) {
                    identificador += cadena.charAt(columnaActual);
                    columnaActual++;
                }
                if (expresion.validarEntero(identificador)) {
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.ENTERO, identificador));
                } else if (expresion.validarDecimal(identificador)) {
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.DECIMAL, identificador));
                } else {
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                }
            } else if (cadena.charAt(columnaActual) == '=' && charSiguiente(cadena, columnaActual) || cadena.charAt(columnaActual) == '!' && charSiguiente(cadena, columnaActual)
                    || cadena.charAt(columnaActual) == '>' && charSiguiente(cadena, columnaActual)
                    || cadena.charAt(columnaActual) == '<' && charSiguiente(cadena, columnaActual)) {
                String identificador = "";
                identificador += cadena.charAt(columnaActual);
                columnaActual++;
                identificador += cadena.charAt(columnaActual);
                if (expresion.validarComparador(identificador)) {
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.OPERADOR_COMPARACION, identificador));
                }
                columnaActual++;
            } else if (cadena.charAt(columnaActual) == '=') {
                String identificador = "" + cadena.charAt(columnaActual);
                columnaActual++;
                columnaT++;
                if (expresion.validarAsignacion(identificador)) {
                    tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.OPERADOR_ASIGNADOR, identificador));
                } else {
                    tokens.add(new Token((lineaT + 1), (columnaT + 1), TokenId.ERROR_LEXICO, identificador));
                }
            } else if (expresion.validarComparador("" + cadena.charAt(columnaActual))) {
                String identificador = "" + cadena.charAt(columnaActual);
                tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.OPERADOR_COMPARACION, identificador));
                columnaActual++;
            } else {
                String identificador = "";
                identificador += cadena.charAt(columnaActual);
                if (expresion.validarSignos(identificador)) {
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.OTROS_OPERADORES, identificador));
                } else if (expresion.validarOperador(identificador)) {
                    tokens.add(new Token((lineaT + 1), columnaActual, TokenId.OPERADOR_ASIGNADOR, identificador));
                } else {
                    tokens.add(new Token((lineaT + 1), (columnaActual + 1), TokenId.ERROR_LEXICO, identificador));
                }
                columnaActual++;
            }
        }
        return tokens;

    }

    private Boolean charSiguiente(String cadena, int columnaActual) {
        try {
            if (cadena.charAt((columnaActual + 1)) == '=') {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private boolean errorID(char c, char siguiente) {
        String string = "" + c;
        if (Character.isLetter(siguiente)) {
            return true;
        } else if (expresion.validarOperador(string)) {
            return false;
        } else if (expresion.validarComparador(string)) {
            return false;
        } else if (expresion.validarAsignacion(string)) {
            return false;
        } else if (c == '#') {
            return false;
        } else if (c == '"') {
            return false;
        }
        return true;
    }

    private boolean validarSigno(char c) {
        if (expresion.validarAsignacion("" + c) || expresion.validarComparador("" + c) || expresion.validarOperador("" + c)
                || expresion.validarSignos("" + c) || c == '#' || c == '"' || c == '\'' || c == '!') {
            return true;
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

}
