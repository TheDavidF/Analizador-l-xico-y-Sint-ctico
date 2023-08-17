/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import com.mycompany.analizadorlexico.AnalizadorL;
import com.mycompany.analizadorlexico.Archivo;
import com.mycompany.analizadorlexico.Expresion;
import com.mycompany.analizadorlexico.Token;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;

import javax.swing.text.BadLocationException;

/**
 *
 * @author DAVID
 */
public class FramePrincipal extends javax.swing.JFrame {

    private int linea;
    private int columna;
    private Archivo file;
    private AnalizadorL lexico;
    private Expresion expresion;

    public FramePrincipal() {
        expresion = new Expresion();
        file = new Archivo();
        lexico = new AnalizadorL();
        initComponents();
        agregarEditor(textArea1, labelText1);
        agregarEditor(textArea2, labelText2);

    }

    private void agregarEditor(JTextArea textArea, JLabel label) {
        textArea.addCaretListener((CaretEvent e) -> {
            // Obtener la posición actual del cursor
            int pos = textArea.getCaretPosition();

            // Obtener el número de línea y columna del cursor
            try {
                linea = textArea.getLineOfOffset(pos);
                columna = pos - textArea.getLineStartOffset(linea);
            } catch (BadLocationException ex) {
                linea = 0;
                columna = 0;
            }

            // Mostrar la información en el JLabel
            label.setText("Línea: " + (linea + 1) + " Columna: " + (columna + 1));
        });

        label.setText("Línea: 1 Columna: 1");
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        labelText1 = new javax.swing.JLabel();
        Jscroll1 = new javax.swing.JScrollPane();
        textArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea2 = new javax.swing.JTextArea();
        labelText2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuGenerarG = new javax.swing.JMenu();
        menuAcercade = new javax.swing.JMenu();
        menuAyuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 204, 255));

        jButton1.setText("Analizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelText1.setBackground(new java.awt.Color(51, 51, 51));
        labelText1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        labelText1.setForeground(new java.awt.Color(204, 204, 204));
        labelText1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelText1.setMaximumSize(new java.awt.Dimension(16, 16));
        labelText1.setMinimumSize(new java.awt.Dimension(35, 16));
        labelText1.setOpaque(true);
        jPanel1.add(labelText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 236, 690, 20));

        textArea1.setColumns(20);
        textArea1.setRows(5);
        textArea1.setBorder(null);
        textArea1.setMargin(new java.awt.Insets(2, 0, 2, 2));
        Jscroll1.setViewportView(textArea1);

        jPanel1.add(Jscroll1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 240));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textArea2.setColumns(20);
        textArea2.setRows(5);
        jScrollPane1.setViewportView(textArea2);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 190));

        labelText2.setBackground(new java.awt.Color(51, 51, 51));
        labelText2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        labelText2.setForeground(new java.awt.Color(204, 204, 204));
        labelText2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelText2.setMaximumSize(new java.awt.Dimension(16, 16));
        labelText2.setMinimumSize(new java.awt.Dimension(34, 16));
        labelText2.setOpaque(true);
        jPanel2.add(labelText2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 690, 20));

        menuArchivo.setText("Archivo");

        jMenuItem1.setText("Subir Archivo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuArchivo.add(jMenuItem1);

        jMenuBar1.add(menuArchivo);

        menuGenerarG.setText("Generar grafico");
        jMenuBar1.add(menuGenerarG);

        menuAcercade.setText("Acerca de");

        menuAyuda.setText("Ayuda");
        menuAcercade.add(menuAyuda);

        jMenuBar1.add(menuAcercade);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(22, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jButton1)
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                .addGap(65, 65, 65))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        file.cargarArchivo(this, textArea1);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        analizarTexto();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FramePrincipal frame = new FramePrincipal();
        frame.setVisible(true);

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

    private void analizarTexto() {
        textArea2.setText("");
        if (textArea1.getText().length() != 0) {
            String texto = textArea1.getText();
            lexico.setCadena(texto);
            ArrayList<Token> tokens = lexico.listarTokens();
            String analisis = "";
            for (Token token : tokens) {
                analisis += "identificador :" + token.getId() + " Lexema: " + token.getCadena() + "  " + "Linea:" + token.getLinea() + "  " + "Columna:" + token.getColumna() + "\n";

            }
            textArea2.setText(analisis);
            System.out.println("es comentario? " + expresion.validarComentario(texto));
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese codigo para analizar");
        }
        

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Jscroll1;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelText1;
    private javax.swing.JLabel labelText2;
    private javax.swing.JMenu menuAcercade;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuGenerarG;
    private javax.swing.JTextArea textArea1;
    private javax.swing.JTextArea textArea2;
    // End of variables declaration//GEN-END:variables
}
