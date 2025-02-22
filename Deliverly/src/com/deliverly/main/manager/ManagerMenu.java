/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.deliverly.main.manager;

import com.deliverly.login.LoginMenu;
import com.deliverly.login.ThemeManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author natsu
 */
public final class ManagerMenu extends javax.swing.JFrame {
    File users_file = new File("src//data//users.txt");
    File orders_file = new File("src//data//orders.txt");
    File menus_file = new File("src//data//menu.txt");
    File complaints_file = new File("src//data//complaints.txt");
    File ratings_file = new File("src//data//ratings.txt");
    DefaultTableModel model, modelOrder, modelComplaints, modelRunner;
    LoginMenu login = new LoginMenu();
    
    
    public void reloadData(DefaultTableModel tableModel){
        try {
            tableModel.setRowCount(0);
            FileReader fr = new FileReader(menus_file);
            BufferedReader br = new BufferedReader(fr);
            String meal;
            while ((meal = br.readLine()) != null) {
                String[] meal_data = meal.split(";");
                tableModel.addRow(new Object[]{
                    meal_data[0], meal_data[1], meal_data[2], meal_data[4]
                    });
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void reloadDataRunner(DefaultTableModel tableModel) {
        try {
            tableModel.setRowCount(0);

            Map<String, String> ratingsMap = new HashMap<>();
            FileReader ratingsFr = new FileReader(ratings_file);
            BufferedReader ratingsBr = new BufferedReader(ratingsFr);
            String ratingLine;
            while ((ratingLine = ratingsBr.readLine()) != null) {
                if (ratingLine.startsWith("runner ID")) {
                    continue;
                }
                String[] ratingData = ratingLine.split(";");
                if (ratingData.length >= 2) {
                    String runnerId = ratingData[0].trim();
                    String rating = ratingData[1].trim();
                    ratingsMap.put(runnerId, rating);
                }
            }
            ratingsBr.close();
            FileReader usersFr = new FileReader(users_file);
            BufferedReader usersBr = new BufferedReader(usersFr);
            String runner;
            while ((runner = usersBr.readLine()) != null) {
                if (!runner.startsWith("RNR")) {
                    continue;
                }
                String[] runnerData = runner.split(";");
                String runnerId = runnerData[0].trim();
                String runnerName = runnerData[3].trim(); 
                String runnerRating = ratingsMap.getOrDefault(runnerId, "N/A");

                tableModel.addRow(new Object[]{runnerId, runnerName, runnerRating});
            }
            usersBr.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void reloadDataOrders(DefaultTableModel tableModel){
        try {
            tableModel.setRowCount(0);
            FileReader fr = new FileReader(orders_file);
            BufferedReader br = new BufferedReader(fr);
            String order;
            while ((order = br.readLine()) != null) {
                if (!order.startsWith("ORD")) {
                    continue;
                }

                String[] order_data = order.split(";");
                tableModel.addRow(new Object[]{
                    order_data[0], order_data[1], order_data[2], order_data[3],
                    order_data[7]
                });
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void reloadDataComplaints(DefaultTableModel tableModel){
        try {
            tableModel.setRowCount(0);
            FileReader fr = new FileReader(complaints_file);
            BufferedReader br = new BufferedReader(fr);
            String complaint;
            while ((complaint = br.readLine()) != null) {
                if (!complaint.startsWith("COM")) {
                    continue;
                }

                String[] complaints_data = complaint.split(";");
                tableModel.addRow(new Object[]{
                    complaints_data[0], complaints_data[1], complaints_data[2],
                    complaints_data[3]
                });
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public ManagerMenu() {
        initComponents();
        LoggedUser.setText(login.getUsername());
        this.modelComplaints = (DefaultTableModel) Complaints.getModel();
        this.modelOrder = (DefaultTableModel) Revenue.getModel();
        this.model = (DefaultTableModel) FoodMenu.getModel();
        this.modelRunner = (DefaultTableModel) Runners.getModel();
        this.reloadData(model);
        this.reloadDataOrders(modelOrder);
        this.reloadDataComplaints(modelComplaints);
        this.reloadDataRunner(modelRunner);
        OverallRevenue.setText(estimatedRevenue());
        TotalOrders.setText(totalOrders());
        numberOfRunners.setText(totalRunners());
        AvgRating.setText(averageRating());
        
        FoodMenu.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting()) {
            int selectedRow = FoodMenu.getSelectedRow();
            if (selectedRow != -1) {
                String foodID = (String) FoodMenu.getValueAt(selectedRow, 0);
                foodIDField.setText(foodID);
            }
        }
        });
        
        Complaints.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting()) {
            int selectedRow = Complaints.getSelectedRow();
            if (selectedRow != -1) {
                String complaintID = (String) Complaints.getValueAt(selectedRow, 0);
                CompID.setText(complaintID);
            }
        }
        });
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        LoggedUser = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Revenue = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        OverallRevenue = new javax.swing.JLabel();
        TotalOrders = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Runners = new javax.swing.JTable();
        numberOfRunners = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        AvgRating = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Complaints = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        CompID = new javax.swing.JTextField();
        Solve = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        FoodMenu = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        foodIDField = new javax.swing.JTextField();
        DeleteButton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        darkModeCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 700));
        setPreferredSize(new java.awt.Dimension(1000, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LoggedUser.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        LoggedUser.setForeground(new java.awt.Color(0, 0, 0));
        LoggedUser.setText("User");
        jPanel1.add(LoggedUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Welcome back,");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        Revenue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Customer ID", "Vendor ID", "Date", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(Revenue);
        if (Revenue.getColumnModel().getColumnCount() > 0) {
            Revenue.getColumnModel().getColumn(0).setResizable(false);
            Revenue.getColumnModel().getColumn(1).setResizable(false);
            Revenue.getColumnModel().getColumn(2).setResizable(false);
            Revenue.getColumnModel().getColumn(3).setResizable(false);
            Revenue.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 720, 470));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Overall Revenue : ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 160, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Total Orders : ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 120, -1, -1));

        OverallRevenue.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        OverallRevenue.setForeground(new java.awt.Color(0, 0, 0));
        OverallRevenue.setText("amount");
        jPanel1.add(OverallRevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 160, -1, -1));

        TotalOrders.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        TotalOrders.setForeground(new java.awt.Color(0, 0, 0));
        TotalOrders.setText("amount");
        jPanel1.add(TotalOrders, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 120, -1, -1));

        jTabbedPane1.addTab("Revenue Dashboard", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Delivery Runners Performance");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Total Delivery Runners: ");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, -1, -1));

        Runners.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Runner ID", "Runner Name", "Rating"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(Runners);
        if (Runners.getColumnModel().getColumnCount() > 0) {
            Runners.getColumnModel().getColumn(0).setResizable(false);
            Runners.getColumnModel().getColumn(1).setResizable(false);
            Runners.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 680, 500));

        numberOfRunners.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        numberOfRunners.setForeground(new java.awt.Color(0, 0, 0));
        numberOfRunners.setText("number");
        jPanel2.add(numberOfRunners, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 100, 50, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Average Rating of Runners: ");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, -1, -1));

        AvgRating.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        AvgRating.setForeground(new java.awt.Color(0, 0, 0));
        AvgRating.setText("Avg");
        jPanel2.add(AvgRating, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 140, -1, -1));

        jTabbedPane1.addTab("Runner Performance", jPanel2);

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Complaints.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ComplaintID", "CustomerID", "Complaint", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(Complaints);
        if (Complaints.getColumnModel().getColumnCount() > 0) {
            Complaints.getColumnModel().getColumn(0).setResizable(false);
            Complaints.getColumnModel().getColumn(1).setResizable(false);
            Complaints.getColumnModel().getColumn(2).setResizable(false);
            Complaints.getColumnModel().getColumn(2).setPreferredWidth(500);
            Complaints.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 760, 490));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Customer Complaints");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Complaint ID");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 140, -1, -1));
        jPanel3.add(CompID, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 160, 150, 30));

        Solve.setText("Solved");
        Solve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolveActionPerformed(evt);
            }
        });
        jPanel3.add(Solve, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 220, -1, -1));

        jTabbedPane1.addTab("Customer Complaints", jPanel3);

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FoodMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Food ID", "Food Name", "Description", "Price"
            }
        ));
        jScrollPane1.setViewportView(FoodMenu);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 97, 700, 441));

        jLabel1.setText("Food ID");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 155, -1, -1));
        jPanel4.add(foodIDField, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 177, 162, 35));

        DeleteButton.setText("Delete");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });
        jPanel4.add(DeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 250, 80, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Vendors Menu Editing");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jTabbedPane1.addTab("Menu Edit", jPanel4);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        jPanel5.setBackground(new java.awt.Color(204, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        darkModeCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        darkModeCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        darkModeCheckBox.setForeground(new java.awt.Color(0, 0, 0));
        darkModeCheckBox.setText("Dark Mode");
        darkModeCheckBox.setContentAreaFilled(false);
        darkModeCheckBox.setMargin(new java.awt.Insets(6, 6, 6, 6));
        darkModeCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                darkModeCheckBoxItemStateChanged(evt);
            }
        });
        jPanel5.add(darkModeCheckBox, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 1000, 70));

        setSize(new java.awt.Dimension(1016, 679));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        deleteFood();
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void darkModeCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_darkModeCheckBoxItemStateChanged
        if (darkModeCheckBox.isSelected()) {
            ThemeManager.setDarkMode(this);
        } else {
            ThemeManager.setLightMode(this);
        }
    }//GEN-LAST:event_darkModeCheckBoxItemStateChanged

    private void SolveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SolveActionPerformed
        solveComplaint();
    }//GEN-LAST:event_SolveActionPerformed

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
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManagerMenu().setVisible(true);
            }
        });
    }
    private String estimatedRevenue(){
        double total = 0.0;
        double company_percent = 0.20;
        double company_revenue = 0.0;
        
        try {
            FileReader fr = new FileReader(orders_file);
            BufferedReader br = new BufferedReader(fr);
            String order;
            while ((order = br.readLine()) != null) {
                if (!order.startsWith("ORD")) {
                    continue;
                }
                String[] order_data = order.split(";"); 
                
                double price = Double.parseDouble(order_data[7]);
                total += price;
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        company_revenue = total * company_percent;
        DecimalFormat df = new DecimalFormat("0.00");
        String revenueStr = df.format(company_revenue) + " RM";
        
        return revenueStr;
        
    }
    private String totalRunners(){
        int runners = 0;
        try {
            FileReader fr = new FileReader(users_file);
            BufferedReader br = new BufferedReader(fr);
            String runner;
            while ((runner = br.readLine()) != null) {
                if (runner.startsWith("RNR")) {
                    runners++;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        String runnersStr = String.valueOf(runners);
        return runnersStr;
    }
    
    private String averageRating() {
        double totalRating = 0.0; 
        int runners = 0;

        try {
            FileReader fr = new FileReader(ratings_file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.startsWith("RNR")) { 
                    String[] parts = line.split(";"); 
                    if (parts.length >= 2) { 
                        double rating = Double.parseDouble(parts[1].trim());
                        totalRating += rating;
                        runners++;
                    }
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        if (runners > 0) {
            double average = totalRating / runners; 
            return String.format("%.2f", average);
        } else {
            return "No ratings found";
        }
    }
    
    private String totalOrders(){
        int orders = 0;
        try {
            FileReader fr = new FileReader(orders_file);
            BufferedReader br = new BufferedReader(fr);
            String order;
            while ((order = br.readLine()) != null) {
                if (order.startsWith("ORD")) {
                    orders++;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        String ordersStr = String.valueOf(orders);
        return ordersStr;
    }
    private void solveComplaint() {
        String complaintID = CompID.getText().trim(); 
        File file = new File("src//data//complaints.txt");
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", 4); 

                if (parts.length == 4 && parts[0].trim().equals(complaintID) && parts[3].trim().equalsIgnoreCase("Unsolved")) {
                    parts[3] = "Solved";
                    found = true;
                    System.out.println("Updated Complaint: " + String.join(";", parts));
                }

                updatedLines.add(String.join(";", parts));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading the complaints file: " + e.getMessage());
            return;
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "Complaint ID not found or already solved.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error updating complaints file: " + e.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(null, "Complaint has been solved successfully!");

        reloadDataComplaints(modelComplaints);
    }


    
    private void deleteFood() {
        try {
            List<String> foods = Files.readAllLines(menus_file.toPath());
            List<String> updatedFoods = new ArrayList<>();

            boolean found = false;

            for (String food : foods) {
                if (food.startsWith(foodIDField.getText().trim() + ";")) {
                    found = true;
                    continue;
                }
                updatedFoods.add(food);
            }

            if (!found) {
                throw new IOException("Food not found!");
            }

            Files.write(menus_file.toPath(), updatedFoods);
            reloadData(model);

            JOptionPane.showMessageDialog(this, "Food deleted successfully!");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error deleting food: " + e.getMessage());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AvgRating;
    private javax.swing.JTextField CompID;
    private javax.swing.JTable Complaints;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JTable FoodMenu;
    private javax.swing.JLabel LoggedUser;
    private javax.swing.JLabel OverallRevenue;
    private javax.swing.JTable Revenue;
    private javax.swing.JTable Runners;
    private javax.swing.JButton Solve;
    private javax.swing.JLabel TotalOrders;
    private javax.swing.JCheckBox darkModeCheckBox;
    private javax.swing.JTextField foodIDField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel numberOfRunners;
    // End of variables declaration//GEN-END:variables
}
