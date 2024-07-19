package bank;

public class Transaction implements Comparable {
    private Account account;
    public int id;
    public int accountId;
    public String type;
    public float amount;
    public String status;

    public Transaction(Account account, int id, int accountId, String type, float amount, String status) {
        this.account = account;
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.status = status;
    }
    
    public Account getAccount() {
        return this.account;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getAccountId() {
        return this.accountId;
    }
    
    public String getType() {
        return this.type;
    }
    
    public float getAmount() {
        return this.amount;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public Boolean isApproved() {
        return this.status.equals("approved");
    }
    
    public Boolean isPending() {
        return this.status.equals("pending");
    }
    
    public Boolean isRejected() {
        return this.status.equals("rejected");
    }
    
    public Boolean isDeposit() {
        return this.type.equals("deposit");
    }
    
    public Boolean isWithdrawal() {
        return this.type.equals("withdrawal");
    }
    
    public Boolean isCredit() {
        return this.type.equals("credit");
    }
    
    @Override
    public int compareTo(Object o) {
        return ((Transaction)o).getId() - this.getId();
    }
}
