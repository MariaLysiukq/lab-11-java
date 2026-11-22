import model.BankAccount;
import model.CheckingAccount;

public class BankAccountTest {

    public static void main(String[] args) {
        BankAccount account = new CheckingAccount("Maria", 1L, 100L, "secret123");
        System.out.println("new account balance -> " + account.getAccountBalance());
        System.out.println("daily limit -> " + ((CheckingAccount) account).getDailyWithdrawalLimit());
        account.deposit(500);
        System.out.println("deposit(500) -> " + account.getAccountBalance());
        account.withdraw(120.5);
        System.out.println("withdraw(120.5) -> " + account.getAccountBalance());
        System.out.println("checkPassword(secret123) -> " + account.checkPassword("secret123"));
        System.out.println("checkPassword(wrong) -> " + account.checkPassword("wrong"));
        check("password short1", () -> new CheckingAccount("Ann", 2L, 101L, "short1"));
        check("password nodigitshere", () -> new CheckingAccount("Ann", 2L, 101L, "nodigitshere"));
        check("deposit(0)", () -> account.deposit(0));
        check("deposit(-50)", () -> account.deposit(-50));
        check("withdraw(-1)", () -> account.withdraw(-1));
        check("withdraw(301)", () -> account.withdraw(301));
        check("withdraw(380)", () -> account.withdraw(380));
        System.out.println("after failed calls balance is still -> " + account.getAccountBalance());
        CheckingAccount small = new CheckingAccount("Bob", 3L, 102L, "password9");
        small.deposit(50);
        check("withdraw(60) with balance 50", () -> small.withdraw(60));
        small.withdraw(50);
        System.out.println("withdraw(50) with balance 50 -> " + small.getAccountBalance());
        check("setAccountBalance(-1.0)", () -> small.setAccountBalance(-1.0));
        small.setDailyWithdrawalLimit(1000);
        small.deposit(800);
        small.withdraw(700);
        System.out.println("limit 1000, deposit 800, withdraw 700 -> " + small.getAccountBalance());
    }

    private static void check(String label, Runnable action) {
        try {
            action.run();
            System.out.println(label + " -> no exception");
        } catch (RuntimeException e) {
            System.out.println(label + " -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
