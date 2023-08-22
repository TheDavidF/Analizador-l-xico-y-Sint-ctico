/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import com.mycompany.analizadorlexico.AnalizadorL;
import com.mycompany.analizadorlexico.Archivo;
import com.mycompany.analizadorlexico.Expresion;
import com.mycompany.analizadorlexico.Grafica;
import com.mycompany.analizadorlexico.Token;
import com.mycompany.analizadorlexico.TokenId;
import static com.mycompany.analizadorlexico.TokenId.IDENTIFICADOR;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
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
    //private Graficador graficador;

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
        analizarLabel = new javax.swing.JLabel();
        limpiarJLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        generarReporte = new javax.swing.JMenuItem();
        menuGenerarG = new javax.swing.JMenu();
        menuAcercade = new javax.swing.JMenu();
        menuAyuda = new javax.swing.JMenu();

        jPopupMenu1.setBackground(new java.awt.Color(145, 200, 228));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(246, 244, 235));

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

        textArea1.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        textArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textArea1KeyPressed(evt);
            }
        });
        Jscroll1.setViewportView(textArea1);

        jPanel1.add(Jscroll1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 330));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelText2.setBackground(new java.awt.Color(51, 51, 51));
        labelText2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        labelText2.setForeground(new java.awt.Color(204, 204, 204));
        labelText2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelText2.setMaximumSize(new java.awt.Dimension(16, 16));
        labelText2.setMinimumSize(new java.awt.Dimension(34, 16));
        labelText2.setOpaque(true);
        jPanel2.add(labelText2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 790, 20));

        jScrollPane2.setViewportView(textArea2);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 200));

        analizarLabel.setBackground(new java.awt.Color(70, 130, 169));
        analizarLabel.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        analizarLabel.setForeground(new java.awt.Color(204, 204, 204));
        analizarLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        analizarLabel.setText("Analizar");
        analizarLabel.setOpaque(true);
        analizarLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                analizarLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                analizarLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                analizarLabelMouseExited(evt);
            }
        });

        limpiarJLabel.setBackground(new java.awt.Color(70, 130, 169));
        limpiarJLabel.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        limpiarJLabel.setForeground(new java.awt.Color(204, 204, 204));
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

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setText("Errores:");

        javax.swing.GroupLayout PanelPrincipalLayout = new javax.swing.GroupLayout(PanelPrincipal);
        PanelPrincipal.setLayout(PanelPrincipalLayout);
        PanelPrincipalLayout.setHorizontalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelPrincipalLayout.createSequentialGroup()
                            .addComponent(limpiarJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(analizarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        PanelPrincipalLayout.setVerticalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPrincipalLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(limpiarJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(analizarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

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

        jMenuBar1.add(jMenu1);

        menuGenerarG.setForeground(new java.awt.Color(246, 244, 235));
        menuGenerarG.setText("Generar grafico");
        menuGenerarG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuGenerarGMouseClicked(evt);
            }
        });
        jMenuBar1.add(menuGenerarG);

        menuAcercade.setForeground(new java.awt.Color(246, 244, 235));
        menuAcercade.setText("Acerca de");

        menuAyuda.setText("Ayuda");
        menuAcercade.add(menuAyuda);

        jMenuBar1.add(menuAcercade);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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

    private void analizarLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_analizarLabelMouseClicked
        analizarTexto();
    }//GEN-LAST:event_analizarLabelMouseClicked

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

    private void analizarLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_analizarLabelMouseEntered
        analizarLabel.setBackground(new Color(116, 155, 194));
    }//GEN-LAST:event_analizarLabelMouseEntered

    private void limpiarJLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_limpiarJLabelMouseExited
        limpiarJLabel.setBackground(new Color(70, 130, 169));

    }//GEN-LAST:event_limpiarJLabelMouseExited

    private void analizarLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_analizarLabelMouseExited
        analizarLabel.setBackground(new Color(70, 130, 169));
    }//GEN-LAST:event_analizarLabelMouseExited

    private void textArea1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textArea1KeyPressed
        try {
            lexico.setCadena(textArea1.getText());
            colorear(lexico.listarTokens());
        } catch (Exception e) {
            System.out.println("error");
        }
    }//GEN-LAST:event_textArea1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FramePrincipal frame = new FramePrincipal();
        frame.setVisible(true);
        Grafica grafica = new Grafica();
        //grafica.crearImagen(grafica.crearGrafos(new Token(0, 0, TokenId.CADENA, "def", "4.55")));

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
            textArea2.setText(analisis);
            System.out.println("es comentario? " + expresion.validarComentario(texto));
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

    public AnalizadorL getLexico() {
        return lexico;
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
    private javax.swing.JLabel analizarLabel;
    private javax.swing.JMenuItem generarReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelText1;
    private javax.swing.JLabel labelText2;
    private javax.swing.JLabel limpiarJLabel;
    private javax.swing.JMenu menuAcercade;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuGenerarG;
    private javax.swing.JTextPane textArea1;
    private javax.swing.JTextPane textArea2;
    // End of variables declaration//GEN-END:variables
}
