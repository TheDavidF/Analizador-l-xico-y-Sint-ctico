/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorsintactico;

import com.mycompany.analizadorlexico.Token;
import com.mycompany.analizadorlexico.TokenId;
import com.mycompany.analizadorlexico.Variable;
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
    private ArrayList<Variable> varDeclaradas;
    private int linea;
    private String errores;
    private ArrayList<Token> sentencias;
    private int cierrePen;
    private boolean salir;

    public AnalizadorS(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.indexToken = 0;
        this.varDeclaradas = new ArrayList<>();
        this.linea = 1;
        this.errores = "";
        sentencias = new ArrayList<>();
        this.cierrePen = 0;
        this.salir = false;

    }

    public void analizar() throws SyntaxError {
        while (indexToken < tokens.size()) {
            if (tokens.get(indexToken).getLinea() == linea) {
                if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                    automataDec();
                }
                try {
                    linea = tokens.get(indexToken).getLinea();
                } catch (Exception e) {
                }
            } else {
                castear();
            }
        }
        System.out.println("análisis sintáctico finalizado");
        /* programa();
        if (indexToken < tokens.size()) {
            // Error: no se consumieron todos los tokens.
            System.out.println("Error de sintaxis en la posición " + indexToken);
        } else {
            System.out.println("Análisis sintáctico completado con éxito.");
        }*/
    }

    private boolean automataDec() {
        salir = false;
        estado = "A";
        while (indexToken < tokens.size()) {
            if (saltoLinea()) {
                if (estado.equals("E")) {
                    actualizarLinea();
                    return true;
                } else {
                    System.out.println("ERROR en linea :" + (linea) + ", declaración invalida");
                    return false;
                }
            } else {
                switch (estado) {
                    case "A":
                        if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                            estado = "B";
                            castear();
                        } else {
                            estado = "ERROR";
                            System.out.println("Error en linea:" + linea + " se esperaba un identificador");
                            castear();
                            actualizarLinea();
                            return false;
                        }
                        break;
                    case "B":
                        if (tokenEsperado(TokenId.OPERADOR_ASIGNADOR, indexToken)) {
                            estado = "C";
                            castear();
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                            estado = "A";
                            castear();
                        } else if (tokenEsperado(TokenId.OPERADOR_ARITMETICO, indexToken)) {
                            estado = "D";
                            castear();
                        } else {
                            estado = "ERROR";
                            System.out.println("Error en linea:" + linea + " se esperaba un =");
                            castear();
                            actualizarLinea();
                            return false;
                        }
                        break;
                    case "C":
                        if (automataExpresion()) {
                            estado = "E";
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            return false;

                        }
                        break;
                    case "D":
                        if (tokenEsperado(TokenId.OPERADOR_ASIGNADOR, indexToken)) {
                            estado = "C";
                            castear();
                        } else {
                            estado = "ERROR";
                            System.out.println("Error en linea:" + linea + " se esperaba un =");
                            actualizarLinea();
                            return false;
                        }
                        break;
                    case "E":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                            estado = "C";
                        } else {
                            castear();
                            actualizarLinea();
                            return true;
                        }
                        break;
                    case "ERROR":
                        castear();
                        actualizarLinea();
                        return false;
                    default:
                        estado = "ERROR";
                        return false;
                }
            }

        }
        if (indexToken == tokens.size() && !estado.equals("E")) {
            System.out.println("Error en linea: " + linea + " declaración invalida");
            return false;
        } else {
            return true;
        }
    }

    private boolean automataExpresion() {
        salir = false;
        estado = "A";
        while (indexToken < tokens.size()) {
            if (saltoLinea()) {
                if (estado.equals("B")) {
                    return true;
                } else {
                    actualizarLinea();
                    System.out.println("ERROR en linea :" + (linea - 1) + ", expresión invalida");
                    salir = false;

                }
            } else {
                switch (estado) {
                    case "A":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("[")) {
                            if (automataArray()) {
                                estado = "B";
                            } else {
                                estado = "ERROR";
                                return false;
                            }
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("{")) {
                            if (automataDiccionario()) {
                                estado = "B";
                            } else {
                                return false;
                            }

                        } else if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) || tokenEsperado(TokenId.CONSTANTE, indexToken)
                                || tokenEsperado(TokenId.BOOLEANO, indexToken)) {
                            estado = "B";
                            if (saltoLinea()) {
                                return true;
                            }
                            castear();
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("(")) {
                            estado = "D";
                            castear();
                        } else if (tokenEsperado(TokenId.OPERADOR_ARITMETICO, indexToken)) {
                            estado = "C";
                            castear();
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            System.out.println("Error en linea:" + linea + " expresión inválida");
                        }
                        break;
                    case "B":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("(")) {
                            estado = "D";
                            castear();
                        } else if (tokenEsperado(TokenId.OPERADOR_ARITMETICO, indexToken)) {
                            estado = "C";
                            castear();
                        } else {
                            return true;
                        }
                        break;
                    case "C":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("(")) {
                            estado = "D";
                            castear();
                        } else if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) || tokenEsperado(TokenId.CONSTANTE, indexToken) || tokenEsperado(TokenId.BOOLEANO, indexToken)) {
                            estado = "B";
                            castear();
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            System.out.println("Error en linea:" + linea + " falta expresión después del operador");
                        }
                        break;
                    case "D":
                        if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) || tokenEsperado(TokenId.CONSTANTE, indexToken) || tokenEsperado(TokenId.BOOLEANO, indexToken)) {
                            estado = "E";
                            castear();
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            System.out.println("Error en linea:" + linea + " Se esperaba una expresión");
                        }
                        break;
                    case "E":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(")")) {
                            estado = "B";
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            System.out.println("Error en linea:" + linea + " se esperaba un paréntesis de cierre");
                        }
                        break;
                    case "ERROR":
                        castear();
                        return false;
                    default:
                        estado = "ERROR";
                }
            }

        }

        if (indexToken == tokens.size() && estado.equals("B")) {
            return true;
        } else {
            System.out.println("Error en linea:" + linea + " expresión invalida");
            return false;
        }
    }

    private boolean automataArray() {
        estado = "A";
        while (indexToken < tokens.size()) {
            if (saltoLinea()) {
                if (estado.equals("D")) {
                    return true;
                } else {
                    actualizarLinea();
                    if (estado.equals("C")) {
                        System.out.println("ERROR en linea :" + (linea - 1) + ", falta corchete de cierre");
                    }
                    return false;
                }
            } else {
                switch (estado) {
                    case "A":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("[")) {
                            estado = "B";
                            castear();
                        } else {
                            estado = "ERROR";
                            return false;
                        }
                        break;
                    case "B":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("]")) {
                            estado = "D";
                            castear();
                            return true;
                        } else if (automataExpresion()) {
                            estado = "C";
                        } else {
                            estado = "ERROR";
                            return false;
                        }
                        break;
                    case "C":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("]")) {
                            estado = "D";
                            castear();
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                            estado = "E";
                            castear();
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            System.out.println("ERROR en linea :" + (linea) + ", falta corchete de cierre");
                            castear();
                            return false;
                        }
                        break;
                    case "D":
                        return true;
                    case "E":
                        if (automataExpresion()) {
                            estado = "C";
                        } else {
                            estado = "ERROR";
                            return false;
                        }
                        break;
                    case "ERROR":
                        System.out.println("Array invalido");
                        return false;
                    default:
                        estado = "ERROR";
                }
            }
        }
        if (indexToken == tokens.size() && estado.equals("D")) {
            return true;
        } else {
            System.out.println("Error en linea:" + linea + " expresión invalida");
            return false;
        }
    }

    private boolean automataDiccionario() {
        estado = "A";
        while (indexToken < tokens.size()) {
            if (saltoLinea()) {
                if (estado.equals("G")) {
                    return true;
                } else {
                    actualizarLinea();
                    System.out.println("ERROR en linea :" + (linea - 1) + ", expresión invalida");

                }
            } else {
                switch (estado) {
                    case "A":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("{")) {
                            estado = "B";
                            castear();
                        } else {
                            estado = "ERROR";
                            return false;
                        }
                        break;
                    case "B":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("}")) {
                            estado = "G";
                            castear();
                            return true;
                        } else if (automataExpresion()) {
                            estado = "C";
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            System.out.println("ERROR en linea :" + (linea) + ", falta corchete de cierre");
                            return false;
                        }
                        break;
                    case "C":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
                            estado = "D";
                            castear();
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            System.out.println("ERROR en linea :" + (linea) + ", falta expresión");
                            return false;
                        }
                        break;
                    case "D":
                        if (automataExpresion()) {
                            estado = "E";
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            System.out.println("ERROR en linea :" + (linea) + ", expresión invalida");
                            return false;
                        }
                    case "E":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("}")) {
                            estado = "G";
                            castear();
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                            estado = "F";
                            castear();
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            System.out.println("ERROR en linea :" + (linea) + ", falta corchete de cierre");
                            return false;
                        }
                        break;
                    case "F":
                        if (automataExpresion()) {
                            estado = "C";
                        } else {
                            estado = "ERROR";
                            return false;
                        }
                        break;
                    case "G":
                        return true;
                    case "ERROR":
                        System.out.println("Diccionario invalido");
                        return false;
                    default:
                        estado = "ERROR";
                }
            }
        }
        if (indexToken == tokens.size() && estado.equals("G")) {
            return true;
        } else {
            if (estado.equals("E") || estado.equals("B")) {
                System.out.println("ERROR en linea :" + (linea) + ", falta llave de cierre");
            } else {
                System.out.println("ERROR en linea :" + (linea) + ", expresión invalida");
            }
            return false;
        }
    }

    public void programa() throws SyntaxError {
        while (indexToken < tokens.size()) {
            sentencia();

        }
        //System.out.println(errores);

    }

    private void actualizarLinea() {
        try {
            this.linea = tokens.get(indexToken).getLinea();
        } catch (Exception e) {
        }
    }

    private void sentencia() throws SyntaxError {
        if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR) && tokenEsperado(TokenId.OPERADOR_ASIGNADOR, (indexToken + 1))) {
            sentencias.add(tokens.get(indexToken));
            castear();
            if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken + 1) && tokens.get(indexToken).getCadena().equals("None")) {
                castear();
                if (saltoLinea()) {
                    throw new SyntaxError("Sentencia invalida, se esperaba una expresión");
                }
                validarDeclaracion();
            } else {
                castear();
                if (saltoLinea()) {
                    throw new SyntaxError("Sentencia invalida, se esperaba una expresión");
                }
                validarAsignacion();
            }
        } else if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) && tokenEsperado(TokenId.OPERADOR_ARITMETICO, (indexToken + 1))) {
            castear();
            validarAsignacion();
        } else if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) || tokenEsperado(TokenId.CONSTANTE, indexToken) || tokenEsperado(TokenId.OTROS_OPERADORES, indexToken)
                && esApertura(tokens.get(indexToken).getCadena())) {
            validarExpresion();
        } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("if")) {
            validarIf();
        } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("while")) {
            validarWhile();
        } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("for")) {
            validarFor();
        }
    }

    private void validarDeclaracion() throws SyntaxError {
        if (tokenEsperado(TokenId.OPERADOR_ASIGNADOR, (indexToken))) {
            castear();
            validarExpresion();
        } else {
            throw new SyntaxError("Asingación invalida");
        }

    }

    private void validarAsignacion() throws SyntaxError {
        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
            castear();
            validarAsignacion();
        } else if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
            castear();
            validarAsignacion();
        } else if (tokenEsperado(TokenId.OPERADOR_ASIGNADOR, indexToken)) {
            castear();
            validarExpresion();
        } else {
            validarExpresion();
        }
    }

    //expresion    -> identificador | constante | "(" expresion ")" | operador expresion
    private void validarExpresion() throws SyntaxError {
        while (indexToken < tokens.size()) {
            if (saltoLinea()) {

            }
            if (tokens.isEmpty()) {
                throw new SyntaxError("Expresión vacía.");
            }
            if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) || tokenEsperado(TokenId.CONSTANTE, indexToken)) {
                castear();
                if (saltoLinea()) {
                    break;
                }
                if (tokens.size() == 1) {
                    // Expresión simple de un solo token (identificador o constante)
                    break;
                } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                    castear();
                    if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) || tokenEsperado(TokenId.CONSTANTE, indexToken)) {
                        validarExpresion();
                    } else {
                        throw new SyntaxError("Expresión invalida");
                    }
                } else {
                    validarExpresion();
                }

            } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && esApertura(tokens.get(indexToken).getCadena())) {
                if (tokens.get(indexToken).getCadena().equals("(")) {
                    if (encontrarParentesisDerecho("(", ")").equals("parentesis encontrado")) {
                        castear();
                        cierrePen++;
                        validarExpresion();
                    }
                } else if (tokens.get(indexToken).getCadena().equals("[")) {
                    if (encontrarParentesisDerecho("[", "]").equals("parentesis encontrado")) {
                        castear();
                        cierrePen = cierrePen + 1;
                        int lineaa = linea;
                        validarExpresion();
                        castear();
                        if (saltoLinea()) {
                            break;
                        }
                    }
                } else if (tokens.get(indexToken).getCadena().equals("{")) {
                    if (encontrarParentesisDerecho("{", "}").equals("parentesis encontrado")) {
                        castear();
                        cierrePen++;
                        validarDiccionario();
                        castear();
                        if (saltoLinea()) {
                            break;
                        }
                    }
                }

            } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && esCierre(tokens.get(indexToken).getCadena())) {
                if (cierrePen > 0) {
                    cierrePen--;
                    castear();
                    try {
                        if (linea != tokens.get(indexToken + 1).getLinea()) {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("no hay mas tokens");
                    }
                    validarExpresion();
                } else {
                    throw new SyntaxError("No se esperaba un paréntesis de cierre");
                }
            } else if (tokenEsperado(TokenId.OPERADOR_ARITMETICO, indexToken)) {
                castear();
                validarExpresion();
            } else if (tokenEsperado(TokenId.BOOLEANO, indexToken)) {
                castear();
                if (saltoLinea()) {
                    break;
                }
                validarExpresion();
            } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                castear();
                if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) || tokenEsperado(TokenId.CONSTANTE, indexToken)) {
                    validarExpresion();
                } else {
                    throw new SyntaxError("Expresión invalida");
                }

            } else {
                throw new SyntaxError("Expresión invalida");
            }
        }

    }

    private boolean saltoLinea() {
        try {
            if (linea != tokens.get(indexToken).getLinea()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("no hay mas tokens");
        }

        return false;
    }

    private String encontrarParentesisDerecho(String apertura, String cierre) throws SyntaxError {
        int contadorParentesis = 0;
        String resultado;
        int i = indexToken;
        while (i < tokens.size()) {
            if (esApertura(tokens.get(i).getCadena()) && tokens.get(indexToken).getCadena().equals(apertura)) {
                contadorParentesis++;
            } else if (esCierre(tokens.get(i).getCadena()) && tokens.get(indexToken).getCadena().equals(apertura)) {
                contadorParentesis--;
                if (contadorParentesis == 0) {
                    return "parentesis encontrado";
                }
            }
            i++;
        }
        if (apertura.equals("(")) {
            throw new SyntaxError("parentesis de cierre no encontrado");
        } else if (apertura.equals("[")) {
            throw new SyntaxError("corchete de cierre no encontrado");
        } else {
            throw new SyntaxError("llave de cierre no encontrada");
        }

    }

    private void validarIf() {

    }

    private void validarWhile() {

    }

    private void validarFor() {

    }

    private void validarDiccionario() throws SyntaxError {
        if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) || tokenEsperado(TokenId.CONSTANTE, indexToken)) {
            castear();
            validarDiccionario();
        }
        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
            castear();
            validarDiccionario();
            if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {//falta si esta definida
                castear();
                if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                    castear();
                    validarDiccionario();
                }
            } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("}")) {

            } else {
                validarExpresion();
            }
        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("}")) {

        } else {
            throw new SyntaxError("declaracion de diccionario invalido");
        }
    }

    private boolean tokenEsperado(TokenId id, int index) {
        try {
            if (tokens.get(index).getId().equals(id)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("no hay mas tokens");
            return false;

        }
    }

    private void validarDeclaracionAsignacion() {
        this.estado = "A";
        ArrayList<String> variables = new ArrayList<>();
        boolean salir = false;
        while (indexToken < tokens.size()) {
            if (linea != tokens.get(indexToken).getLinea()) {
                if (estado != "F") {
                    errores += hayError(estadoError(estado)) + "\n";
                    estado = "ERROR";
                }
                linea = tokens.get(indexToken).getLinea();
            }
            switch (estado) {
                case "A":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "B";
                        variables.add(tokens.get(indexToken).getCadena());
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError(estado)) + "\n";
                        salir = true;
                    }
                    break;
                case "B":
                    if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && tokens.get(indexToken).getCadena().equals(",")) {
                        estado = "C";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.OPERADOR_ARITMETICO)) {
                        estado = "C";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.OPERADOR_ASIGNADOR)) {
                        estado = "E";
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError(estado)) + "\n";
                        salir = true;
                    }
                    break;
                case "C":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "B";
                        variables.add(tokens.get(indexToken).getCadena());
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError(estado)) + "\n";
                        salir = true;
                    }
                    break;
                case "D":
                    if (tokens.get(indexToken).getId().equals(TokenId.OPERADOR_ASIGNADOR)) {
                        estado = "E";
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError(estado)) + "\n";
                        salir = true;
                    }
                    break;
                case "E":
                    if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "F";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR) && estaDeclarada(tokens.get(indexToken).getCadena())) {
                        agregarLlamada(tokens.get(indexToken).getCadena());
                        estado = "F";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR) && !estaDeclarada(tokens.get(indexToken).getCadena())) {
                        estado = "ERROR";
                        salir = true;
                        System.out.println("la variable " + tokens.get(indexToken).getCadena() + " no está definida");
                    } else if (tokens.get(indexToken).getId().equals(TokenId.BOOLEANO)) {
                        estado = "F";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esApertura(tokens.get(indexToken).getCadena())) {
                        estado = "H";
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError(estado)) + "\n";
                        salir = true;
                    }
                    break;
                case "F":
                    if (tokens.get(indexToken).getId().equals(TokenId.OPERADOR_ARITMETICO)) {
                        estado = "G";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esApertura(tokens.get(indexToken).getCadena())) {
                        estado = "H";
                    } else {
                        estado = "F";
                        if (estado == "F") {
                            for (String variable : variables) {
                                varDeclaradas.add(new Variable(variable, (linea - 1)));
                            }
                            variables.clear();
                            errores += "no hay errores en linea: " + (linea - 1) + "\n";
                        }
                        salir = true;
                        break;
                    }
                    break;
                case "G":
                    if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "F";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esApertura(tokens.get(indexToken).getCadena())) {
                        estado = "H";
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError("token no esperado")) + "\n";
                        salir = true;
                    }
                    break;
                case "H":
                    if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "I";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR) && estaDeclarada(tokens.get(indexToken).getCadena())) {
                        estado = "I";
                        agregarLlamada(tokens.get(indexToken).getCadena());
                    } else if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR) && !estaDeclarada(tokens.get(indexToken).getCadena())) {
                        estado = "ERROR";
                        System.out.println("la variable " + tokens.get(indexToken).getCadena() + " no está definida");
                    } else if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esCierre(tokens.get(indexToken).getCadena())) {
                        estado = "F";
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError("token no esperado")) + "\n";
                        salir = true;
                    }
                    break;
                case "I":
                    if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && tokens.get(indexToken).getCadena().equals(":")) {
                        estado = "K";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && tokens.get(indexToken).getCadena().equals(",")) {
                        estado = "J";
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError("token no esperado")) + "\n";
                        salir = true;
                    }
                    break;
                case "J":
                    if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "I";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR) && estaDeclarada(tokens.get(indexToken).getCadena())) {
                        estado = "I";
                        agregarLlamada(tokens.get(indexToken).getCadena());
                    } else if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR) && !estaDeclarada(tokens.get(indexToken).getCadena())) {
                        estado = "ERROR";
                        System.out.println("la variable " + tokens.get(indexToken).getCadena() + " no está definida");
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError("token no esperado")) + "\n";
                        salir = true;
                    }
                    break;
                case "K":
                    if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "L";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR) && estaDeclarada(tokens.get(indexToken).getCadena())) {
                        estado = "L";
                        agregarLlamada(tokens.get(indexToken).getCadena());
                    } else if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR) && !estaDeclarada(tokens.get(indexToken).getCadena())) {
                        estado = "ERROR";
                        System.out.println("la variable " + tokens.get(indexToken).getCadena() + " no está definida");
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError("token no esperado")) + "\n";
                        salir = true;
                    }
                    break;
                case "L":
                    if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && tokens.get(indexToken).getCadena().equals(",")) {
                        estado = "M";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esCierre(tokens.get(indexToken).getCadena())) {
                        estado = "F";
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError("token no esperado")) + "\n";
                        salir = true;
                    }
                    break;
                case "M":
                    if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "N";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR) && estaDeclarada(tokens.get(indexToken).getCadena())) {
                        estado = "N";
                        agregarLlamada(tokens.get(indexToken).getCadena());
                    } else if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR) && !estaDeclarada(tokens.get(indexToken).getCadena())) {
                        estado = "ERROR";
                        System.out.println("la variable " + tokens.get(indexToken).getCadena() + " no está definida");
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError("token no esperado")) + "\n";
                        salir = true;
                    }
                    break;
                case "N":
                    if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && tokens.get(indexToken).getCadena().equals(":")) {
                        estado = "K";
                    } else {
                        estado = "ERROR";
                        errores += hayError(estadoError("token no esperado")) + "\n";
                        salir = true;
                    }
                    break;
                case "ERROR":
                    salir = true;
                    break;
                default:
                    estado = "ERROR";
                    throw new AssertionError();

            }
            if (salir) {
                salir = false;
                break;
            }
            castear();
        }
        if (estado == "F" && indexToken == tokens.size()) {
            for (String variable : variables) {
                varDeclaradas.add(new Variable(variable, (linea)));
            }
            variables.clear();
            errores += "no hay errores en linea: " + (linea) + "\n";
        } else {
            variables.clear();
            errores += hayError(estado) + "\n";
        }
    }

    private void validarExpresionesLogicas() {
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "B";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "B";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esApertura(tokens.get(indexToken).getCadena())) {
                        estado = "C";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.PALABRA_RESERVADA) && tokens.get(indexToken).getCadena().equals("len")) {
                        estado = "K";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.INDENT)) {
                        estado = "A";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "B":
                    if (tokens.get(indexToken).getId().equals(TokenId.OPERADOR_COMPARACION)) {
                        estado = "D";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.PALABRA_RESERVADA) && tokens.get(indexToken).getCadena().equals("in")) {
                        estado = "I";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.PALABRA_RESERVADA) && tokens.get(indexToken).getCadena().equals("is")) {
                        estado = "J";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.INDENT)) {
                        estado = "B";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "C":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "E";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "E";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.INDENT)) {
                        estado = "C";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "D":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "H";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "H";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.INDENT)) {
                        estado = "D";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "E":
                    if (tokens.get(indexToken).getId().equals(TokenId.OPERADOR_COMPARACION)) {
                        estado = "F";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esCierre(tokens.get(indexToken).getCadena())) {
                        estado = "D";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.INDENT)) {
                        estado = "E";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "F":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "G";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "G";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.INDENT)) {
                        estado = "F";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "G":
                    if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esCierre(tokens.get(indexToken).getCadena())) {
                        estado = "H";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.INDENT)) {
                        estado = "G";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "H":
                    if (tokens.get(indexToken).getId().equals(TokenId.OPERADOR_LOGICO)) {
                        estado = "A";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.INDENT)) {
                        estado = "H";
                    } else {
                        estado = "H";

                    }
                    break;
                case "I":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "H";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "H";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.INDENT)) {
                        estado = "I";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "J":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "H";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "H";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.PALABRA_RESERVADA) && tokens.get(indexToken).getCadena().equals("None")) {
                        estado = "H";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.INDENT)) {
                        estado = "J";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "K":
                    if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esApertura(tokens.get(indexToken).getCadena())) {
                        estado = "C";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.INDENT)) {
                        estado = "K";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "ERROR":

                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    private void validarIff() {
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokens.get(indexToken).getId().equals(TokenId.PALABRA_RESERVADA) && tokens.get(indexToken).getCadena().equals("if")) {
                        estado = "B";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "B":
                    estado = "C";//necesito validar expresion y seguir con el flujo de ejecución

                    break;
                case "C":
                    if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && tokens.get(indexToken).getCadena().equals(":")) {
                        estado = "D";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "D":
                    estado = "E";//necesito validar boque de código 
                    break;
                case "E":
                    if (tokens.get(indexToken).getId().equals(TokenId.PALABRA_RESERVADA) && tokens.get(indexToken).getCadena().equals("else") || tokens.get(indexToken).getCadena().equals("elif")) {
                        estado = "F";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "F":
                    estado = "C";//necesito validar expresion y seguir con el flujo de ejecución
                    if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && tokens.get(indexToken).getCadena().equals(":")) {
                        estado = "D";
                    } else {
                        estado = "ERROR";
                    }

                    break;
                case "ERROR":

                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    private String estadoError(String estado) {
        String error = "";
        switch (estado) {
            case "B":
                error = "Se esperaba un token después del identificador";
                return error;
            case "C":
                error = "Se esperaba otro identificador";
                return error;
            case "D":
                error = "Se esperaba un operador asignador";
                return error;
            case "E":
                error = "Se esperaba un token después del operador";
                return error;
            case "H":
                error = "Falta operador de cierre";
                return error;
            default:
                return error;
        }

    }

    private void siguiente(String validacion) {
        try {
            if (tokens.get(indexToken + 1).getId().equals(this)) {

            }
        } catch (Exception e) {
        }

    }

    private boolean estaDeclarada(String var) {
        for (Variable variable : varDeclaradas) {
            if (variable.equals(var)) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }

    private void agregarLlamada(String var) {
        for (Variable variable : varDeclaradas) {
            if (variable.equals(var)) {
                variable.setLlamadas(variable.getLlamadas() + 1);
            }

        }

    }

    private boolean esApertura(String var) {
        if (var.equals("(") || var.equals("{") || var.equals("[")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean esCierre(String var) {
        if (var.equals(")") || var.equals("]") || var.equals("}")) {
            return true;
        } else {
            return false;
        }
    }

    private String hayError(String mensaje) {
        String error = "Error en la línea " + linea
                + ": " + mensaje;
        return error;
    }

    private void castear() {
        try {
            this.indexToken++;
        } catch (Exception e) {
            System.out.println("no hay mas tokens");
        }
    }

}
