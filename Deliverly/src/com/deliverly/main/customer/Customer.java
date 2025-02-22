package com.deliverly.main.customer;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Customer {
    private String username;
    private final String ordersFile = "src/data/orders.txt";
    private final String complaintsFile = "src/data/complaints.txt";
    private final String usersFile = "src/data/users.txt"; 
    
    public Customer(String username) {
        this.username = username;
    }
    public String getUsername() {
    return username;
}

    public String getCustomerIDFromUsersFile(String username) {
        File file = new File(usersFile); 

        if (!file.exists()) {
            System.out.println("ERROR: users.txt not found at " + file.getAbsolutePath());
            return "ERROR";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 1) {
                    String storedCustomerID = parts[0].trim(); 
                    String storedUsername = parts[1].trim();   

                    System.out.println("DEBUG: Checking " + storedUsername + " against " + username);

                    if (storedUsername.equalsIgnoreCase(username.trim())) {
                        return storedCustomerID; 
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";     }
    public String[] getUserIDAndUsername(String identifier) {
    File file = new File(usersFile); 

    if (!file.exists()) {
        System.out.println("ERROR: users.txt not found at " + file.getAbsolutePath());
        return new String[]{"ERROR", "ERROR"};
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";"); 
            if (parts.length > 1) {
                String storedCustomerID = parts[0].trim(); 
                String storedUsername = parts[1].trim();   

                System.out.println("DEBUG: Checking " + storedUsername + " and " + storedCustomerID + " against " + identifier);

                if (storedUsername.equalsIgnoreCase(identifier.trim()) || storedCustomerID.equalsIgnoreCase(identifier.trim())) {
                    return new String[]{storedCustomerID, storedUsername}; 
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return new String[]{"ERROR", "ERROR"}; 
}


    public void submitComplaint(String complaint) {
        String customerID = getCustomerIDFromUsersFile(this.username); 

        if (customerID.equals("ERROR")) {
            JOptionPane.showMessageDialog(null, "Error retrieving Customer ID. Complaint not submitted.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(complaintsFile, true))) {
            writer.write(customerID + ";" + complaint + ";Unsolved");
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Complaint submitted successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving complaint: " + e.getMessage());
        }
    }

    public void placeOrder(String orderDetails) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ordersFile, true))) {
            writer.write(username + ";" + orderDetails);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getOrderHistory() {
        List<String> history = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ordersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ";")) { 
                    history.add(line.substring(username.length() + 1)); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history;
    }

    public static String validateLogin(String enteredUsername, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/data/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3) { 
                    String storedUserID = parts[0];  
                    String storedUsername = parts[1]; 
                    String storedPassword = parts[2]; 

                    if ((storedUsername.equals(enteredUsername) || storedUserID.equals(enteredUsername)) &&
                            storedPassword.equals(enteredPassword)) {
                        return storedUserID; 
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
