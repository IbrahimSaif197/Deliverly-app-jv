/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.deliverly.main.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


/**
 *
 * @author natsu
 */
public class TopUpMenu extends javax.swing.JFrame {

    public TopUpMenu() {
        initComponents();
        this.model = (DefaultTableModel) UsersTable.getModel();
        this.reloadData();
        
        UsersTable.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting()) {
            int selectedRow = UsersTable.getSelectedRow();
            if (selectedRow != -1) {
                String username = (String) UsersTable.getValueAt(selectedRow, 1);
                userID.setText(username);
            }
        }
        });
    }
    File users_file = new File("src//data//users.txt");
    DefaultTableModel model;
    
    
    public void reloadData(){
        try {
            model.setRowCount(0);
            FileReader fr = new FileReader(users_file);
            BufferedReader br = new BufferedReader(fr);
            String user;
            while ((user = br.readLine()) != null) {
                String[] user_data = user.split(";");
                model.addRow(new Object[]{
                    user_data[0], user_data[1], user_data[2], user_data[3],
                    user_data[4], user_data[5], user_data[6], user_data[7],
                    user_data[8]});
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        UsersTable = new javax.swing.JTable();
        userID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Amount = new javax.swing.JTextField();
        Cancel = new javax.swing.JButton();
        TopUp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(225, 254, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Top-Up Menu");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 230, 60));

        UsersTable.setForeground(new java.awt.Color(0, 0, 0));
        UsersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UserID", "Username", "Password", "Name", "Phone", "Address", "Email", "Credits", "Role"
            }
        ));
        jScrollPane1.setViewportView(UsersTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 610, 420));

        userID.setForeground(new java.awt.Color(0, 0, 0));
        userID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userIDActionPerformed(evt);
            }
        });
        jPanel1.add(userID, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 230, 210, 40));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("User ID");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 210, -1, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Top-up Amount");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 290, -1, -1));

        Amount.setForeground(new java.awt.Color(0, 0, 0));
        Amount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                AmountFocusLost(evt);
            }
        });
        jPanel1.add(Amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 310, 210, 40));

        Cancel.setForeground(new java.awt.Color(0, 0, 0));
        Cancel.setText("Cancel");
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });
        jPanel1.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 390, 80, 30));

        TopUp.setForeground(new java.awt.Color(0, 0, 0));
        TopUp.setText("Top-Up");
        TopUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TopUpActionPerformed(evt);
            }
        });
        jPanel1.add(TopUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 390, 80, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(956, 619));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
        this.dispose();
        new AdminMenu().setVisible(true);
    }//GEN-LAST:event_CancelActionPerformed

    private void TopUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TopUpActionPerformed
        try {
            int added_amount = Integer.parseInt(Amount.getText().trim());
        
            if (added_amount < 0) {
                JOptionPane.showMessageDialog(null, "The amount cannot be negative!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }    

            List<String> users = new ArrayList<>();
            FileReader fr = new FileReader(users_file);
            BufferedReader br = new BufferedReader(fr);
            String user;

            while ((user = br.readLine()) != null) {
                String[] data = user.split(";");
                String found_username = data[1];
                if (found_username.equals(userID.getText().trim())) {
                    int credits = Integer.parseInt(data[7]);
                    data[7] = String.valueOf(credits + added_amount);
                    user = String.join(";", data);
                }
                users.add(user);
            }
            br.close();

            FileWriter fw = new FileWriter(users_file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String line : users) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            
            reloadData();
            JOptionPane.showMessageDialog(null, "Topped-Up Seccessfully");
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Amount " + e.getMessage());
        }
    }//GEN-LAST:event_TopUpActionPerformed

    private void userIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userIDActionPerformed
        //
    }//GEN-LAST:event_userIDActionPerformed

    private void AmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AmountFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_AmountFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TopUpMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TopUpMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TopUpMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TopUpMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TopUpMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Amount;
    private javax.swing.JButton Cancel;
    private javax.swing.JButton TopUp;
    private javax.swing.JTable UsersTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField userID;
    // End of variables declaration//GEN-END:variables
}
