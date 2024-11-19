import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "rootwert";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ORIS";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
//        ResultSet result = statement.executeQuery("select * from driver");
//
//        while (result.next()) {
//            System.out.println(result.getInt("id") + " " + result.getString("name"));
//        }

        Scanner scanner = new Scanner(System.in);
        String firstName = scanner.nextLine();
        String secondName = scanner.nextLine();
        int age = scanner.nextInt();
        int experience = scanner.nextInt();
        scanner.nextLine();
        String car = scanner.nextLine();
        String car_color = scanner.nextLine();


        String sqlInsertUser = "insert into driver(first_name, last_name, age, experience, car, car_color)" +
                "values (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, secondName);
        preparedStatement.setInt(3, age);
        preparedStatement.setInt(4, experience);
        preparedStatement.setString(5, car);
        preparedStatement.setString(6, car_color);

        int affectedRows = preparedStatement.executeUpdate();

        System.out.println("Было добавлено " + affectedRows + " строк");
    }
}
