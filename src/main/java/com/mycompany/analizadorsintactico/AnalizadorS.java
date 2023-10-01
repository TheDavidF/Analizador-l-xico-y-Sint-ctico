/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorsintactico;

import com.mycompany.analizadorlexico.Token;
import com.mycompany.analizadorlexico.TokenId;
import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author DAVID
 */
public class AnalizadorS {

    private ArrayList<Token> tokens;
    private int indexToken;
    private Token token;

    public AnalizadorS(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.indexToken = 0;
    }

    public void analizador() {
        while (indexToken < tokens.size()) {
            if (tipoToken(TokenId.IDENTIFICADOR, tokens.get(indexToken))) {
                analizarDeclaracion();
            } else {
                indexToken++;
            }
        }
    }

    private void analizarDeclaracion() {
        boolean esValida = false;
        token = tokens.get(indexToken);
        try {
            if (tipoToken(TokenId.IDENTIFICADOR, token)) {
                castearToken();
                if (tipoToken(TokenId.OPERADOR_ASIGNADOR, token)) {
                    castearToken();
                    if (tipoToken(TokenId.CONSTANTE, token)) {
                        castearToken();
                        esValida = true;
                    } else if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("[")) {
                        castearToken();
                        esValida = true;
                        if (tipoToken(TokenId.CONSTANTE, token)) {
                            castearToken();
                            esValida = true;
                            while (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(",")) {
                                castearToken();
                                if (tipoToken(TokenId.CONSTANTE, token)) {
                                    castearToken();
                                    esValida = true;

                                } else {
                                    esValida = false;
                                    break;
                                }
                            }
                        }
                    } else if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("]")) {
                        castearToken();
                        esValida = true;
                    } else {
                        castearToken();
                        esValida = false;
                    }
                    //evalua si vienen varias declaraciones
                } else if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(",")) {
                    while (token.getCadena().equals(",")) {
                        castearToken();
                        if (tipoToken(TokenId.IDENTIFICADOR, token)) {
                            castearToken();
                            esValida = true;
                        } else {
                            castearToken();
                            esValida = false;
                            break;
                        }
                    } //fin while

                    if (esValida == true) {
                        if (tipoToken(TokenId.OPERADOR_ASIGNADOR, token)) {
                            castearToken();
                            if (tipoToken(TokenId.CONSTANTE, token)) {
                                castearToken();
                                esValida = true;
                                while (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(",")) {
                                    castearToken();
                                    if (tipoToken(TokenId.CONSTANTE, token)) {
                                        castearToken();
                                        esValida = true;

                                    } else {
                                        esValida = false;
                                        break;
                                    }
                                }
                            } else if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("[")) {
                                castearToken();
                                esValida = true;
                                if (tipoToken(TokenId.CONSTANTE, token)) {
                                    castearToken();
                                    esValida = true;
                                    while (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(",")) {
                                        castearToken();
                                        if (tipoToken(TokenId.CONSTANTE, token)) {
                                            castearToken();
                                            esValida = true;

                                        } else {
                                            esValida = false;
                                            break;
                                        }
                                    }
                                }
                            } else if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("]")) {
                                castearToken();
                                esValida = true;
                            } else {
                                castearToken();
                                esValida = false;
                            }
                        }
                    }
                } else if (tipoToken(TokenId.OPERADOR_ARITMETICO, token) && token.getCadena().equals("*")) {
                    castearToken();
                    if (tipoToken(TokenId.OPERADOR_ASIGNADOR, token)) {
                        castearToken();
                        if (tipoToken(TokenId.CONSTANTE, token)) {
                            castearToken();
                            esValida = true;
                        } else {
                            castearToken();
                            esValida = false;
                        }
                    } else {
                        esValida = false;
                    }
                } else {
                    esValida = false;
                }

            }

            if (esValida == true) {
                System.out.println("todo correcto en la ejecución");
            } else {
                hayError("token no esperado");
            }
        } catch (Exception e) {
            System.out.println("fin de analisis sintactico");
        }

    }

    private boolean tipoToken(TokenId tokenEsperado, Token token) {
        if (token.getId() == tokenEsperado) {
            return true;
        }
        return false;
    }

    private Pila invertirPila(Pila pila) {
        Pila expresion = new Pila();
        while (!pila.estaVacia()) {
            expresion.insertar(pila.extraer());
        }
        return expresion;
    }

    private Token anterior() {
        return tokens.get(indexToken - 1);
    }

    private void hayError(String mensaje) {
        System.err.println("Error en la línea " + anterior().getLinea()
                + ": " + mensaje);
    }

    private void castearToken() {
        try {
            indexToken++;
            token = tokens.get(indexToken);
        } catch (Exception e) {
            System.out.println("no hay mas tokens");
        }
    }
}
