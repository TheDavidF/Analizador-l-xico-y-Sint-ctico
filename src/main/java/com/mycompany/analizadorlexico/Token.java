/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico;

/**
 *
 * @author DAVID
 */
public class Token {
    
    private TokenId id;
    private int linea;
    private int columna;
    private String lexema;
    private String patron;
    
    public Token(int linea, int columna, TokenId tokenId, String Cadena, String patron){
        this.lexema = Cadena;
        this.columna = columna;
        this.id = tokenId;
        this.linea = linea;
        this.patron = patron;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public TokenId getId() {
        return id;
    }

    public void setId(TokenId id) {
        this.id = id;
    }

    public String getCadena() {
        return lexema;
    }

    public void setCadena(String cadena) {
        this.lexema = cadena;
    }

    public String getPatron() {
        return patron;
    }

    public void setPatron(String patron) {
        this.patron = patron;
    }

    @Override
    public String toString() {
        return "Token{" + "linea=" + linea + ", columna=" + columna + ", cadena=" + lexema + ", patron=" + patron + '}';
    }

    
    
}
