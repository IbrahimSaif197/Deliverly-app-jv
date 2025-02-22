package com.deliverly.main.customer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerMenu extends JFrame {
private String username;
private Customer customer;
    
public CustomerMenu(String username) {
        this.username = username;
        this.customer = new Customer(username);
        initComponents();
        jLabel2.setText(username);
        loadReviews();
        loadMenuItems();
        loadOrderHistory();
        loadTransactionHistory();
        loadUserBalance();
        orderedItemsList.setModel(new DefaultListModel<>()); 
        checkNotifications(username);
    }
private String getOrderStatus(String orderID) {
    try (BufferedReader br = new BufferedReader(new FileReader("src/data/orders.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] orderDetails = line.split(";");
            if (orderDetails.length >= 6 && orderDetails[0].equals(orderID)) {
                return orderDetails[5].trim(); // Status is in the 6th column
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading order file: " + e.getMessage());
    }
    return "Unknown"; // Default if not found
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
                    String storedUsername = data[0].trim(); 
                    String message = data[1].trim();

                    System.out.println("Found notification -> Username: " + storedUsername + ", Message: " + message);

                    if (storedUsername.equalsIgnoreCase(customerUsername.trim())) { 
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
private void loadUserBalance() {
    String customerID = customer.getCustomerIDFromUsersFile(username);
    if (customerID.equals("ERROR")) {
        JOptionPane.showMessageDialog(this, "Error retrieving Customer ID.");
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/users.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] userDetails = line.split(";");
            if (userDetails.length >= 8 && userDetails[0].equals(customerID)) { 
                String balance = userDetails[7]; 
                jLabel9.setText("RM " + balance);
                return;
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading balance: " + e.getMessage());
    }
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
private void cancelOrder() {
    String selectedOrder = orderHistoryList.getSelectedValue();

    if (selectedOrder == null) {
        JOptionPane.showMessageDialog(this, "Please select an order to cancel.");
        return;
    }

    String[] orderDetails = selectedOrder.split("\\|");
    if (orderDetails.length < 4) {
        JOptionPane.showMessageDialog(this, "Invalid order format. Cannot cancel.");
        return;
    }

    String orderID = orderDetails[0].trim();
    String orderStatus = getOrderStatus(orderID); // Fetch order status from file

    if (!orderStatus.equalsIgnoreCase("Pending")) {
        JOptionPane.showMessageDialog(this, "You can only cancel pending orders!");
        return;
    }

    // Refund Credit if Paid Using Credit
    double totalAmount = Double.parseDouble(orderDetails[3].replace("Rm", "").trim());
    refundCreditToCustomer(totalAmount);

    // Remove Order from File
    if (removeOrderFromFile(orderID)) {
        JOptionPane.showMessageDialog(this, "Order " + orderID + " has been canceled.");
        loadOrderHistory(); // Refresh order history
    } else {
        JOptionPane.showMessageDialog(this, "Error canceling order.");
    }
}

private void refundCreditToCustomer(double refundAmount) {
    String customerID = customer.getCustomerIDFromUsersFile(username);
    double currentCredit = customer.getUserCredit(customerID);
    double newCredit = currentCredit + refundAmount;
    customer.updateUserCredit(customerID, newCredit);
    JOptionPane.showMessageDialog(this, "RM " + refundAmount + " refunded to your credit.");
}

private boolean removeOrderFromFile(String orderID) {
    File ordersFile = new File("src/data/orders.txt");
    File tempFile = new File("src/data/orders_temp.txt");
    boolean orderRemoved = false;

    try (BufferedReader br = new BufferedReader(new FileReader(ordersFile));
         BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

        String line;
        while ((line = br.readLine()) != null) {
            if (!line.startsWith(orderID)) { 
                bw.write(line);
                bw.newLine();
            } else {
                orderRemoved = true; 
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error removing order: " + e.getMessage());
        return false;
    }

    if (orderRemoved && ordersFile.delete()) {
        tempFile.renameTo(ordersFile);
    }
    
    return orderRemoved;
}

private void submitReview() {
    String selectedOrder = orderHistoryList.getSelectedValue();
    String reviewText = reviewTextArea.getText().trim();
    String selectedRating = rating.getSelectedItem().toString();

    if (selectedOrder == null) {
        JOptionPane.showMessageDialog(this, "Select an order to review.");
        return;
    }
    if (reviewText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Enter a review before submitting.");
        return;
    }

    String orderID = selectedOrder.split("\\|")[0].trim();

    File ordersFile = new File("src/data/orders.txt");
    File tempFile = new File("src/data/orders_temp.txt");

    boolean orderFound = false;

    try (BufferedReader br = new BufferedReader(new FileReader(ordersFile));
         BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

        String line;

        while ((line = br.readLine()) != null) {
            String trimmedLine = line.trim();
            String[] orderDetails = trimmedLine.split(";");

            if (trimmedLine.startsWith("ORD") && orderDetails.length >= 9 && orderDetails[0].equals(orderID)) {
                orderDetails[8] = selectedRating; 
                line = String.join(";", orderDetails); 
                orderFound = true;
            }

            bw.write(line);
            bw.newLine();
        }

        if (orderFound) {
            bw.write("Review: " + reviewText);
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Review submitted successfully with rating: " + selectedRating);
        } else {
            JOptionPane.showMessageDialog(this, "Error: Order ID not found! Review not saved.");
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving review: " + e.getMessage());
    }

    if (orderFound && ordersFile.delete()) {
        tempFile.renameTo(ordersFile);
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
    DecimalFormat df = new DecimalFormat("0.00");

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/menu.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
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
        StringBuilder orderDetailsBuilder = new StringBuilder();
        boolean orderFound = false;

        while ((line = br.readLine()) != null) {
            if (line.startsWith("ORD")) { 
                if (orderFound) {
                    orderModel.addElement(orderDetailsBuilder.toString());
                }
                
                String[] orderDetails = line.split(";");
                if (orderDetails.length >= 9) {
                    String orderID = orderDetails[0];
                    String orderCustomerID = orderDetails[1];
                    String itemIDs = orderDetails[3];
                    String status = orderDetails[5];
                    String deliveryMethod = orderDetails[6];
                    double totalPrice = Double.parseDouble(orderDetails[7]);
                    String rating = orderDetails[8];

                    if (orderCustomerID.equals(userId)) {
                        orderFound = true;
                        StringBuilder itemDetails = new StringBuilder();
                        String[] itemArray = itemIDs.split(",");

                        for (String itemID : itemArray) {
                            if (menuItems.containsKey(itemID)) {
                                itemDetails.append(menuItems.get(itemID))
                                        .append(" (Rm").append(menuPrices.get(itemID)).append("), ");
                            }
                        }

                        if (itemDetails.length() > 0) {
                            itemDetails.setLength(itemDetails.length() - 2);
                        }

                        String formattedPrice = df.format(totalPrice);
                        orderDetailsBuilder = new StringBuilder(orderID + " | " + itemDetails +
                                " | " + deliveryMethod + " | Rm" + formattedPrice + 
                                " | Status: " + status + " | Rating: " + rating);
                    } else {
                        orderFound = false;
                    }
                }
            } else if (line.startsWith("Review:") && orderFound) {
                orderDetailsBuilder.append("\n   ").append(line);
            }
        }

        if (orderFound) {
            orderModel.addElement(orderDetailsBuilder.toString());
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
    model.setColumnIdentifiers(new String[]{"Payment Method", "Card Number", "Date", "Amount"});

    DecimalFormat df = new DecimalFormat("0.00");

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/receipts.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");

            if (data.length >= 8 && data[0].equals(userId)) { 
                String paymentMethod = data[7]; // Last column (Cash, Card, Credit)
                String cardNumber = data[2];   
                String date = data[4];         
                double amount = Double.parseDouble(data[6]); 
                String formattedAmount = String.format("%.2f", amount);

                model.addRow(new Object[]{paymentMethod, cardNumber, date, formattedAmount});  
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
        rating = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cancelOrder = new javax.swing.JButton();
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
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(101, 233, 238));

        menuPanel.setBackground(new java.awt.Color(230, 240, 255));

        jLabel3.setText("Delivery Options");

        deliveryOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dine-in", "Take-away", "Delivery" }));
        deliveryOption.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        deliveryOption.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        placeOrderButton.setBackground(new java.awt.Color(52, 152, 219));
        placeOrderButton.setForeground(new java.awt.Color(255, 255, 255));
        placeOrderButton.setText("Proceed to payment");
        placeOrderButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        placeOrderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                placeOrderButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                placeOrderButtonMouseExited(evt);
            }
        });
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
                                .addGap(39, 39, 39)
                                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(orderedItemsList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(placeOrderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(menuPanelLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(deliveryOption, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(393, 456, Short.MAX_VALUE))
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

        orderHistoryPanel.setBackground(new java.awt.Color(255, 235, 235));

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

        rating.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));

        jLabel8.setText("Rating");

        cancelOrder.setText("Cancel");
        cancelOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelOrderActionPerformed(evt);
            }
        });

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
                .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderHistoryPanelLayout.createSequentialGroup()
                                .addComponent(submitReviewButton)
                                .addGap(18, 18, 18)
                                .addComponent(rating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45))
                            .addComponent(reviewsList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(187, Short.MAX_VALUE))
                    .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(orderHistoryPanelLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(cancelOrder)
                .addGap(0, 0, Short.MAX_VALUE))
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
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(orderHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deliveryOption1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reorderButton)
                    .addComponent(submitReviewButton)
                    .addComponent(rating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancelOrder)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        mainPanel.addTab("Orders", orderHistoryPanel);

        transactionHistoryPanel.setBackground(new java.awt.Color(240, 255, 240));

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

        complainsPanel.setBackground(new java.awt.Color(255, 255, 230));

        complainsTextArea.setColumns(20);
        complainsTextArea.setRows(5);
        complains.setViewportView(complainsTextArea);

        jLabel6.setText("Write your complain");

        submitComplain.setBackground(new java.awt.Color(231, 76, 60));
        submitComplain.setForeground(new java.awt.Color(255, 255, 255));
        submitComplain.setText("Submit");
        submitComplain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        submitComplain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitComplainMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitComplainMouseExited(evt);
            }
        });
        submitComplain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitComplainButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout complainsPanelLayout = new javax.swing.GroupLayout(complainsPanel);
        complainsPanel.setLayout(complainsPanelLayout);
        complainsPanelLayout.setHorizontalGroup(
            complainsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, complainsPanelLayout.createSequentialGroup()
                .addContainerGap(288, Short.MAX_VALUE)
                .addGroup(complainsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(complains, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(complainsPanelLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel6)))
                .addGap(350, 350, 350))
            .addGroup(complainsPanelLayout.createSequentialGroup()
                .addGap(374, 374, 374)
                .addComponent(submitComplain, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        complainsPanelLayout.setVerticalGroup(
            complainsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complainsPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(complains, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submitComplain)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        mainPanel.addTab("Complains", complainsPanel);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel1.setText("Welcome back");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel2.setText("User");

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setText("credit");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Balance :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(17, 17, 17))
            .addComponent(mainPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel9)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)))
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

    String orderID = generateOrderID();
    String vendorID = "VEN302";
    
    PaymentWindow paymentWindow = new PaymentWindow(customerID, totalAmount, orderID, vendorID, orderedItemIDs.toString(), deliveryMethod);
    paymentWindow.setVisible(true);
    loadOrderHistory();
    }//GEN-LAST:event_placeOrderButtonActionPerformed

    private void submitComplainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitComplainButtonActionPerformed
   String complaint = complainsTextArea.getText().trim();

    if (complaint.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a complaint.");
        return;    }
    
    String customerID = customer.getCustomerIDFromUsersFile(customer.getUsername()); 

    if (customerID.equals("ERROR")) {
        JOptionPane.showMessageDialog(this, "Error retrieving Customer ID. Complaint not submitted.");
        return;    }
    
    String status = "Unsolved"; 
    String complaintID = generateComplaintID();

    String complaintEntry = complaintID + ";" + customerID + ";" + complaint + ";" + status;

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/complaints.txt", true))) {
        bw.newLine(); 
        bw.write(complaintEntry);
        bw.flush(); 
        JOptionPane.showMessageDialog(this, "Thank you for reaching out! 🙌 Your complaint has been submitted successfully. Our team is on it, and we’ll work to resolve it as soon as possible. We appreciate your patience and value your feedback! 💙✨");
        complainsTextArea.setText(""); 
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving complaint: " + e.getMessage());
    }
}

private String generateComplaintID() {
    String lastComplaintID = "COM000"; 

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/complaints.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split(";");
                if (parts.length > 0 && parts[0].startsWith("COM")) {
                    lastComplaintID = parts[0];
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading complaints file: " + e.getMessage());
    }

    int lastID = Integer.parseInt(lastComplaintID.substring(3));
    int newID = lastID + 1;

    return "COM" + String.format("%03d", newID); 
    
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
        return;    }

    String[] orderDetails = selectedOrder.split("\\|");

    if (orderDetails.length < 4) {
        JOptionPane.showMessageDialog(this, "Invalid order format. Cannot reorder.");
        return;    }

    String itemDetails = orderDetails[1].trim();
    
    String selectedDeliveryMethod = deliveryOption1.getSelectedItem().toString(); 
    double totalAmount = 0;
    StringBuilder menuItemIDs = new StringBuilder();
    String vendorID = null;

    try (BufferedReader br = new BufferedReader(new FileReader("src/data/menu.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] menuDetails = line.split(";");
            if (menuDetails.length == 5) {
                String itemID = menuDetails[0];
                String itemName = menuDetails[1];
                double price = Double.parseDouble(menuDetails[3]);

                if (itemDetails.contains(itemName)) {
                    totalAmount += price;
                    if (menuItemIDs.length() > 0) {
                        menuItemIDs.append(",");
                    }
                    menuItemIDs.append(itemID);

                    if (vendorID == null) {
                        vendorID = menuDetails[4];
                    }
                }
            }
        }
    } catch (IOException e) {        JOptionPane.showMessageDialog(this, "Error reading menu: " + e.getMessage());        return;    }

    if (menuItemIDs.length() == 0) {
        JOptionPane.showMessageDialog(this, "Error: No valid items found in menu!");
        return;    }
    String orderID = generateOrderID();

    JOptionPane.showMessageDialog(this, "Redirecting to payment with delivery method: " + selectedDeliveryMethod);

    new PaymentWindow(customerID, totalAmount, orderID, vendorID, menuItemIDs.toString(), selectedDeliveryMethod).setVisible(true);
    
    loadOrderHistory();
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

    private void placeOrderButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_placeOrderButtonMouseEntered
 placeOrderButton.setBackground(new java.awt.Color(41, 128, 185));
    }//GEN-LAST:event_placeOrderButtonMouseEntered

    private void submitComplainMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_submitComplainMouseEntered
submitComplain.setBackground(new java.awt.Color(255,255,102));

    }//GEN-LAST:event_submitComplainMouseEntered

    private void placeOrderButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_placeOrderButtonMouseExited
placeOrderButton.setBackground(new java.awt.Color(52, 152, 219));
    }//GEN-LAST:event_placeOrderButtonMouseExited

    private void submitComplainMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_submitComplainMouseExited
submitComplain.setBackground(new java.awt.Color(231,76,60));
    }//GEN-LAST:event_submitComplainMouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadReviews();
        loadMenuItems();
        loadOrderHistory();
        loadTransactionHistory();
        loadUserBalance();
        checkNotifications(username);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cancelOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelOrderActionPerformed
cancelOrder();
    }//GEN-LAST:event_cancelOrderActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane FoodItemsList;
    private javax.swing.JList<String> beverageList;
    private javax.swing.JScrollPane beveragesItemsList;
    private javax.swing.JButton cancelOrder;
    private javax.swing.JScrollPane complains;
    private javax.swing.JPanel complainsPanel;
    private javax.swing.JTextArea complainsTextArea;
    private javax.swing.JComboBox<String> deliveryOption;
    private javax.swing.JComboBox<String> deliveryOption1;
    private javax.swing.JScrollPane dessertItemsList;
    private javax.swing.JList<String> dessertList;
    private javax.swing.JList<String> foodList;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane mainPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JList<String> orderHistoryList;
    private javax.swing.JPanel orderHistoryPanel;
    private javax.swing.JList<String> orderedItemsList;
    private javax.swing.JButton placeOrderButton;
    private javax.swing.JComboBox<String> rating;
    private javax.swing.JButton reorderButton;
    private javax.swing.JTextArea reviewTextArea;
    private javax.swing.JList<String> reviewsList;
    private javax.swing.JButton submitComplain;
    private javax.swing.JButton submitReviewButton;
    private javax.swing.JPanel transactionHistoryPanel;
    private javax.swing.JTable transactionTable;
    // End of variables declaration//GEN-END:variables
}
