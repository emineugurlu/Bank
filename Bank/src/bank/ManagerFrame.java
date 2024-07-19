package bank;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ManagerFrame extends javax.swing.JFrame {
    User manager;
    DBManager dbManager;
    
    /**
     * Creates new form ManagerFrame
     */
    public ManagerFrame(User manager) {
        initComponents();
        this.manager = manager;
        this.dbManager = new DBManager();
        this.loadBankers();
        this.loadCreditRequests();
        
        TBankers.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if(event.getValueIsAdjusting()) return;
            int selectedRow = TBankers.getSelectedRow();
            if(selectedRow > -1 == false) return;
            int bankerId = (int)TBankers.getValueAt(selectedRow, 0);
            if(bankerId > 0 == false) return;
            this.bankerSelected(bankerId);
        });
        
        TCreditRequests.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if(event.getValueIsAdjusting()) return;
            int selectedRow = TCreditRequests.getSelectedRow();
            if(selectedRow > -1 == false) return;
            int customerId = (int)TCreditRequests.getValueAt(selectedRow, 1);
            int accountId = (int)TCreditRequests.getValueAt(selectedRow, 3);
            int transactionId = (int)TCreditRequests.getValueAt(selectedRow, 0);
            if(transactionId > 0 == false) return;
            this.creditRequestSelected(customerId, accountId, transactionId);
        });
    }
    
    private void bankerSelected(int bankerId) {
        User banker = new User(this.dbManager, bankerId);
        TFId.setText(String.valueOf(banker.getId()));
        TFUsername.setText(banker.getUsername());
        TFPassword.setText(banker.getPassword());
        TFFirstname.setText(banker.getFirstname());
        TFLastname.setText(banker.getLastname());
        BCreateBanker.setEnabled(false);
        BUpdateBanker.setEnabled(true);
        BDeleteBanker.setEnabled(true);
    }
    
    private void creditRequestSelected(int customerId, int accountId, int transactionId) {
        User customer = new User(this.dbManager, customerId);
        Account account = customer.getAccountById(accountId);
        Transaction transaction = account.getTransactionById(transactionId);
        
        if(transaction.isPending()) {
            BApproveCredit.setEnabled(true);
            BRejectCredit.setEnabled(true);
        } else {
            BApproveCredit.setEnabled(false);
            BRejectCredit.setEnabled(false);
        }
    }
    
    private void loadBankers() {
        DefaultTableModel table = (DefaultTableModel)TBankers.getModel();
        table.setRowCount(0);
        
        ArrayList<User> bankers = this.dbManager.getUsers("banker");
        for (User banker : bankers) {
            table.addRow(new Object[]{
                banker.getId(),
                banker.getUsername(),
                banker.getPassword(),
                banker.getFirstname(),
                banker.getLastname(),
            });
        }
    }
    
    private void loadCreditRequests() {
        ArrayList<Transaction> creditTransactions = new ArrayList<>();   
        
        ArrayList<User> customers = this.dbManager.getUsers("customer");
        for (User customer : customers) {
            ArrayList<Account> accounts = customer.getAccounts();
            for (Account account : accounts) {
                ArrayList<Transaction> transactions = account.getCreditTransactions();
                for (Transaction transaction : transactions) {
                    creditTransactions.add(transaction);
                }
            }
        }
        
        Collections.sort(creditTransactions);
        
        DefaultTableModel table = (DefaultTableModel)TCreditRequests.getModel();
        table.setRowCount(0);
        for (Transaction transaction : creditTransactions) {
            Account account = transaction.getAccount();
            User customer = account.getUser();
            
            String status = "";
            switch(transaction.getStatus()) {
                case "pending" -> status = "bekliyor";
                case "approved" -> status = "onaylandı";
                case "rejected" -> status = "reddedildi";
            }
             
            table.addRow(new Object[]{
                transaction.getId(),
                customer.getId(),
                customer.getUsername(),
                account.getId(),
                account.getName(),
                transaction.getAmount(),
                status,
            });
        }
        
        BApproveCredit.setEnabled(false);
        BRejectCredit.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        BClear = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        TFId = new javax.swing.JTextField();
        TFUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        TFPassword = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TFFirstname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        TFLastname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        BCreateBanker = new javax.swing.JButton();
        BUpdateBanker = new javax.swing.JButton();
        BDeleteBanker = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TBankers = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TCreditRequests = new javax.swing.JTable();
        BApproveCredit = new javax.swing.JButton();
        BRejectCredit = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        BRefreshCredits = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Banka - Müdür Paneli");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        BClear.setText("Temizle");
        BClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BClearActionPerformed(evt);
            }
        });

        jLabel1.setText("ID");

        TFId.setEnabled(false);

        jLabel2.setText("Kullanıcı Adı");

        jLabel3.setText("Parola");

        jLabel4.setText("Ad");

        jLabel5.setText("Soyad");

        BCreateBanker.setText("Ekle");
        BCreateBanker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCreateBankerActionPerformed(evt);
            }
        });

        BUpdateBanker.setText("Güncelle");
        BUpdateBanker.setEnabled(false);
        BUpdateBanker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BUpdateBankerActionPerformed(evt);
            }
        });

        BDeleteBanker.setText("Sil");
        BDeleteBanker.setEnabled(false);
        BDeleteBanker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDeleteBankerActionPerformed(evt);
            }
        });

        TBankers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kullanıcı Adı", "Parola", "Ad", "Soyad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        TBankers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TBankers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(TBankers);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Bankacılar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TFLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TFId, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                .addComponent(TFUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TFPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TFFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BCreateBanker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BUpdateBanker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BDeleteBanker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(TFId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(TFUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TFPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TFFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(TFLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BCreateBanker)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BUpdateBanker)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BDeleteBanker)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TCreditRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Başvuru No", "Kullanıcı No", "Kullanıcı Adı", "Hesap No", "Hesap Adı", "Kredi Miktarı", "Durum"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        jScrollPane1.setViewportView(TCreditRequests);

        BApproveCredit.setText("Onayla");
        BApproveCredit.setEnabled(false);
        BApproveCredit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BApproveCreditActionPerformed(evt);
            }
        });

        BRejectCredit.setText("Reddet");
        BRejectCredit.setEnabled(false);
        BRejectCredit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRejectCreditActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Kredi Başvuruları");

        BRefreshCredits.setText("Yenile");
        BRefreshCredits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRefreshCreditsActionPerformed(evt);
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
                            .addComponent(BApproveCredit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BRejectCredit, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(BRefreshCredits, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(BRefreshCredits)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BApproveCredit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BRejectCredit)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void clearBankerFields() {
        TFId.setText("");
        TFUsername.setText("");
        TFPassword.setText("");
        TFFirstname.setText("");
        TFLastname.setText("");
        BCreateBanker.setEnabled(true);
        BUpdateBanker.setEnabled(false);
        BDeleteBanker.setEnabled(false);
        TBankers.getSelectionModel().clearSelection();
    }
    
    private void BClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BClearActionPerformed
        this.clearBankerFields();
    }//GEN-LAST:event_BClearActionPerformed

    private void BCreateBankerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCreateBankerActionPerformed
        String username = TFUsername.getText();
        String password = TFPassword.getText();
        String firstname = TFFirstname.getText();
        String lastname = TFLastname.getText();
        String role = "banker";
        
        Boolean isUsernameExists = this.dbManager.isUsernameExists(username);
        if(isUsernameExists) {
            JOptionPane.showMessageDialog(null, "Kullanıcı adı zaten kullanılıyor!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Boş alan bırakmayınız!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User banker = new User(this.dbManager, 0, username, password, firstname, lastname, role);
        Boolean status = banker.save();
        if(status) {
            this.loadBankers();
            this.bankerSelected(banker.getId());
        }
    }//GEN-LAST:event_BCreateBankerActionPerformed

    private void BUpdateBankerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BUpdateBankerActionPerformed
        int id = Integer.parseInt(TFId.getText());
        String username = TFUsername.getText();
        String password = TFPassword.getText();
        String firstname = TFFirstname.getText();
        String lastname = TFLastname.getText();
        String role = "banker";
        
        if(username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Boş alan bırakmayınız!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User banker = new User(this.dbManager, id, username, password, firstname, lastname, role);
        Boolean status = banker.save();
        if(status) {
            int selectedRow = TBankers.getSelectedRow();
            this.loadBankers();
            TBankers.setRowSelectionInterval(selectedRow, selectedRow);
        }
    }//GEN-LAST:event_BUpdateBankerActionPerformed

    private void BDeleteBankerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDeleteBankerActionPerformed
        int id = Integer.parseInt(TFId.getText());
    
        User banker = new User(this.dbManager, id);
        Boolean status = banker.delete();
        if(status) {
            this.loadBankers();
            this.clearBankerFields();
        }
    }//GEN-LAST:event_BDeleteBankerActionPerformed

    private void BRefreshCreditsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRefreshCreditsActionPerformed
        this.loadCreditRequests();
    }//GEN-LAST:event_BRefreshCreditsActionPerformed

    private void BApproveCreditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BApproveCreditActionPerformed
        int selectedRow = TCreditRequests.getSelectedRow();
        int customerId = (int)TCreditRequests.getValueAt(selectedRow, 1);
        int accountId = (int)TCreditRequests.getValueAt(selectedRow, 3);
        int transactionId = (int)TCreditRequests.getValueAt(selectedRow, 0);
        
        User customer = new User(this.dbManager, customerId);
        Account account = customer.getAccountById(accountId);

        Boolean status = account.approveCredit(transactionId);
        if (status) {
            this.loadCreditRequests();
            TCreditRequests.setRowSelectionInterval(selectedRow, selectedRow);
        }
    }//GEN-LAST:event_BApproveCreditActionPerformed

    private void BRejectCreditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRejectCreditActionPerformed
        int selectedRow = TCreditRequests.getSelectedRow();
        int customerId = (int)TCreditRequests.getValueAt(selectedRow, 1);
        int accountId = (int)TCreditRequests.getValueAt(selectedRow, 3);
        int transactionId = (int)TCreditRequests.getValueAt(selectedRow, 0);
        
        User customer = new User(this.dbManager, customerId);
        Account account = customer.getAccountById(accountId);

        Boolean status = account.rejectCredit(transactionId);
        if (status) {
            this.loadCreditRequests();
            TCreditRequests.setRowSelectionInterval(selectedRow, selectedRow);
        }
    }//GEN-LAST:event_BRejectCreditActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BApproveCredit;
    private javax.swing.JButton BClear;
    private javax.swing.JButton BCreateBanker;
    private javax.swing.JButton BDeleteBanker;
    private javax.swing.JButton BRefreshCredits;
    private javax.swing.JButton BRejectCredit;
    private javax.swing.JButton BUpdateBanker;
    private javax.swing.JTable TBankers;
    private javax.swing.JTable TCreditRequests;
    private javax.swing.JTextField TFFirstname;
    private javax.swing.JTextField TFId;
    private javax.swing.JTextField TFLastname;
    private javax.swing.JTextField TFPassword;
    private javax.swing.JTextField TFUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
