/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import com.mycompany.analizadorlexico.AnalizadorL;
import com.mycompany.analizadorlexico.Archivo;
import com.mycompany.analizadorlexico.Expresion;
import com.mycompany.analizadorlexico.Token;
import com.mycompany.analizadorlexico.TokenId;
import static com.mycompany.analizadorlexico.TokenId.IDENTIFICADOR;
import com.mycompany.analizadorsintactico.AnalizadorS;
import com.mycompany.analizadorsintactico.BloqueDeCodigo;
import com.mycompany.analizadorsintactico.SyntaxError;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    private AnalizadorS analizadorS;

    public FramePrincipal() {
        //graficador = new Graficador();
        //graficador.crearGrafos();
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        PanelPrincipal = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        labelText1 = new javax.swing.JLabel();
        Jscroll1 = new javax.swing.JScrollPane();
        textArea1 = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        labelText2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea2 = new javax.swing.JTextPane();
        lexicoLabel = new javax.swing.JLabel();
        limpiarJLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        analizarLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        paneSin = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        generarReporte = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        menuGenerarG = new javax.swing.JMenu();

        jPopupMenu1.setBackground(new java.awt.Color(145, 200, 228));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(246, 244, 235));
        setMinimumSize(new java.awt.Dimension(1100, 750));
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        PanelPrincipal.setBackground(new java.awt.Color(175, 211, 226));
        PanelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelText1.setBackground(new java.awt.Color(51, 51, 51));
        labelText1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        labelText1.setForeground(new java.awt.Color(204, 204, 204));
        labelText1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelText1.setMaximumSize(new java.awt.Dimension(16, 16));
        labelText1.setMinimumSize(new java.awt.Dimension(35, 16));
        labelText1.setOpaque(true);
        jPanel1.add(labelText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 800, 20));

        Jscroll1.setForeground(new java.awt.Color(0, 0, 0));

        textArea1.setBackground(new java.awt.Color(246, 244, 235));
        textArea1.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        textArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textArea1KeyPressed(evt);
            }
        });
        Jscroll1.setViewportView(textArea1);

        jPanel1.add(Jscroll1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 330));

        PanelPrincipal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 61, 610, 348));

        jPanel2.setForeground(new java.awt.Color(60, 63, 65));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelText2.setBackground(new java.awt.Color(51, 51, 51));
        labelText2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        labelText2.setForeground(new java.awt.Color(204, 204, 204));
        labelText2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelText2.setMaximumSize(new java.awt.Dimension(16, 16));
        labelText2.setMinimumSize(new java.awt.Dimension(34, 16));
        labelText2.setOpaque(true);
        jPanel2.add(labelText2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 790, 20));

        textArea2.setEditable(false);
        textArea2.setBackground(new java.awt.Color(246, 244, 235));
        textArea2.setForeground(new java.awt.Color(0, 0, 0));
        textArea2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        textArea2.setEnabled(false);
        textArea2.setSelectionColor(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(textArea2);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 200));

        PanelPrincipal.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 463, 610, -1));

        lexicoLabel.setBackground(new java.awt.Color(70, 130, 169));
        lexicoLabel.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        lexicoLabel.setForeground(new java.awt.Color(246, 244, 235));
        lexicoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lexicoLabel.setText("Analisis Léxico");
        lexicoLabel.setOpaque(true);
        lexicoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lexicoLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lexicoLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lexicoLabelMouseExited(evt);
            }
        });
        PanelPrincipal.add(lexicoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 119, 22));

        limpiarJLabel.setBackground(new java.awt.Color(70, 130, 169));
        limpiarJLabel.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        limpiarJLabel.setForeground(new java.awt.Color(246, 244, 235));
        limpiarJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        limpiarJLabel.setText("Limpiar");
        limpiarJLabel.setOpaque(true);
        limpiarJLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                limpiarJLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                limpiarJLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                limpiarJLabelMouseExited(evt);
            }
        });
        PanelPrincipal.add(limpiarJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 21, 78, 22));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setText("Errores:");
        PanelPrincipal.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 421, 71, 24));

        analizarLabel1.setBackground(new java.awt.Color(70, 130, 169));
        analizarLabel1.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        analizarLabel1.setForeground(new java.awt.Color(246, 244, 235));
        analizarLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        analizarLabel1.setText("Analisis Sintáctico");
        analizarLabel1.setOpaque(true);
        analizarLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                analizarLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                analizarLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                analizarLabel1MouseExited(evt);
            }
        });
        PanelPrincipal.add(analizarLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 20, 136, 22));

        jScrollPane1.setForeground(new java.awt.Color(0, 0, 0));

        paneSin.setEditable(false);
        paneSin.setBackground(new java.awt.Color(246, 244, 235));
        paneSin.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        paneSin.setForeground(new java.awt.Color(0, 0, 0));
        paneSin.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        paneSin.setEnabled(false);
        paneSin.setSelectionColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(paneSin);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
        );

        PanelPrincipal.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 60, 370, 622));

        getContentPane().add(PanelPrincipal);

        jMenuBar1.setBackground(new java.awt.Color(70, 130, 169));

        menuArchivo.setForeground(new java.awt.Color(246, 244, 235));
        menuArchivo.setText("Archivo");

        jMenuItem1.setText("Subir Archivo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuArchivo.add(jMenuItem1);

        jMenuBar1.add(menuArchivo);

        jMenu1.setForeground(new java.awt.Color(246, 244, 235));
        jMenu1.setText("Reporte");

        generarReporte.setText("Generar Reporte");
        generarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarReporteActionPerformed(evt);
            }
        });
        jMenu1.add(generarReporte);

        jMenuItem2.setText("Generar tabla de simbolos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Ver métodos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        menuGenerarG.setForeground(new java.awt.Color(246, 244, 235));
        menuGenerarG.setText("Generar grafico");
        menuGenerarG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuGenerarGMouseClicked(evt);
            }
        });
        jMenuBar1.add(menuGenerarG);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        file.cargarArchivo(this, textArea1);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void generarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarReporteActionPerformed
        Reporte reporte = new Reporte(this);
        this.setVisible(false);
        reporte.setVisible(true);
    }//GEN-LAST:event_generarReporteActionPerformed

    private void lexicoLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lexicoLabelMouseClicked
        analizarTexto();
    }//GEN-LAST:event_lexicoLabelMouseClicked

    private void menuGenerarGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuGenerarGMouseClicked
        this.setVisible(false);
        Graficador graficador = new Graficador(this, lexico.getTokens());
        graficador.setVisible(true);
    }//GEN-LAST:event_menuGenerarGMouseClicked

    private void limpiarJLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_limpiarJLabelMouseClicked
        textArea1.setText("");
        textArea2.setText("");
        lexico.getTokens().clear();
    }//GEN-LAST:event_limpiarJLabelMouseClicked

    private void limpiarJLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_limpiarJLabelMouseEntered
        limpiarJLabel.setBackground(new Color(116, 155, 194));
    }//GEN-LAST:event_limpiarJLabelMouseEntered

    private void lexicoLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lexicoLabelMouseEntered
        lexicoLabel.setBackground(new Color(116, 155, 194));
    }//GEN-LAST:event_lexicoLabelMouseEntered

    private void limpiarJLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_limpiarJLabelMouseExited
        limpiarJLabel.setBackground(new Color(70, 130, 169));

    }//GEN-LAST:event_limpiarJLabelMouseExited

    private void lexicoLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lexicoLabelMouseExited
        lexicoLabel.setBackground(new Color(70, 130, 169));
    }//GEN-LAST:event_lexicoLabelMouseExited

    private void textArea1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textArea1KeyPressed
        //try {
        //    lexico.setCadena(textArea1.getText());
        //    colorear(lexico.listarTokens());
        //} catch (Exception e) {
        //   System.out.println("error");
        //} 
    }//GEN-LAST:event_textArea1KeyPressed

    private void analizarLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_analizarLabel1MouseClicked
        if (this.analizadorS != null) {

            try {
                analizadorS.analizar();
                if (analizadorS.getErrores() == "") {
                    ArrayList<BloqueDeCodigo> bloques = analizadorS.getBloques();
                    for (BloqueDeCodigo bloque : bloques) {
                        System.out.println(bloque.toString());
                    }
                    paneSin.setText("Análisis sintáctico completado, no se encontraron errores");
                } else {
                    paneSin.setText(analizadorS.getErrores());
                }
                JOptionPane.showMessageDialog(this, "Análisis sintáctico realizado con éxito");

            } catch (Exception e) {
                System.out.println("error en frame principal");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error, Se requiere analisis léxico previo");
        }
    }//GEN-LAST:event_analizarLabel1MouseClicked

    private void analizarLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_analizarLabel1MouseEntered
        analizarLabel1.setBackground(new Color(116, 155, 194));
    }//GEN-LAST:event_analizarLabel1MouseEntered

    private void analizarLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_analizarLabel1MouseExited
        analizarLabel1.setBackground(new Color(70, 130, 169));
    }//GEN-LAST:event_analizarLabel1MouseExited

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            this.setVisible(false);
            TablaSim simbolos = new TablaSim(this);
            simbolos.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Se requiere analisis léxico previo");
        }


    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        Metodos metodos = new Metodos(this);
        this.setVisible(false);
        metodos.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

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
        lexico.getTokens().clear();
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
            String errores = "";
            for (Token token : tokens) {
                if (token.getId() == TokenId.ERROR_LEXICO) {
                    errores = "------Analisis completo------\n";
                    errores += "Error cadena no reconocida " + token.getCadena() + " en la Linea: " + token.getLinea() + "   Y Columna:" + token.getColumna() + "\n";
                } else {
                    errores = "------Analisis completo, no se encontraron errores------";
                }
            }
            textArea2.setText(errores);
            textArea2.setText(analisis);
            System.out.println("es comentario? " + expresion.validarComentario(texto));
            analizarS();
            JOptionPane.showMessageDialog(this, "Análisis léxico realizado con éxito");
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese codigo para analizar");
        }

    }

    private void colorear(ArrayList<Token> tokens) {

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
                case CONSTANTE:
                    atributo = colorAnaranjado;
                    break;
                case COMENTARIO:
                    atributo = colorGris;
                    break;
                case OTROS_OPERADORES:
                    atributo = colorVerde;
                    break;
                case BOOLEANO:
                    atributo = colorAnaranjado;
                    break;
                case ERROR_LEXICO:
                    atributo = colorRojo;
                    break;
                default:
                    //throw new AssertionError();
                    atributo = null;
            }

            if (atributo != null) {
                ArrayList<Integer> posiciones = calcularPosicionInicio(doc, token);
                for (Integer posicion : posiciones) {
                    int start = posicion;
                    int length = token.getCadena().length();

                    doc.setCharacterAttributes(start, length, atributo, false);
                }

            }
        }

    }

    private void analizarS() {
        analizadorS = new AnalizadorS(lexico.getTokens());

    }

    public AnalizadorL getLexico() {
        return lexico;
    }

    public AnalizadorS getSin() {
        return analizadorS;
    }

    private ArrayList<Integer> calcularPosicionInicio(StyledDocument doc, Token token) {
        ArrayList<Integer> posiciones = new ArrayList<>();
        try {
            String textoDocumento = doc.getText(0, doc.getLength());
            String tokenTexto = token.getCadena();

            int posicionInicio = -1;
            while ((posicionInicio = textoDocumento.indexOf(tokenTexto, posicionInicio + 1)) != -1) {
                posiciones.add(posicionInicio);
            }
            return posiciones;
        } catch (BadLocationException e) {
            e.printStackTrace();
            return posiciones; // Manejo de error
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Jscroll1;
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JLabel analizarLabel1;
    private javax.swing.JMenuItem generarReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelText1;
    private javax.swing.JLabel labelText2;
    private javax.swing.JLabel lexicoLabel;
    private javax.swing.JLabel limpiarJLabel;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuGenerarG;
    private javax.swing.JTextPane paneSin;
    private javax.swing.JTextPane textArea1;
    private javax.swing.JTextPane textArea2;
    // End of variables declaration//GEN-END:variables
}
