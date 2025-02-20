package com.deliverly.main.customer;

import java.io.*;
import java.util.*;

public class Customer {
    private String username;
    private String ordersFile = "orders.txt";
    private String complaintsFile = "complaints.txt";

    public Customer(String username) {
        this.username = username;
    }

    // Save order to orders.txt
    public void placeOrder(String orderDetails) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ordersFile, true))) {
            writer.write(username + ": " + orderDetails);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get order history from orders.txt
    public List<String> getOrderHistory() {
        List<String> history = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ordersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username + ":")) {
                    history.add(line.substring(username.length() + 2));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history;
    }

    // Submit a complaint
    public void submitComplaint(String complaint) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(complaintsFile, true))) {
            writer.write(username + ": " + complaint);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Validate login by checking users.txt
    public static String validateLogin(String enteredUsername, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String storedUsername = parts[0];
                    String storedPassword = parts[1];
                    String role = parts[2];

                    if (storedUsername.equals(enteredUsername) && storedPassword.equals(enteredPassword)) {
                        return role;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
