package com.deliverly.main.customer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
public class CustomerMenu extends JFrame {
    private String username;
    private Customer customer;
    private String ID;


    public CustomerMenu(String ID, String username) {
        this.ID = ID;
        this.username = username;
        this.customer = new Customer(username);
        initComponents();
        checkNotifications(username);
        jLabel2.setText(username);
    }

      @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JTabbedPane();
        menuPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        menuList = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        deliveryOption = new javax.swing.JComboBox<>();
        placeOrder = new javax.swing.JButton();
        orderHistoryPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderHistoryList = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        reviewsList = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        reorderButton = new javax.swing.JButton();
        complainsPanel = new javax.swing.JPanel();
        complains = new javax.swing.JScrollPane();
        complainsTextArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        submitComplain = new javax.swing.JButton();
        transactionHistoryPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        transactionTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(225, 254, 255));

        menuList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(menuList);

        jLabel3.setText("Delivery Options");

        deliveryOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dine-in", "Take-away", "Delivery" }));
        deliveryOption.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        deliveryOption.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        placeOrder.setText("Place Order");

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addContainerGap(432, Short.MAX_VALUE)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(placeOrder)
                    .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deliveryOption, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(370, 370, 370))
            .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(menuPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(564, Short.MAX_VALUE)))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deliveryOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(placeOrder)
                .addContainerGap(276, Short.MAX_VALUE))
            .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(menuPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(268, Short.MAX_VALUE)))
        );

        mainPanel.addTab("Menu", menuPanel);

        orderHistoryList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(orderHistoryList);

        jLabel4.setText("Order History");

        reviewsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });

        jLabel5.setText("Reviews");

        reorderButton.setText("Reorder");

        javax.swing.GroupLayout orderHistoryPanelLayout = new javax.swing.GroupLayout(orderHistoryPanel);
        orderHistoryPanel.setLayout(orderHistoryPanelLayout);
        orderHistoryPanelLayout.setHorizontalGroup(
            orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderHistoryPanelLayout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(234, 234, 234))
            .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(reviewsList, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(reorderButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        orderHistoryPanelLayout.setVerticalGroup(
            orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(reviewsList, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(reorderButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.addTab("Orders", orderHistoryPanel);

        complainsTextArea.setColumns(20);
        complainsTextArea.setRows(5);
        complains.setViewportView(complainsTextArea);

        jLabel6.setText("Write your complain");

        submitComplain.setText("Submit");

        javax.swing.GroupLayout complainsPanelLayout = new javax.swing.GroupLayout(complainsPanel);
        complainsPanel.setLayout(complainsPanelLayout);
        complainsPanelLayout.setHorizontalGroup(
            complainsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complainsPanelLayout.createSequentialGroup()
                .addGroup(complainsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(complainsPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(complainsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(complains, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(complainsPanelLayout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(submitComplain)))
                .addContainerGap(625, Short.MAX_VALUE))
        );
        complainsPanelLayout.setVerticalGroup(
            complainsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complainsPanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(complains, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(submitComplain)
                .addContainerGap(122, Short.MAX_VALUE))
        );

        mainPanel.addTab("Complains", complainsPanel);

        transactionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "null", "Title 2", "Title 3", "Title 4"
            }
        ));
        transactionTable.setColumnSelectionAllowed(true);
        jScrollPane3.setViewportView(transactionTable);
        transactionTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jLabel7.setText("Transaction History");

        javax.swing.GroupLayout transactionHistoryPanelLayout = new javax.swing.GroupLayout(transactionHistoryPanel);
        transactionHistoryPanel.setLayout(transactionHistoryPanelLayout);
        transactionHistoryPanelLayout.setHorizontalGroup(
            transactionHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionHistoryPanelLayout.createSequentialGroup()
                .addGroup(transactionHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(transactionHistoryPanelLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(transactionHistoryPanelLayout.createSequentialGroup()
                        .addGap(240, 240, 240)
                        .addComponent(jLabel7)))
                .addContainerGap(331, Short.MAX_VALUE))
        );
        transactionHistoryPanelLayout.setVerticalGroup(
            transactionHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionHistoryPanelLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.addTab("Transaction History", transactionHistoryPanel);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel1.setText("Welcome back");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel2.setText("User");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainPanel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel))
        );

        setSize(new java.awt.Dimension(914, 524));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
private void placeOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
    String selectedItem = menuList.getSelectedValue();
    String deliveryMethod = deliveryOption.getSelectedItem().toString();

    if (selectedItem != null) {
        customer.placeOrder(selectedItem + " - " + deliveryMethod);
        JOptionPane.showMessageDialog(this, "Order placed successfully!");
    } else {
        JOptionPane.showMessageDialog(this, "Please select an item.");
    }
}
private void submitComplaintButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
    String complaint = complaintsTextArea.getText();
    if (!complaint.isEmpty()) {
        customer.submitComplaint(complaint);
        JOptionPane.showMessageDialog(this, "Complaint submitted successfully!");
    } else {
        JOptionPane.showMessageDialog(this, "Please enter a complaint.");
    }
}
private void reorderButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
    String selectedOrder = orderHistoryList.getSelectedValue();
    if (selectedOrder != null) {
        customer.placeOrder(selectedOrder);
        JOptionPane.showMessageDialog(this, "Reordered successfully!");
    } else {
        JOptionPane.showMessageDialog(this, "Please select an order to reorder.");
    }
}
    private void checkNotifications(String customerUsername) {
        try {
            File notificationsFile = new File("src//data//notifications.txt");
            if (!notificationsFile.exists()) {
                JOptionPane.showMessageDialog(null, "No new notifications.");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(notificationsFile));
            List<String> updatedNotifications = new ArrayList<>();
            String line;
            boolean hasNotification = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";", 2);

                if (data.length == 2) {
                    String storedUsername = data[0].trim(); // Trim spaces
                    String message = data[1].trim();

                    System.out.println("Found notification -> Username: " + storedUsername + ", Message: " + message);

                    if (storedUsername.equalsIgnoreCase(customerUsername.trim())) { // Case insensitive check
                        JOptionPane.showMessageDialog(null, "Notification: " + message);
                        hasNotification = true;
                    } else {
                        updatedNotifications.add(line);
                    }
                }
            }
            br.close();

            FileWriter fw = new FileWriter(notificationsFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String notif : updatedNotifications) {
                bw.write(notif);
                bw.newLine();
            }
            bw.close();

            if (!hasNotification) {
                JOptionPane.showMessageDialog(null, "No new notifications.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error checking notifications: " + e.getMessage());
        }
    }



    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane complains;
    private javax.swing.JPanel complainsPanel;
    private javax.swing.JTextArea complainsTextArea;
    private javax.swing.JComboBox<String> deliveryOption;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane mainPanel;
    private javax.swing.JList<String> menuList;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JList<String> orderHistoryList;
    private javax.swing.JPanel orderHistoryPanel;
    private javax.swing.JButton placeOrder;
    private javax.swing.JButton reorderButton;
    private javax.swing.JList<String> reviewsList;
    private javax.swing.JButton submitComplain;
    private javax.swing.JPanel transactionHistoryPanel;
    private javax.swing.JTable transactionTable;
    // End of variables declaration//GEN-END:variables

    private static class complaintsTextArea {

        private static String getText() {
            return null;
        }

        public complaintsTextArea() {
        }
    }
}
