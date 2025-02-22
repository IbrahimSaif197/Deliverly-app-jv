package com.deliverly.main.customer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

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
    jLabel2.setText("Total: $" + totalAmount);
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        cashRadioButton = new javax.swing.JRadioButton();
        cardRadioButton = new javax.swing.JRadioButton();
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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(129, 129, 129)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(cashRadioButton)
                                    .addGap(37, 37, 37)
                                    .addComponent(cardRadioButton))
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cardRadioButton)
                    .addComponent(cashRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(59, 59, 59))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void confirmPaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmPaymentButtonActionPerformed
        String paymentMethod = cashRadioButton.isSelected() ? "Cash" : "Card";
    String date = java.time.LocalDate.now().toString();

    String cardNumber = "N/A";
    String cvc = "N/A";
    String cardDate = "N/A";

    if (!cashRadioButton.isSelected() && !cardRadioButton.isSelected()) {
        JOptionPane.showMessageDialog(this, "Please select a payment method!");
        return;
    }

    if (paymentMethod.equals("Card")) {
        cardNumber = jTextField1.getText().trim();
        cvc = jTextField2.getText().trim();
        cardDate = jTextField3.getText().trim();

        if (cardNumber.isEmpty() || cvc.isEmpty() || cardDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all card details!");
            return;
        }

        if (!cvc.matches("\\d{3}")) {
            JOptionPane.showMessageDialog(this, "Invalid CVC! Enter a 3-digit number.");
            return;
        }

        if (!cardNumber.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(this, "Invalid Card Number! Enter a 16-digit number.");
            return;
        }
        if (!cardDate.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Invalid Expiry Date! Enter in MMYY format.");
            return;
        }
    }

    Customer customer = new Customer(customerID);  
    String[] userDetails = customer.getUserIDAndUsername(customerID); 

    String correctUserID = userDetails[0]; 
    String correctUsername = userDetails[1]; 

    if (correctUserID.equals("ERROR")) {
        JOptionPane.showMessageDialog(this, "Error retrieving Customer ID. Payment not saved.");
        return;
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/receipts.txt", true))) {
        bw.newLine();
        bw.write(correctUserID + ";" + correctUsername + ";" + cardNumber + ";" + cvc + ";" + date + ";" + cardDate + ";" + totalAmount);
        JOptionPane.showMessageDialog(this, "Payment Successful! Saving order...");

        saveOrder();

        this.dispose(); 
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving receipt: " + e.getMessage());
    }
}

private void saveOrder() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/data/orders.txt", true))) {
        bw.newLine(); 
        bw.write(orderID + ";" + customerID + ";" + vendorID + ";" + orderedItems + ";"
                 + java.time.LocalDate.now() + ";" + "Pending" + ";" + deliveryMethod + ";" + totalAmount);
        bw.flush();
        JOptionPane.showMessageDialog(this, "Order placed successfully!");
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
