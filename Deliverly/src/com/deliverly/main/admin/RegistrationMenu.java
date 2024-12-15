/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.deliverly.main.admin;



import com.deliverly.login.LoginMenu;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author natsu
 */
public class RegistrationMenu extends javax.swing.JFrame {

    /**
     * Creates new form RegistrationMenu
     */
    
    public RegistrationMenu() {
        initComponents();
        this.reloadData();
    }
    LoginMenu login = new LoginMenu();
    File users_file = new File("src//data//users.txt"); //calls users file
    
    public void reloadData(){
        try {
            DefaultTableModel model = (DefaultTableModel) UsersTable.getModel();
            model.setRowCount(0);
            FileReader fr = new FileReader(users_file);
            BufferedReader br = new BufferedReader(fr);
            String user;
            while ((user = br.readLine()) != null) {
                String[] user_data = user.split(";");
                model.addRow(new Object[]{
                    user_data[0], user_data[1], user_data[2], user_data[3],
                    user_data[4], user_data[5], user_data[6], user_data[7]});
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        ID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        phone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        goBack = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        CreateButton = new javax.swing.JButton();
        Role = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        UsersTable = new javax.swing.JTable();
        password = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MainPanel.setBackground(new java.awt.Color(225, 254, 255));
        MainPanel.setMaximumSize(new java.awt.Dimension(200, 200));
        MainPanel.setMinimumSize(new java.awt.Dimension(200, 200));
        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Phone Number");
        MainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        MainPanel.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 200, 30));

        ID.setText("ADM");
        ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDActionPerformed(evt);
            }
        });
        MainPanel.add(ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 200, 30));

        jLabel4.setText("Name");
        MainPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 200, 20));

        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });
        MainPanel.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 200, 30));

        jLabel2.setText("Username");
        MainPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jLabel5.setText("ID");
        MainPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 200, 20));

        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });
        MainPanel.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 200, 30));

        jLabel6.setText("Email");
        MainPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

        jLabel7.setText("Password");
        MainPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneActionPerformed(evt);
            }
        });
        MainPanel.add(phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 200, 30));

        jLabel8.setText("Address");
        MainPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressActionPerformed(evt);
            }
        });
        MainPanel.add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 200, 30));

        goBack.setText("Go Back");
        goBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackActionPerformed(evt);
            }
        });
        MainPanel.add(goBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, 80, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel9.setText("Registration Menu");
        MainPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 230, 60));

        CreateButton.setText("Create");
        CreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateButtonActionPerformed(evt);
            }
        });
        MainPanel.add(CreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 480, 80, 30));

        Role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "customer", "runner", "vendor", "manager" }));
        Role.setSelectedItem("");
        Role.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoleActionPerformed(evt);
            }
        });
        MainPanel.add(Role, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        UsersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UserID", "Username", "Password", "Name", "Phone", "Address", "Email", "Role"
            }
        ));
        jScrollPane1.setViewportView(UsersTable);

        MainPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 640, -1));
        MainPanel.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 200, 30));

        jLabel3.setText("Role");
        MainPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 30, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(956, 619));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDActionPerformed

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameActionPerformed

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

    private void phoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneActionPerformed

    private void addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressActionPerformed

    private void goBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBackActionPerformed
        this.dispose();
        new AdminMenu().setVisible(true);
    }//GEN-LAST:event_goBackActionPerformed

    private void CreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateButtonActionPerformed
        try { 
            String role = Role.getSelectedItem().toString();
            String password_string = new String(password.getPassword());
            FileWriter fw = new FileWriter(users_file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            checkUsernames(users_file);
            if(isUsernameAvailable(username.getText()) == true){
                bw.newLine();
                bw.write(
                    ID.getText() + ";" +
                    username.getText() + ";" +
                    password_string + ";" +
                    name.getText() + ";" +
                    phone.getText() + ";" +
                    address.getText() + ";" +
                    email.getText() + ";" +
                    role
                );
                bw.flush();
                this.reloadData();
                JOptionPane.showMessageDialog(null, "Successfully added a new " + role);
                
            }else {
                JOptionPane.showMessageDialog(null, "Username is already used! Please use a different username.");
            }
            
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_CreateButtonActionPerformed

    private void RoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoleActionPerformed
        if(Role.getSelectedItem() == "admin"){
                ID.setText("ADM");
            }
        else if(Role.getSelectedItem() == "customer"){
                ID.setText("CUS");
            }
        else if(Role.getSelectedItem() == "runner"){
                ID.setText("RNR");
            }
        else if(Role.getSelectedItem() == "vendor"){
                ID.setText("VEN");
            }
        else if(Role.getSelectedItem() == "manager"){
                ID.setText("MGR");
            }
    }//GEN-LAST:event_RoleActionPerformed
    
    
    
    
    
    private static boolean checkUsernames(File users_file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(users_file))) {
            
            String user;
            reader.readLine();
            while ((user = reader.readLine()) != null) {
                String[] fields = user.split(";");
                if (fields.length > 1) {
                    usernames.add(fields[1]); // Add username to the set
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Check if a username is available
    private static boolean isUsernameAvailable(String username) {
        return !usernames.contains(username);
    }
    
    private static final Set<String> usernames = new HashSet<>();
    
    
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
            java.util.logging.Logger.getLogger(RegistrationMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrationMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrationMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrationMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrationMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CreateButton;
    private javax.swing.JTextField ID;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JComboBox<String> Role;
    private javax.swing.JTable UsersTable;
    private javax.swing.JTextField address;
    private javax.swing.JTextField email;
    private javax.swing.JButton goBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField phone;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
