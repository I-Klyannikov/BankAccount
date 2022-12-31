import java.sql.*;
import java.util.Scanner;

public class BankAccountDemo {
    public static boolean isLogin = false;

    /**
     * @param args the comand line argument
     */
    public static void main(String[] args) throws Exception {
        int option = 0;
        Scanner scanner = new Scanner(System.in);

        while (option == 0) {
            System.out.println("==== SELECT AN OPTION ====\n");
            System.out.println("1. Create new account");
            System.out.println("2. Sing IN\n");

            while (option < 1 || option > 2) {
                System.out.println("Type your choice: ");

                option = scanner.nextInt();
            }
        }

        switch (option) {
            case 1:
                System.out.println("\n\n==== CREATE NEW ACCOUNT====\n");
                System.out.println("Enter first name: ");
                String firstName = scanner.next().trim();

                System.out.println("Enter last name: ");
                String lastName = scanner.next().trim();

                //Создадим объект пользователя
                Account account = new Account(firstName, lastName);
                account.register();
                break;

            case 2:
                System.out.println("\n\n====SIGN IN====\n");
                System.out.println("Enter your card number: ");
                String cardnumber = scanner.next();
                System.out.println("Enter your pincode: ");
                String pincode = scanner.next();
                Operation operation = new Operation(cardnumber, pincode);

                try {
                    Connection c = DataBase.connection();
                    Statement stmt4 = c.createStatement();
                    String sql4 = "SELECT * FROM Card WHERE card_number = '" + cardnumber + "' AND pincode = '" + pincode + "'";
                    ResultSet rs4 = stmt4.executeQuery(sql4);

                    if (rs4.next()) {
                        isLogin = true;

                        System.out.println("\n\n====LOGIN SUCCESS====\n");
                        System.out.println("---Enter an option----\n");
                        System.out.println("1. Balance");
                        System.out.println("2. Deposit");
                        System.out.println("3. Send to other person");

                        int option_user = 0;

                        while (option_user < 1 || option_user > 3) {
                            System.out.println("\nType your choice: ");
                            option_user = scanner.nextInt();
                        }

                        int balance = 0;

                        //check option
                        switch (option_user) {
                            case 1:
                                System.out.println("\n\n====SHOW BALANCE====\n");
                                balance = operation.showBalance(cardnumber);
                                System.out.println(balance + "$");
                                break;

                            case 2:
                                System.out.println("\n\n====MAKE DEPOSIT====\n");
                                int amount = 0;

                                while (amount <= 0) {
                                    System.out.println("Type amount: ");
                                    amount = scanner.nextInt();
                                }

                                //Выполняем пополнение счёта клиента
                                operation.deposit(amount, cardnumber);

                                //Получим текущий баланс клиента
                                balance = operation.showBalance(cardnumber);
                                System.out.println("\nCurrent balance is " + balance + " $");
                                break;

                            case 3:
                                System.out.println("\n\n====SEND MONEY TO OTHER CARD====\n");
                                System.out.println("Enter number of other client: ");
                                String number_othen = scanner.next();

                                int amount_other = 0;

                                while (amount_other <= 0) {
                                    System.out.println("Enter amount for other client: ");
                                    amount_other = scanner.nextInt();
                                }

                                operation.sendMoneyToOther(amount_other, number_othen, cardnumber);

                                System.out.println("\nYou sent " + amount_other + " $ to " + number_othen);
                                break;

                            default:
                                break;
                        }
                    } else {
                        System.out.println("\nLogin fail");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;

            default:
                break;
        }
    }
}
