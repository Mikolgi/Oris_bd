import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "rootwert";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ORIS";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Сколько пользователей хотите добавить?");
        int number = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < number; i++) {
            System.out.print("Введите имя пользователя: ");
            String firstName = scanner.nextLine();

            System.out.print("Введите фамилию пользователя: ");
            String secondName = scanner.nextLine();

            System.out.print("Введите возраст пользователя: ");
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Введите опыт вождения пользователся в годах: ");
            int experience = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Введите марку машины пользователя: ");
            String car = scanner.nextLine();

            System.out.print("Введите цвет машины пользователя: ");
            String carColor = scanner.nextLine();

            User user = new User(null, firstName, secondName, age, experience, car, carColor);

            userRepository.save(user);
        }

        System.out.println("Введите ID пользователя, которому хотите поменять данные");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите новое имя для пользователя:");
        String name = scanner.nextLine();

        System.out.println("Введите новую фамилию для пользователя");
        String surname = scanner.nextLine();

        System.out.println("Введите новый возраст пользователя");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите новый опыт вождения");
        int experience = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите новую машину пользователя");
        String car = scanner.nextLine();

        System.out.println("Введите новый цвет машины пользователя");
        String car_color = scanner.nextLine();

        User userUpdate = new User((long) id, name, surname, age, experience, car, car_color);
        userRepository.update(userUpdate);
        System.out.println("Пользователь успешно обновлен");

        List<User> usersAll = userRepository.findAll();
        usersAll.forEach(user -> System.out.println(user.getFirstName()));

        List<User> usersByAge19 = userRepository.findAllByAge(19);
        usersByAge19.forEach(user -> System.out.println("Имя: " + user.getFirstName() + ", возраст: " + user.getAge()));

        List<User> usersByBMWСar = userRepository.findAllByCar("BMW");
        usersByBMWСar.forEach(user -> System.out.println("Имя: " + user.getFirstName() + ", машина: " + user.getCar()));

        List<User> usersByCarColor = userRepository.findAllByCar_Color("Black");
        usersByCarColor.forEach(user -> System.out.println("Имя: " + user.getFirstName() + ", цвет машины: " + user.getCar_color()));

        List<User> usersByExperience2 = userRepository.findAllByExperience(2);
        usersByExperience2.forEach(user -> System.out.println("Имя: " + user.getFirstName() + ", опыт вождения: " + user.getExperience()));

        Optional<User> userID7 = userRepository.findById(7L);
        System.out.println("Пользователь с ID 7: " + userID7.get().getFirstName());

        userRepository.remove();

        userRepository.removeById(7L);


    }
}
