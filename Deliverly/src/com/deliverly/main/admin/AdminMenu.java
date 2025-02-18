
package com.deliverly.main.admin;



import com.deliverly.login.LoginMenu;
import com.deliverly.login.ThemeManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
public final class AdminMenu extends javax.swing.JFrame {

    File users_file = new File("src//data//users.txt"); // users file
    File receipts_file = new File("src//data//receipts.txt"); // receipts file
    DefaultTableModel model;
    LoginMenu login = new LoginMenu();

    public void reloadData(DefaultTableModel tableModel){
        try {
            tableModel.setRowCount(0);
            FileReader fr = new FileReader(users_file);
            BufferedReader br = new BufferedReader(fr);
            String user;
            while ((user = br.readLine()) != null) {
                if (!user.trim().isEmpty()) {
                    String[] user_data = user.split(";");
                    if (user_data.length >= 9) {
                        tableModel.addRow(new Object[]{
                            user_data[0], user_data[1], user_data[2], user_data[3],
                            user_data[4], user_data[5], user_data[6], user_data[7],
                            user_data[8]});
                    }
                }
                
            }
            br.close();
            fr.close();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public AdminMenu() {
        initComponents();
        LoggedUser.setText(login.getUsername());
        //tips config
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(2000);
        
        darkModeCheckBox.setSelected(ThemeManager.isDarkModeEnabled());
        darkModeCheckBox1.setSelected(ThemeManager.isDarkModeEnabled());
        if (ThemeManager.isDarkModeEnabled()) {
            ThemeManager.setDarkMode(this);
        } else {
            ThemeManager.setLightMode(this);
        }
        
        //table
        this.model = (DefaultTableModel) UsersTable.getModel();
        this.reloadData(model);
        //Tips
        ID.setToolTipText("ROLXXX");
        username.setToolTipText("Username should be 2 characters at least");
        password.setToolTipText("Password should be at least 8 characters and 1 number");
        emailField.setToolTipText("example@gmail.com");
        
        UsersTable.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting()) {
            int selectedRow = UsersTable.getSelectedRow();
            if (selectedRow != -1) {
                String iD = (String) UsersTable.getValueAt(selectedRow, 0);
                ID.setText(iD);
                String usernameData = (String) UsersTable.getValueAt(selectedRow, 1);
                username.setText(usernameData);
                String passwordData = (String) UsersTable.getValueAt(selectedRow, 2);
                password.setText(passwordData);
                String nameData = (String) UsersTable.getValueAt(selectedRow, 3);
                name.setText(nameData);
                String phoneData = (String) UsersTable.getValueAt(selectedRow, 4);
                phone.setText(phoneData);
                String addressData = (String) UsersTable.getValueAt(selectedRow, 5);
                address.setText(addressData);
                String emailData = (String) UsersTable.getValueAt(selectedRow, 6);
                emailField.setText(emailData);
                
                
            }
            roleSelectionBackwards();
        }
        });
        
        UsersTable.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting()) {
            int selectedRow = UsersTable.getSelectedRow();
            if (selectedRow != -1) {
                String topUpUsername = (String) UsersTable.getValueAt(selectedRow, 1);
                userID.setText(topUpUsername);
            }
        }
        });
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        RegistrationPanel = new javax.swing.JPanel();
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
        CreateButton = new javax.swing.JButton();
        Role = new javax.swing.JComboBox<>();
        password = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LoggedUser = new javax.swing.JLabel();
        darkModeCheckBox = new javax.swing.JCheckBox();
        TopUpPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        userID = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Amount = new javax.swing.JTextField();
        Cancel = new javax.swing.JButton();
        TopUp = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        CardNumber = new javax.swing.JTextField();
        CVC = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        YearSpinner = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        MonthSpinner = new javax.swing.JSpinner();
        darkModeCheckBox1 = new javax.swing.JCheckBox();
        Table = new javax.swing.JPanel();
        Delete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        UsersTable = new javax.swing.JTable();
        SearchField = new javax.swing.JTextField();
        Edit1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        setMinimumSize(new java.awt.Dimension(1000, 660));
        setPreferredSize(new java.awt.Dimension(1000, 660));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(300, 300));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(300, 300));

        RegistrationPanel.setBackground(new java.awt.Color(204, 255, 255));
        RegistrationPanel.setForeground(new java.awt.Color(0, 0, 0));
        RegistrationPanel.setMaximumSize(new java.awt.Dimension(200, 200));
        RegistrationPanel.setMinimumSize(new java.awt.Dimension(200, 200));
        RegistrationPanel.setPreferredSize(new java.awt.Dimension(300, 570));
        RegistrationPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Phone Number");
        RegistrationPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

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
        RegistrationPanel.add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 200, 30));

        ID.setForeground(new java.awt.Color(0, 0, 0));
        ID.setText("ADM");
        ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDActionPerformed(evt);
            }
        });
        ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                IDKeyReleased(evt);
            }
        });
        RegistrationPanel.add(ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 200, 30));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Name");
        RegistrationPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 200, 20));

        name.setForeground(new java.awt.Color(0, 0, 0));
        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });
        RegistrationPanel.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 200, 30));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Username");
        RegistrationPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("ID");
        RegistrationPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 200, 20));

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
        RegistrationPanel.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 200, 30));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Email");
        RegistrationPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Password");
        RegistrationPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        phone.setForeground(new java.awt.Color(0, 0, 0));
        phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneActionPerformed(evt);
            }
        });
        RegistrationPanel.add(phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 200, 30));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Address");
        RegistrationPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        address.setForeground(new java.awt.Color(0, 0, 0));
        address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressActionPerformed(evt);
            }
        });
        RegistrationPanel.add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 200, 30));

        goBack.setForeground(new java.awt.Color(0, 0, 0));
        goBack.setText("Go Back");
        goBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackActionPerformed(evt);
            }
        });
        RegistrationPanel.add(goBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, 80, 30));

        CreateButton.setForeground(new java.awt.Color(0, 0, 0));
        CreateButton.setText("Create");
        CreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateButtonActionPerformed(evt);
            }
        });
        RegistrationPanel.add(CreateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 480, 80, 30));

        Role.setForeground(new java.awt.Color(0, 0, 0));
        Role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "customer", "runner", "vendor", "manager" }));
        Role.setSelectedItem("");
        Role.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoleActionPerformed(evt);
            }
        });
        RegistrationPanel.add(Role, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

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
        RegistrationPanel.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 200, 30));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Role");
        RegistrationPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 30, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Welcome back,");
        RegistrationPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        LoggedUser.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        LoggedUser.setForeground(new java.awt.Color(0, 0, 0));
        LoggedUser.setText("User");
        RegistrationPanel.add(LoggedUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        darkModeCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        darkModeCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        darkModeCheckBox.setForeground(new java.awt.Color(0, 0, 0));
        darkModeCheckBox.setText("Dark Mode");
        darkModeCheckBox.setContentAreaFilled(false);
        darkModeCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                darkModeCheckBoxItemStateChanged(evt);
            }
        });
        darkModeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                darkModeCheckBoxActionPerformed(evt);
            }
        });
        RegistrationPanel.add(darkModeCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 110, 30));

        jTabbedPane1.addTab("User Management", RegistrationPanel);

        TopUpPanel.setBackground(new java.awt.Color(204, 255, 255));
        TopUpPanel.setForeground(new java.awt.Color(204, 255, 255));
        TopUpPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Top-Up Menu");
        TopUpPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 230, 60));

        userID.setForeground(new java.awt.Color(0, 0, 0));
        userID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userIDActionPerformed(evt);
            }
        });
        TopUpPanel.add(userID, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 210, 40));

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Card Number");
        TopUpPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Top-up Amount");
        TopUpPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        Amount.setForeground(new java.awt.Color(0, 0, 0));
        Amount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                AmountFocusLost(evt);
            }
        });
        TopUpPanel.add(Amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 210, 40));

        Cancel.setForeground(new java.awt.Color(0, 0, 0));
        Cancel.setText("Cancel");
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });
        TopUpPanel.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 80, 30));

        TopUp.setForeground(new java.awt.Color(0, 0, 0));
        TopUp.setText("Top-Up");
        TopUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TopUpActionPerformed(evt);
            }
        });
        TopUpPanel.add(TopUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 370, 80, 30));

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("User ID");
        TopUpPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        CardNumber.setForeground(new java.awt.Color(0, 0, 0));
        CardNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CardNumberActionPerformed(evt);
            }
        });
        TopUpPanel.add(CardNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 210, 40));

        CVC.setForeground(new java.awt.Color(0, 0, 0));
        CVC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CVCActionPerformed(evt);
            }
        });
        TopUpPanel.add(CVC, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 60, 30));

        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("CVC");
        TopUpPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        YearSpinner.setModel(new javax.swing.SpinnerNumberModel(2025, 2025, 2050, 1));
        TopUpPanel.add(YearSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 70, 30));

        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Expiry Date MM - YY");
        TopUpPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, -1, -1));

        MonthSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));
        TopUpPanel.add(MonthSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 50, 30));

        darkModeCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        darkModeCheckBox1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        darkModeCheckBox1.setForeground(new java.awt.Color(0, 0, 0));
        darkModeCheckBox1.setText("Dark Mode");
        darkModeCheckBox1.setContentAreaFilled(false);
        darkModeCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                darkModeCheckBox1ItemStateChanged(evt);
            }
        });
        TopUpPanel.add(darkModeCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 110, 40));

        jTabbedPane1.addTab("Top Up Menu", TopUpPanel);

        Table.setBackground(new java.awt.Color(204, 255, 255));
        Table.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Delete.setForeground(new java.awt.Color(0, 0, 0));
        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });
        Table.add(Delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 560, 80, 30));

        UsersTable.setForeground(new java.awt.Color(0, 0, 0));
        UsersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UserID", "Username", "Password", "Name", "Phone", "Address", "Email", "Credits", "Role"
            }
        ));
        UsersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UsersTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(UsersTable);

        Table.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 640, -1));

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
        Table.add(SearchField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 420, 30));

        Edit1.setForeground(new java.awt.Color(0, 0, 0));
        Edit1.setText("Edit");
        Edit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Edit1ActionPerformed(evt);
            }
        });
        Table.add(Edit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, 80, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Search");
        Table.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 50, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Table, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                    .addComponent(Table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1016, 669));
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
    }//GEN-LAST:event_goBackActionPerformed

    private void CreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateButtonActionPerformed
        createUser();
    }//GEN-LAST:event_CreateButtonActionPerformed

    private void RoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoleActionPerformed
        roleSelection();
    }//GEN-LAST:event_RoleActionPerformed

    private void SearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchFieldKeyReleased
        searchUser();
    }//GEN-LAST:event_SearchFieldKeyReleased

    private void Edit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Edit1ActionPerformed
        editUser();
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

    }//GEN-LAST:event_usernameFocusLost

    private void passwordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFocusLost

    }//GEN-LAST:event_passwordFocusLost

    private void emailFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailFieldFocusLost

    }//GEN-LAST:event_emailFieldFocusLost

    private void UsersTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UsersTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_UsersTableMouseClicked

    private void userIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userIDActionPerformed
        //
    }//GEN-LAST:event_userIDActionPerformed

    private void AmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AmountFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_AmountFocusLost

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelActionPerformed

    private void TopUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TopUpActionPerformed
        topUp();
    }//GEN-LAST:event_TopUpActionPerformed

    private void CardNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CardNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CardNumberActionPerformed

    private void CVCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CVCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CVCActionPerformed

    private void IDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IDKeyReleased
        roleSelectionBackwards();
    }//GEN-LAST:event_IDKeyReleased

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        deleteUser();
    }//GEN-LAST:event_DeleteActionPerformed

    private void darkModeCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_darkModeCheckBoxItemStateChanged
        if (darkModeCheckBox.isSelected()) {
            ThemeManager.setDarkMode(this);
        } else {
            ThemeManager.setLightMode(this);
        }
    }//GEN-LAST:event_darkModeCheckBoxItemStateChanged

    private void darkModeCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_darkModeCheckBox1ItemStateChanged
        if (darkModeCheckBox1.isSelected()) {
            ThemeManager.setDarkMode(this);
        } else {
            ThemeManager.setLightMode(this);
        }
    }//GEN-LAST:event_darkModeCheckBox1ItemStateChanged

    private void darkModeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_darkModeCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_darkModeCheckBoxActionPerformed

    private void createUser(){
        try { 
            String role = Role.getSelectedItem().toString();
            String password_string = new String(password.getPassword());
            
            
            FileWriter fw = new FileWriter(users_file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            if (dataErrors() == 0) {
                bw.newLine();
                bw.write(
                    ID.getText() + ";" +
                    username.getText() + ";" +
                    password_string + ";" +
                    name.getText() + ";" +
                    phone.getText() + ";" +
                    address.getText() + ";" +
                    emailField.getText() + ";" +
                    "0;" + // credits
                    role
                );
                bw.flush();
                bw.close();
                fw.close();
                JOptionPane.showMessageDialog(null, "Successfully added a new " + role);
                this.reloadData(model);
            }
            
            }catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
    }
    
    private boolean isPasswordValid(String password) {
       if (password.length() < 8) {
           return false;
       }
        if (!password.matches(".*\\d.*")) { 
           return false;
        }
           return true;
    }


    private boolean isPhoneValid(String phone) {
        return phone.matches("^\\+?[0-9]{10}$");
    }
    
    private void roleSelection() {
        String currentID = ID.getText();
        String numberID = currentID.length() > 3 ? currentID.substring(3) : "";

        if(Role.getSelectedItem().equals("admin")) {
            ID.setText("ADM" + numberID);
        }
        else if(Role.getSelectedItem().equals("customer")) {
            ID.setText("CUS" + numberID);
        }
        else if(Role.getSelectedItem().equals("runner")) {
            ID.setText("RNR" + numberID);
        }
        else if(Role.getSelectedItem().equals("vendor")) {
            ID.setText("VEN" + numberID);
        }
        else if(Role.getSelectedItem().equals("manager")) {
            ID.setText("MGR" + numberID);
        }
    }
    private void roleSelectionBackwards() {
        String idText = ID.getText();
        if (idText.startsWith("CUS")) {
            Role.setSelectedItem("customer");
        } else if (idText.startsWith("ADM")) {
            Role.setSelectedItem("admin");
        } else if (idText.startsWith("RNR")) {
            Role.setSelectedItem("runner");
        } else if (idText.startsWith("VEN")) {
            Role.setSelectedItem("vendor");
        } else if (idText.startsWith("MGR")) {
            Role.setSelectedItem("manager");
        }
    }

    private boolean checkEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(emailRegex);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    
    private void searchUser() {
        try {
            String searchtext = SearchField.getText().trim();
            model.setRowCount(0);
            
            if (searchtext.isEmpty()) {
                reloadData(model);
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
    }
    
    private void deleteUser() {
        try {
            List<String> users = Files.readAllLines(users_file.toPath());
            List<String> updatedUsers = new ArrayList<>();

            boolean found = false;

            for (String line : users) {
                if (line.startsWith(ID.getText().trim() + ";")) {
                    found = true;
                    continue; // Skip this line
                }
                updatedUsers.add(line);
            }

            if (!found) {
                throw new IOException("User not found!");
            }
            
            Files.write(users_file.toPath(), updatedUsers);
            reloadData(model);
            clearFields();

            JOptionPane.showMessageDialog(this, "User deleted successfully!");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage());
        }
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
    
    
    private void topUp() {
        try {
            int added_amount = Integer.parseInt(Amount.getText().trim());
            if (added_amount < 0) {
                JOptionPane.showMessageDialog(null, "The amount cannot be negative!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String cardNumberStr = CardNumber.getText().trim();
            if (!isValidCardNumber(cardNumberStr)) {
                JOptionPane.showMessageDialog(null, "Card number must be exactly 16 digits!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String cvcStr = CVC.getText().trim();
            if (!isValidCVC(cvcStr)) {
                JOptionPane.showMessageDialog(null, "CVC must be exactly 3 digits!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int month = (int) MonthSpinner.getValue();
            int year = (int) YearSpinner.getValue();
            if (!isValidExpiryDate(month, year)) {
                JOptionPane.showMessageDialog(null, "Card has expired or invalid expiry date!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<String> users = new ArrayList<>();
            String customerName = "";

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
                    customerName = data[3];
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

            FileWriter receipt_fw = new FileWriter(receipts_file, true);
            BufferedWriter receipt_bw = new BufferedWriter(receipt_fw);

            LocalDate currentDate = LocalDate.now();
            String transactionDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            String expiryDate = String.format("%02d/%04d", month, year);

            // Updated receipt format to include both dates
            String receiptData = String.format("%s;%s;%s;%s;%s;%s;%d",
                userID.getText().trim(),
                customerName,
                cardNumberStr,
                cvcStr,
                transactionDate,    // Transaction date
                expiryDate,         // Card expiry date
                added_amount
            );

            receipt_bw.newLine();
            receipt_bw.write(receiptData);
            receipt_bw.close();
            JOptionPane.showMessageDialog(null, "Topped-Up Successfully");
            reloadData(model);
            

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input format: Please check all fields contain valid numbers", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editUser() {
        try {
            List<String> lines = Files.readAllLines(users_file.toPath());
            List<String> updatedData = new ArrayList<>();

            // Get password correctly from JPasswordField
            char[] passwordChars = password.getPassword();
            String passwordStr = new String(passwordChars);

            // Create the new user data string
            String newUserData = String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s",
                ID.getText().trim(),
                username.getText().trim(),
                passwordStr,
                name.getText().trim(),
                phone.getText().trim(),
                address.getText().trim(),
                emailField.getText().trim(),
                "0",
                Role.getSelectedItem().toString().trim()
            );

            boolean found = false;
            for (String line : lines) {
                if (line.startsWith(ID.getText().trim() + ";")) {
                    updatedData.add(newUserData);
                    found = true;
                } else {
                    updatedData.add(line);
                }
            }

            if (!found) {
                throw new IOException("User not found!");
            }

            Files.write(users_file.toPath(), updatedData);

            reloadData(model);

            clearFields();

            JOptionPane.showMessageDialog(this, "User updated successfully!");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating user: " + e.getMessage());
        }
    }

    private void clearFields() {
        ID.setText("");
        username.setText("");
        password.setText("");
        name.setText("");
        phone.setText("");
        address.setText("");
        emailField.setText("");
    }
    
    
    private int dataErrors(){
        String password_string = new String(password.getPassword());
        StringBuilder errorMessages = new StringBuilder();
        checkUsernames(users_file);
        checkIDs(users_file);
            
        if (username.getText().length() <= 1) {
            errorMessages.append("Username must be more than 1 character.\n");
        } else if (!isUsernameAvailable(username.getText())) {
            errorMessages.append("Username is already used.\n");
        }
            
        if (!isIDAvailable(ID.getText())) {
            errorMessages.append("ID is already used.\n");
        }
            
        if (!isPasswordValid(password_string)) {
            errorMessages.append("Password must be at least 8 characters long and include at least one number.\n");
        }
            
        if (!isPhoneValid(phone.getText())) {
            errorMessages.append("Phone number is invalid. It must contain 10-15 digits and can include an optional '+' at the start.\n");
        }
            
        if (!checkEmail(emailField.getText())) {
            errorMessages.append("Email is invalid.\n");
        }
            
        if (errorMessages.length() > 0) {
            JOptionPane.showMessageDialog(null, errorMessages.toString());
            return 1;
        } else {
            return 0;
        }
        
    }
    
    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.length() == 16 && cardNumber.matches("\\d+");
    }

    private boolean isValidCVC(String cvc) {
        return cvc.length() == 3 && cvc.matches("\\d+");
    }

    private boolean isValidExpiryDate(int month, int year) {
        LocalDate today = LocalDate.now();
        LocalDate cardDate = LocalDate.of(year, month, 1);
        return cardDate.isAfter(today);
    }
    
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
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Amount;
    private javax.swing.JTextField CVC;
    private javax.swing.JButton Cancel;
    private javax.swing.JTextField CardNumber;
    private javax.swing.JButton CreateButton;
    private javax.swing.JButton Delete;
    private javax.swing.JButton Edit1;
    private javax.swing.JTextField ID;
    private javax.swing.JLabel LoggedUser;
    private javax.swing.JSpinner MonthSpinner;
    private javax.swing.JPanel RegistrationPanel;
    private javax.swing.JComboBox<String> Role;
    private javax.swing.JTextField SearchField;
    private javax.swing.JPanel Table;
    private javax.swing.JButton TopUp;
    private javax.swing.JPanel TopUpPanel;
    private javax.swing.JTable UsersTable;
    private javax.swing.JSpinner YearSpinner;
    private javax.swing.JTextField address;
    private javax.swing.JCheckBox darkModeCheckBox;
    private javax.swing.JCheckBox darkModeCheckBox1;
    private javax.swing.JTextField emailField;
    private javax.swing.JButton goBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField name;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField phone;
    private javax.swing.JTextField userID;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

    void setVisible() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
