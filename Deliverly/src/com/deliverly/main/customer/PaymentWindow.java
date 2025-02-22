package com.deliverly.main.customer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;

public class PaymentWindow extends javax.swing.JFrame {
    private String customerID;
    private double totalAmount;
    private String orderID;
    private String vendorID;
    private String orderedItems;
    private String deliveryMethod;

    public PaymentWindow(String customerID, double totalAmount, String orderID, String vendorID, String orderedItems, String deliveryMethod) {
    this.customerID = customerID;
    this.totalAmount = totalAmount;
    this.orderID = orderID;
    this.vendorID = vendorID;
    this.orderedItems = orderedItems;
    this.deliveryMethod = deliveryMethod;
    initComponents();
    DecimalFormat df = new DecimalFormat("#.##");
    jLabel2.setText("Total: Rm" + df.format(totalAmount));
}
private double getUserCredit(String customerID) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/data/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(";");
                if (userDetails.length >= 8 && userDetails[0].equals(customerID)) {
                    return Double.parseDouble(userDetails[7]);  
                }
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving user credit: " + e.getMessage());
        }
        return 0.0; 
    }

private void updateUserCredit(String customerID, double newCredit) {
        File usersFile = new File("src/data/users.txt");
        File tempFile = new File("src/data/users_temp.txt");
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(usersFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(";");
                if (userDetails.length >= 8 && userDetails[0].equals(customerID)) {
                    userDetails[7] = String.format("%.2f", newCredit);  
                    line = String.join(";", userDetails);
                    updated = true;
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating user credit: " + e.getMessage());
            return;
        }

        if (updated) {
            if (usersFile.delete() && tempFile.renameTo(usersFile)) {
                JOptionPane.showMessageDialog(this, "Credit updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error replacing the file!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Customer ID not found. No changes made.");
            tempFile.delete(); 
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        cashRadioButton = new javax.swing.JRadioButton();
        cardRadioButton = new javax.swing.JRadioButton();
        creditRadioButton = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Choose Payment Method");

        buttonGroup1.add(cashRadioButton);
        cashRadioButton.setText("Cash");

        buttonGroup1.add(cardRadioButton);
        cardRadioButton.setText("Card");

        buttonGroup1.add(creditRadioButton);
        creditRadioButton.setText("Credit");

        jButton1.setText("Confirm Payment");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmPaymentButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Total Amount: ");

        jTextField1.setText("Card Number");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });

        jTextField2.setText("CVC");
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField2MouseClicked(evt);
            }
        });

        jTextField3.setText("Card Date");
        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(108, 108, 108))
            .addGroup(layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(cashRadioButton)
                            .addGap(18, 18, 18)
                            .addComponent(cardRadioButton)
                            .addGap(18, 18, 18)
                            .addComponent(creditRadioButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel2)))
                .addContainerGap(83, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cardRadioButton)
                    .addComponent(cashRadioButton)
                    .addComponent(creditRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void confirmPaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmPaymentButtonActionPerformed
    String paymentMethod;
    
        if (cashRadioButton.isSelected()) {
            paymentMethod = "Cash";
        } else if (cardRadioButton.isSelected()) {
            paymentMethod = "Card";
        } else if (creditRadioButton.isSelected()) {
            paymentMethod = "Credit";
        } else {
            JOptionPane.showMessageDialog(this, "Please select a payment method!");
            return;
        }

        if (paymentMethod.equals("Credit")) {
            double availableCredit = getUserCredit(customerID);
            if (availableCredit < totalAmount) {
                JOptionPane.showMessageDialog(this, "Insufficient credit! Available: RM " + String.format("%.2f", availableCredit));
                return;
            }
            updateUserCredit(customerID, availableCredit - totalAmount);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/receipts.txt", true))) {
            bw.newLine();
            bw.write(customerID + ";" + paymentMethod + ";" + totalAmount);
            JOptionPane.showMessageDialog(this, "Payment Successful! Saving order...");
            saveOrder();
            this.dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving receipt: " + e.getMessage());
        }
    }

    private void saveOrder() {
        DecimalFormat df = new DecimalFormat("#.##");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/orders.txt", true))) {
            bw.newLine();
            bw.write(orderID + ";" + customerID + ";" + vendorID + ";" + orderedItems + ";"
                + java.time.LocalDate.now() + ";Pending;" + deliveryMethod + ";"
                + df.format(totalAmount) + ";4.0"); // Rating as placeholder
            bw.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving order: " + e.getMessage());
        }

    }//GEN-LAST:event_confirmPaymentButtonActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
     jTextField1.setText("");
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseClicked
     jTextField2.setText("");
    }//GEN-LAST:event_jTextField2MouseClicked

    private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseClicked
     jTextField3.setText("");
    }//GEN-LAST:event_jTextField3MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton cardRadioButton;
    private javax.swing.JRadioButton cashRadioButton;
    private javax.swing.JRadioButton creditRadioButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
