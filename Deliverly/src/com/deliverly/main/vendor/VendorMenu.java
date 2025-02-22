package com.deliverly.main.vendor;

import com.deliverly.login.LoginMenu;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import java.awt.CardLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.deliverly.login.ThemeManager;

public class VendorMenu extends javax.swing.JFrame {

    private String vendorId;
    private VendorDataHandler dataHandler;
    private DefaultTableModel menuModel;
    private DefaultTableModel orderModel;
    private DefaultTableModel reviewModel;
     private NumberFormat formatter;
     
    public VendorMenu() {
        initComponents();
                LoginMenu login = new LoginMenu();
        this.vendorId = "";
        this.formatter = new DecimalFormat("#0.00");
        //get the logged in user
        try{
            String input_username = login.getUsername();
            if(input_username == null || input_username.isEmpty()){
               JOptionPane.showMessageDialog(null, "Error! Could not get the logged user information, please make sure you are logged in");
               this.dispose();
               return;
            }
            boolean userFound = false;
            java.io.File users_file = new java.io.File("src//data//users.txt");
            java.io.FileReader fr = new java.io.FileReader(users_file);
            java.io.BufferedReader br = new java.io.BufferedReader(fr);
            String user;

            while((user=br.readLine())!=null){
                String saved_username = user.split(";")[1];
                String saved_email = user.split(";")[6];
                if(input_username.equals(saved_username) || input_username.equals(saved_email)){
                    userFound = true;
                    this.vendorId = user.split(";")[0];
                    break;
                }
            }
            if(!userFound){
                 JOptionPane.showMessageDialog(null, "Error! could not find logged user.");
                this.dispose();
            }else {
                dataHandler = new VendorDataHandler(this.vendorId);
                LoggedUser.setText(dataHandler.loadVendorName());
            }
        }catch(java.io.IOException e){
             JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
        }
        this.menuModel = (DefaultTableModel) menuTable.getModel();
        this.orderModel = (DefaultTableModel) orderTable.getModel();
        this.reviewModel = (DefaultTableModel) reviewTable.getModel();
        

        loadMenuItems();
        loadOrders();
        // Set initial visibility
        homePanel.setVisible(true);
        menuPanel.setVisible(false);
        orderPanel.setVisible(false);
        orderHistoryPanel.setVisible(false);
        reviewPanel.setVisible(false);
         revenuePanel.setVisible(false);
    }
    private void loadMenuItems() {
        menuModel.setRowCount(0);
        List<MenuItem> menuItems = dataHandler.loadMenuItems();
        for (MenuItem item : menuItems) {
            menuModel.addRow(new Object[]{item.getId(), item.getName(), item.getDescription(), item.getPrice()});
        }
    }
    private void loadOrders() {
       orderModel.setRowCount(0);
       List<Order> orders = dataHandler.loadOrders();
       for(Order order : orders){
           orderModel.addRow(new Object[]{order.getId(), order.getCustomerId(),order.getOrderDate(),order.getStatus(),order.getDeliveryOption(),this.formatter.format(order.getTotalPrice())});
       }
    }
    
    private void clearMenuItemFields() {
        itemIdField.setText("");
        itemNameField.setText("");
        itemDescriptionField.setText("");
        itemPriceField.setText("");
    }
    
        private void clearOrderFields() {
        orderIDField.setText("");
        orderStatusBox.setSelectedItem("Pending");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        orderButton = new javax.swing.JButton();
        homeButton = new javax.swing.JButton();
        orderHistoryButton = new javax.swing.JButton();
        reviewButton = new javax.swing.JButton();
        revenueButton = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        darkModeCheckBox = new javax.swing.JCheckBox();
        notificationsButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        menuTable = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        itemIdField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        itemNameField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        itemDescriptionField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        itemPriceField = new javax.swing.JTextField();
        createMenuItemButton = new javax.swing.JButton();
        deleteMenuItemButton = new javax.swing.JButton();
        updateMenuItemButton = new javax.swing.JButton();
        homePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LoggedUser = new javax.swing.JLabel();
        orderPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        orderIDField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        orderStatusBox = new javax.swing.JComboBox<>();
        acceptOrderButton = new javax.swing.JButton();
        cancelOrderButton = new javax.swing.JButton();
        orderHistoryPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        orderHistoryTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        orderHistoryComboBox = new javax.swing.JComboBox<>();
        loadOrderHistoryButton = new javax.swing.JButton();
        reviewPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        reviewTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        reviewOrderId = new javax.swing.JTextField();
        loadReviewsButton = new javax.swing.JButton();
        revenuePanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        revenueLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(240, 240, 240));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(153, 255, 255));
        jPanel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        orderButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        orderButton.setText("Order");
        orderButton.setFocusPainted(false);
        orderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderButtonActionPerformed(evt);
            }
        });
        jPanel2.add(orderButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 90, 30));

        homeButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        homeButton.setText("Home");
        homeButton.setFocusPainted(false);
        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeButtonActionPerformed(evt);
            }
        });
        jPanel2.add(homeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, 30));

        orderHistoryButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        orderHistoryButton.setText("Order History");
        orderHistoryButton.setFocusPainted(false);
        orderHistoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderHistoryButtonActionPerformed(evt);
            }
        });
        jPanel2.add(orderHistoryButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 120, 30));

        reviewButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        reviewButton.setText("Reviews");
        reviewButton.setFocusPainted(false);
        reviewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reviewButtonActionPerformed(evt);
            }
        });
        jPanel2.add(reviewButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 90, 30));

        revenueButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        revenueButton.setText("Revenue");
        revenueButton.setFocusPainted(false);
        revenueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revenueButtonActionPerformed(evt);
            }
        });
        jPanel2.add(revenueButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 90, 30));

        menuButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuButton.setText("Menu");
        menuButton.setFocusPainted(false);
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });
        jPanel2.add(menuButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 80, 30));

        darkModeCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        darkModeCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
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
        jPanel2.add(darkModeCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 110, 30));

        notificationsButton.setText("Notfications");
        notificationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                notificationsButtonActionPerformed(evt);
            }
        });
        jPanel2.add(notificationsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 100, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 914, 50));

        mainPanel.setBackground(new java.awt.Color(204, 255, 255));
        mainPanel.setLayout(new java.awt.CardLayout());

        menuPanel.setBackground(new java.awt.Color(204, 255, 255));
        menuPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(250, 250, 250));
        jScrollPane1.setForeground(new java.awt.Color(204, 255, 255));

        menuTable.setBackground(new java.awt.Color(250, 250, 250));
        menuTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Description", "Price"
            }
        ));
        menuTable.setSelectionBackground(new java.awt.Color(250, 250, 250));
        menuTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(menuTable);

        menuPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 572, 450));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Item ID:");
        menuPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 22, 60, 20));

        itemIdField.setForeground(new java.awt.Color(51, 51, 51));
        itemIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemIdFieldActionPerformed(evt);
            }
        });
        menuPanel.add(itemIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 150, 25));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Item Name:");
        menuPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 62, 80, 20));

        itemNameField.setForeground(new java.awt.Color(51, 51, 51));
        itemNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNameFieldActionPerformed(evt);
            }
        });
        menuPanel.add(itemNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, 150, 25));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Description:");
        menuPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 102, 80, 20));

        itemDescriptionField.setForeground(new java.awt.Color(51, 51, 51));
        itemDescriptionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemDescriptionFieldActionPerformed(evt);
            }
        });
        menuPanel.add(itemDescriptionField, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 100, 150, 25));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Price:");
        menuPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 142, 40, 20));

        itemPriceField.setForeground(new java.awt.Color(51, 51, 51));
        itemPriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPriceFieldActionPerformed(evt);
            }
        });
        menuPanel.add(itemPriceField, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 140, 150, 25));

        createMenuItemButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        createMenuItemButton.setText("Create");
        createMenuItemButton.setFocusPainted(false);
        createMenuItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createMenuItemButtonActionPerformed(evt);
            }
        });
        menuPanel.add(createMenuItemButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, 80, 30));

        deleteMenuItemButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        deleteMenuItemButton.setText("Delete");
        deleteMenuItemButton.setFocusPainted(false);
        deleteMenuItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemButtonActionPerformed(evt);
            }
        });
        menuPanel.add(deleteMenuItemButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 180, 80, 30));

        updateMenuItemButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        updateMenuItemButton.setText("Update");
        updateMenuItemButton.setToolTipText("");
        updateMenuItemButton.setFocusPainted(false);
        updateMenuItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateMenuItemButtonActionPerformed(evt);
            }
        });
        menuPanel.add(updateMenuItemButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 220, 80, 30));

        mainPanel.add(menuPanel, "card3");

        homePanel.setBackground(new java.awt.Color(204, 255, 255));
        homePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Welcome Back,");
        homePanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 200, 30));

        LoggedUser.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        LoggedUser.setForeground(new java.awt.Color(51, 51, 51));
        LoggedUser.setText("User");
        homePanel.add(LoggedUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 200, 30));

        mainPanel.add(homePanel, "card2");

        orderPanel.setBackground(new java.awt.Color(204, 255, 255));
        orderPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBackground(new java.awt.Color(250, 250, 250));
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));

        orderTable.setBackground(new java.awt.Color(250, 250, 250));
        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Customer ID", "Order Date", "Status", "Delivery Option", "Total Price"
            }
        ));
        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(orderTable);

        orderPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 569, 505));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Manage Orders");
        orderPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 150, 25));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Order ID:");
        orderPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 80, 20));

        orderIDField.setForeground(new java.awt.Color(51, 51, 51));
        orderIDField.setText("jTextField1");
        orderIDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderIDFieldActionPerformed(evt);
            }
        });
        orderPanel.add(orderIDField, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 80, 130, 25));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Status:");
        orderPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, 80, 20));

        orderStatusBox.setForeground(new java.awt.Color(51, 51, 51));
        orderStatusBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Accepted", "Cancelled", "Completed" }));
        orderStatusBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderStatusBoxActionPerformed(evt);
            }
        });
        orderPanel.add(orderStatusBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 120, 181, 25));

        acceptOrderButton.setText("Accept");
        acceptOrderButton.setFocusPainted(false);
        acceptOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptOrderButtonActionPerformed(evt);
            }
        });
        orderPanel.add(acceptOrderButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 170, 80, 30));

        cancelOrderButton.setText("Cancel");
        cancelOrderButton.setFocusPainted(false);
        cancelOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelOrderButtonActionPerformed(evt);
            }
        });
        orderPanel.add(cancelOrderButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 170, 80, 30));

        mainPanel.add(orderPanel, "card4");

        orderHistoryPanel.setBackground(new java.awt.Color(204, 255, 255));
        orderHistoryPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setBackground(new java.awt.Color(250, 250, 250));

        orderHistoryTable.setBackground(new java.awt.Color(250, 250, 250));
        orderHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Customer ID", "Order Dat", "Status", "Delivery Option", "Total Price"
            }
        ));
        jScrollPane3.setViewportView(orderHistoryTable);

        orderHistoryPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 874, 490));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Filter Order History By:");
        orderHistoryPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 130, 20));

        orderHistoryComboBox.setForeground(new java.awt.Color(51, 51, 51));
        orderHistoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "daily", "monthly", "quarterly", "yearly" }));
        orderHistoryPanel.add(orderHistoryComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 106, 25));

        loadOrderHistoryButton.setText("Load");
        loadOrderHistoryButton.setFocusPainted(false);
        loadOrderHistoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadOrderHistoryButtonActionPerformed(evt);
            }
        });
        orderHistoryPanel.add(loadOrderHistoryButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 80, 30));

        mainPanel.add(orderHistoryPanel, "card5");

        reviewPanel.setBackground(new java.awt.Color(204, 255, 255));
        reviewPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setBackground(new java.awt.Color(250, 250, 250));
        jScrollPane4.setForeground(new java.awt.Color(255, 255, 255));

        reviewTable.setBackground(new java.awt.Color(250, 250, 250));
        reviewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Review"
            }
        ));
        jScrollPane4.setViewportView(reviewTable);

        reviewPanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 592, 507));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Order ID:");
        reviewPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, 70, 20));

        reviewOrderId.setForeground(new java.awt.Color(51, 51, 51));
        reviewOrderId.setText("reviewOrderId");
        reviewOrderId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reviewOrderIdActionPerformed(evt);
            }
        });
        reviewPanel.add(reviewOrderId, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 15, 159, 25));

        loadReviewsButton.setText("Load Reviews");
        loadReviewsButton.setFocusPainted(false);
        loadReviewsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadReviewsButtonActionPerformed(evt);
            }
        });
        reviewPanel.add(loadReviewsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 120, 30));

        mainPanel.add(reviewPanel, "card6");

        revenuePanel.setBackground(new java.awt.Color(204, 255, 255));
        revenuePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Total Revenue:");
        revenuePanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 25, 130, 25));

        revenueLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        revenueLabel.setForeground(new java.awt.Color(51, 51, 51));
        revenueLabel.setText("0.00");
        revenuePanel.add(revenueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 100, 20));

        mainPanel.add(revenuePanel, "card7");

        getContentPane().add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 46, 914, 590));

        setSize(new java.awt.Dimension(926, 642));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void orderHistoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderHistoryButtonActionPerformed
      homePanel.setVisible(false);
      menuPanel.setVisible(false);
      orderPanel.setVisible(false);
      orderHistoryPanel.setVisible(true);
      reviewPanel.setVisible(false);
      revenuePanel.setVisible(false);
    }//GEN-LAST:event_orderHistoryButtonActionPerformed

    private void itemIdFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemIdFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemIdFieldActionPerformed

    private void itemNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemNameFieldActionPerformed

    private void itemDescriptionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemDescriptionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemDescriptionFieldActionPerformed

    private void itemPriceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPriceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemPriceFieldActionPerformed

    private void createMenuItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createMenuItemButtonActionPerformed
        String name = itemNameField.getText();
        String description = itemDescriptionField.getText();
        String priceStr = itemPriceField.getText();

        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
             double price = Double.parseDouble(priceStr);
              // Generate a unique ID for the new menu item
                String itemId = "MI" + itemIdField.getText(); // Example ID generation
                MenuItem newItem = new MenuItem(itemId,name, description, price,this.vendorId);
                dataHandler.saveMenuItem(newItem,true);
                loadMenuItems();
                clearMenuItemFields();
                JOptionPane.showMessageDialog(this, "Menu item created successfully.");
        }catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price format.", "Input Error", JOptionPane.ERROR_MESSAGE);
         }
    }//GEN-LAST:event_createMenuItemButtonActionPerformed

    private void deleteMenuItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuItemButtonActionPerformed
          String itemId = itemIdField.getText();
        if (itemId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dataHandler.deleteMenuItem(itemId);
            loadMenuItems();
            clearMenuItemFields();
            JOptionPane.showMessageDialog(this, "Menu item deleted successfully.");
        }
    }//GEN-LAST:event_deleteMenuItemButtonActionPerformed

    private void orderStatusBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderStatusBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orderStatusBoxActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        homePanel.setVisible(false);
        menuPanel.setVisible(true);
        orderPanel.setVisible(false);
        orderHistoryPanel.setVisible(false);
        reviewPanel.setVisible(false);
        revenuePanel.setVisible(false);
         loadMenuItems();
         clearMenuItemFields();
    }//GEN-LAST:event_menuButtonActionPerformed

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        homePanel.setVisible(true);
        menuPanel.setVisible(false);
        orderPanel.setVisible(false);
        orderHistoryPanel.setVisible(false);
        reviewPanel.setVisible(false);
        revenuePanel.setVisible(false);
    }//GEN-LAST:event_homeButtonActionPerformed

    private void orderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderButtonActionPerformed
       homePanel.setVisible(false);
       menuPanel.setVisible(false);
       orderPanel.setVisible(true);
       orderHistoryPanel.setVisible(false);
       reviewPanel.setVisible(false);
       revenuePanel.setVisible(false);
        loadOrders();
        clearOrderFields();
    }//GEN-LAST:event_orderButtonActionPerformed

    private void reviewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reviewButtonActionPerformed
        homePanel.setVisible(false);
        menuPanel.setVisible(false);
        orderPanel.setVisible(false);
        orderHistoryPanel.setVisible(false);
        reviewPanel.setVisible(true);
        revenuePanel.setVisible(false);
        reviewOrderId.setText("");
        reviewModel.setRowCount(0);
    }//GEN-LAST:event_reviewButtonActionPerformed

    private void reviewOrderIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reviewOrderIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reviewOrderIdActionPerformed

    private void revenueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revenueButtonActionPerformed
       homePanel.setVisible(false);
       menuPanel.setVisible(false);
       orderPanel.setVisible(false);
       orderHistoryPanel.setVisible(false);
       reviewPanel.setVisible(false);
       revenuePanel.setVisible(true);
       List<Order> allOrders = dataHandler.loadOrders();
       double totalRevenue = dataHandler.calculateTotalRevenue(allOrders);
       revenueLabel.setText(this.formatter.format(totalRevenue));
    }//GEN-LAST:event_revenueButtonActionPerformed

    private void loadOrderHistoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadOrderHistoryButtonActionPerformed
         String period = orderHistoryComboBox.getSelectedItem().toString();
         DefaultTableModel model = (DefaultTableModel) orderHistoryTable.getModel();
         model.setRowCount(0);
         List<Order> orders = dataHandler.loadOrderHistory(period);
         for (Order order : orders) {
             model.addRow(new Object[]{order.getId(), order.getCustomerId(),order.getOrderDate(),order.getStatus(),order.getDeliveryOption(),this.formatter.format(order.getTotalPrice())});
        }
    }//GEN-LAST:event_loadOrderHistoryButtonActionPerformed

    private void loadReviewsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadReviewsButtonActionPerformed
         String orderId = reviewOrderId.getText();
         if (orderId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter order ID to view reviews", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
           }
         List<String> reviews = dataHandler.loadCustomerReviews(orderId);
          DefaultTableModel model = (DefaultTableModel) reviewTable.getModel();
         model.setRowCount(0);
         for (String review : reviews) {
            model.addRow(new Object[]{review});
         }
         if (reviews.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No reviews found for order ID " + orderId);
          }
    }//GEN-LAST:event_loadReviewsButtonActionPerformed

    private void updateMenuItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateMenuItemButtonActionPerformed
          String id = itemIdField.getText();
          String name = itemNameField.getText();
          String description = itemDescriptionField.getText();
          String priceStr = itemPriceField.getText();
        if (id.isEmpty() || name.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an item or fill all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
         try{
               double price = Double.parseDouble(priceStr);
                MenuItem updatedItem = new MenuItem(id,name,description,price,this.vendorId);
                dataHandler.saveMenuItem(updatedItem,false);
                loadMenuItems();
                clearMenuItemFields();
                JOptionPane.showMessageDialog(this, "Menu item updated successfully.");
          }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Invalid price format", "Input Error", JOptionPane.ERROR_MESSAGE);
          }
    }//GEN-LAST:event_updateMenuItemButtonActionPerformed

    private void menuTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuTableMouseClicked
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow != -1) {
             itemIdField.setText(menuTable.getValueAt(selectedRow, 0).toString());
            itemNameField.setText(menuTable.getValueAt(selectedRow, 1).toString());
            itemDescriptionField.setText(menuTable.getValueAt(selectedRow, 2).toString());
            itemPriceField.setText(menuTable.getValueAt(selectedRow, 3).toString());
        }
    }//GEN-LAST:event_menuTableMouseClicked

    private void acceptOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptOrderButtonActionPerformed
        String orderId = orderIDField.getText();
        String status = orderStatusBox.getSelectedItem().toString();
         if(orderId.isEmpty()){
             JOptionPane.showMessageDialog(this, "Please select an order to update status", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
         }
        List<Order> allOrders = dataHandler.loadOrders();
         for(Order order : allOrders){
             if(order.getId().equals(orderId)){
                order.setStatus(status);
                dataHandler.updateOrder(order);
                loadOrders();
                clearOrderFields();
                 JOptionPane.showMessageDialog(this, "Order status updated.");
                 return;
             }
         }
    }//GEN-LAST:event_acceptOrderButtonActionPerformed

    private void cancelOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelOrderButtonActionPerformed
          String orderId = orderIDField.getText();
        String status = orderStatusBox.getSelectedItem().toString();
           if(orderId.isEmpty()){
             JOptionPane.showMessageDialog(this, "Please select an order to update status", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
         }
          List<Order> allOrders = dataHandler.loadOrders();
         for(Order order : allOrders){
             if(order.getId().equals(orderId)){
                order.setStatus(status);
                dataHandler.updateOrder(order);
                loadOrders();
                clearOrderFields();
                 JOptionPane.showMessageDialog(this, "Order status updated.");
                 return;
             }
         }
    }//GEN-LAST:event_cancelOrderButtonActionPerformed

    private void orderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseClicked
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow != -1) {
             orderIDField.setText(orderTable.getValueAt(selectedRow, 0).toString());
            orderStatusBox.setSelectedItem(orderTable.getValueAt(selectedRow, 3).toString());
        }
    }//GEN-LAST:event_orderTableMouseClicked

    private void orderIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderIDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orderIDFieldActionPerformed

    private void darkModeCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_darkModeCheckBoxItemStateChanged
        if (darkModeCheckBox.isSelected()) {
            ThemeManager.setDarkMode(this);
        } else {
            ThemeManager.setLightMode(this);
        }
    }//GEN-LAST:event_darkModeCheckBoxItemStateChanged

    private void darkModeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_darkModeCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_darkModeCheckBoxActionPerformed

    private void notificationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_notificationsButtonActionPerformed
List<String> notifications = dataHandler.loadVendorNotifications();

    if (notifications.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No new notifications.");
    } else {
        StringBuilder message = new StringBuilder("Notifications:\n");
        for (String notif : notifications) {
            message.append("- ").append(notif).append("\n");
        }
        JOptionPane.showMessageDialog(this, message.toString());
    }
        jPanel2.add(notificationsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 5, 140, 30));
    }//GEN-LAST:event_notificationsButtonActionPerformed

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
            java.util.logging.Logger.getLogger(VendorMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VendorMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VendorMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VendorMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VendorMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LoggedUser;
    private javax.swing.JButton acceptOrderButton;
    private javax.swing.JButton cancelOrderButton;
    private javax.swing.JButton createMenuItemButton;
    private javax.swing.JCheckBox darkModeCheckBox;
    private javax.swing.JButton deleteMenuItemButton;
    private javax.swing.JButton homeButton;
    private javax.swing.JPanel homePanel;
    private javax.swing.JTextField itemDescriptionField;
    private javax.swing.JTextField itemIdField;
    private javax.swing.JTextField itemNameField;
    private javax.swing.JTextField itemPriceField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton loadOrderHistoryButton;
    private javax.swing.JButton loadReviewsButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton menuButton;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JTable menuTable;
    private javax.swing.JButton notificationsButton;
    private javax.swing.JButton orderButton;
    private javax.swing.JButton orderHistoryButton;
    private javax.swing.JComboBox<String> orderHistoryComboBox;
    private javax.swing.JPanel orderHistoryPanel;
    private javax.swing.JTable orderHistoryTable;
    private javax.swing.JTextField orderIDField;
    private javax.swing.JPanel orderPanel;
    private javax.swing.JComboBox<String> orderStatusBox;
    private javax.swing.JTable orderTable;
    private javax.swing.JButton revenueButton;
    private javax.swing.JLabel revenueLabel;
    private javax.swing.JPanel revenuePanel;
    private javax.swing.JButton reviewButton;
    private javax.swing.JTextField reviewOrderId;
    private javax.swing.JPanel reviewPanel;
    private javax.swing.JTable reviewTable;
    private javax.swing.JButton updateMenuItemButton;
    // End of variables declaration//GEN-END:variables
}
