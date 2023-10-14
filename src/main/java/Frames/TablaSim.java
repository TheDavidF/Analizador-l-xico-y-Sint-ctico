/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frames;

import com.mycompany.analizadorsintactico.Variable;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DAVID
 */
public class TablaSim extends javax.swing.JFrame {

    private DefaultTableModel modelReporte;
    private FramePrincipal frame;
    
    public TablaSim(FramePrincipal frame) {
        this.frame = frame;
        initComponents();
        modelReporte = new DefaultTableModel();
        crearModelo();
        llenarDatos(frame.getSin().getVar());
    }

    private void crearModelo(){
        String[] cabecera = {"Variable", "Linea", "Columna" ,"Valor"};
        modelReporte.setColumnIdentifiers(cabecera);
        tablaSim.setModel(modelReporte);
    } 
    
    private void llenarDatos(ArrayList<Variable> variables){
        Object[] datos = new Object[modelReporte.getColumnCount()];
        for (Variable var : variables) {
            datos[0] = var.getNomre();
            datos[1] = var.getLinea();
            datos[2] = var.getColumna();
            datos[3] = var.getExpresion();
            
            modelReporte.addRow(datos);
        }
        tablaSim.setModel(modelReporte);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        label = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        tablaSim = new javax.swing.JTable();
        Volver = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusTraversalPolicyProvider(true);
        setMaximumSize(new java.awt.Dimension(916, 600));

        jPanel1.setBackground(new java.awt.Color(175, 211, 226));
        jPanel1.setMaximumSize(new java.awt.Dimension(871, 716));
        jPanel1.setMinimumSize(new java.awt.Dimension(871, 716));

        label.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        label.setForeground(new java.awt.Color(0, 0, 0));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("Tabla Simbolos");

        tablaSim.setBackground(new java.awt.Color(246, 244, 235));
        tablaSim.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollPane.setViewportView(tablaSim);

        Volver.setBackground(new java.awt.Color(70, 130, 169));
        Volver.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 16)); // NOI18N
        Volver.setForeground(new java.awt.Color(255, 255, 255));
        Volver.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Volver.setText("Volver");
        Volver.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Volver.setOpaque(true);
        Volver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                VolverMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Volver, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(193, 193, 193)
                        .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(Volver, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void VolverMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VolverMousePressed
        this.setVisible(false);
        this.frame.setVisible(true);
    }//GEN-LAST:event_VolverMousePressed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Volver;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable tablaSim;
    // End of variables declaration//GEN-END:variables
}
