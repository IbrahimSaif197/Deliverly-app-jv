
package com.deliverly.main.admin;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author natsu
 */
public final class RegistrationMenu extends javax.swing.JFrame {

    /**
     * Creates new form RegistrationMenu
     */
    
    public RegistrationMenu() {
        initComponents();
        //tips config
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(2000);
        
        //table
        this.model = (DefaultTableModel) UsersTable.getModel();
        this.reloadData();
        //Tips
        ID.setToolTipText("ROLXXX");
        username.setToolTipText("Username should be 2 characters at least");
        password.setToolTipText("Password should be at least 8 characters and 1 number");
        emailField.setToolTipText("example@gmail.com");
    }
    File users_file = new File("src//data//users.txt"); //calls users file
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

        MainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
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
        Delete = new javax.swing.JButton();
        Edit1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        SearchField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MainPanel.setBackground(new java.awt.Color(225, 254, 255));
        MainPanel.setForeground(new java.awt.Color(0, 0, 0));
        MainPanel.setMaximumSize(new java.awt.Dimension(200, 200));
        MainPanel.setMinimumSize(new java.awt.Dimension(200, 200));
        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Phone Number");
        MainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        emailField.setForeground(new java.awt.Color(0, 0, 0));
        emailField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                emailFieldFocusLost(evt);
            }
        });
        emailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailFieldActionPerformed(evt);
            }
        });
        MainPanel.add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 200, 30));

        ID.setForeground(new java.awt.Color(0, 0, 0));
        ID.setText("ADM");
        ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDActionPerformed(evt);
            }
        });
        MainPanel.add(ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 200, 30));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Name");
        MainPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 200, 20));

        name.setForeground(new java.awt.Color(0, 0, 0));
        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });
        MainPanel.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 200, 30));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Username");
        MainPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("ID");
        MainPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 200, 20));

        username.setForeground(new java.awt.Color(0, 0, 0));
        username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernameFocusLost(evt);
            }
        });
        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });
        username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                usernameKeyReleased(evt);
            }
        });
        MainPanel.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 200, 30));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Email");
        MainPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Password");
        MainPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        phone.setForeground(new java.awt.Color(0, 0, 0));
        phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneActionPerformed(evt);
            }
        });
        MainPanel.add(phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 200, 30));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Address");
        MainPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        address.setForeground(new java.awt.Color(0, 0, 0));
        address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressActionPerformed(evt);
            }
        });
        MainPanel.add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 200, 30));

        goBack.setForeground(new java.awt.Color(0, 0, 0));
        goBack.setText("Go Back");
        goBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackActionPerformed(evt);
            }
        });
        MainPanel.add(goBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, 80, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Registration Menu");
        MainPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 230, 60));

        CreateButton.setForeground(new java.awt.Color(0, 0, 0));
        CreateButton.setText("Create");
        CreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateButtonActionPerformed(evt);
            }
        });
        MainPanel.add(CreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 480, 80, 30));

        Role.setForeground(new java.awt.Color(0, 0, 0));
        Role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "customer", "runner", "vendor", "manager" }));
        Role.setSelectedItem("");
        Role.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoleActionPerformed(evt);
            }
        });
        MainPanel.add(Role, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        UsersTable.setForeground(new java.awt.Color(0, 0, 0));
        UsersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UserID", "Username", "Password", "Name", "Phone", "Address", "Email", "Credits", "Role"
            }
        ));
        jScrollPane1.setViewportView(UsersTable);

        MainPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 640, -1));

        password.setForeground(new java.awt.Color(0, 0, 0));
        password.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                passwordComponentAdded(evt);
            }
        });
        password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordFocusLost(evt);
            }
        });
        password.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                passwordMouseEntered(evt);
            }
        });
        password.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                passwordPropertyChange(evt);
            }
        });
        MainPanel.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 200, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Search");
        MainPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 50, 30));

        Delete.setForeground(new java.awt.Color(0, 0, 0));
        Delete.setText("Delete");
        MainPanel.add(Delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 540, 80, 30));

        Edit1.setForeground(new java.awt.Color(0, 0, 0));
        Edit1.setText("Edit");
        Edit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Edit1ActionPerformed(evt);
            }
        });
        MainPanel.add(Edit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 540, 80, 30));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Role");
        MainPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 30, -1));

        SearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFieldActionPerformed(evt);
            }
        });
        SearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchFieldKeyReleased(evt);
            }
        });
        MainPanel.add(SearchField, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 420, 30));

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

    private void emailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailFieldActionPerformed

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
            checkIDs(users_file);
            
            if(isUsernameAvailable(username.getText()) == true && username.getText().length() > 1 == true
                    && isIDAvailable(ID.getText()) == true){
                bw.newLine();
                bw.write(
                    ID.getText() + ";" +
                    username.getText() + ";" +
                    password_string + ";" +
                    name.getText() + ";" +
                    phone.getText() + ";" +
                    address.getText() + ";" +
                    emailField.getText() + ";" +
                    "0;" + //credits
                    role
                );
                bw.flush();
                this.reloadData();
                JOptionPane.showMessageDialog(null, "Successfully added a new " + role);
                
            }else {
                JOptionPane.showMessageDialog(null, "Username or ID is already used! Please use a different username.");
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

    private void SearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchFieldKeyReleased
        try {
            String searchtext = SearchField.getText().trim();
            model.setRowCount(0);
            
            if (searchtext.isEmpty()) {
                reloadData();
            }
            
            FileReader fr = new FileReader(users_file);
            BufferedReader br = new BufferedReader(fr);
            String user;
            
            while ((user = br.readLine()) != null) {
                String[] data = user.split(";");
                String user_id = data[0].trim();
                String search_user = data[1].trim();
                String search_pass = data[2].trim();
                String search_name = data[3].trim();
                String seach_phone = data[4].trim();
                String search_address = data[5].trim();
                String search_email = data[6].trim();
                String search_credit = data[7].trim();
                String role = data[8].trim();
                
                if (user_id.toLowerCase().contains(searchtext.toLowerCase()) ||
                    search_user.toLowerCase().contains(searchtext.toLowerCase()) ||
                    search_pass.toLowerCase().contains(searchtext.toLowerCase()) ||
                    search_name.toLowerCase().contains(searchtext.toLowerCase()) ||
                    seach_phone.toLowerCase().contains(searchtext.toLowerCase()) ||
                    search_address.toLowerCase().contains(searchtext.toLowerCase()) ||
                    search_email.toLowerCase().contains(searchtext.toLowerCase()) ||
                    search_credit.toLowerCase().contains(searchtext.toLowerCase()) ||
                    role.toLowerCase().contains(searchtext.toLowerCase())) {
                    model.addRow(new Object[]{user_id, search_user, search_pass, search_name,
                        seach_phone, search_address, search_email, search_credit, role});
                }
            }
            br.close();
            
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "No users found");
        }
    }//GEN-LAST:event_SearchFieldKeyReleased

    private void Edit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Edit1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edit1ActionPerformed

    private void SearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchFieldActionPerformed

    private void passwordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passwordMouseEntered
        // 
    }//GEN-LAST:event_passwordMouseEntered

    private void passwordPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_passwordPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordPropertyChange

    private void passwordComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_passwordComponentAdded
        //
    }//GEN-LAST:event_passwordComponentAdded

    private void usernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameKeyReleased
        //
    }//GEN-LAST:event_usernameKeyReleased

    private void usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameFocusLost
        if(!(username.getText().length() > 1)){
            JOptionPane.showMessageDialog(null, "Username should be 2 characters at least. Please Try Again!");
        }
    }//GEN-LAST:event_usernameFocusLost

    private void passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFocusLost
        if(!(password.getPassword().length > 8)){
            JOptionPane.showMessageDialog(null, "Password should be 8 characters at least. Please Try Again!");
        }
    }//GEN-LAST:event_passwordFocusLost

    private void emailFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailFieldFocusLost
        if(!(checkEmail(emailField.getText()))){
            JOptionPane.showMessageDialog(null, "Please enter a valid email address!");
        }
    }//GEN-LAST:event_emailFieldFocusLost



    private boolean checkEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    
    
    
    
    private static boolean checkUsernames(File users_file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(users_file))) {
            
            String user;
            reader.readLine();
            while ((user = reader.readLine()) != null) {
                String[] fields = user.split(";");
                if (fields.length > 1) {
                    usernames.add(fields[1]);
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean isUsernameAvailable(String username) {
        return !usernames.contains(username);
    }
    
    private static final Set<String> usernames = new HashSet<>();
    
    
    private static boolean checkIDs(File users_file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(users_file))) {
            
            String user;
            reader.readLine();
            while ((user = reader.readLine()) != null) {
                String[] fields = user.split(";");
                if (fields.length > 1) {
                    ids.add(fields[0]);
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean isIDAvailable(String username) {
        return !ids.contains(username);
    }
    
    private static final Set<String> ids = new HashSet<>();
    
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
    private javax.swing.JButton Delete;
    private javax.swing.JButton Edit1;
    private javax.swing.JTextField ID;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JComboBox<String> Role;
    private javax.swing.JTextField SearchField;
    private javax.swing.JTable UsersTable;
    private javax.swing.JTextField address;
    private javax.swing.JTextField emailField;
    private javax.swing.JButton goBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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

    void setVisible() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
