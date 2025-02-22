package com.deliverly.main.vendor;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class VendorDataHandler {

    private final File menuItemsFile;
    private final File ordersFile;
    private final File usersFile;
    private String vendorId;

    public VendorDataHandler(String vendorId) {
        this.menuItemsFile = new File("src//data//menu.txt");
        this.ordersFile = new File("src//data//orders.txt");
        this.usersFile = new File("src//data//users.txt");
        this.vendorId = vendorId;
    }

    // Menu Item Management
    public List<MenuItem> loadMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(menuItemsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 5 && data[4].equals(this.vendorId)) {
                    menuItems.add(new MenuItem(data[0], data[1], data[2], Double.parseDouble(data[3]),data[4]));
                }
            }
        } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error loading menu items: " + e.getMessage());
        }
        return menuItems;
    }

    public void saveMenuItem(MenuItem menuItem, boolean isNew) {
        try {
            List<String> lines = new ArrayList<>();
            if (!isNew) {
                try (BufferedReader br = new BufferedReader(new FileReader(menuItemsFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                         String[] data = line.split(";");
                        if (data.length > 0 && data[0].equals(menuItem.getId())) {
                            lines.add(menuItem.toString());
                        } else {
                            lines.add(line);
                        }
                    }
                }
            } else {
                 try (BufferedReader br = new BufferedReader(new FileReader(menuItemsFile))) {
                     String line;
                     while ((line = br.readLine()) != null) {
                         lines.add(line);
                     }
                 }
                lines.add(menuItem.toString());
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(menuItemsFile))) {
                 for (String line : lines) {
                        bw.write(line);
                        bw.newLine();
                 }
            }
        } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "Error saving menu item: " + e.getMessage());
        }
    }

    public void deleteMenuItem(String itemId) {
         try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(menuItemsFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                     String[] data = line.split(";");
                    if (data.length > 0 && !data[0].equals(itemId)) {
                        lines.add(line);
                    }
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(menuItemsFile))) {
                 for (String line : lines) {
                        bw.write(line);
                        bw.newLine();
                 }
            }
        } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "Error deleting menu item: " + e.getMessage());
        }
    }
    
    public List<Order> loadOrders() {
        List<Order> orders = new ArrayList<>();
         try (BufferedReader br = new BufferedReader(new FileReader(ordersFile))) {
                String line;
             while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 8 && data[2].equals(this.vendorId)) {
                    orders.add(new Order(data[0], data[1], data[2], data[3], data[4], data[5], data[6],Double.parseDouble(data[7])));
                }
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error loading orders: " + e.getMessage());
        }
        return orders;
    }

     public void updateOrder(Order order) {
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(ordersFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length > 0 && data[0].equals(order.getId())) {
                        lines.add(order.toString());
                    } else {
                        lines.add(line);
                    }
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ordersFile))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "Error updating order: " + e.getMessage());
        }
    }
public List<String> loadVendorNotifications() {
    List<String> notifications = new ArrayList<>();

    File notificationsFile = new File("src/data/notifications.txt");

    try (BufferedReader br = new BufferedReader(new FileReader(notificationsFile))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";", 2);
            if (data.length == 2) {
                String recipient = data[0]; // Vendor ID or username
                String message = data[1];

                if (recipient.equalsIgnoreCase(this.vendorId)) {
                    notifications.add(message);
                }
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error loading notifications: " + e.getMessage());
    }

    return notifications;
}


    public List<Order> loadOrderHistory(String period) {
    List<Order> filteredOrders = new ArrayList<>();
    List<Order> allOrders = loadOrders();
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    for (Order order : allOrders) {
       try{
            LocalDate orderDate = LocalDate.parse(order.getOrderDate(), formatter);


            switch (period) {
                case "daily":
                    if (orderDate.isEqual(today)) {
                        filteredOrders.add(order);
                    }
                    break;
                case "monthly":
                     if (orderDate.getMonth() == today.getMonth() && orderDate.getYear() == today.getYear()) {
                        filteredOrders.add(order);
                    }
                    break;
                case "quarterly":
                   int currentQuarter = (today.getMonthValue() - 1) / 3 + 1;
                    int orderQuarter = (orderDate.getMonthValue() - 1) / 3 + 1;
                     if (orderQuarter == currentQuarter && orderDate.getYear() == today.getYear()) {
                        filteredOrders.add(order);
                     }
                     break;
                case "yearly":
                     if (orderDate.getYear() == today.getYear()) {
                         filteredOrders.add(order);
                    }
                    break;
                default:
                   return allOrders;
            }
           }catch(Exception e){
                 JOptionPane.showMessageDialog(null, "Error parsing the date " + order.getOrderDate() + " " + e.getMessage());
                    continue;
           }
        }
    return filteredOrders;
}

     public String loadVendorName(){
        String name ="";
        try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 9 && data[0].equals(this.vendorId)) {
                     name = data[3];
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading vendor name" + e.getMessage());
        }
    
        return name;
    }
     public double calculateTotalRevenue(List<Order> orders) {
        double totalRevenue = 0;
        for (Order order : orders) {
            totalRevenue += order.getTotalPrice();
        }
        return totalRevenue;
    }
   public List<String> loadCustomerReviews(String orderId) {
        List<String> reviews = new ArrayList<>();
        // Assuming reviews are stored in the same order.txt file, appended after each order
         try (BufferedReader br = new BufferedReader(new FileReader(ordersFile))) {
            String line;
             boolean foundOrder = false;
            while ((line = br.readLine()) != null) {
                 String[] data = line.split(";");
                 if (data.length > 0 && data[0].equals(orderId)){
                     foundOrder = true;
                 }else if(foundOrder){
                       if(line.startsWith("Review: ")){
                            reviews.add(line.substring(8));
                        } else {
                             break;
                        }
                 }
            }
        } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "Error loading reviews: " + e.getMessage());
        }

        return reviews;
    }
}