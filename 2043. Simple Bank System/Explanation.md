## 🧠 **Explanation**

### 🔍 Problem Understanding

We need to **simulate a banking system** that performs three operations safely:

1. **Withdraw**
2. **Deposit**
3. **Transfer**

Each operation must follow two important **validity rules**:

* The **account number(s)** used must exist (between `1` and `n`).
* The **money** withdrawn or transferred must **not exceed** the balance of the source account.

If a transaction is valid → return `true`,
If not valid → return `false`.

---

### ⚙️ **Approach (How to solve)**

1. **Store balances** in a `long[]` array (since money values can be very large — up to `10¹²`).
2. Implement each operation (`withdraw`, `deposit`, and `transfer`) as a separate method.
3. Before performing any transaction:

   * **Check if the account(s) exist** → use helper method `isValidAccount()`.
   * For withdraw or transfer → **check sufficient balance**.
4. Update balances accordingly:

   * **Withdraw**: `balance[account - 1] -= money`
   * **Deposit**: `balance[account - 1] += money`
   * **Transfer**: combine both steps (withdraw from one, deposit to another).

We use `account - 1` because the problem uses **1-based account numbering**, but Java arrays are **0-based**.

---

### 💡 **Why this works**

* The problem only requires **simple state updates** (no concurrency or complex data structures).
* We ensure **validity checks first** — preventing invalid index access or negative balances.
* Since all operations are O(1), this design is very efficient even with 10⁴ operations.

---

## 💻 **Java Solution**

```java
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
```

---

## 🧩 **Example Walkthrough**

**Input**

```text
Bank bank = new Bank([10, 100, 20, 50, 30]);
bank.withdraw(3, 10);
bank.transfer(5, 1, 20);
bank.deposit(5, 20);
bank.transfer(3, 4, 15);
bank.withdraw(10, 50);
```

**Step-by-step**

| Operation        | Description             | Result | Balances          |
| ---------------- | ----------------------- | ------ | ----------------- |
| withdraw(3,10)   | Valid (20≥10)           | ✅      | [10,100,10,50,30] |
| transfer(5,1,20) | Valid                   | ✅      | [30,100,10,50,10] |
| deposit(5,20)    | Valid                   | ✅      | [30,100,10,50,30] |
| transfer(3,4,15) | Invalid (10<15)         | ❌      | [30,100,10,50,30] |
| withdraw(10,50)  | Invalid (no account 10) | ❌      | [30,100,10,50,30] |

**Output**

```
[null, true, true, true, false, false]
```

---

## 🧾 **Complexity Analysis**

| Operation                     | Time | Space |
| ----------------------------- | ---- | ----- |
| Withdraw / Deposit / Transfer | O(1) | O(1)  |

---

