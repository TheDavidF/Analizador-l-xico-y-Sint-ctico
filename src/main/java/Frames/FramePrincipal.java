/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import com.mycompany.analizadorlexico.AnalizadorL;
import com.mycompany.analizadorlexico.Archivo;
import com.mycompany.analizadorlexico.Expresion;
import com.mycompany.analizadorlexico.Token;
import static com.mycompany.analizadorlexico.TokenId.IDENTIFICADOR;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.text.AttributeSet;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

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

    private void agregarEditor(JTextPane textPane, JLabel label) {
        textPane.addCaretListener((CaretEvent e) -> {
            // Obtener la posición actual del cursor
            int pos = textPane.getCaretPosition();

            // Obtener el número de línea y columna del cursor
            try {
                StyledDocument doc = textPane.getStyledDocument();
                
                linea = doc.getDefaultRootElement().getElementIndex(pos);
                columna = pos - doc.getDefaultRootElement().getElement(linea).getStartOffset();
            } catch (Exception ex) {
                linea = 0;
                columna = 0;
            }

            // Mostrar la información en el JLabel
            label.setText("Línea: " + (linea + 1) + " Columna: " + (columna + 1));
        });

        label.setText("Línea: 1 Columna: 1");
        textPane.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        labelText1 = new javax.swing.JLabel();
        Jscroll1 = new javax.swing.JScrollPane();
        textArea1 = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        labelText2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea2 = new javax.swing.JTextPane();
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

        textArea1.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        Jscroll1.setViewportView(textArea1);

        jPanel1.add(Jscroll1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 240));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelText2.setBackground(new java.awt.Color(51, 51, 51));
        labelText2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        labelText2.setForeground(new java.awt.Color(204, 204, 204));
        labelText2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelText2.setMaximumSize(new java.awt.Dimension(16, 16));
        labelText2.setMinimumSize(new java.awt.Dimension(34, 16));
        labelText2.setOpaque(true);
        jPanel2.add(labelText2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 690, 20));

        jScrollPane2.setViewportView(textArea2);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 190));

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
            colorear(tokens);
            String analisis = "";
            for (Token token : tokens) {
                analisis += "identificador :" + token.getId() + " Lexema: " + token.getCadena() + "  " + "Linea:" + token.getLinea() + "  " + "Columna:" + token.getColumna() + "\n";
                //analisis += token;
            }
            textArea2.setText(analisis);
            System.out.println("es comentario? " + expresion.validarComentario(texto));
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese codigo para analizar");
        }
        

    }
    
    private void colorear(ArrayList<Token> tokens){
        
        final StyleContext contenido = StyleContext.getDefaultStyleContext();
        
        // colores
        final AttributeSet colorNegro = contenido.addAttribute(contenido.getEmptySet(), StyleConstants.Foreground, new Color(0, 0, 0));
        final AttributeSet colorCeleste = contenido.addAttribute(contenido.getEmptySet(), StyleConstants.Foreground, new Color(64, 207, 255));
        final AttributeSet colorMorado = contenido.addAttribute(contenido.getEmptySet(), StyleConstants.Foreground, new Color(128, 0, 128));
        final AttributeSet colorAnaranjado = contenido.addAttribute(contenido.getEmptySet(), StyleConstants.Foreground, new Color(255, 128, 0));
        final AttributeSet colorGris = contenido.addAttribute(contenido.getEmptySet(), StyleConstants.Foreground, new Color(155, 155, 155));
        final AttributeSet colorVerde = contenido.addAttribute(contenido.getEmptySet(), StyleConstants.Foreground, new Color(0, 128, 0));
        final AttributeSet colorRojo = contenido.addAttribute(contenido.getEmptySet(), StyleConstants.Foreground, new Color(255, 0, 0));
        
        
         StyledDocument doc = textArea1.getStyledDocument();

        for (Token token : tokens) {
            AttributeSet atributo = null;
            switch (token.getId()) {
                case IDENTIFICADOR:
                    atributo = colorNegro;
                    break;
                case OPERADOR_ARITMETICO, OPERADOR_COMPARACION, OPERADOR_ASIGNADOR, OPERADOR_LOGICO:
                    atributo = colorCeleste;
                    break;
                case PALABRA_RESERVADA:
                    atributo = colorMorado;
                    break;
                case ENTERO, DECIMAL:
                    atributo = colorAnaranjado;
                    break;
                case COMENTARIO:
                    atributo = colorGris;
                    break;
                case OTROS_OPERADORES:
                    atributo = colorVerde;
                    break;
                case ERROR_LEXICO:
                    atributo = colorRojo;
                    break;
                case CADENA:
                    atributo = colorVerde;
                    break;
                default:
                    //throw new AssertionError();
                    atributo = null;
            }
            
            if(atributo != null){
            int start = calcularPosicionInicio(doc, token);
            int length = token.getCadena().length();

            doc.setCharacterAttributes(start, length, atributo, false);
            }
        }
        
    }
    
    private int calcularPosicionInicio(StyledDocument doc, Token token) {
    try {
        String textoDocumento = doc.getText(0, doc.getLength());
        String tokenTexto = token.getCadena();
        
        int inicioToken = textoDocumento.indexOf(tokenTexto);
        return inicioToken;
    } catch (BadLocationException e) {
        e.printStackTrace();
        return -1; // Manejo de erroro
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Jscroll1;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelText1;
    private javax.swing.JLabel labelText2;
    private javax.swing.JMenu menuAcercade;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuGenerarG;
    private javax.swing.JTextPane textArea1;
    private javax.swing.JTextPane textArea2;
    // End of variables declaration//GEN-END:variables
}
