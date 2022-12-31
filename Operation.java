import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Действия с финансовыми операциями
 */

public class Operation {
    private String cardnumber;
    private String pincode;
    private Integer balance;

    Operation(String cardnumber, String pincode) {
        this.cardnumber = cardnumber;
        this.pincode = pincode;
    }

    /**
     * Показывает баланс
     *
     * @param cardnumber
     * @return
     */

    public Integer showBalance(String cardnumber) {
        try {
            Connection c = DataBase.connection();
            Statement stmt = c.createStatement();
            Statement stmt5 = c.createStatement();
            String sql5 = "SELECT * FROM Balance WHERE card_number = '" + cardnumber + "'";
            ResultSet rs5 = stmt5.executeQuery(sql5);

            while (rs5.next()) {
                this.balance = rs5.getInt(2);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return this.balance;
    }

    /**
     * Пополнить счёт
     *
     * @param amount
     * @param cardnumber
     */

    public void deposit(Integer amount, String cardnumber) {
        // Записываем сумму на счёт

        try {
            Connection c = DataBase.connection();
            Statement stmt6 = c.createStatement();
            String sql6 = "UPDATE Balance SET balance = balanse + '" + amount + "' WHERE card_number = '" + cardnumber + "'";
            stmt6.executeUpdate(sql6);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Отправить деньги другому клиенту
     *
     * @param amount_other
     * @param number_other
     * @param cardnumber
     */

    public void sendMoneyToOther(Integer amount_other, String number_other, String cardnumber) {

        try {
            Connection c = DataBase.connection();
            // Переводим на счёт другому клиенту

            Statement stmt8 = c.createStatement();
            String sql8 = "UPDATE Balance SET balance = balance + '" + amount_other + "' WHERE card_number = '" + number_other + "'";
            stmt8.executeUpdate(sql8);

            //Снимаем со счёта текущего клиента

            Statement stmt9 = c.createStatement();
            String sql9 = "UPDATE Balance SET balance = balance - '" + amount_other + "' WHERE card_number = '" + cardnumber + "'";
            stmt9.executeUpdate(sql9);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

