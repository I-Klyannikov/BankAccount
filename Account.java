import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Account {
    private String firstName;
    private String lastName;
    private Integer balance;

    Account(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Регистрирует нового пользователя
     *
     * @return
     * @throws java.lang.Exception
     */
    public Boolean register() throws Exception {
        // делаем регистрацию
        try {
            Connection c = DataBase.connection();
            Statement stmt = c.createStatement();
            String sql = "INSERT INTO Account VALUES (null, " + this.firstName + "\', \'" + this.lastName + "\')";
            stmt.executeUpdate(sql);

            //last id

            Statement stmt2 = c.createStatement();
            String sql2 = "SLECT LAST INSERT ID()";
            ResultSet rs2 = stmt.executeQuery(sql2);

            int last_account_id = 0;

            while (rs2.next()) {
                last_account_id = rs2.getInt(1);
            }

            String cardNumber = this.generateCardNumber();
            String code = this.generatePincode();

            // добавляем cardNumber и пинкод
            Statement stmt3 = c.createStatement();
            String sql3 = "INSERT INTO Card VALUES ('" + last_account_id + "\', \'" + cardNumber + "\', \'" + code + "')";
            stmt3.executeUpdate(sql3);

            //создаём сумму баланса
            Statement stmt4 = c.createStatement();
            String sql4 = "INSERT INTO Balance VALUES ('" + cardNumber + "\', '0')";
            stmt4.executeUpdate(sql4);

            c.close();

            System.out.println("\n\nAccount was successfully created.\n");
            System.out.println("Card Number: " + cardNumber);
            System.out.println("Pincode: " + code);

            return true;
        } catch (Exception e) {
            System.out.println(e);

            return false;
        }
    }

    /**
     * Генерирует номер карты
     *
     * @return
     */

    public String generateCardNumber() {
        int length = 8;
        String passwordSet = "1234567890";
        char[] cardNumber = new char[length];
        for (int i = 0; i < length; i++) {
            int rand = (int) (Math.random() * passwordSet.length());
            cardNumber[i] = passwordSet.charAt(rand);
        }
        return new String(cardNumber);
    }

    /**
     * Генерирует пин-код
     *
     * @return
     */

    public String generatePincode() {
        int length = 4;
        String passwordSet = "1234567890";
        char[] cardNumber = new char[length];
        for (int i = 0; i < length; i++) {
            int rand = (int) (Math.random() * passwordSet.length());
            cardNumber[i] = passwordSet.charAt(rand);
        }
        return new String(cardNumber);
    }
}
