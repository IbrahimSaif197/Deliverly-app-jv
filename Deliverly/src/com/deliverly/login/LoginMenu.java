package com.deliverly.login;


import com.deliverly.main.admin.AdminMenu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import com.deliverly.main.customer.CustomerMenu;
import com.deliverly.main.manager.ManagerMenu;
import com.deliverly.main.runner.RunnerMenu;
import com.deliverly.main.vendor.VendorMenu;

/**
 *
 * @author natsu
 */
public class LoginMenu extends javax.swing.JFrame {
    
    private static String username;
    private static String role;
    public LoginMenu() {
        initComponents();
        this.pack();
    }
    public void setUsername(String username){
        LoginMenu.username = username;
    }
    
    public String getUsername(){
        return LoginMenu.username;
    }
    public void setRole(String role){
        LoginMenu.role = role;
    }
    public String getRole(){
        return LoginMenu.role;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        Logo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        usernamelogo = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        passwordlogo = new javax.swing.JLabel();
        LoginButton = new javax.swing.JButton();
        ClearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainPanel.setBackground(new java.awt.Color(225, 254, 255));
        MainPanel.setMaximumSize(new java.awt.Dimension(200, 200));
        MainPanel.setMinimumSize(new java.awt.Dimension(200, 200));
        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/data/BlackLogo (1).png"))); // NOI18N
        MainPanel.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, -1, -1));

        jLabel1.setText("Username or Email");
        MainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 206, 200, 29));

        jLabel2.setText("Password");
        MainPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 284, 200, 29));

        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });
        MainPanel.add(usernameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 241, 200, 37));

        usernamelogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/data/free-user-icon-3296-thumb (1).png"))); // NOI18N
        MainPanel.add(usernamelogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(217, 241, -1, -1));

        password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordKeyPressed(evt);
            }
        });
        MainPanel.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 319, 200, 35));

        passwordlogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/data/hiclipart.com.png"))); // NOI18N
        MainPanel.add(passwordlogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(217, 319, -1, -1));

        LoginButton.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        LoginButton.setText("Login");
        LoginButton.setMaximumSize(new java.awt.Dimension(30, 30));
        LoginButton.setMinimumSize(new java.awt.Dimension(30, 30));
        LoginButton.setPreferredSize(new java.awt.Dimension(30, 30));
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });
        LoginButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LoginButtonKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                LoginButtonKeyTyped(evt);
            }
        });
        MainPanel.add(LoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 374, 66, -1));

        ClearButton.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        ClearButton.setText("Clear");
        ClearButton.setMaximumSize(new java.awt.Dimension(30, 30));
        ClearButton.setMinimumSize(new java.awt.Dimension(30, 30));
        ClearButton.setPreferredSize(new java.awt.Dimension(30, 30));
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });
        MainPanel.add(ClearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(394, 374, 66, -1));

        getContentPane().add(MainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 540));

        setSize(new java.awt.Dimension(736, 549));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        try{
            boolean userFound = false;
            File users_file = new File("src//data//users.txt");
            FileReader fr = new FileReader(users_file);
            BufferedReader br = new BufferedReader(fr);
            String user;

            String input_username = usernameField.getText();
            String input_password = new String(password.getPassword());
            
            while((user=br.readLine())!=null){
                String saved_username = user.split(";")[1];
                String saved_email = user.split(";")[6];
                String saved_password = user.split(";")[2];
                
                if(input_username.equals(saved_username) && input_password.equals(saved_password) ||
                        input_username.equals(saved_email) && input_password.equals(saved_password)){
                    userFound = true;
                    setUsername(usernameField.getText());
                    JOptionPane.showMessageDialog(null, "Successfully login");
                    break;
                }
            }
            if(!userFound){
                JOptionPane.showMessageDialog(
                null, "Unable to login! Enter valid Username and Password!");
            } else {
                this.dispose();                
                if(user.split(";")[0].contains("CUS") == true){
                    new CustomerMenu().setVisible(true);
                }
                else if(user.split(";")[0].contains("RNR") == true){
                    new RunnerMenu().setVisible(true);
                }
                else if(user.split(";")[0].contains("VEN") == true){
                    new VendorMenu().setVisible(true);
                }
                else if(user.split(";")[0].contains("ADM") == true){
                    new AdminMenu().setVisible(true);
                }
                else if(user.split(";")[0].contains("MNG") == true){
                    new ManagerMenu().setVisible(true);
                }
                
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
                            
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearButtonActionPerformed
        usernameField.setText("");
        password.setText("");
    }//GEN-LAST:event_ClearButtonActionPerformed

    private void LoginButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LoginButtonKeyPressed
        // nothing
    }//GEN-LAST:event_LoginButtonKeyPressed

    private void LoginButtonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LoginButtonKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_LoginButtonKeyTyped

    private void passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER){
        LoginButtonActionPerformed(null);
    }
    }//GEN-LAST:event_passwordKeyPressed

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
            java.util.logging.Logger.getLogger(LoginMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ClearButton;
    private javax.swing.JButton LoginButton;
    private javax.swing.JLabel Logo;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField password;
    private javax.swing.JLabel passwordlogo;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernamelogo;
    // End of variables declaration//GEN-END:variables
}
