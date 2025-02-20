package com.deliverly.main.customer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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
        loadTransactionHistory();
        orderedItemsList.setModel(new DefaultListModel<>()); 
    }
private void loadReviews() {
    DefaultListModel<String> reviewsModel = new DefaultListModel<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader("src/data/orders.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("Review:")) { 
                reviewsModel.addElement(line.replace("Review:", "").trim());
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading reviews: " + e.getMessage());
    }
    reviewsList.setModel(reviewsModel);
}
private void loadMenuItems() {
    DefaultListModel<String> foodModel = new DefaultListModel<>();
    DefaultListModel<String> beverageModel = new DefaultListModel<>();
    DefaultListModel<String> dessertModel = new DefaultListModel<>();

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/menu.txt"))) {
        String line;
        boolean isFirstLine = true;

        while ((line = br.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false; // Skip header
                continue;
            }

            String[] details = line.split(";");
            if (details.length == 5) {
                String itemName = details[1];
                String category = details[2]; 
                String price = details[3]; 

                String formattedItem = itemName + " - Rm" + price;
                
                switch (category.toLowerCase()) {
                    case "food":
                        foodModel.addElement(formattedItem);
                        break;
                    case "beverage":
                        beverageModel.addElement(formattedItem);
                        break;
                    case "dessert":
                        dessertModel.addElement(formattedItem);
                        break;
                }
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading menu: " + e.getMessage());
    }

    foodList.setModel(foodModel);
    beverageList.setModel(beverageModel);
    dessertList.setModel(dessertModel);
}

private void submitReview() {
    String selectedOrder = orderHistoryList.getSelectedValue(); 
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

    String userId = customer.getCustomerIDFromUsersFile(username);

    if (userId.equals("ERROR")) {
        JOptionPane.showMessageDialog(this, "Error retrieving Customer ID.");
        return;
    }
    HashMap<String, String> menuItems = new HashMap<>();
    HashMap<String, String> menuPrices = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/menu.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("ItemID")) continue; 

            String[] details = line.split(";");
            if (details.length >= 4) {
                String itemID = details[0];  
                String itemName = details[1]; 
                String price = details[3];   

                menuItems.put(itemID, itemName);
                menuPrices.put(itemID, price);
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading menu items: " + e.getMessage());
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/orders.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] orderDetails = line.split(";");

            if (orderDetails.length >= 8) {
                String orderID = orderDetails[0];  
                String orderCustomerID = orderDetails[1]; 
                String vendorID = orderDetails[2]; 
                String itemIDs = orderDetails[3];  
                String orderDate = orderDetails[4]; 
                String status = orderDetails[5];  
                String deliveryMethod = orderDetails[6]; 
                String totalPrice = orderDetails[7]; 

                if (orderCustomerID.equals(userId)) {
                    StringBuilder itemDetails = new StringBuilder();
                    String[] itemArray = itemIDs.split(",");

                    for (String itemID : itemArray) {
                        if (menuItems.containsKey(itemID)) {
                            itemDetails.append(menuItems.get(itemID)).append(" (Rm").append(menuPrices.get(itemID)).append("), ");
                        }
                    }

                    if (itemDetails.length() > 0) {
                        itemDetails.setLength(itemDetails.length() - 2);
                    }

                    String orderEntry = orderID + " | " + itemDetails + " | " + deliveryMethod + " | Rm" + totalPrice + " | " + status;
                    orderModel.addElement(orderEntry);
                }
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading order history: " + e.getMessage());
    }

    orderHistoryList.setModel(orderModel);
}

private void loadTransactionHistory() {
    String userId = customer.getCustomerIDFromUsersFile(username);
    
    if (userId.equals("ERROR")) {
        JOptionPane.showMessageDialog(this, "Error retrieving Customer ID.");
        return;
    }

    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new String[]{"Card Number", "Date", "Amount"});

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/receipts.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");

            if (data.length >= 6 && data[0].equals(userId)) { 
                String cardNumber = data[2];   
                String date = data[4];         
                String amount = data[6];       

                model.addRow(new Object[]{cardNumber, date, amount});
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading transaction history: " + e.getMessage());
    }

    transactionTable.setModel(model);
}
private String generateOrderID() {
    int randomID = (int) (Math.random() * 900) + 100; 
    return "ORD" + randomID;
}
private String getVendorIDForItem(String itemName) {
    try (BufferedReader br = new BufferedReader(new FileReader("src/data/menu.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] menuDetails = line.split(";");
            if (menuDetails.length >= 5 && menuDetails[1].equalsIgnoreCase(itemName.trim())) {
                return menuDetails[4]; 
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "VEN000"; 
}
private String getMenuItemID(String itemName) {
    try (BufferedReader br = new BufferedReader(new FileReader("src/data/menu.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] menuDetails = line.split(";");
            if (menuDetails.length >= 2 && menuDetails[1].equalsIgnoreCase(itemName.trim())) {
                return menuDetails[0]; 
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "MI000"; 
}
private void addToOrderList(javax.swing.JList<String> sourceList) {
    String selectedItem = sourceList.getSelectedValue();
    
    if (selectedItem != null) {
        DefaultListModel<String> model;
        
        if (orderedItemsList.getModel() instanceof DefaultListModel) {
            model = (DefaultListModel<String>) orderedItemsList.getModel();
        } else {
            model = new DefaultListModel<>();
            orderedItemsList.setModel(model);
        }
        
        model.addElement(selectedItem); 
    }
}


      @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JTabbedPane();
        menuPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        deliveryOption = new javax.swing.JComboBox<>();
        placeOrderButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        FoodItemsList = new javax.swing.JScrollPane();
        foodList = new javax.swing.JList<>();
        beveragesItemsList = new javax.swing.JScrollPane();
        beverageList = new javax.swing.JList<>();
        dessertItemsList = new javax.swing.JScrollPane();
        dessertList = new javax.swing.JList<>();
        orderedItemsList = new javax.swing.JList<>();
        orderHistoryPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderHistoryList = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        reviewTextArea = new javax.swing.JTextArea();
        submitReviewButton = new javax.swing.JButton();
        reviewsList = new javax.swing.JList<>();
        reorderButton = new javax.swing.JButton();
        deliveryOption1 = new javax.swing.JComboBox<>();
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

        jLabel5.setText("Menu");

        foodList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        foodList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                foodListMouseClicked(evt);
            }
        });
        FoodItemsList.setViewportView(foodList);

        jTabbedPane1.addTab("Food", FoodItemsList);

        beverageList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        beverageList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                beverageListMouseClicked(evt);
            }
        });
        beveragesItemsList.setViewportView(beverageList);

        jTabbedPane1.addTab("Beverages", beveragesItemsList);

        dessertList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        dessertList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dessertListMouseClicked(evt);
            }
        });
        dessertItemsList.setViewportView(dessertList);

        jTabbedPane1.addTab("Desserts", dessertItemsList);

        orderedItemsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        orderedItemsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderedItemsListMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(menuPanelLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(placeOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(menuPanelLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(orderedItemsList, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(menuPanelLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(deliveryOption, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(387, 447, Short.MAX_VALUE))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deliveryOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orderedItemsList, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(placeOrderButton)))
                .addContainerGap(96, Short.MAX_VALUE))
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
        reorderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reorderButtonActionPerformed(evt);
            }
        });

        deliveryOption1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dine-in", "Take-away", "Delivery" }));
        deliveryOption1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        deliveryOption1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        javax.swing.GroupLayout orderHistoryPanelLayout = new javax.swing.GroupLayout(orderHistoryPanel);
        orderHistoryPanel.setLayout(orderHistoryPanelLayout);
        orderHistoryPanelLayout.setHorizontalGroup(
            orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(reorderButton)
                        .addGap(55, 55, 55)
                        .addComponent(deliveryOption1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reviewsList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderHistoryPanelLayout.createSequentialGroup()
                        .addComponent(submitReviewButton)
                        .addGap(85, 85, 85)))
                .addContainerGap(187, Short.MAX_VALUE))
        );
        orderHistoryPanelLayout.setVerticalGroup(
            orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addComponent(reviewsList, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deliveryOption1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reorderButton)
                    .addComponent(submitReviewButton))
                .addContainerGap(92, Short.MAX_VALUE))
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
                .addComponent(mainPanel))
        );

        setSize(new java.awt.Dimension(872, 538));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void submitReviewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitReviewButtonActionPerformed
        submitReview();
    }//GEN-LAST:event_submitReviewButtonActionPerformed

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

    String deliveryMethod = deliveryOption.getSelectedItem().toString();
    double totalAmount = 0;
    StringBuilder orderedItemIDs = new StringBuilder(); 

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/menu.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] details = line.split(";");
            if (details.length == 5) {
                String itemID = details[0];
                String itemName = details[1];
                double price = Double.parseDouble(details[3]);

                for (int i = 0; i < model.getSize(); i++) {
                    String selectedItem = model.getElementAt(i);
                    if (selectedItem.startsWith(itemName)) {
                        totalAmount += price;
                        if (orderedItemIDs.length() > 0) {
                            orderedItemIDs.append(","); 
                        }
                        orderedItemIDs.append(itemID);
                    }
                }
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading menu: " + e.getMessage());
        return;
    }

    if (orderedItemIDs.length() == 0) {
        JOptionPane.showMessageDialog(this, "Error: No valid items found in menu!");
        return;
    }

    String orderID = "ORD" + (100 + (int) (Math.random() * 900)); 
    String vendorID = "VEN302"; 
    String status = "Pending";
    String date = java.time.LocalDate.now().toString();

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/orders.txt", true))) {
        bw.newLine();
        bw.write(orderID + ";" + customerID + ";" + vendorID + ";" + orderedItemIDs + ";" + date + ";" + status + ";" + deliveryMethod + ";" + totalAmount);
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

    
    String customerID = customer.getCustomerIDFromUsersFile(customer.getUsername()); 

    if (customerID.equals("ERROR")) {
        JOptionPane.showMessageDialog(this, "Error retrieving Customer ID. Complaint not submitted.");
        return;
    }

    String status = "Unsolved"; 

    
    String complaintEntry = customerID + ";" + complaint + ";" + status;

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/complaints.txt", true))) {
        bw.newLine(); 
        bw.write(complaintEntry);
        bw.flush(); 
        JOptionPane.showMessageDialog(this, "Complaint submitted successfully!");
        complainsTextArea.setText(""); 
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving complaint: " + e.getMessage());
    }
    }//GEN-LAST:event_submitComplainButtonActionPerformed

    private void reorderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reorderButtonActionPerformed
String selectedOrder = orderHistoryList.getSelectedValue();
    
    if (selectedOrder == null) {
        JOptionPane.showMessageDialog(this, "Please select an order to reorder.");
        return;
    }

    String customerID = customer.getCustomerIDFromUsersFile(username);
    if (customerID.equals("ERROR")) {
        JOptionPane.showMessageDialog(this, "Error retrieving Customer ID. Cannot place order.");
        return;
    }

    String[] orderDetails = selectedOrder.split("\\|");

    if (orderDetails.length < 3) {
        JOptionPane.showMessageDialog(this, "Invalid order format. Cannot reorder.");
        return;
    }

    double totalAmount = 0;
    StringBuilder menuItemIDs = new StringBuilder();
    String vendorID = null;

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/orders.txt", true))) {
        for (String orderItem : orderDetails) {
            String[] itemParts = orderItem.trim().split(" - Rm");

            if (itemParts.length < 2) continue;

            String itemName = itemParts[0].trim();
            double price;
            try {                price = Double.parseDouble(itemParts[1].trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error parsing price: " + itemParts[1]);
                return;            }
            totalAmount += price;
            String menuItemID = getMenuItemID(itemName);
            if (!menuItemIDs.isEmpty()) {
                menuItemIDs.append(",");
            }
            menuItemIDs.append(menuItemID);

            if (vendorID == null) {
                vendorID = getVendorIDForItem(itemName);
            }
        }
        if (vendorID == null) vendorID = "VEN000"; 

        String deliveryMethod = deliveryOption.getSelectedItem().toString();
        String orderID = generateOrderID();
        String orderDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String orderStatus = "Pending";

        String formattedOrder = orderID + ";" + customerID + ";" + vendorID + ";" + menuItemIDs.toString() + ";"
                                + orderDate + ";" + orderStatus + ";" + deliveryMethod + ";" + totalAmount;
        bw.write(formattedOrder);
        bw.newLine();

        JOptionPane.showMessageDialog(this, "Order placed successfully with " + deliveryMethod + "! Redirecting to payment...");

        loadOrderHistory();

        new PaymentWindow(customerID, totalAmount).setVisible(true);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving reordered item: " + e.getMessage());
        return;    }
    }//GEN-LAST:event_reorderButtonActionPerformed

    private void orderedItemsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderedItemsListMouseClicked
  if (evt.getClickCount() == 2) { 
        int selectedIndex = orderedItemsList.getSelectedIndex();
        if (selectedIndex != -1) {
        DefaultListModel<String> model = (DefaultListModel<String>) orderedItemsList.getModel();
        model.remove(selectedIndex); }    }
    }//GEN-LAST:event_orderedItemsListMouseClicked

    private void beverageListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beverageListMouseClicked
        addToOrderList(beverageList);
    }//GEN-LAST:event_beverageListMouseClicked

    private void dessertListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dessertListMouseClicked
        addToOrderList(dessertList);
    }//GEN-LAST:event_dessertListMouseClicked

    private void foodListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_foodListMouseClicked
        addToOrderList(foodList);
    }//GEN-LAST:event_foodListMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane FoodItemsList;
    private javax.swing.JList<String> beverageList;
    private javax.swing.JScrollPane beveragesItemsList;
    private javax.swing.JScrollPane complains;
    private javax.swing.JPanel complainsPanel;
    private javax.swing.JTextArea complainsTextArea;
    private javax.swing.JComboBox<String> deliveryOption;
    private javax.swing.JComboBox<String> deliveryOption1;
    private javax.swing.JScrollPane dessertItemsList;
    private javax.swing.JList<String> dessertList;
    private javax.swing.JList<String> foodList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane mainPanel;
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