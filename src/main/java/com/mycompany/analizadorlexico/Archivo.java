/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author DAVID
 */
public class Archivo {

    private File archivo;
    
    public Archivo(){
    
    }
    
    public void cargarArchivo(JFrame frame, JTextArea textA){
        JFileChooser director = new JFileChooser();
        int seleccion = director.showOpenDialog(frame);
        if(seleccion == JFileChooser.APPROVE_OPTION){
            // selecciona el fichero
            File file = director.getSelectedFile();
            archivo = file;
            textA.setText(file.getAbsolutePath());
            try (FileReader escritor = new FileReader(file)){
                String cadena = "";
                int valor = escritor.read();
                while(valor != -1){
                    cadena = cadena + (char) valor;
                    valor = escritor.read();
                }
                textA.setText(cadena);
            } catch (IOException el) {
                el.printStackTrace();
            }
        }
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }
    
    
    
}
