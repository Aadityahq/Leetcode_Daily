class Bank {
    private long[] balance;  // To store balances of all accounts

    // Constructor to initialize the bank with account balances
    public Bank(long[] balance) {
        this.balance = balance;
    }

    // Helper function to check if an account number is valid
    private boolean isValidAccount(int account) {
        return account >= 1 && account <= balance.length;
    }

    // Withdraw money from an account
    public boolean withdraw(int account, long money) {
        if (!isValidAccount(account)) return false;
        if (balance[account - 1] < money) return false;
        balance[account - 1] -= money;
        return true;
    }

    // Deposit money into an account
    public boolean deposit(int account, long money) {
        if (!isValidAccount(account)) return false;
        balance[account - 1] += money;
        return true;
    }

    // Transfer money between two accounts
    public boolean transfer(int account1, int account2, long money) {
        if (!isValidAccount(account1) || !isValidAccount(account2)) return false;
        if (balance[account1 - 1] < money) return false;
        balance[account1 - 1] -= money;
        balance[account2 - 1] += money;
        return true;
    }
}
