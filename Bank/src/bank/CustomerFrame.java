package bank;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class CustomerFrame extends javax.swing.JFrame {
    User customer;
    DBManager dbManager;
    /**
     * Creates new form CustomerFrame
     */
    public CustomerFrame(User customer) {
        initComponents();
        this.customer = customer;
        this.dbManager = new DBManager();
        this.loadAccounts();
        
        LCustomerName.setText(this.customer.getFirstname() + " " + this.customer.getLastname());
        
        TAccounts.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if(event.getValueIsAdjusting()) return;
            int selectedRow = TAccounts.getSelectedRow();
            if(selectedRow > -1 == false) return;
            int customerId = (int)TAccounts.getValueAt(selectedRow, 0);
            int accountId = (int)TAccounts.getValueAt(selectedRow, 2);
            if(customerId > 0 == false) return;
            this.accountSelected(accountId);
        });
    }
    
    public void accountSelected(int accountId) {
        BDeposit.setEnabled(true);
        BWithdrawal.setEnabled(true);
        
        loadCreditRequests(accountId);
        BRequestCredit.setEnabled(true);
    }
    
    private void loadAccounts() {
        DefaultTableModel table = (DefaultTableModel)TAccounts.getModel();
        table.setRowCount(0);
        
        customer.fetchAccounts();
        ArrayList<Account> accounts = customer.getAccounts();
        for (Account account : accounts) {
            table.addRow(new Object[]{
                customer.getId(),
                customer.getUsername(),
                account.getId(),
                account.getName(),
                account.getBalance(),
            });
        }
        
        BDeposit.setEnabled(false);
        BWithdrawal.setEnabled(false);
        
        BRequestCredit.setEnabled(false);
        DefaultTableModel creditRequestsTable = (DefaultTableModel)TCreditRequests.getModel();
        creditRequestsTable.setRowCount(0);
    }
    
    private void loadCreditRequests(int accountId) {
        DefaultTableModel table = (DefaultTableModel)TCreditRequests.getModel();
        table.setRowCount(0);
        
        customer.fetchAccounts();
        Account account = customer.getAccountById(accountId);
        
        ArrayList<Transaction> transactions = account.getCreditTransactions();
        
        Collections.sort(transactions);
        
        for (Transaction transaction : transactions) {
            String status = "";
            switch(transaction.getStatus()) {
                case "pending" -> status = "bekliyor";
                case "approved" -> status = "onaylandı";
                case "rejected" -> status = "reddedildi";
            }
            
            table.addRow(new Object[]{
                transaction.getId(),
                account.getId(),
                account.getName(),
                transaction.getAmount(),
                status,
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TAccounts = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TFAmount = new javax.swing.JTextField();
        BDeposit = new javax.swing.JButton();
        BWithdrawal = new javax.swing.JButton();
        LCustomerName = new javax.swing.JLabel();
        BRefresh = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TCreditRequests = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        TFCreditAmount = new javax.swing.JTextField();
        BRequestCredit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Banka - Müşteri Paneli");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TAccounts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kullanıcı No", "Kullanıcı Adı", "Hesap No", "Hesap Adı", "Bakiye"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TAccounts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TAccounts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(TAccounts);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Banka Hesapları");

        jLabel9.setText("Para Miktarı");

        BDeposit.setText("Yatır");
        BDeposit.setEnabled(false);
        BDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDepositActionPerformed(evt);
            }
        });

        BWithdrawal.setText("Çek");
        BWithdrawal.setEnabled(false);
        BWithdrawal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BWithdrawalActionPerformed(evt);
            }
        });

        LCustomerName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LCustomerName.setText("customerName");

        BRefresh.setText("Yenile");
        BRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TFAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BDeposit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BWithdrawal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LCustomerName)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(LCustomerName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(BRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(TFAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BDeposit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BWithdrawal)))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TCreditRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Başvuru No", "Hesap No", "Hesap Adı", "Kredi Miktarı", "Durum"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TCreditRequests.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TCreditRequests.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(TCreditRequests);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Kredi Başvuruları");

        jLabel10.setText("Kredi Miktarı");

        BRequestCredit.setText("Başvur");
        BRequestCredit.setEnabled(false);
        BRequestCredit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRequestCreditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TFCreditAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BRequestCredit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel8))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(TFCreditAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRequestCredit))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDepositActionPerformed
        float amount = 0;
        try {
            amount = Float.parseFloat(TFAmount.getText());
        } catch (Exception e) {}

        if(amount <= 0) {
            JOptionPane.showMessageDialog(null, "Lütfen geçerli bir miktar giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = TAccounts.getSelectedRow();
        int accountId = (int)TAccounts.getValueAt(selectedRow, 2);
        customer.fetchAccounts();
        Account account = customer.getAccountById(accountId);

        Boolean status = account.deposit(amount);
        if(status) {
            TFAmount.setText("");
            this.loadAccounts();
            TAccounts.setRowSelectionInterval(selectedRow, selectedRow);
        }
    }//GEN-LAST:event_BDepositActionPerformed

    private void BWithdrawalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BWithdrawalActionPerformed
        float amount = 0;
        try {
            amount = Float.parseFloat(TFAmount.getText());
        } catch (Exception e) {}

        if(amount <= 0) {
            JOptionPane.showMessageDialog(null, "Lütfen geçerli bir miktar giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = TAccounts.getSelectedRow();
        int accountId = (int)TAccounts.getValueAt(selectedRow, 2);
        customer.fetchAccounts();
        Account account = customer.getAccountById(accountId);

        float balance = account.getBalance();
        if(amount > balance) {
            JOptionPane.showMessageDialog(null, "Çekilen miktar bakiyeyi geçemez!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Boolean status = account.withdrawal(amount);
        if(status) {
            TFAmount.setText("");
            this.loadAccounts();
            TAccounts.setRowSelectionInterval(selectedRow, selectedRow);
        }
    }//GEN-LAST:event_BWithdrawalActionPerformed

    private void BRequestCreditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRequestCreditActionPerformed
        float amount = 0;
        try {
            amount = Float.parseFloat(TFCreditAmount.getText());
        } catch (Exception e) {}

        if(amount <= 0) {
            JOptionPane.showMessageDialog(null, "Lütfen geçerli bir miktar giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = TAccounts.getSelectedRow();
        int accountId = (int)TAccounts.getValueAt(selectedRow, 2);
        customer.fetchAccounts();
        Account account = customer.getAccountById(accountId);

        Boolean status = account.requestCredit(amount);
        if(status) {
            TFCreditAmount.setText("");
            this.loadCreditRequests(accountId);
        }
    }//GEN-LAST:event_BRequestCreditActionPerformed

    private void BRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRefreshActionPerformed
        this.loadAccounts();
    }//GEN-LAST:event_BRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BDeposit;
    private javax.swing.JButton BRefresh;
    private javax.swing.JButton BRequestCredit;
    private javax.swing.JButton BWithdrawal;
    private javax.swing.JLabel LCustomerName;
    private javax.swing.JTable TAccounts;
    private javax.swing.JTable TCreditRequests;
    private javax.swing.JTextField TFAmount;
    private javax.swing.JTextField TFCreditAmount;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
