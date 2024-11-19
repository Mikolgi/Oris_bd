import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private Connection connection;

    private static final String SQL_SELECT_ALL_FROM_DRIVER = "select * from driver";
    private static final String SQL_FIND_BY_ID = "select * from driver where id = ?";
    private static final String SQL_SAVE = "insert into driver(first_name, last_name, age, experience, car, car_color) values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE driver set first_name = ?, last_name = ?, age = ?, experience = ?, car = ?, car_color = ? where id = ?";
    private static final String SQL_REMOVE = "Delete from driver where id = (select max(id) from driver)";
    private static final String SQL_REMOVE_BY_ID = "Delete from driver where id = ?";
    private static final String SQL_FIND_ALL_BY_AGE = "select * from driver where age = ?";
    private static final String SQL_FIND_ALL_BY_CAR = "select * from driver where car = ?";
    private static final String SQL_FIND_ALL_BY_CAR_COLOR = "select * from driver where car_color = ?";
    private static final String SQL_FIND_ALL_BY_EXPERIENCE = "select * from driver where experience = ?";

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);

            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("experience"),
                        resultSet.getString("car"),
                        resultSet.getString("car_color"));
                result.add(user);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("experience"),
                        resultSet.getString("car"),
                        resultSet.getString("car_color"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.empty();
    }

    @Override
    public void save(User entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE)) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getAge());
            preparedStatement.setInt(4, entity.getExperience());
            preparedStatement.setString(5, entity.getCar());
            preparedStatement.setString(6, entity.getCar_color());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Пользователь " + entity + " сохранен");
            } else {
                System.out.println("Пользователь" + entity + "не сохранен");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void update(User entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getAge());
            preparedStatement.setInt(4, entity.getExperience());
            preparedStatement.setString(5, entity.getCar());
            preparedStatement.setString(6, entity.getCar_color());
            preparedStatement.setLong(7, entity.getId());
            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow > 0) {
                System.out.println("Пользователь с ID " + entity.getId() + " был обновлен");
            } else {
                System.out.println(("Пользователь не был найден и обновлен"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void remove() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE)) {
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Последний пользователь был удален");
            } else {
                System.out.println("Пользователей нет, удаление невозможно");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void removeById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_BY_ID)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Пользователь с ID " + id + " был удален");
            } else {
                System.out.println("Пользователь с этим ID не найден");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public List<User> findAllByAge(Integer age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_BY_AGE)) {
            preparedStatement.setInt(1, age);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("experience"),
                        resultSet.getString("car"),
                        resultSet.getString("car_color"));
                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    public List<User> findAllByCar(String car) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_BY_CAR)) {
            preparedStatement.setString(1, car);
            List<User> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("experience"),
                        resultSet.getString("car"),
                        resultSet.getString("car_color"));
                result.add(user);

            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<User> findAllByCar_Color(String car_color) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_BY_CAR_COLOR)) {
            preparedStatement.setString(1, car_color);
            List<User> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("experience"),
                        resultSet.getString("car"),
                        resultSet.getString("car_color"));
                result.add(user);

            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<User> findAllByExperience(Integer experience) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_BY_EXPERIENCE)) {
            preparedStatement.setInt(1, experience);
            List<User> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("experience"),
                        resultSet.getString("car"),
                        resultSet.getString("car_color"));
                result.add(user);

            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
