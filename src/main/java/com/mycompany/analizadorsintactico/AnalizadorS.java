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
    private boolean esValido = true;

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

    private void automataDeclaracion() {
        char estado = 'A';
        for (Token token1 : tokens) {
            if (tipoToken(TokenId.IDENTIFICADOR, token1)) {
                estado = 'B';

            } else {
                estado = 'E';
            }
        }
    }

    private void analizarDeclaracion() {
        token = tokens.get(indexToken);
        try {
            if (tipoToken(TokenId.IDENTIFICADOR, token) && token != null) {
                castearToken();
                if (tipoToken(TokenId.OPERADOR_ASIGNADOR, token) && token != null) {
                    castearToken();
                    if (tipoToken(TokenId.CONSTANTE, token) && token != null) {
                        castearToken();
                        esValido = true;
                    } else if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("[") && token != null) {
                        castearToken();
                        esValido = true;

                        if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("]") && token != null) {
                            castearToken();
                            esValido = true;
                        } else if (tipoToken(TokenId.CONSTANTE, token) && token != null) {
                            castearToken();
                            esValido = true;
                            while (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(",") && token != null) {
                                castearToken();
                                if (tipoToken(TokenId.CONSTANTE, token) && token != null) {
                                    castearToken();
                                    esValido = true;
                                } else {
                                    esValido = false;
                                    break;
                                }
                            }
                        } else {
                            dicAnidado();
                            while (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(",") && token != null) {
                                castearToken();
                                dicAnidado();
                            }
                        }
                        if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("]") && token != null) {
                            castearToken();
                            esValido = true;
                        } else {
                            castearToken();
                            esValido = false;
                        }
                    } else if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("{") && token != null) {
                        dicAnidado();
                        while (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(",") && token != null) {
                            castearToken();
                            dicAnidado();
                        }
                    } else {
                        castearToken();
                        esValido = false;
                    }
                    //evalua si vienen varias declaraciones
                } else if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(",") && token != null) {
                    while (token.getCadena().equals(",") && token != null) {
                        castearToken();
                        if (tipoToken(TokenId.IDENTIFICADOR, token) && token != null) {
                            castearToken();
                            esValido = true;
                        } else {
                            castearToken();
                            esValido = false;
                            break;
                        }
                    } //fin while
                    if (esValido == true) {
                        if (tipoToken(TokenId.OPERADOR_ASIGNADOR, token) && token != null) {
                            castearToken();
                            if (tipoToken(TokenId.CONSTANTE, token) && token != null) {
                                castearToken();
                                esValido = true;
                                while (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(",") && token != null) {
                                    castearToken();
                                    if (tipoToken(TokenId.CONSTANTE, token) && token != null) {
                                        castearToken();
                                        esValido = true;
                                    } else {
                                        esValido = false;
                                        break;
                                    }
                                }//fin while
                                //para verificar arreglos    
                            } else if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("[") && token != null) {
                                castearToken();
                                esValido = true;
                                if (tipoToken(TokenId.CONSTANTE, token) && token != null) {
                                    castearToken();
                                    esValido = true;
                                    while (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(",") && token != null) {
                                        castearToken();
                                        if (tipoToken(TokenId.CONSTANTE, token) && token != null) {
                                            castearToken();
                                            esValido = true;

                                        } else {
                                            esValido = false;
                                            break;
                                        }
                                    }//fin while
                                }
                            } else if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("]") && token != null) {
                                castearToken();
                                esValido = true;
                            } else {
                                castearToken();
                                esValido = false;
                            }
                        }
                    }
                    //para verificar operaciones aritmeticas en declaraciones, pendiente a agregar todos los operadores    
                } else if (tipoToken(TokenId.OPERADOR_ARITMETICO, token) && token.getCadena().equals("*") && token != null) {
                    castearToken();
                    if (tipoToken(TokenId.OPERADOR_ASIGNADOR, token) && token != null) {
                        castearToken();
                        if (tipoToken(TokenId.CONSTANTE, token) && token != null) {
                            castearToken();
                            esValido = true;
                        } else {
                            castearToken();
                            esValido = false;
                        }
                    } else {
                        esValido = false;
                    }
                } else {
                    esValido = false;
                }

            }

            if (esValido == true) {
                System.out.println("todo correcto en la ejecución en linea:" + (token.getLinea() - 1));
            } else {
                hayError("token no esperado");
            }
        } catch (Exception e) {
            System.out.println("fin de analisis sintactico");
        }

    }

    private void dicAnidado() {
        try {
            if (token != null) {
                token = tokens.get(indexToken);
            }

            if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("{") && token != null) {
                esValido = true;
                castearToken();
                if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("}") && token != null) {
                    esValido = true;
                    castearToken();
                } else if (tipoToken(TokenId.IDENTIFICADOR, token) || tipoToken(TokenId.CONSTANTE, token) && token != null) {
                    while (tipoToken(TokenId.IDENTIFICADOR, token) || tipoToken(TokenId.CONSTANTE, token) && token != null) {
                        castearToken();
                        if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(":") && token != null) {
                            esValido = true;
                            castearToken();
                            if (tipoToken(TokenId.IDENTIFICADOR, token) || tipoToken(TokenId.CONSTANTE, token) && token != null) {
                                castearToken();
                                if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals(",") && (tipoToken(TokenId.IDENTIFICADOR, tokens.get(indexToken + 1))
                                        || tipoToken(TokenId.CONSTANTE, tokens.get(indexToken + 1))) && token != null) {
                                    esValido = true;
                                    castearToken();
                                } else if (tipoToken(TokenId.OTROS_OPERADORES, token) && token.getCadena().equals("}") && token != null) {
                                    esValido = true;
                                    castearToken();
                                    break;
                                } else {
                                    esValido = false;
                                    castearToken();
                                    break;
                                }
                            } else {
                                esValido = false;
                                break;
                            }
                        } else {
                            esValido = false;
                            castearToken();
                            break;
                        }
                    } //fin while
                } else {
                    esValido = false;
                    castearToken();
                }
            }
        } catch (Exception e) {
            System.out.println("no hay diccionarios anidados");
        }

    }

    private boolean tipoToken(TokenId tokenEsperado, Token token) {
        if (token != null && token.getId() == tokenEsperado) {
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
            this.indexToken++;
            this.token = tokens.get(indexToken);
        } catch (Exception e) {
            System.out.println("no hay mas tokens");
            token = null;
        }
    }
}
