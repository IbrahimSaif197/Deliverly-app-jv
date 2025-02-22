package com.deliverly.main.runner;

import com.deliverly.login.LoginMenu;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class RunnerMenu extends javax.swing.JFrame {
    // Constants for file paths
    private static final String DATA_DIR = "src//data//";
    private static final File TASKS_FILE = new File(DATA_DIR + "delivery_tasks.txt");
    private static final File ORDERS_FILE = new File(DATA_DIR + "orders.txt");
    private static final File USERS_FILE = new File(DATA_DIR + "users.txt");

    private DefaultTableModel tasksModel;
    private DefaultTableModel acceptedModel;
    private DefaultTableModel HistoryModel;
    

    
    public RunnerMenu() {
        initComponents();
        this.tasksModel = (DefaultTableModel) TasksTable.getModel();
        this.acceptedModel = (DefaultTableModel) AcceptedTable.getModel();
        this.HistoryModel = (DefaultTableModel) TasksHistory.getModel();
        reloadTasks();
        reloadAcceptedTasks();
        loadTaskHistory(HistoryModel);
    }

    public void reloadTasks() {
        try {
            tasksModel.setRowCount(0);
            try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.startsWith("Review:")) {
                        String[] data = line.split(";");
                        if (data.length >= 8 && data[5].equalsIgnoreCase("Pending")) {
                            tasksModel.addRow(new Object[]{
                                data[0], // Order ID
                                data[1], // Customer ID
                                data[2], // Vendor ID
                                data[7]  // Fee
                            });
                        }
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading tasks: " + e.getMessage());
        }
    }

    public void reloadAcceptedTasks() {
        try {
            acceptedModel.setRowCount(0);
            String loggedInRunnerID = fetchUserID(LoginMenu.username);
            if (loggedInRunnerID == null) {
                JOptionPane.showMessageDialog(null, "Error: Could not fetch runner ID");
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.startsWith("Review:")) {
                        String[] data = line.split(";");
                        if (data.length >= 8 && data[4].equals(loggedInRunnerID) && 
                            (data[5].equalsIgnoreCase("Accepted") || data[5].equalsIgnoreCase("Picked Up"))) {

                            acceptedModel.addRow(new Object[]{
                                data[0], // Order ID
                                data[1], // Customer ID
                                data[2], // Vendor ID
                                data[7], // Fee
                                data[5]  // Status
                            });
                        }
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading accepted tasks: " + e.getMessage());
        }
    }


    private String fetchUserID(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(";");
                if (userData.length >= 2 && userData[1].equals(username)) {
                    return userData[0];
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error fetching user ID: " + e.getMessage());
        }
        System.out.println("Error: Username not found in users.txt");
        return null;
    }
    
    private String fetchAssignedRunner(String orderID) {
    try (BufferedReader reader = new BufferedReader(new FileReader(TASKS_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");
            if (data.length >= 2 && data[0].equals(orderID)) {
                return data[1]; // Runner ID is at index 1
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error fetching assigned runner: " + e.getMessage());
    }
    return null; // If no match found
}

    
    private void logAcceptedTask(String orderID, String runnerID, String vendorID, String fee) {
        if (orderID == null || runnerID == null || vendorID == null || fee == null) {
            JOptionPane.showMessageDialog(null, "Invalid task data");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TASKS_FILE, true))) {
            String taskEntry = String.join(";", orderID, runnerID, vendorID, fee, "Accepted");
            bw.write(taskEntry + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error logging accepted task: " + e.getMessage());
        }
    }

    private void updateDeliveryTaskStatus(String orderID, String newStatus) {
        if (orderID == null || newStatus == null) {
            JOptionPane.showMessageDialog(null, "Invalid order ID or status");
            return;
        }

        List<String> updatedTasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(TASKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 5 && data[0].trim().equals(orderID.trim())) {
                    data[4] = newStatus;
                    line = String.join(";", data);
                }
                updatedTasks.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading delivery tasks: " + e.getMessage());
            return;
        }

        if (updatedTasks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: No matching tasks found to update.");
            }

    }

    private void acceptTask() {
        int selectedRow = TasksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to accept.");
            return;
        }

        // Get task details from table
        String orderID = (String) TasksTable.getValueAt(selectedRow, 0);
        String customerID = (String) TasksTable.getValueAt(selectedRow, 1);
        String vendorID = (String) TasksTable.getValueAt(selectedRow, 2);
        String fee = (String) TasksTable.getValueAt(selectedRow, 3);

        // Fetch logged-in runner's ID
        String runnerID = fetchUserID(LoginMenu.username);
        if (runnerID == null) {
            JOptionPane.showMessageDialog(this, "Error: Unable to retrieve runner ID.");
            return;
        }

        // Read all orders and update the selected one
        List<String> updatedOrders = new ArrayList<>();
        boolean taskUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(orderID + ";")) {
                    // Update the order's runner and status
                    String[] data = line.split(";");
                    if (data.length >= 9) {
                        data[4] = runnerID;  // Assign runner
                        data[5] = "Accepted"; // Update status
                        line = String.join(";", data);
                        taskUpdated = true;
                    }
                }
                updatedOrders.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading orders: " + e.getMessage());
            return;
        }

        // Write the updated orders back to the file
        if (taskUpdated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
                for (String order : updatedOrders) {
                    writer.write(order + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error updating order status: " + e.getMessage());
                return;
            }

            // Move task to Accepted Table
            DefaultTableModel acceptedModel = (DefaultTableModel) AcceptedTable.getModel();
            acceptedModel.addRow(new Object[]{orderID, customerID, vendorID, fee, "Accepted"});

            // Remove from TasksTable
            ((DefaultTableModel) TasksTable.getModel()).removeRow(selectedRow);

            JOptionPane.showMessageDialog(this, "Task Accepted!");
            reloadTasks();
            reloadAcceptedTasks();
        } else {
            JOptionPane.showMessageDialog(this, "Error: Task was not found or could not be updated.");
        }
    }



    private String getOrderStatus(String orderID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("Review:")) {
                    String[] data = line.split(";");
                    if (data.length >= 6 && data[0].equals(orderID)) {
                        return data[5];
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading order status: " + e.getMessage());
        }
        return "Unknown";
    }
    private List<String> getOrderReviews(String orderID) {
        List<String> reviews = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            boolean isReview = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ORD") && line.contains(orderID)) {
                    isReview = true;  // Start collecting reviews after finding the order ID
                } else if (isReview && line.startsWith("Review:")) {
                    // Remove the rating number (e.g., ";5") and keep only the review text
                    String reviewText = line.replaceAll(";[0-9]$", "");  
                    reviews.add(reviewText.replace("Review: ", "")); 
                } else if (line.startsWith("ORD") && !line.contains(orderID)) {
                    isReview = false; // Stop collecting when a new order starts
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading reviews: " + e.getMessage());
        }
        return reviews;
    }

    public void loadTaskHistory(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Clear existing rows

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ORD")) { // Process only order lines
                    String[] data = line.split(";");
                    if (data.length >= 9 && "Delivered".equalsIgnoreCase(data[5])) {
                        String orderID = data[0];  // Task ID
                        String customerID = data[1]; // Customer ID
                        String vendorID = data[2]; // Vendor ID
                        String fee = data[7]; // Fee
                        String rating = data[8];

                        tableModel.addRow(new Object[]{orderID, customerID, vendorID, fee, rating});
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading task history: " + e.getMessage());
        }
    }


    
    private void updateTaskStatus(String orderID, String runnerID, String newStatus, double rating) {
        List<String> updatedOrders = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ORD")) {
                    String[] data = line.split(";");
                    if (data.length >= 9 && data[0].trim().equals(orderID.trim())) {
                        data[4] = runnerID;
                        data[5] = newStatus;
                        data[8] = String.valueOf(rating);  // Set rating in index 8
                        line = String.join(";", data);
                        found = true;
                    }
                }
                updatedOrders.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading orders: " + e.getMessage());
            return;
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "Error: Task ID not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
            for (String line : updatedOrders) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error updating order status: " + e.getMessage());
        }
    }




    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TasksTable = new javax.swing.JTable();
        Accept = new javax.swing.JButton();
        Reject = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        AcceptedTable = new javax.swing.JTable();
        Delivered = new javax.swing.JButton();
        PickUp = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TasksHistory = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(225, 254, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TasksTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Order ID", "Customer ID", "Vendor ID", "Fee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TasksTable);
        if (TasksTable.getColumnModel().getColumnCount() > 0) {
            TasksTable.getColumnModel().getColumn(0).setResizable(false);
            TasksTable.getColumnModel().getColumn(1).setResizable(false);
            TasksTable.getColumnModel().getColumn(2).setResizable(false);
            TasksTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 510, 240));

        Accept.setText("Accept");
        Accept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AcceptActionPerformed(evt);
            }
        });
        jPanel1.add(Accept, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 290, -1, -1));

        Reject.setText("Reject");
        Reject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RejectActionPerformed(evt);
            }
        });
        jPanel1.add(Reject, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 290, -1, -1));

        AcceptedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Order ID", "Customer ID", "Vendor ID", "Fee", "Status"
            }
        ));
        jScrollPane3.setViewportView(AcceptedTable);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 500, 170));

        Delivered.setText("Delivered");
        Delivered.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeliveredActionPerformed(evt);
            }
        });
        jPanel1.add(Delivered, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 500, -1, -1));

        PickUp.setText("Picked UP");
        PickUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PickUpActionPerformed(evt);
            }
        });
        jPanel1.add(PickUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 500, -1, -1));

        jTabbedPane1.addTab("Tasks", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        TasksHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Task ID", "Customer ID", "Vendor ID", "Fee", "Rating"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TasksHistory);
        if (TasksHistory.getColumnModel().getColumnCount() > 0) {
            TasksHistory.getColumnModel().getColumn(0).setResizable(false);
            TasksHistory.getColumnModel().getColumn(1).setResizable(false);
            TasksHistory.getColumnModel().getColumn(2).setResizable(false);
            TasksHistory.getColumnModel().getColumn(3).setResizable(false);
            TasksHistory.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(235, 235, 235)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(355, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tasks History", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1042, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Revenue Dashboard", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RejectActionPerformed
        int selectedRow = TasksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to reject.");
            return;
        }

        // Get Order ID
        String orderID = (String) TasksTable.getValueAt(selectedRow, 0);

        // Read and update the orders file
        List<String> updatedOrders = new ArrayList<>();
        boolean taskUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(orderID + ";")) {
                    String[] data = line.split(";");
                    if (data.length >= 9 && data[5].equalsIgnoreCase("Pending")) {
                        data[5] = "Rejected"; // Change status to Rejected
                        line = String.join(";", data);
                        taskUpdated = true;
                    }
                }
                updatedOrders.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading orders: " + e.getMessage());
            return;
        }

        // Write updated content to orders.txt
        if (taskUpdated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
                for (String order : updatedOrders) {
                    writer.write(order + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error updating order status: " + e.getMessage());
                return;
            }

            // Remove from Tasks Table
            ((DefaultTableModel) TasksTable.getModel()).removeRow(selectedRow);

            // Reload Tasks Table to reflect the rejection
            reloadTasks();

            JOptionPane.showMessageDialog(this, "Task has been Rejected!");
        } else {
            JOptionPane.showMessageDialog(this, "Error: Task was not found or is already processed.");
        }



    }//GEN-LAST:event_RejectActionPerformed

    private void AcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcceptActionPerformed
                                            
        acceptTask();
        reloadTasks(); 
        reloadAcceptedTasks(); 


    }//GEN-LAST:event_AcceptActionPerformed

    private void DeliveredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeliveredActionPerformed
        int selectedRow = AcceptedTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as Delivered.");
            return;
        }

        String orderID = (String) AcceptedTable.getValueAt(selectedRow, 0);
        String vendorID = (String) AcceptedTable.getValueAt(selectedRow, 2);
        String fee = (String) AcceptedTable.getValueAt(selectedRow, 3);
        String status = (String) AcceptedTable.getValueAt(selectedRow, 4);

        if (!"Picked Up".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "You must pick up the task before marking it as Delivered.");
            return;
        }

        double rating = calculateOrderRating(orderID);

        updateTaskStatus(orderID, fetchUserID(LoginMenu.username), "Delivered", rating);

        loadTaskHistory(HistoryModel);

        ((DefaultTableModel) AcceptedTable.getModel()).removeRow(selectedRow);

        JOptionPane.showMessageDialog(this, "Task marked as Delivered!");
    }//GEN-LAST:event_DeliveredActionPerformed

    private double calculateOrderRating(String orderID) {
        double totalRating = 0;
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            boolean isReview = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ORD") && line.contains(orderID)) {
                    isReview = true;
                } else if (isReview && line.startsWith("Review:")) {
                    String[] parts = line.split(";");
                    if (parts.length == 2) {
                        try {
                            totalRating += Double.parseDouble(parts[1]);
                            count++;
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid rating format: " + parts[1]);
                        }
                    }
                } else if (line.startsWith("ORD") && !line.contains(orderID)) {
                    isReview = false;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading reviews: " + e.getMessage());
        }

        return count == 0 ? 0 : totalRating / count;
    }
    


    private void PickUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PickUpActionPerformed
        int selectedRow = AcceptedTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as Picked Up.");
            return;
        }

        String orderID = (String) AcceptedTable.getValueAt(selectedRow, 0);
        String runnerID = fetchUserID(LoginMenu.username);

        if (runnerID == null) {
            JOptionPane.showMessageDialog(this, "Error: Unable to retrieve runner ID.");
            return;
        }

        // Read and update the orders file
        List<String> updatedOrders = new ArrayList<>();
        boolean taskUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(orderID + ";")) {
                    String[] data = line.split(";");
                    if (data.length >= 9 && data[4].equals(runnerID) && data[5].equals("Accepted")) {
                        data[5] = "Picked Up"; // Change status
                        line = String.join(";", data);
                        taskUpdated = true;
                    }
                }
                updatedOrders.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading orders: " + e.getMessage());
            return;
        }

        // Write updated content to orders.txt
        if (taskUpdated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
                for (String order : updatedOrders) {
                    writer.write(order + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error updating order status: " + e.getMessage());
                return;
            }

            // Update the table's status column
            AcceptedTable.setValueAt("Picked Up", selectedRow, 4);

            JOptionPane.showMessageDialog(this, "Task marked as Picked Up!");
        } else {
            JOptionPane.showMessageDialog(this, "Error: Task was not found or is not assigned to you.");
        }
    }//GEN-LAST:event_PickUpActionPerformed
    



    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new RunnerMenu().setVisible(true);
        });
    }
  
   
   




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Accept;
    private javax.swing.JTable AcceptedTable;
    private javax.swing.JButton Delivered;
    private javax.swing.JButton PickUp;
    private javax.swing.JButton Reject;
    private javax.swing.JTable TasksHistory;
    private javax.swing.JTable TasksTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
