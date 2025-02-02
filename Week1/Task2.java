
class BankAccount {
	
    private double balance;
    public BankAccount(double balance) {
        this.balance = balance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount + ", Current Balance: " + balance);
    }

    public synchronized void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount + ", Current Balance: " + balance);
        } else {
            System.out.println("Insufficient funds for withdrawal of: " + amount);
        }
    }

    public synchronized double getBalance() {
        return balance;
    }
}

class DepositThread extends Thread {
    private BankAccount account;
    private double amount;

    public DepositThread(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.deposit(amount);
    }
}

class WithdrawThread extends Thread {
    private BankAccount account;
    private double amount;

    public WithdrawThread(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}

public class Task2 {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); 
        Thread user1Deposit = new DepositThread(account, 500); 
        Thread user2Deposit = new DepositThread(account, 700); 
        Thread user1Withdraw = new WithdrawThread(account, 300); 
        Thread user2Withdraw = new WithdrawThread(account, 1500);
        user1Deposit.start();
        user2Deposit.start();
        user1Withdraw.start();
        user2Withdraw.start();
    }
}


