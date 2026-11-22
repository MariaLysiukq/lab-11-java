package model;

import exceptions.DailyLimitExceededException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

public class CheckingAccount extends BankAccount {

    private double dailyWithdrawalLimit = 300;

    public CheckingAccount(String customerName, Long accountNumber, Long userId, String userPassword) {
        super(customerName, accountNumber, userId, userPassword);
    }

    @Override
    public void deposit(double amount) {
        if (!(amount > 0)) throw new InvalidAmountException("Deposit amount must be greater than 0: " + amount);
        setAccountBalance(getAccountBalance() + amount);
    }

    @Override
    public void withdraw(double amount) {
        if (!(amount > 0)) throw new InvalidAmountException("Withdrawal amount must be greater than 0: " + amount);
        if (amount > dailyWithdrawalLimit) throw new DailyLimitExceededException("Withdrawal amount " + amount + " exceeds daily limit " + dailyWithdrawalLimit);
        if (amount > getAccountBalance()) throw new InsufficientFundsException("Insufficient funds: balance " + getAccountBalance() + ", requested " + amount);
        setAccountBalance(getAccountBalance() - amount);
    }

    public double getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public void setDailyWithdrawalLimit(double dailyWithdrawalLimit) {
        if (dailyWithdrawalLimit < 0) throw new InvalidAmountException("Daily withdrawal limit cannot be negative: " + dailyWithdrawalLimit);
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
    }
}
