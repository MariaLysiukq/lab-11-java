package model;

import exceptions.InvalidAmountException;
import exceptions.InvalidPasswordException;

public abstract class BankAccount {

    private String customerName;
    private Long accountNumber;
    private Long userId;
    private String userPassword;
    private Double accountBalance;

    public BankAccount(String customerName, Long accountNumber, Long userId, String userPassword) {
        this.customerName = customerName;
        this.accountNumber = accountNumber;
        this.userId = userId;
        setUserPassword(userPassword);
        this.accountBalance = 0.0;
    }

    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    public static void validatePassword(String password) {
        if (password == null || password.length() < 8) throw new InvalidPasswordException("Password must be at least 8 characters long");
        if (password.chars().noneMatch(Character::isDigit)) throw new InvalidPasswordException("Password must contain at least one digit");
    }

    public boolean checkPassword(String password) {
        return userPassword.equals(password);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        validatePassword(userPassword);
        this.userPassword = userPassword;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        if (accountBalance == null || accountBalance < 0) throw new InvalidAmountException("Account balance cannot be negative: " + accountBalance);
        this.accountBalance = accountBalance;
    }
}
