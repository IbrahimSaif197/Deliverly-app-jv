package com.deliverly.main.customer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;

public class CustomerMenu extends JFrame {
    private String username;
    private Customer customer;
    private javax.swing.JPanel reviewsPanel;
    
    public CustomerMenu(String username) {
        this.username = username;
        this.customer = new Customer(username);
        initComponents();
        jLabel2.setText(username);
        loadReviews();
        loadMenuItems();
        loadOrderHistory();
        orderedItemsList.setModel(new DefaultListModel<>()); 

    }
    
private void loadReviews() {
    DefaultListModel<String> reviewsModel = new DefaultListModel<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader("src/data/orders.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("Review:")) { // Identify review lines in the file
                reviewsModel.addElement(line.replace("Review:", "").trim());
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading reviews: " + e.getMessage());
    }
    
    reviewsList.setModel(reviewsModel);
}
private void loadMenuItems() {
    DefaultListModel<String> menuModel = new DefaultListModel<>();

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/menu.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] details = line.split(";");
            if (details.length == 4) {
                String itemName = details[1] + " - $" + details[3]; // Format: "Burger - $5.99"
                menuModel.addElement(itemName);
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading menu: " + e.getMessage());
    }

    menuList.setModel(menuModel);
}
private void submitReview() {
    String selectedOrder = orderHistoryList.getSelectedValue(); // Get selected order
    String reviewText = reviewTextArea.getText().trim();

    if (selectedOrder == null) {
        JOptionPane.showMessageDialog(this, "Select an order to review.");
        return;
    }
    if (reviewText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Enter a review before submitting.");
        return;
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/orders.txt", true))) {
        bw.newLine(); 
        bw.write("Review: " + reviewText);
        JOptionPane.showMessageDialog(this, "Review submitted successfully!");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving review: " + e.getMessage());
    }
    loadReviews();
}
private void loadOrderHistory() {
    DefaultListModel<String> orderModel = new DefaultListModel<>();

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/orders.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] orderDetails = line.split(";");

            // Ensure line contains a valid order (not a review)
            if (orderDetails.length >= 7) {
                String orderID = orderDetails[0];
                String customerID = orderDetails[1]; // Extract Customer ID
                String itemName = orderDetails[3];
                String orderDate = orderDetails[4];
                String status = orderDetails[5];
                String price = orderDetails[7]; // Price is at index 7

                // Filter orders by current user
                if (customerID.equals(username)) {
                    String orderEntry = orderID + " | " + itemName + " | $" + price + " | " + status;
                    orderModel.addElement(orderEntry);
                }
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading order history: " + e.getMessage());
    }

    
    orderHistoryList.setModel(orderModel);
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
        placeOrderButton = new javax.swing.JButton();
        orderedItemsList = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        orderHistoryPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderHistoryList = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        reviewTextArea = new javax.swing.JTextArea();
        submitReviewButton = new javax.swing.JButton();
        reviewsList = new javax.swing.JList<>();
        reorderButton = new javax.swing.JButton();
        transactionHistoryPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        transactionTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        complainsPanel = new javax.swing.JPanel();
        complains = new javax.swing.JScrollPane();
        complainsTextArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        submitComplain = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(225, 254, 255));

        menuList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        menuList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(menuList);

        jLabel3.setText("Delivery Options");

        deliveryOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dine-in", "Take-away", "Delivery" }));
        deliveryOption.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        deliveryOption.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        placeOrderButton.setText("Place Order");
        placeOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeOrderButtonActionPerformed(evt);
            }
        });

        orderedItemsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });

        jLabel5.setText("Menu");

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(placeOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(orderedItemsList, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deliveryOption, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(384, Short.MAX_VALUE))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addComponent(deliveryOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(orderedItemsList, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(placeOrderButton))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(153, Short.MAX_VALUE))
        );

        mainPanel.addTab("Menu", menuPanel);

        orderHistoryList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(orderHistoryList);

        jLabel4.setText("Order History");

        reviewTextArea.setColumns(20);
        reviewTextArea.setRows(5);
        jScrollPane4.setViewportView(reviewTextArea);

        submitReviewButton.setText("Submit review");
        submitReviewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitReviewButtonActionPerformed(evt);
            }
        });

        reviewsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });

        reorderButton.setText("Reorder");

        javax.swing.GroupLayout orderHistoryPanelLayout = new javax.swing.GroupLayout(orderHistoryPanel);
        orderHistoryPanel.setLayout(orderHistoryPanelLayout);
        orderHistoryPanelLayout.setHorizontalGroup(
            orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(reorderButton)
                        .addGap(167, 167, 167)
                        .addComponent(submitReviewButton))
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(reviewsList, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel4))))
                .addContainerGap(290, Short.MAX_VALUE))
        );
        orderHistoryPanelLayout.setVerticalGroup(
            orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(submitReviewButton)
                            .addComponent(reorderButton)))
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addComponent(reviewsList, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        mainPanel.addTab("Orders", orderHistoryPanel);

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
                        .addGap(180, 180, 180)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(transactionHistoryPanelLayout.createSequentialGroup()
                        .addGap(338, 338, 338)
                        .addComponent(jLabel7)))
                .addContainerGap(211, Short.MAX_VALUE))
        );
        transactionHistoryPanelLayout.setVerticalGroup(
            transactionHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionHistoryPanelLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        mainPanel.addTab("Transaction History", transactionHistoryPanel);

        complainsTextArea.setColumns(20);
        complainsTextArea.setRows(5);
        complains.setViewportView(complainsTextArea);

        jLabel6.setText("Write your complain");

        submitComplain.setText("Submit");
        submitComplain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitComplainButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout complainsPanelLayout = new javax.swing.GroupLayout(complainsPanel);
        complainsPanel.setLayout(complainsPanelLayout);
        complainsPanelLayout.setHorizontalGroup(
            complainsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complainsPanelLayout.createSequentialGroup()
                .addGroup(complainsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(complainsPanelLayout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(complains, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(complainsPanelLayout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(complainsPanelLayout.createSequentialGroup()
                        .addGap(365, 365, 365)
                        .addComponent(submitComplain)))
                .addContainerGap(341, Short.MAX_VALUE))
        );
        complainsPanelLayout.setVerticalGroup(
            complainsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complainsPanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(complains, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submitComplain)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        mainPanel.addTab("Complains", complainsPanel);

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
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(mainPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(872, 538));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void submitReviewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitReviewButtonActionPerformed
        submitReview();
    }//GEN-LAST:event_submitReviewButtonActionPerformed

    private void menuListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuListMouseClicked
String selectedItem = menuList.getSelectedValue();
    if (selectedItem != null) {
        DefaultListModel<String> model = (DefaultListModel<String>) orderedItemsList.getModel();
        model.addElement(selectedItem); // Add selected menu item to ordered items list
    }
    }//GEN-LAST:event_menuListMouseClicked

    private void placeOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeOrderButtonActionPerformed
                
   DefaultListModel<String> model = (DefaultListModel<String>) orderedItemsList.getModel();

    if (model.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please select at least one item to order.");
        return;
    }

    String customerID = customer.getCustomerIDFromUsersFile(customer.getUsername());
    if (customerID.equals("ERROR")) {
        JOptionPane.showMessageDialog(this, "Error retrieving Customer ID. Order not placed.");
        return;
    }

    double totalAmount = 0;
    String deliveryMethod = deliveryOption.getSelectedItem().toString();

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/orders.txt", true))) {
        for (int i = 0; i < model.getSize(); i++) {
            String selectedItem = model.getElementAt(i);
            String[] parts = selectedItem.split(" - \\$");
            String itemName = parts[0];
            double price = (parts.length > 1) ? Double.parseDouble(parts[1]) : 0.00;
            totalAmount += price;

            bw.write(customerID + ";" + itemName + ";" + price + ";" + deliveryMethod);
            bw.newLine();
        }
        JOptionPane.showMessageDialog(this, "Order placed successfully!");

        model.clear();

        new PaymentWindow(customerID, totalAmount).setVisible(true);

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving order: " + e.getMessage());
    }
    }//GEN-LAST:event_placeOrderButtonActionPerformed

    private void submitComplainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitComplainButtonActionPerformed
    String complaint = complainsTextArea.getText().trim();

    if (complaint.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a complaint.");
        return;
    }

    // Get the correct Customer ID using the stored username
    String customerID = customer.getCustomerIDFromUsersFile(customer.getUsername()); // ✅ Pass username as parameter

    if (customerID.equals("ERROR")) {
        JOptionPane.showMessageDialog(this, "Error retrieving Customer ID. Complaint not submitted.");
        return;
    }

    String status = "Unsolved"; // Default status for new complaints

    // Format complaint correctly as "CUS001;Complaint Text;Unsolved"
    String complaintEntry = customerID + ";" + complaint + ";" + status;

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/complaints.txt", true))) {
        bw.newLine(); // Ensure new complaint starts on a new line
        bw.write(complaintEntry);
        bw.flush(); // ✅ Ensure data is written
        JOptionPane.showMessageDialog(this, "Complaint submitted successfully!");
        complainsTextArea.setText(""); // Clear the text area after submission
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving complaint: " + e.getMessage());
    }
    }//GEN-LAST:event_submitComplainButtonActionPerformed

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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane mainPanel;
    private javax.swing.JList<String> menuList;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JList<String> orderHistoryList;
    private javax.swing.JPanel orderHistoryPanel;
    private javax.swing.JList<String> orderedItemsList;
    private javax.swing.JButton placeOrderButton;
    private javax.swing.JButton reorderButton;
    private javax.swing.JTextArea reviewTextArea;
    private javax.swing.JList<String> reviewsList;
    private javax.swing.JButton submitComplain;
    private javax.swing.JButton submitReviewButton;
    private javax.swing.JPanel transactionHistoryPanel;
    private javax.swing.JTable transactionTable;
    // End of variables declaration//GEN-END:variables
}