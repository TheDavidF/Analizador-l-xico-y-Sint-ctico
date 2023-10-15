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
    private String estado;
    private final ArrayList<Variable> varDeclaradas;
    private int linea;
    private String errores;
    private final ArrayList<Funcion> funciones;
    private int indent;
    private String contenido = "";
    private ArrayList<BloqueDeCodigo> bloques;
    private Instruccion instruccion;
    private ArrayList<String> parametros;
    private boolean finalizoBloque;
    private boolean ternario ;

    public AnalizadorS(ArrayList<Token> tokens) {
        this.parametros = new ArrayList<>();
        this.instruccion = new Instruccion();
        this.tokens = tokens;
        this.bloques = new ArrayList<>();
        this.indexToken = 0;
        this.varDeclaradas = new ArrayList<>();
        this.linea = 1;
        this.errores = "";
        this.funciones = new ArrayList<>();
        this.indent = 0;
        this.finalizoBloque = false;
        this.ternario = false;

    }

    public ArrayList<Variable> getVar() {
        return varDeclaradas;
    }

    public void analizar() throws SyntaxError {
        while (indexToken < tokens.size()) {
            actualizarLinea();
            if (tokens.get(indexToken).getLinea() == linea) {
                if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                    if ((indexToken + 1) != tokens.size() && tokenEsperado(TokenId.OTROS_OPERADORES, indexToken + 1) && tokens.get(indexToken + 1).getCadena().equals("(")) {
                        llamarMétodo();
                    } else {
                        automataDec();
                    }
                } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("if")) {
                    automataIf();
                } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("while")) {
                    automataWhile();
                } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("print")) {
                    autoPrint();
                } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("def")) {
                    autoFuncion();
                } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("for")) {
                    autoFor();
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
                    linea++;
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

    private void declararVariables(ArrayList<Variable> vars) {
        boolean declarada = false;
        for (Variable var : vars) {
            if (varDeclaradas.size() == 0) {
                varDeclaradas.add(var);
            } else {
                for (Variable varDeclarada : varDeclaradas) {
                    if (varDeclarada.getNomre().equals(var.getNomre())) {
                        if (varDeclarada.getExpresion() != contenido) {
                            varDeclarada.setExpresion(contenido);
                        }
                        declarada = true;
                    }
                }
                if (!declarada) {
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
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        estado = "B";
                        castear();
                    }
                    break;
                case "B":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("(")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        estado = "C";
                        castear();
                    } else {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        actualizarLinea();
                        errores += errores += "ERROR en linea :" + (linea) + ", sentencia invalida" + "\n";
                        castear();
                        estado = "C";
                    }
                    break;
                case "C":
                    if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) && (indexToken + 1) != tokens.size() && tokens.get(indexToken + 1).getCadena().equals(")")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        if (estaDeclarada(tokens.get(indexToken).getCadena()) || parametrosV(tokens.get(indexToken).getCadena())) {
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
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
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
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        estado = "E";
                        castear();
                    } else {
                        if (saltoLinea()) {
                            estado = "E";
                            actualizarLinea();
                            errores += errores += "ERROR en linea :" + (linea) + ", sentencia invalida, falta )" + "\n";
                        } else {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            actualizarLinea();
                            errores += "ERROR en linea :" + (linea) + ", sentencia invalida" + "\n";
                            castear();
                        }

                    }
                    break;
                case "E":
                    instruccion.vaciarInstruccion();
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
            instruccion.vaciarInstruccion();
            return true;
        }
    }

    private boolean automataDec() {
        Variable variable = null;
        actualizarLinea();
        ArrayList<Variable> vars = new ArrayList<>();
        estado = "A";
        while (indexToken < tokens.size()) {
            if (saltoLinea()) {
                if (estado.equals("E")) {
                    instruccion.vaciarInstruccion();
                    vars.add(variable);
                    declararVariables(vars);
                    vars.clear();
                    actualizarLinea();
                    return true;
                } else {
                    instruccion.vaciarInstruccion();
                    errores += "ERROR en linea :" + (linea) + ", declaración invalida" + "\n";
                    System.out.println("ERROR en linea :" + (linea) + ", declaración invalida");
                    return false;
                }
            } else {
                switch (estado) {
                    case "A":
                        if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            estado = "B";
                            variable = new Variable(tokens.get(indexToken).getCadena(), tokens.get(indexToken).getLinea(), tokens.get(indexToken).getColumna());
                            castear();
                        } else {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
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
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            estado = "C";
                            castear();
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            estado = "A";
                            castear();
                        } else if (tokenEsperado(TokenId.OPERADOR_ARITMETICO, indexToken)) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            estado = "D";
                            castear();
                        } else {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            estado = "ERROR";
                            errores += "Error en linea:" + linea + " se esperaba un =" + "\n";
                            System.out.println("Error en linea:" + linea + " se esperaba un =");
                            castear();
                            actualizarLinea();
                            return false;
                        }
                        break;
                    case "C":
                        if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)
                                && (indexToken + 1) != tokens.size() && tokenEsperado(TokenId.OTROS_OPERADORES, indexToken + 1)
                                && tokens.get(indexToken + 1).getCadena().equals("(")) {
                            if (llamarMétodo()) {
                                estado = "E";
                            } else {
                                estado = "E";
                            }
                        } else if (automataExpresion()) {
                            variable.setExpresion(contenido);
                            estado = "E";
                        } else {
                            variable.setExpresion(contenido);
                            estado = "ERROR";
                            actualizarLinea();
                            return false;

                        }
                        break;
                    case "D":
                        if (tokenEsperado(TokenId.OPERADOR_ASIGNADOR, indexToken)) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            estado = "C";
                            castear();
                        } else {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
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
                            break;
                        }
                        if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("if")) {
                            if (!saltoLinea() && autoTernario()) {
                                estado = "E";
                            } else {
                                estado = "E";
                            }
                        } else if (tokenEsperado(TokenId.COMENTARIO, indexToken)) {
                            castear();
                            actualizarLinea();
                            break;
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            castear();
                            estado = "C";
                        } else {
                            instruccion.vaciarInstruccion();
                            castear();
                            actualizarLinea();
                            vars.add(variable);
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
            instruccion.vaciarInstruccion();
            vars.add(variable);
            declararVariables(vars);
            vars.clear();
            return true;
        }
    }

    private boolean autoTernario() {
        ternario = true;
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("if")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        estado = "B";
                    }
                    break;
                case "B":
                    if (automataExpresion()) {
                        estado = "C";
                    } else {
                        estado = "C";
                    }
                    break;
                case "C":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("else")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        estado = "D";
                    }
                    break;
                case "D":
                    if (automataExpresion()) {
                        estado = "E";
                    } else {
                        estado = "E";
                    }
                    break;
                case "E":
                    instruccion.vaciarInstruccion();
                    ternario = false;
                    return true;
                default:
                    throw new AssertionError();
            }
        }
        if (indexToken == tokens.size() && !estado.equals("E")) {
            actualizarLinea();
            errores += "Error en linea: " + linea + " condicional ternario invalido" + "\n";
            ternario = false;
            return false;
        } else {
            instruccion.vaciarInstruccion();
            ternario = false;
            return true;
        }
    }

    private boolean automataExpresion() {
        contenido = "";
        actualizarLinea();
        estado = "A";
        while (indexToken < tokens.size()) {
            if (saltoLinea()) {
                if (estado.equals("B")) {
                    instruccion.vaciarInstruccion();
                    return true;
                } else {
                    instruccion.vaciarInstruccion();
                    actualizarLinea();
                    errores += "ERROR en linea: " + linea + ", expresion inválida" + "\n";
                    System.out.println("ERROR en linea :" + (linea - 1) + ", expresión invalida");
                }
            } else {
                switch (estado) {
                    case "A":
                        if (tokens.get(indexToken).getCadena().equals("not")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            castear();
                            estado = "A";
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("[")) {
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
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "B";
                            if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                                if (estaDeclarada(tokens.get(indexToken).getCadena()) || parametrosV(tokens.get(indexToken).getCadena())) {
                                    instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                                    castear();
                                    break;
                                } else {
                                    instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                                    actualizarLinea();
                                    errores += "Error en linea:" + linea + ", la variable " + tokens.get(indexToken).getCadena() + " no esta declarada" + "\n";
                                    castear();
                                    break;
                                }
                            }
                            if (saltoLinea()) {
                                instruccion.vaciarInstruccion();
                                return true;
                            }
                            castear();
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("(")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "D";
                            castear();
                        } else if (tokenEsperado(TokenId.OPERADOR_ARITMETICO, indexToken)) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "C";
                            castear();
                        } else if (tokenEsperado(TokenId.OPERADOR_COMPARACION, indexToken)) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "C";
                            castear();
                        } else {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            estado = "ERROR";
                            actualizarLinea();
                            errores += "Error en linea:" + linea + " expresión inválida" + "\n";
                            System.out.println("Error en linea:" + linea + " expresión inválida");
                        }
                        break;
                    case "B":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("(")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "D";
                            castear();
                        } else if (tokenEsperado(TokenId.OPERADOR_ARITMETICO, indexToken)) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "C";
                            castear();
                        } else if (tokenEsperado(TokenId.OPERADOR_COMPARACION, indexToken) ) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "C";
                            castear();
                        } else if (tokenEsperado(TokenId.OPERADOR_LOGICO, indexToken)) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "A";
                            castear();
                        } else if (tokens.get(indexToken).getCadena().equals("is") || tokens.get(indexToken).getCadena().equals("in")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "A";
                            castear();
                        } else {
                            instruccion.vaciarInstruccion();
                            return true;
                        }
                        break;
                    case "C":
                        if (tokens.get(indexToken).getCadena().equals("not")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            castear();
                            estado = "C";
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("(")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "D";
                            castear();
                        } else if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) || tokenEsperado(TokenId.CONSTANTE, indexToken) || tokenEsperado(TokenId.BOOLEANO, indexToken)) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "B";
                            if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                                if (estaDeclarada(tokens.get(indexToken).getCadena()) || parametrosV(tokens.get(indexToken).getCadena())) {
                                    instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                                    castear();
                                    break;
                                } else {
                                    instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                                    actualizarLinea();
                                    errores += "Error en linea:" + linea + ", la variable " + tokens.get(indexToken).getCadena() + " no esta declarada" + "\n";
                                    castear();
                                    break;
                                }
                            }
                            castear();
                        } else {
                            estado = "ERROR";
                            actualizarLinea();
                            errores += "Error en linea:" + linea + " falta expresión después del operador" + "\n";
                            System.out.println("Error en linea:" + linea + " falta expresión después del operador");
                        }
                        break;
                    case "D":
                        if (tokens.get(indexToken).getCadena().equals("not")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            castear();
                            estado = "D";
                        } else if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken) || tokenEsperado(TokenId.CONSTANTE, indexToken) || tokenEsperado(TokenId.BOOLEANO, indexToken)) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "E";
                            if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                                if (estaDeclarada(tokens.get(indexToken).getCadena())) {
                                    instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                                    castear();
                                    break;
                                } else {
                                    instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                                    actualizarLinea();
                                    errores += "Error en linea:" + linea + ", la variable " + tokens.get(indexToken).getCadena() + " no esta declarada" + "\n";
                                    castear();
                                    break;
                                }
                            }
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
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "B";
                        } else {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
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
            instruccion.vaciarInstruccion();
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
                    instruccion.vaciarInstruccion();
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
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "B";
                            castear();
                        } else {
                            estado = "ERROR";
                            return false;
                        }
                        break;
                    case "B":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("]")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
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
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "D";
                            castear();
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "E";
                            castear();
                        } else {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            estado = "ERROR";
                            actualizarLinea();
                            System.out.println("ERROR en linea :" + (linea) + ", falta corchete de cierre");
                            castear();

                            if (!saltoLinea()) {
                                instruccion.vaciarInstruccion();
                                linea++;
                            } else {
                                instruccion.vaciarInstruccion();
                                actualizarLinea();
                            }
                            return false;
                        }
                        break;
                    case "D":
                        instruccion.vaciarInstruccion();
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
                    instruccion.vaciarInstruccion();
                    return true;
                } else {
                    instruccion.vaciarInstruccion();
                    errores += "ERROR en linea :" + (linea) + ", expresión invalida" + "\n";
                    System.out.println("ERROR en linea :" + (linea) + ", expresión invalida");
                    return false;
                }
            } else {
                switch (estado) {
                    case "A":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("{")) {
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "B";
                            castear();
                        } else {
                            estado = "ERROR";
                            return false;
                        }
                        break;
                    case "B":
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("}")) {
                            contenido += tokens.get(indexToken).getCadena();
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
                            contenido += tokens.get(indexToken).getCadena();
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
                            contenido += tokens.get(indexToken).getCadena();
                            estado = "G";
                            castear();
                        } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                            contenido += tokens.get(indexToken).getCadena();
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
        int lineaInicio = 1;
        int columnaInicio = 1;
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("if")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        lineaInicio = tokens.get(indexToken).getLinea();
                        columnaInicio = tokens.get(indexToken).getColumna();
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
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        if (saltoLinea()) {
                            instruccion.vaciarInstruccion();
                        }
                        estado = "D";
                    } else {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        if (saltoLinea()) {
                            instruccion.vaciarInstruccion();
                        }
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
                    } else if (automataBloque(new BloqueDeCodigo("bloqueIf linea:" + lineaInicio, lineaInicio, columnaInicio))) {
                        estado = "E";
                    } else {
                        estado = "E";
                    }
                    break;
                case "E":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("else")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        lineaInicio = tokens.get(indexToken).getLinea();
                        columnaInicio = tokens.get(indexToken).getColumna();
                        castear();
                        estado = "F";
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("elif")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        lineaInicio = tokens.get(indexToken).getLinea();
                        columnaInicio = tokens.get(indexToken).getColumna();
                        castear();
                        estado = "H";
                    } else {
                        return true;
                    }
                    break;
                case "F":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        if (saltoLinea()) {
                            instruccion.vaciarInstruccion();
                        }
                        actualizarLinea();
                        estado = "G";
                    } else {
                        if (saltoLinea()) {
                            instruccion.vaciarInstruccion();
                        }
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
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
                    } else if (automataBloque(new BloqueDeCodigo("bloqueElse linea:" + lineaInicio, lineaInicio, columnaInicio))) {
                        estado = "G";
                        return true;
                    } else {
                        estado = "G";
                        return true;
                    }
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

    private boolean parametrosV(String paraString) {
        for (String parametro : parametros) {
            if (parametro.equals(paraString)) {
                return true;
            }
        }
        return false;
    }

    //para expresiones funciona y en teoria para condicionales if
    private boolean automataBloque(BloqueDeCodigo bloque1) {
        BloqueDeCodigo bloque = bloque1;
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
                        estado = "C";
                        nivelIndent = 1;
                        break;
                    }
                    if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                        castear();
                        if ((indexToken + 1) != tokens.size()
                                && (!tokenEsperado(TokenId.OPERADOR_ASIGNADOR, indexToken) || tokenEsperado(TokenId.OPERADOR_ARITMETICO, indexToken))
                                && !tokenEsperado(TokenId.OPERADOR_ASIGNADOR, indexToken + 1)) {
                            indexToken--;
                            if (automataExpresion()) {
                                estado = "C";
                                nivelIndent = 1;
                            } else {
                                estado = "C";
                                nivelIndent = 1;
                            }
                        } else {
                            indexToken--;
                            if (automataDec()) {
                                estado = "C";
                                nivelIndent = 1;
                                if (!indentado) {
                                    actualizarLinea();
                                    errores += "ERROR en linea :" + (linea) + ", falta indentación en el bloque" + "\n";
                                    System.out.println("ERROR en linea :" + (linea) + ", falta indentación en el bloque");
                                }
                                break;
                            } else {
                                nivelIndent = 1;
                                estado = "C";
                                break;
                            }
                        }
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("while")) {
                        if (automataWhile()) {
                            nivelIndent = 1;
                            estado = "C";
                        } else {
                            nivelIndent = 1;
                            estado = "C";
                            break;
                        }
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("if")) {
                        if (automataIf()) {
                            nivelIndent = 1;
                            estado = "C";
                        } else {
                            estado = "C";
                            break;
                        }
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("for")) {
                        if (autoFor()) {
                            nivelIndent = 1;
                            estado = "C";
                        } else {
                            estado = "C";
                            break;
                        }
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("break")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        if (indentado) {
                            castear();
                            nivelIndent = 1;
                            estado = "C";
                            romper = true;
                        } else {
                            actualizarLinea();
                            errores += "Error en linea:" + linea + ", falta indentación" + "\n";
                            castear();
                            nivelIndent = 1;
                            estado = "C";

                        }
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("print")) {
                        if (autoPrint()) {
                            nivelIndent = 1;
                            estado = "C";
                        } else {
                            System.out.println("Error en linea: " + linea + ", sentencia invalida" + "\n");
                            nivelIndent = 1;
                            estado = "C";
                        }
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("return")) {
                        castear();
                        if (automataExpresion()) {
                            nivelIndent = 1;
                            estado = "C";
                            indent--;
                            return true;
                        } else {
                            estado = "C";
                            indent--;
                            return true;
                        }
                    } else if (tokenEsperado(TokenId.CONSTANTE, indexToken)) {
                        if (automataExpresion()) {
                            nivelIndent = 1;
                            estado = "C";
                        } else {
                            nivelIndent = 1;
                            estado = "C";
                        }
                    } else {
                        if (saltoLinea()) {
                            nivelIndent = 1;
                            estado = "C";
                        } else {
                            errores += "Error en linea: " + linea + ", sentencia invalida" + "\n";
                            castear();
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
                            indentado = true;
                            estado = "B";
                            actualizarLinea();
                            castear();
                        }

                    } else if (!indentado && nivelIndent == indent && !tokens.get(indexToken).getCadena().equals("break")
                            && !tokens.get(indexToken).getCadena().equals("else") && !tokens.get(indexToken).getCadena().equals("elif")) {
                        estado = "B";
                        actualizarLinea();
                        errores += "Error en linea:" + linea + ", falta indentación" + "\n";
                        indentado = true;
                    } else if (!indentado && (tokens.get(indexToken).getCadena().equals("else") || tokens.get(indexToken).getCadena().equals("elif"))) {
                        if (saltoLinea()) {
                            if (nivelIndent == (indent - 1)) {
                                indent--;
                                nivelIndent = 1;
                                indentado = true;
                                finalizoBloque = true;
                                return true;
                            } else if (nivelIndent == indent) {
                                indent--;
                                nivelIndent = 1;
                                indentado = true;
                                return true;
                            }
                            indent--;
                            nivelIndent = 1;
                            indentado = true;
                            return true;
                        } else {
                            estado = "B";
                            actualizarLinea();
                            errores += "Error en linea: " + linea + ", sentencia invalida" + "\n";
                            castear();
                            indentado = true;
                        }
                    } else {
                        if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("break")) {
                            actualizarLinea();
                            errores += "Error en linea:" + linea + ", falta indentación" + "\n";
                            nivelIndent = 1;
                            estado = "C";
                            indent--;
                            castear();
                            bloque.setInstrucciones(instruccion.getBloque().getInstrucciones());
                            bloques.add(bloque);
                            return true;

                        }
                        if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("return")) {
                            castear();
                            if (nivelIndent > 1 && indent > 0) {
                                if (automataExpresion()) {
                                    nivelIndent = 1;
                                    estado = "C";
                                    indent--;
                                    bloque.setInstrucciones(instruccion.getBloque().getInstrucciones());
                                    bloques.add(bloque);
                                    return true;
                                } else {
                                    estado = "C";
                                    indent--;
                                    bloque.setInstrucciones(instruccion.getBloque().getInstrucciones());
                                    bloques.add(bloque);
                                    return true;
                                } 
                            }else {
                                actualizarLinea();
                                errores += errores += "Error en linea:" + linea + ", falta indentación" + "\n";;
                               if (automataExpresion()) {
                                    nivelIndent = 1;
                                    estado = "C";
                                    indent--;
                                    bloque.setInstrucciones(instruccion.getBloque().getInstrucciones());
                                    bloques.add(bloque);
                                    return true;
                                } else {
                                    estado = "C";
                                    indent--;
                                    bloque.setInstrucciones(instruccion.getBloque().getInstrucciones());
                                    bloques.add(bloque);
                                    return true;
                                }      
                            }
                        }
                        indent--;
                        bloque.setInstrucciones(instruccion.getBloque().getInstrucciones());
                        bloques.add(bloque);
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

    public ArrayList<BloqueDeCodigo> getBloques() {
        return bloques;
    }

    private boolean automataCond() {
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.BOOLEANO, indexToken)) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        return true;
                    } else if (tokenEsperado(TokenId.OPERADOR_LOGICO, indexToken) && tokens.get(indexToken).getCadena().equals("not")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
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
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        estado = "C";
                    } else {
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            estado = "D";
                        } else {
                            if (!saltoLinea()) {
                                actualizarLinea();
                                castear();
                                errores += "ERROR en linea :" + (linea) + ", falta operador lógico" + "\n";
                                System.out.println("ERROR en linea :" + (linea) + ", falta operador lógico");
                                estado = "C";
                            }
                        }

                    }
                    break;
                case "C":
                    if (automataExpresion()) {
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
                            estado = "D";
                        } else {
                            estado = "B";
                        }
                    } else {
                        if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
                            estado = "D";
                        } else {
                            estado = "B";
                        }
                    }
                    break;
                case "D":
                    if (tokenEsperado(TokenId.OPERADOR_LOGICO, indexToken) && !tokens.get(indexToken).getCadena().equals("not")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
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
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
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
        int lineaInicio = 1;
        int columnaInicio = 1;
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("while")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        lineaInicio = tokens.get(indexToken).getLinea();
                        columnaInicio = tokens.get(indexToken).getColumna();
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
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        if (saltoLinea()) {
                            instruccion.vaciarInstruccion();
                        }
                        estado = "D";
                    } else {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        if (saltoLinea()) {
                            instruccion.vaciarInstruccion();
                        }
                        estado = "D";
                        actualizarLinea();
                        errores += "ERROR en linea :" + (linea) + ", falta :";
                        castear();

                    }
                    break;
                case "D":
                    if (automataBloque(new BloqueDeCodigo("bloqueWhile linea:" + lineaInicio, lineaInicio, columnaInicio))) {
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

    private boolean autoFor() {
        int lineaInicio = 1;
        int columnaInicio = 1;
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("for")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        lineaInicio = tokens.get(indexToken).getLinea();
                        columnaInicio = tokens.get(indexToken).getColumna();
                        estado = "B";
                        castear();
                    }
                    break;
                case "B":
                    if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                        parametros.add(tokens.get(indexToken).getCadena());
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        estado = "C";
                        castear();
                    } else {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        estado = "C";
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " falta identificador" + "\n";
                        castear();
                    }
                    break;
                case "C":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("in")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        estado = "D";
                        castear();
                    } else {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " falta palabra reservada in" + "\n";
                        castear();
                        estado = "D";
                    }
                    break;
                case "D":
                    if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        if (estaDeclarada(tokens.get(indexToken).getCadena())) {
                            estado = "E";
                            castear();
                        } else {
                            actualizarLinea();
                            errores += "Error en linea: " + linea + ", la variable" + tokens.get(indexToken).getCadena() + " no está definída" + "\n";
                            castear();
                            estado = "E";
                        }
                    } else if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("range")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        estado = "K";
                        castear();
                    } else {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " falta una lista" + "\n";
                        castear();
                        estado = "E";
                    }
                    break;
                case "E":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        if (saltoLinea()) {
                            instruccion.vaciarInstruccion();
                        }
                        estado = "F";
                    } else {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        if (saltoLinea()) {
                            instruccion.vaciarInstruccion();
                        }
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " falta :" + "\n";
                        castear();
                        estado = "F";
                    }
                    break;
                case "F":
                    if (automataBloque(new BloqueDeCodigo("bloqueFor linea:" + lineaInicio, lineaInicio, columnaInicio))) {
                        estado = "G";
                    } else {
                        estado = "G";
                    }
                    break;
                case "G":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("else")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        lineaInicio = tokens.get(indexToken).getLinea();
                        columnaInicio = tokens.get(indexToken).getColumna();
                        if (finalizoBloque == false) {
                            estado = "H";
                        } else {
                            estado = "J";
                            break;
                        }

                        castear();
                    } else {
                        instruccion.vaciarInstruccion();

                        return true;
                    }
                    break;
                case "H":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        if (saltoLinea()) {
                            instruccion.vaciarInstruccion();
                        }
                        estado = "I";
                    } else {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        if (saltoLinea()) {
                            instruccion.vaciarInstruccion();
                        }
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " falta :" + "\n";
                        castear();
                        estado = "I";
                    }
                    break;
                case "I":
                    if (automataBloque(new BloqueDeCodigo("bloqueElse linea:" + lineaInicio, lineaInicio, columnaInicio))) {
                        estado = "J";
                    } else {
                        estado = "J";
                    }
                    break;
                case "J":
                    instruccion.vaciarInstruccion();

                    return true;
                case "K":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("(")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        estado = "L";
                    } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(")")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        estado = "E";
                    } else {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " falta (" + "\n";
                        estado = "L";
                        castear();
                    }
                    break;
                case "L":
                    if (tokenEsperado(TokenId.CONSTANTE, indexToken) || tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        estado = "M";
                    } else {
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " se esperaba un intervalo del rango" + "\n";
                        estado = "M";
                        castear();
                    }
                    break;
                case "M":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        estado = "N";
                    } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(")")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        estado = "E";
                    } else {
                        castear();
                        estado = "N";
                    }
                    break;
                case "N":
                    if (tokenEsperado(TokenId.CONSTANTE, indexToken) || tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        estado = "K";
                    } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(")")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        estado = "E";
                    } else {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " se esperaba un intervalo del rango" + "\n";
                        estado = "E";
                        castear();
                    }
                    break;
                default:
                    throw new AssertionError();
            }
        }
        if (indexToken == tokens.size() && !estado.equals("J") && !estado.equals("G")) {
            actualizarLinea();
            errores += "Error en linea: " + linea + " ciclo for invalido" + "\n";
            return false;
        } else {
            instruccion.vaciarInstruccion();
            return true;
        }
    }

    private boolean autoFuncion() {
        int lineaInicio = 1;
        int columnaInicio = 1;
        Funcion funcion = null;
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.PALABRA_RESERVADA, indexToken) && tokens.get(indexToken).getCadena().equals("def")) {
                        estado = "B";
                        castear();
                    }
                    break;
                case "B":
                    if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                        lineaInicio = tokens.get(indexToken).getLinea();
                        columnaInicio = tokens.get(indexToken).getColumna();
                        actualizarLinea();
                        funcion = new Funcion(tokens.get(indexToken).getCadena(), tokens.get(indexToken).getLinea(), tokens.get(indexToken).getColumna(), parametros);
                        castear();
                        estado = "C";
                    } else {
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " falta identificador de la función" + "\n";
                        estado = "C";
                        castear();
                    }
                    break;
                case "C":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("(")) {
                        estado = "D";
                        castear();
                    } else {
                        estado = "D";
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " falta paréntesis de apertura" + "\n";
                        castear();
                    }
                    break;
                case "D":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(")")) {
                        estado = "F";
                        castear();
                    } else if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                        parametros.add(tokens.get(indexToken).getCadena());
                        estado = "I";
                        castear();
                    } else {
                        estado = "E";
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " parámetro inválido" + "\n";
                        castear();
                    }
                    break;
                case "E":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(")")) {
                        estado = "F";
                        castear();
                    } else {
                        estado = "F";
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " falta paréntesis de cierre" + "\n";
                        castear();
                    }
                    break;
                case "F":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(":")) {
                        estado = "G";
                        castear();
                    } else {
                        estado = "G";
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " falta :" + "\n";
                        castear();
                    }
                    break;
                case "G":
                    if (automataBloque(new BloqueDeCodigo("bloque" + funcion.getNombre(), lineaInicio, columnaInicio))) {
                        estado = "H";
                    } else {
                        estado = "H";
                    }
                    break;
                case "H":
                    declararFuncion(funcion);
                    return true;
                case "I":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                        estado = "J";
                        castear();
                    } else {
                        estado = "E";
                    }
                    break;
                case "J":
                    if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                        parametros.add(tokens.get(indexToken).getCadena());
                        estado = "I";
                        castear();
                    } else {
                        estado = "I";
                        actualizarLinea();
                        errores += "Error en linea: " + linea + " se esperaba otro parámetro" + "\n";
                        castear();
                    }
                    break;
                default:
                    throw new AssertionError();
            }
        }
        if (indexToken == tokens.size() && !estado.equals("H")) {
            actualizarLinea();
            errores += "Error en linea: " + linea + " función inválida" + "\n";
            return false;
        } else {
            declararFuncion(funcion);
            return true;
        }
    }

    private boolean llamarMétodo() {
        String fun = "";
        estado = "A";
        while (indexToken < tokens.size()) {
            switch (estado) {
                case "A":
                    if (tokenEsperado(TokenId.IDENTIFICADOR, indexToken)) {
                        if (funcionDeclarada(tokens.get(indexToken).getCadena())) {
                            fun = tokens.get(indexToken).getCadena();
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            estado = "B";
                            castear();
                        } else {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            actualizarLinea();
                            errores += "Error en linea: " + linea + " no hay ninguna función " + tokens.get(indexToken).getCadena() + " declarada" + "\n";
                            estado = "B";
                            castear();
                        }
                    }
                    break;
                case "B":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals("(")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        estado = "C";
                        castear();
                    } else {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        estado = "C";
                        actualizarLinea();
                        errores += "Error en linea: " + linea + ", falta paréntesis de apertura " + "\n";
                        castear();
                    }
                    break;
                case "C":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(")")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        estado = "E";
                        castear();
                    } else if (automataExpresion()) {
                        estado = "D";
                    } else {
                        estado = "D";
                    }
                    break;
                case "D":
                    if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(",")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        castear();
                        estado = "C";
                    } else if (tokenEsperado(TokenId.OTROS_OPERADORES, indexToken) && tokens.get(indexToken).getCadena().equals(")")) {
                        instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                        estado = "E";
                        castear();
                    } else {
                        if (saltoLinea()) {
                            estado = "E";
                            actualizarLinea();
                            errores += "Error en linea: " + linea + ", falta paréntesis de cierre " + "\n";
                        } else {
                            instruccion.agregarCadena(tokens.get(indexToken).getCadena());
                            estado = "E";
                            actualizarLinea();
                            errores += "Error en linea: " + linea + ", falta paréntesis de cierre " + "\n";
                            castear();
                        }
                    }
                    break;
                case "E":
                    agregarLLamada(fun);
                    instruccion.vaciarInstruccion();
                    return true;
                default:
                    throw new AssertionError();
            }
        }
        if (indexToken == tokens.size() && !estado.equals("E")) {
            actualizarLinea();
            errores += "Error en linea: " + linea + " llamada inválida" + "\n";
            return false;
        } else {
            agregarLLamada(fun);
            return true;
        }
    }

    public ArrayList<Funcion> getFunciones() {
        return funciones;
    }

    private void agregarLLamada(String nombre) {
        for (Funcion funcion : funciones) {
            if (funcion.getNombre().equals(nombre)) {
                funcion.setLlamadas(funcion.getLlamadas() + 1);
            }
        }
    }

    private boolean funcionDeclarada(String nombre) {
        for (Funcion funcion : funciones) {
            if (funcion.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    private void declararFuncion(Funcion funcion) {
        boolean declarada = false;
        for (Funcion metodo : funciones) {
            if (funciones.size() == 0) {
                funciones.add(funcion);
            } else if (metodo.getNombre().equals(funcion.getNombre())) {
                metodo.setLlamadas(metodo.getLlamadas() + 1);
                declarada = true;
            }
        }
        if (!declarada) {
            funciones.add(funcion);
        }

    }

    private void actualizarLinea() {
        try {
            this.linea = tokens.get(indexToken).getLinea();
        } catch (Exception e) {
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
        for (Variable variable : varDeclaradas) {
            if (variable.getNomre().equals(var)) {
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
