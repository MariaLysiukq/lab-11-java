import exceptions.AccountException;
import exceptions.AccountNotFoundException;
import exceptions.DuplicateUserIdException;
import exceptions.InvalidAmountException;
import exceptions.InvalidCustomerNameException;
import exceptions.InvalidUserIdException;
import exceptions.NonZeroBalanceException;
import exceptions.PasswordMismatchException;
import exceptions.WrongPasswordException;
import model.BankAccount;
import model.CheckingAccount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientInterface {

    private final List<BankAccount> accounts = new ArrayList<>();

    private final Scanner scanner = new Scanner(System.in);

    private long nextAccountNumber = 1;

    private List<String> options = List.of(
            "1 - Create account",
            "2 - Delete account",
            "3 - Make account deposit",
            "4 - Make account withdrawal",
            "5 - Print account info",
            "6 - Exit"
    );

    public void start() throws IOException {
        while (true) {
            printMainMenu();
            String option = scanner.next();
            try {
                switch (option) {
                    case "1" -> addNewAccount();
                    case "2" -> deleteAccount();
                    case "3" -> deposit();
                    case "4" -> withdraw();
                    case "5" -> printInfo();
                    case "6" -> {
                        return;
                    }
                    default -> System.err.println("Please enter a valid choice (1 thru 6)");
                }
            } catch (AccountException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void printMainMenu() throws IOException {
        System.out.println("\n****************");
        System.out.println("Banking system: ");
        System.out.println("****************");
        options.forEach(System.out::println);
        System.out.print("\n>>> Choose your option: ");
    }

    private void addNewAccount() {
        System.out.println("\n****************");
        System.out.println("Creating new account: ");
        System.out.println("****************");
        System.out.print(">>> Enter customer name: ");
        scanner.nextLine();
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) throw new InvalidCustomerNameException("Customer name cannot be empty");
        System.out.print(">>> Enter user id: ");
        Long userId = parseUserId(scanner.next());
        if (findAccount(userId).isPresent()) throw new DuplicateUserIdException("Account with user id " + userId + " already exists");
        System.out.print(">>> Enter user password: ");
        String password = scanner.next();
        BankAccount.validatePassword(password);
        System.out.print(">>> Confirm password: ");
        String confirmPassword = scanner.next();
        if (!password.equals(confirmPassword)) throw new PasswordMismatchException("Password and confirmation do not match");
        BankAccount account = new CheckingAccount(name, nextAccountNumber++, userId, password);
        accounts.add(account);
        System.out.println("Account created successfully. Account number: " + account.getAccountNumber());
    }

    private Long parseUserId(String input) {
        try {
            long userId = Long.parseLong(input);
            if (userId <= 0) throw new InvalidUserIdException("User id must be a positive number: " + input);
            return userId;
        } catch (NumberFormatException e) {
            throw new InvalidUserIdException("User id must be a number: " + input);
        }
    }

    private Optional<BankAccount> findAccount(Long userId) {
        return accounts.stream().filter(a -> a.getUserId().equals(userId)).findFirst();
    }

    private BankAccount authenticate() {
        System.out.print(">>> Enter user id: ");
        Long userId = parseUserId(scanner.next());
        System.out.print(">>> Enter user password: ");
        String password = scanner.next();
        BankAccount account = findAccount(userId).orElseThrow(() -> new AccountNotFoundException("Account with user id " + userId + " not found"));
        if (!account.checkPassword(password)) throw new WrongPasswordException("Wrong password for user id " + userId);
        return account;
    }

    private void deleteAccount() {
        System.out.println("\n****************");
        System.out.println("Deleting account: ");
        System.out.println("****************");
        BankAccount account = authenticate();
        if (account.getAccountBalance() != 0) throw new NonZeroBalanceException("Cannot delete account with non-zero balance: " + account.getAccountBalance());
        accounts.remove(account);
        System.out.println("Account with user id " + account.getUserId() + " deleted successfully");
    }

    private double parseAmount(String input) {
        try {
            double amount = Double.parseDouble(input);
            if (!Double.isFinite(amount)) throw new InvalidAmountException("Amount must be a finite number: " + input);
            return amount;
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("Amount must be a number: " + input);
        }
    }

    private void deposit() {
        System.out.println("\n****************");
        System.out.println("Making a deposit: ");
        System.out.println("****************");
        BankAccount account = authenticate();
        System.out.print(">>> Enter amount: ");
        account.deposit(parseAmount(scanner.next()));
        System.out.println("Deposit successful. New balance: " + account.getAccountBalance());
    }

    private void withdraw() {
        System.out.println("\n****************");
        System.out.println("Making a withdrawal: ");
        System.out.println("****************");
        BankAccount account = authenticate();
        System.out.print(">>> Enter amount: ");
        account.withdraw(parseAmount(scanner.next()));
        System.out.println("Withdrawal successful. New balance: " + account.getAccountBalance());
    }

    private void printInfo() {
        System.out.println("\n****************");
        System.out.println("Printing account info: ");
        System.out.println("****************");
        BankAccount account = authenticate();
        System.out.println("\nAccount info: ");
        System.out.println(" - customer name: " + account.getCustomerName());
        System.out.println(" - account number: " + account.getAccountNumber());
        System.out.println(" - account balance: " + account.getAccountBalance());
        if (account instanceof CheckingAccount checking) {
            System.out.println(" - account type: Checking");
            System.out.println(" - account daily withdrawal limit: " + checking.getDailyWithdrawalLimit());
        }
        System.out.print("\nPress Enter to return to main menu...");
        scanner.nextLine();
        if (scanner.hasNextLine()) scanner.nextLine();
    }
}