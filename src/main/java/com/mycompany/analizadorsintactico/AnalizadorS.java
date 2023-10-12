/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorsintactico;

import com.mycompany.analizadorlexico.Token;
import com.mycompany.analizadorlexico.TokenId;
import java.util.ArrayList;

/**
 *
 * @author DAVID
 */
public class AnalizadorS {

    private final ArrayList<Token> tokens;
    private int indexToken;
    private Token token;
    private String estado;
    private final ArrayList<String> varDeclaradas;
    private int linea;
    private String errores;
    private final ArrayList<Token> sentencias;
    private int indent;

    public AnalizadorS(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.indexToken = 0;
        this.varDeclaradas = new ArrayList<>();
        this.linea = 1;
        this.errores = "";
        sentencias = new ArrayList<>();
        this.indent = 0;

    }

    public void analizar() throws SyntaxError {
        while (indexToken < tokens.size()) {
            actualizarLinea();
            if (tokens.get(indexToken).getLinea() == linea) {
                if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                    automataDec();
                } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("if")) {
                    automataIf();
                } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("while")) {
                    automataWhile();
                } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("print")) {

                } else if (tokenEsperado(TokenId.COMENTARIO, indexToken)) {
                    castear();
                    actualizarLinea();
                } else if (tokenEsperado(TokenId.INDENT, indexToken)) {
                    actualizarLinea();
                    errores += "Error en linea: " + linea + ", indentación fuera de lugar" + "\n";
                    castear();
                } else {
                    actualizarLinea();
                    errores += "Error en linea: " + linea + ", sentencia invalida" + "\n";
                    castear();
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
    }

    private void declararVariables(ArrayList<String> vars) {
        boolean declarada = false;
        for (String var : vars) {
            if (varDeclaradas.size() == 0) {
                varDeclaradas.add(var);
            } else {
                for (String varDeclarada : varDeclaradas) {
                    if (varDeclarada.equals(var)) {
                        declarada = true;
                    }
                }
                if(!declarada){
                    varDeclaradas.add(var);
                }
            }
        }
    }

    private boolean autoPrint() {
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("print")) {
                        estado = "B";
                        castear();
                    }
                    break;
                case "B":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("(")) {
                        estado = "C";
                        castear();
                    } else {
                        actualizarLinea();
                        errores += errores += "ERROR en linea :" + (linea) + ", sentencia invalida" + "\n";
                        castear();
                        estado = "C";
                    }
                    break;
                case "C":
                    if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) && (indexToken + 1) != tokens.size() && tokens.get(indexToken + 1).getCadena().equals(")")) {
                        if (estaDeclarada(tokens.get(indexToken).getCadena())) {
                            castear();
                            estado = "D";
                        } else {
                            actualizarLinea();
                            errores += "ERROR en linea :" + (linea) + ", la variable " + tokens.get(indexToken).getCadena() + " no esta declarada" + "\n";
                            estado = "D";
                            castear();

                        }
                    } else if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                        if (automataExpresion()) {
                            estado = "D";
                        } else {
                            actualizarLinea();
                            System.out.println("Error en linea: " + linea + ", expresion invalida");
                            estado = "D";
                        }
                    } else if (tokenEsperado(TokenId.CONSTANTE, indexToken) && (indexToken + 1) != tokens.size() && tokens.get(indexToken + 1).getCadena().equals(")")) {
                        castear();
                        estado = "D";
                    } else if (tokenEsperado(TokenId.CONSTANTE, indexToken)) {
                        if (automataExpresion()) {
                            estado = "D";
                        } else {
                            System.out.println("Error en linea: " + linea + ", expresion invalida");
                            estado = "D";
                        }
                    } else if (tokens.get(indexToken).getCadena().equals(")")) {
                        estado = "D";
                    } else {
                        if (saltoLinea()) {
                            castear();
                            return false;
                        } else {
                            estado = "D";
                            actualizarLinea();
                            errores += errores += "ERROR en linea :" + (linea) + ", sentencia invalida" + "\n";
                            castear();
                        }
                    }
                    break;
                case "D":
                    if (tokens.get(indexToken).getCadena().equals(")")) {
                        estado = "E";
                        castear();
                    } else {
                        estado = "E";
                        actualizarLinea();
                        errores += errores += "ERROR en linea :" + (linea) + ", sentencia invalida, falta )" + "\n";
                    }
                    break;
                case "E":
                    return true;
                default:
                    throw new AssertionError();
            }
        }
        if (indexToken == tokens.size() && !estado.equals("E")) {
            actualizarLinea();
            errores += "Error en linea: " + linea + " declaración invalida" + "\n";
            System.out.println("Error en linea: " + linea + " declaración invalida");
            return false;
        } else {
            return true;
        }
    }

    private boolean automataDec() {
        actualizarLinea();
        ArrayList<String> vars = new ArrayList<>();
        estado = "A";
        while (indexToken < tokens.size()) {
            if (saltoLinea()) {
                if (estado.equals("E")) {
                    declararVariables(vars);
                    vars.clear();
                    actualizarLinea();
                    return true;
                } else {
                    errores += "ERROR en linea :" + (linea) + ", declaración invalida" + "\n";
                    System.out.println("ERROR en linea :" + (linea) + ", declaración invalida");
                    return false;
                }
            } else {
                switch (estado) {
                    case "A":
                        if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                            estado = "B";
                            vars.add(tokens.get(indexToken).getCadena());
                            castear();
                        } else {
                            estado = "ERROR";
                            errores += "Error en linea:" + linea + " se esperaba un identificador" + "\n";
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
                            errores += "Error en linea:" + linea + " se esperaba un =" + "\n";
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
                            errores += "Error en linea:" + linea + " se esperaba un =" + "\n";
                            System.out.println("Error en linea:" + linea + " se esperaba un =");
                            actualizarLinea();
                            return false;
                        }
                        break;
                    case "E":
                        if (tokenEsperado(TokenId.COMENTARIO, indexToken)) {
                            castear();
                            actualizarLinea();
                            break;
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                            estado = "C";
                        } else {
                            castear();
                            actualizarLinea();
                            declararVariables(vars);
                            vars.clear();
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
            errores += "Error en linea: " + linea + " declaración invalida" + "\n";
            System.out.println("Error en linea: " + linea + " declaración invalida");
            return false;
        } else {
            declararVariables(vars);
            vars.clear();
            return true;
        }
    }

    private boolean automataExpresion() {
        actualizarLinea();
        estado = "A";
        while (indexToken < tokens.size()) {
            if (saltoLinea()) {
                if (estado.equals("B")) {
                    return true;
                } else {
                    actualizarLinea();
                    errores += "ERROR en linea: " + linea + ", expresion inválida" + "\n";
                    System.out.println("ERROR en linea :" + (linea - 1) + ", expresión invalida");

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
                                estado = "B";
                                break;
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
                            errores += "Error en linea:" + linea + " expresión inválida" + "\n";
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
                            errores += "Error en linea:" + linea + " falta expresión después del operador" + "\n";
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
                            errores += "Error en linea:" + linea + " Se esperaba una expresión" + "\n";
                            System.out.println("Error en linea:" + linea + " Se esperaba una expresión");
                        }
                        break;
                    case "E":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(")")) {
                            estado = "B";
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            errores += "Error en linea:" + linea + " se esperaba un paréntesis de cierre" + "\n";
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
            errores += "Error en linea:" + linea + " expresión invalida" + "\n";
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
                    if (estado.equals("C")) {
                        errores += "ERROR en linea :" + (linea) + ", falta corchete de cierre" + "\n";
                        System.out.println("ERROR en linea :" + (linea) + ", falta corchete de cierre");
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

                            if (!saltoLinea()) {
                                linea++;
                            } else {
                                actualizarLinea();
                            }

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
                        actualizarLinea();
                        errores += "Error linea: " + linea + "array invalido" + "\n";
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
            errores += "Error en linea:" + linea + " expresión invalida" + "\n";
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
                    errores += "ERROR en linea :" + (linea) + ", expresión invalida" + "\n";
                    System.out.println("ERROR en linea :" + (linea) + ", expresión invalida");
                    return false;
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
                            errores += "ERROR en linea :" + (linea) + ", falta corchete de cierre" + "\n";
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
                            errores += "ERROR en linea :" + (linea) + ", falta expresión" + "\n";
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
                            errores += "ERROR en linea :" + (linea) + ", expresión invalida" + "\n";
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
                            errores += "ERROR en linea :" + (linea) + ", falta corchete de cierre" + "\n";
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
                errores += "ERROR en linea :" + (linea) + ", falta llave de cierre" + "\n";
                System.out.println("ERROR en linea :" + (linea) + ", falta llave de cierre");
            } else {
                System.out.println("ERROR en linea :" + (linea) + ", expresión invalida");
            }
            return false;
        }
    }

    private boolean automataIf() {
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("if")) {
                        castear();
                        estado = "B";
                    } else {
                        estado = "ERROR";
                    }
                    break;
                case "B":
                    if (automataCond()) {
                        estado = "C";
                    } else {
                        actualizarLinea();
                        errores += "ERROR en linea :" + (linea) + ", condicion invalida" + "\n";
                        System.out.println("ERROR en linea :" + (linea) + ", condicion invalida");
                        estado = "C";
                    }
                    break;
                case "C":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
                        castear();
                        estado = "D";
                    } else {
                        estado = "D";
                        actualizarLinea();
                        errores += "ERROR en linea :" + (linea - 1) + ", falta :" + "\n";
                        System.out.println("ERROR en linea :" + (linea) + ", falta :");
                    }
                    break;
                case "D":
                    if (tokenEsperado(TokenId.COMENTARIO, indexToken)) {
                        castear();
                        break;
                    } else if (automataBloque()) {
                        estado = "E";
                    } else {
                        estado = "E";
                    }
                    break;
                case "E":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("else")) {
                        castear();
                        estado = "F";
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("elif")) {
                        castear();
                        estado = "H";
                    } else {
                        return true;
                    }
                    break;
                case "F":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
                        castear();
                        actualizarLinea();
                        estado = "G";
                    } else {
                        estado = "G";
                        actualizarLinea();
                        errores += "ERROR en linea :" + (linea - 1) + ", falta :" + "\n";
                        System.out.println("ERROR en linea :" + (linea) + ", falta :");

                    }
                    break;
                case "G":
                    if (tokenEsperado(TokenId.COMENTARIO, indexToken)) {
                        castear();
                        break;
                    } else if (automataBloque()) {
                        estado = "G";
                    } else {
                        estado = "G";
                    }
                    break;
                case "H":
                    if (automataCond()) {
                        estado = "C";
                    } else {
                        estado = "C";
                    }
                    break;
                default:
                    throw new AssertionError();
            }
        }

        if (indexToken == tokens.size() && !estado.equals("E") && !estado.equals("G")) {
            errores += "Error en linea: " + linea + " sentencia invalida" + "\n";
            System.out.println("Error en linea: " + linea + " declaración invalida");
            return false;
        } else {
            return true;
        }

    }

    //para expresiones funciona y en teoria para condicionales if
    private boolean automataBloque() {
        indent++;
        boolean romper = false;
        boolean indentado = false;
        int nivelIndent = 1;
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.COMENTARIO, indexToken)) {
                        castear();
                        break;
                    } else if (tokenEsperado(TokenId.INDENT, indexToken)) {
                        if (indent > nivelIndent) {
                            nivelIndent++;
                            indentado = false;
                            castear();
                            estado = "A";
                        } else {
                            indentado = true;
                            castear();
                            estado = "B";
                        }

                    } else {
                        indentado = false;
                        actualizarLinea();
                        errores += "Error en linea:" + linea + ", falta indentación" + "\n";
                        estado = "B";
                    }
                    break;
                case "B":
                    if (romper) {
                        if (!indentado) {
                            return true;
                        } else {
                            actualizarLinea();
                            errores += "ERROR en linea :" + (linea) + ", el ciclo finalizó, no se evalua esta linea" + "\n";
                        }
                    }
                    if (tokenEsperado(TokenId.COMENTARIO, indexToken)) {
                        castear();
                        estado = "A";
                        break;
                    }
                    if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                        if (automataDec()) {
                            estado = "C";
                            if (!indentado) {
                                actualizarLinea();
                                errores += "ERROR en linea :" + (linea) + ", falta indentación en el bloque" + "\n";
                                System.out.println("ERROR en linea :" + (linea) + ", falta indentación en el bloque");
                            }
                            break;
                        } else {
                            estado = "C";
                            break;
                        }
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("while")) {
                        if (automataWhile()) {
                            estado = "C";
                        } else {
                            estado = "C";
                            break;
                        }
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("if")) {
                        if (automataIf()) {
                            estado = "C";
                        } else {
                            estado = "C";
                            break;
                        }
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("break")) {
                        if (indentado) {
                            castear();
                            estado = "C";
                            romper = true;
                        } else {
                            actualizarLinea();
                            errores += "Error en linea:" + linea + ", falta indentación" + "\n";
                            castear();
                            estado = "C";

                        }
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("print")) {
                        if (autoPrint()) {
                            estado = "C";
                        } else {
                            System.out.println("Error en linea: " + linea + ", sentencia invalida" + "\n");
                            estado = "C";
                        }
                    }
                    break;
                case "C":
                    if (tokenEsperado(TokenId.COMENTARIO, indexToken)) {
                        castear();
                    } else if (tokenEsperado(TokenId.INDENT, indexToken)) {
                        if (nivelIndent < indent) {
                            nivelIndent++;
                            indentado = false;
                            castear();
                        } else {
                            if (nivelIndent == 1) {
                                estado = "B";
                                indentado = true;
                                actualizarLinea();
                                castear();
                            } else {
                                estado = "A";
                                indentado = true;
                                actualizarLinea();
                                castear();
                            }
                        }

                    } else {
                        if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("break")) {
                            actualizarLinea();
                            errores += "Error en linea:" + linea + ", falta indentación" + "\n";
                            estado = "B";
                        }
                        indent--;
                        return true;

                    }
                    break;
                default:
                    throw new AssertionError();
            }

        }
        if (indexToken == tokens.size() && !estado.equals("C")) {
            errores += "Error en linea: " + linea + " sentencia invalida" + "\n";
            System.out.println("Error en linea: " + linea + " declaración invalida");
            return false;
        } else {
            return true;
        }
    }

    private boolean automataCond() {
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.BOOLEANO, indexToken)) {
                        castear();
                        return true;
                    } else if (tokenEsperado(TokenId.OPERADOR_LOGICO, indexToken) && tokens.get(indexToken).getCadena().equals("not")) {
                        castear();
                        estado = "E";
                    } else if (automataExpresion()) {
                        estado = "B";
                    } else {
                        estado = "B";
                    }
                    break;
                case "B":
                    if (tokenEsperado(TokenId.OPERADOR_COMPARACION, indexToken)) {
                        castear();
                        estado = "C";
                    } else {
                        actualizarLinea();
                        castear();
                        errores += "ERROR en linea :" + (linea) + ", falta operador lógico" + "\n";
                        System.out.println("ERROR en linea :" + (linea) + ", falta operador lógico");
                        estado = "C";
                    }
                    break;
                case "C":
                    if (automataExpresion()) {
                        estado = "D";
                    } else {
                        estado = "D";
                    }
                    break;
                case "D":
                    if (tokenEsperado(TokenId.OPERADOR_LOGICO, indexToken) && !tokens.get(indexToken).getCadena().equals("not")) {
                        castear();
                        estado = "F";
                    } else {
                        return true;
                    }
                    break;
                case "E":
                    if (automataExpresion()) {
                        estado = "B";
                    } else {
                        estado = "B";
                    }
                    break;
                case "F":
                    if (tokenEsperado(TokenId.OPERADOR_LOGICO, indexToken) && tokens.get(indexToken).getCadena().equals("not")) {
                        castear();
                        estado = "E";
                    } else if (automataExpresion()) {
                        estado = "B";
                    } else {
                        actualizarLinea();
                        errores += "ERROR en linea :" + (linea) + ", falta expresion" + "\n";
                        System.out.println("ERROR en linea :" + (linea) + ", falta falta expresion");
                        estado = "B";
                    }
                    break;
                default:
                    throw new AssertionError();
            }

        }

        if (indexToken == tokens.size() && !estado.equals("D")) {
            errores += "Error en linea: " + linea + " sentencia invalida" + "\n";
            System.out.println("Error en linea: " + linea + " declaración invalida");
            return false;
        } else {
            return true;
        }
    }

    private boolean automataWhile() {
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("while")) {
                        castear();
                        estado = "B";
                    }
                    break;
                case "B":
                    if (automataCond()) {
                        estado = "C";
                    } else {
                        estado = "C";
                    }
                    break;
                case "C":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
                        castear();
                        estado = "D";
                    } else {
                        estado = "D";
                        actualizarLinea();
                        errores += "ERROR en linea :" + (linea) + ", falta :";
                        castear();

                    }
                    break;
                case "D":
                    if (automataBloque()) {
                        estado = "E";
                    } else {
                        estado = "E";
                    }
                    break;
                case "E":
                    return true;
                default:
                    throw new AssertionError();
            }

        }

        if (indexToken == tokens.size() && !estado.equals("E")) {
            errores += "Error en linea: " + linea + " sentencia invalida" + "\n";
            System.out.println("Error en linea: " + linea + " declaración invalida");
            return false;
        } else {
            return true;
        }
    }

    private void actualizarLinea() {
        try {
            this.linea = tokens.get(indexToken).getLinea();
        } catch (Exception e) {
        }
    }

    public void programa() throws SyntaxError {
        while (indexToken < tokens.size()) {
            sentencia();

        }
        //System.out.println(errores);

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
        }
    }

    /*
    private void sentencia1() {
        if (automataDec()) {

        } else if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) && tokenEsperado(TokenId.OPERADOR_ARITMETICO, (indexToken + 1))) {
            castear();
        } else if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) || tokenEsperado(TokenId.CONSTANTE, indexToken) || tokenEsperado(TokenId.OTROS_OPERADORES, indexToken)
                && esApertura(tokens.get(indexToken).getCadena())) {
        } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("if")) {
            validarIf();
        } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("while")) {
            validarWhile();
        } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("for")) {
            validarFor();
        }
    }

     */
    private void validarDeclaracion() throws SyntaxError {
        if (tokenEsperado(TokenId.OPERADOR_ASIGNADOR, (indexToken))) {
            castear();
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
        } else {
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

    /*private String encontrarParentesisDerecho(String apertura, String cierre) throws SyntaxError {
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
     */
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

    private void siguiente(String validacion) {
        try {
            if (tokens.get(indexToken + 1).getId().equals(this)) {

            }
        } catch (Exception e) {
        }

    }

    private boolean estaDeclarada(String var) {
        for (String variable : varDeclaradas) {
            if (variable.equals(var)) {
                return true;
            } 

        }
        return false;
    }

    private void castear() {
        try {
            this.indexToken++;
        } catch (Exception e) {
            System.out.println("no hay mas tokens");
        }
    }

    public String getErrores() {
        return errores;
    }

}
