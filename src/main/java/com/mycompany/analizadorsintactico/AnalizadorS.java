/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorsintactico;

import com.mycompany.analizadorlexico.Token;
import com.mycompany.analizadorlexico.TokenId;
import java.awt.List;
import java.time.chrono.ThaiBuddhistEra;
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
    private String estado;
    private ArrayList<String> varDeclaradas;

    public AnalizadorS(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.indexToken = 0;
        this.varDeclaradas = new ArrayList<>();
    }

    public void analizador() {
        //while (indexToken < tokens.size()) {
        //    if (tipoToken(TokenId.IDENTIFICADOR, tokens.get(indexToken))) {
        //        analizarDeclaracion();
        //    } else {
        //        indexToken++;
        //    }
        //}
        validarDeclaracionAsignacion();
    }

    private void automataDeclaracion() {
        char estado1 = 'A';
        for (Token token1 : tokens) {
            if (tipoToken(TokenId.IDENTIFICADOR, token1)) {
                estado1 = 'B';

            } else {
                estado1 = 'E';
            }
        }
    }

    private void validarDeclaracionAsignacion() {
        this.estado = "A";
        String variable = null;
        boolean salir = false;
        for (Token token1 : tokens) {
            switch (estado) {
                case "A":
                    if (token1.getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "B";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "B":
                    if (token1.getId().equals(TokenId.OTROS_OPERADORES) && token1.getCadena().equals(",")) {
                        estado = "C";
                    } else if (token1.getId().equals(TokenId.OPERADOR_ARITMETICO)) {
                        estado = "C";
                    } else if (token1.getId().equals(TokenId.OPERADOR_ASIGNADOR)) {
                        estado = "E";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "C":
                    if (token1.getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "B";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "D":
                    if (token1.getId().equals(TokenId.OPERADOR_ASIGNADOR)) {
                        estado = "E";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "E":
                    if (token1.getId().equals(TokenId.CONSTANTE)) {
                        estado = "F";
                    } else if (token1.getId().equals(TokenId.IDENTIFICADOR) && estaDeclarada(token1.getCadena())) {
                        estado = "F";
                    } else if (!estaDeclarada(token1.getCadena())) {
                        System.out.println("la variable " + token1.getCadena() + " no está definida");
                    } else if(token1.getId().equals(TokenId.OTROS_OPERADORES) && esApertura(token1.getCadena())){
                        estado = "H";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "F":
                    if(token1.getId().equals(TokenId.OPERADOR_ARITMETICO)){
                        estado = "G";
                    } else if(token1.getId().equals(TokenId.OTROS_OPERADORES) && esApertura(token1.getCadena())){
                        estado = "H";
                    } else {
                        estado = "F";
                        salir = true;
                        break;
                    }
                    break;
                case "G":
                    if (token1.getId().equals(TokenId.CONSTANTE)) {
                        estado = "F";
                    } else if(token1.getId().equals(TokenId.OTROS_OPERADORES) && esApertura(token1.getCadena())){
                        estado = "H";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "H":
                    if (token1.getId().equals(TokenId.CONSTANTE)) {
                        estado = "I";
                    } else if (token1.getId().equals(TokenId.IDENTIFICADOR) && estaDeclarada(token1.getCadena())) {
                        estado = "I";
                    } else if(token1.getId().equals(TokenId.OTROS_OPERADORES) && esCierre(token1.getCadena())){
                        estado = "F";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "I":
                    if(token1.getId().equals(TokenId.OTROS_OPERADORES) && token1.getCadena().equals(":")){
                        estado = "k";
                    } else  if (token1.getId().equals(TokenId.OTROS_OPERADORES) && token1.getCadena().equals(",")) {
                        estado = "J";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "J":
                    if (token1.getId().equals(TokenId.CONSTANTE)) {
                        estado = "I";
                    } else if (token1.getId().equals(TokenId.IDENTIFICADOR) && estaDeclarada(token1.getCadena())) {
                        estado = "I";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "K":
                    if (token1.getId().equals(TokenId.CONSTANTE)) {
                        estado = "L";
                    } else if (token1.getId().equals(TokenId.IDENTIFICADOR) && estaDeclarada(token1.getCadena())) {
                        estado = "L";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "L":
                    if (token1.getId().equals(TokenId.OTROS_OPERADORES) && token1.getCadena().equals(",")) {
                        estado = "M";
                    } else if(token1.getId().equals(TokenId.OTROS_OPERADORES) && esCierre(token1.getCadena())){
                        estado = "F";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "M":
                    if (token1.getId().equals(TokenId.CONSTANTE)) {
                        estado = "N";
                    } else if (token1.getId().equals(TokenId.IDENTIFICADOR) && estaDeclarada(token1.getCadena())) {
                        estado = "N";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                case "N":
                    if(token1.getId().equals(TokenId.OTROS_OPERADORES) && token1.getCadena().equals(":")){
                        estado = "k";
                    } else {
                        estado = "ERROR";
                        hayError("");
                    }
                    break;
                default:
                    estado = "ERROR";
                    throw new AssertionError();
                
                }
            if(salir){
                break;
            }
        }
        if(estado == "F"){
            System.out.println("no hay errores");
        } else{
            System.out.println("existieron errores sintacticos");
        }

    }

    private boolean estaDeclarada(String var) {
        for (String variable : varDeclaradas) {
            if (variable.equals(var)) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }

    private boolean esApertura(String var){
        if(var.equals("(") || var.equals("{") || var.equals("[")){
            return true;
        } else {
            return false;
        }
    }
    
    private boolean esCierre(String var){
        if(var.equals(")") || var.equals("}") || var.equals("}")){
            return true;
        } else {
            return false;
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
