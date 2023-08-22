/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import com.mycompany.analizadorlexico.Token;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DAVID
 */
public class Reporte extends javax.swing.JFrame {

    private FramePrincipal frame;
    private DefaultTableModel modelReporte;
    
    public Reporte(FramePrincipal frame) {
        this.frame = frame;
        initComponents();
        modelReporte = new DefaultTableModel();
        crearModelo();
        llenarDatos(frame.getLexico().getTokens());
        
    }

    private void crearModelo(){
        String[] cabecera = {"Token", "Patron", "Lexema", "Linea", "Columna"};
        modelReporte.setColumnIdentifiers(cabecera);
        tablaRep.setModel(modelReporte);
    } 
    
    private void llenarDatos(ArrayList<Token> tokens){
        Object[] datos = new Object[modelReporte.getColumnCount()];
        for (Token token : tokens) {
            datos[0] = token.getId();
            datos[1] = token.getPatron();
            datos[2] = token.getCadena();
            datos[3] = token.getLinea();
            datos[4] = token.getColumna();
            modelReporte.addRow(datos);
        }
        tablaRep.setModel(modelReporte);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelReportes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRep = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reporte");
        setBackground(new java.awt.Color(246, 244, 235));

        jPanel1.setMaximumSize(new java.awt.Dimension(871, 716));
        jPanel1.setMinimumSize(new java.awt.Dimension(871, 716));
        jPanel1.setPreferredSize(new java.awt.Dimension(871, 716));

        labelReportes.setBackground(new java.awt.Color(102, 102, 102));
        labelReportes.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        labelReportes.setForeground(new java.awt.Color(255, 255, 255));
        labelReportes.setText("     Volver");
        labelReportes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        labelReportes.setOpaque(true);
        labelReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelReportesMouseClicked(evt);
            }
        });

        tablaRep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaRep.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tablaRep);
        tablaRep.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(377, 377, 377)
                        .addComponent(labelReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(labelReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 883, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 728, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void labelReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelReportesMouseClicked
        this.setVisible(false);
        frame.setVisible(true);
        
    }//GEN-LAST:event_labelReportesMouseClicked


    public void setFrame(FramePrincipal frame) {
        this.frame = frame;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelReportes;
    private javax.swing.JTable tablaRep;
    // End of variables declaration//GEN-END:variables
}
