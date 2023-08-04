package com.mycompany.analizadorlexico;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;

public class JTextAreaLineColumn extends JFrame {

    private JTextArea textArea;
    private JLabel labelPosicion;
    

    public JTextAreaLineColumn() {
        setTitle("JTextArea Line and Column Demo");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Agregar el listener del cursor al JTextArea
        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {

                // Obtener la posición actual del cursor
                int pos = textArea.getCaretPosition();

                // Obtener el número de línea y columna del cursor
                int linea;
                int columna;
                try {
                    linea = textArea.getLineOfOffset(pos);
                    columna = pos - textArea.getLineStartOffset(linea);
                } catch (Exception ex) {
                    linea = 0;
                    columna = 0;
                }

                // Mostrar la información en el JLabel
                labelPosicion.setText("Línea: " + (linea + 1) + " Columna: " + (columna + 1));
            }
        });

        labelPosicion = new JLabel("Línea: 1 Columna: 1");
        labelPosicion.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(textArea);

        // Agregar el JTextArea y el JLabel al contenido del JFrame
        add(scrollPane, BorderLayout.CENTER);
        add(labelPosicion, BorderLayout.SOUTH);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JLabel getLabelPosicion() {
        return labelPosicion;
    }

    public void setLabelPosicion(JLabel labelPosicion) {
        this.labelPosicion = labelPosicion;
    }

}
