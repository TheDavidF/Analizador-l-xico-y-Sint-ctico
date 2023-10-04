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

    public AnalizadorS(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.indexToken = 0;
        this.varDeclaradas = new ArrayList<>();
        this.linea = 1;
        this.errores = "";

    }

    public void analizador() {
        while (indexToken < tokens.size()) {
            if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                validarDeclaracionAsignacion();
            } else {
                castear();
            }
        }
        System.out.println(errores);

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
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "C":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "E";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "E";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "D":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "H";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "H";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "E":
                    if (tokens.get(indexToken).getId().equals(TokenId.OPERADOR_COMPARACION)) {
                        estado = "F";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esCierre(tokens.get(indexToken).getCadena())) {
                        estado = "D";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "F":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "G";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "G";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "G":
                    if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esCierre(tokens.get(indexToken).getCadena())) {
                        estado = "H";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "H":
                    if (tokens.get(indexToken).getId().equals(TokenId.OPERADOR_LOGICO)) {
                        estado = "A";
                    } else {
                        estado = "H";

                    }
                    break;
                case "I":
                    if (tokens.get(indexToken).getId().equals(TokenId.IDENTIFICADOR)) {
                        estado = "H";
                    } else if (tokens.get(indexToken).getId().equals(TokenId.CONSTANTE)) {
                        estado = "H";
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
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "K":
                    if (tokens.get(indexToken).getId().equals(TokenId.OTROS_OPERADORES) && esApertura(tokens.get(indexToken).getCadena())) {
                        estado = "C";
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
    
    private void validarIf(){
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if(tokens.get(indexToken).getId().equals(TokenId.PALABRA_RESERVADA) && tokens.get(indexToken).getCadena().equals("if")){
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
                    if(tokens.get(indexToken).getId().equals(TokenId.PALABRA_RESERVADA) && tokens.get(indexToken).getCadena().equals("else") || tokens.get(indexToken).getCadena().equals("elif")){
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
