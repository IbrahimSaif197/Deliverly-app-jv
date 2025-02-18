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

    
    public RunnerMenu() {
        initComponents();
        this.tasksModel = (DefaultTableModel) TasksTable.getModel();
        this.acceptedModel = (DefaultTableModel) AcceptedTable.getModel();
        reloadTasks();
        reloadAcceptedTasks();
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

            try (BufferedReader br = new BufferedReader(new FileReader(TASKS_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length >= 4 && data[1].equals(loggedInRunnerID)) {
                        String status = getOrderStatus(data[0]);
                        if (status != null && status.equalsIgnoreCase("Accepted") && 
                         !status.equalsIgnoreCase("Completed") && !status.equalsIgnoreCase("Canceled")){
                            acceptedModel.addRow(new Object[]{
                                data[0], // Order ID
                                data[2], // Vendor ID
                                data[3]  // Fee
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

    private void updateTaskStatus(String orderID, String runnerID, String newStatus) {
        if (runnerID == null) {
           JOptionPane.showMessageDialog(null, "Error: Cannot update task, runner ID is missing.");
            return;
}

        List<String> updatedOrders = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Review:")) {
                    updatedOrders.add(line);
                    continue;
                }

                String[] data = line.split(";");
                if (data.length >= 8 && data[0].trim().equals(orderID.trim())) {
                    data[4] = runnerID;
                    data[5] = newStatus;
                    line = String.join(";", data);
                }
                updatedOrders.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading orders: " + e.getMessage());
            return;
        }

        boolean found = false;
            for (String line : updatedOrders) {
                if (line.startsWith(orderID + ";")) {
                found = true;
                break;
    }
}

                if (!found) {
            JOptionPane.showMessageDialog(null, "Error: Task ID not found.");
}}
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
        int selectedRow = TasksTable.getSelectedRow(); // Get selected row from the Tasks table
        if (selectedRow != -1) {
        String taskDetails = (String) TasksTable.getValueAt(selectedRow, 0);

        // Move the task to AcceptedTable
        DefaultTableModel acceptedModel = (DefaultTableModel) AcceptedTable.getModel();
        acceptedModel.addRow(new Object[]{taskDetails});

        // Remove the task from TasksTable
        ((DefaultTableModel) TasksTable.getModel()).removeRow(selectedRow);

        // Update task status in the text file
        updateTaskStatusInFile(taskDetails, "accepted");

    }   else {
        JOptionPane.showMessageDialog(this, "Please select a task to accept.");
    }
}


    private void completeTask() {
        int selectedRow = AcceptedTable.getSelectedRow();
        if (selectedRow != -1) {
        String taskDetails = (String) AcceptedTable.getValueAt(selectedRow, 0);
        updateTaskStatusInFile(taskDetails, "completed");

        // Move task to history
        DefaultTableModel historyModel = (DefaultTableModel) TasksHistory.getModel();
        historyModel.addRow(new Object[]{taskDetails});

        // Remove from accepted tasks
        ((DefaultTableModel) AcceptedTable.getModel()).removeRow(selectedRow);
    }   else {
        JOptionPane.showMessageDialog(this, "Please select a task to complete.");
    }
}

    private void cancelTask() {
        int selectedRow = AcceptedTable.getSelectedRow();
        if (selectedRow != -1) {
        String taskDetails = (String) AcceptedTable.getValueAt(selectedRow, 0);
        updateTaskStatusInFile(taskDetails, "canceled");

        // Remove from accepted tasks
        ((DefaultTableModel) AcceptedTable.getModel()).removeRow(selectedRow);
    }   else {
        JOptionPane.showMessageDialog(this, "Please select a task to cancel.");
    }
}
    private void updateTaskStatusInFile(String taskDetails, String status) {
    File file = new File("tasks.txt");
    List<String> updatedTasks = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.equals(taskDetails)) {
                if (!status.equals("rejected")) { 
                    updatedTasks.add(line + " - " + status);
                }
            } else {
                updatedTasks.add(line);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
        for (String task : updatedTasks) {
            bw.write(task);
            bw.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
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

    public void loadTaskHistory(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        int completedCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(TASKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 4) {
                    String taskID = data[0];
                    String vendorID = data[2];
                    String fee = data[3];
                    String status = getOrderStatus(taskID);

                    if (status != null && status.equalsIgnoreCase("Completed")) {
                        tableModel.addRow(new Object[]{taskID, vendorID, fee});
                        completedCount++;
                    }
                }
            }
            JOptionPane.showMessageDialog(this, 
                "Total Completed Tasks: " + completedCount, 
                "Task Summary", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading task history: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
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
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TasksHistory = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
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
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "OrderID", "CustomerID", "VendorID", "Fee"
            }
        ));
        jScrollPane3.setViewportView(AcceptedTable);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 500, 170));

        jButton4.setText("Complete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 500, -1, -1));

        jButton5.setText("Cancel");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 500, -1, -1));

        jTabbedPane1.addTab("Tasks", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jButton3.setText("Load Task History");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        TasksHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "TaskID", "VendorID", "Fee"
            }
        ));
        jScrollPane2.setViewportView(TasksHistory);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(370, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(23, 23, 23))
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
        if (selectedRow != -1) {
        String taskDetails = (String) TasksTable.getValueAt(selectedRow, 0); // Assuming first column has task details
        updateTaskStatusInFile(taskDetails, "rejected");
        ((DefaultTableModel) TasksTable.getModel()).removeRow(selectedRow);
    }   else {
        JOptionPane.showMessageDialog(this, "Please select a task to reject.");
    }
    }//GEN-LAST:event_RejectActionPerformed

    private void AcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcceptActionPerformed
                                            
        acceptTask();
        reloadAcceptedTasks();

    }//GEN-LAST:event_AcceptActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultTableModel historyModel = (DefaultTableModel) TasksHistory.getModel();
        loadTaskHistory(historyModel);
    
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        completeTask();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        cancelTask();
    }//GEN-LAST:event_jButton5ActionPerformed
    



    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new RunnerMenu().setVisible(true);
        });
    }
  
   
   




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Accept;
    private javax.swing.JTable AcceptedTable;
    private javax.swing.JButton Reject;
    private javax.swing.JTable TasksHistory;
    private javax.swing.JTable TasksTable;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
