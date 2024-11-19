import java.util.List;

public interface UserRepository extends CrudRepository<User>{
    List<User> findAllByAge(Integer age);
    List<User> findAllByExperience(Integer experience);
    List<User> findAllByCar_Color(String car_color);
    List<User> findAllByCar(String car);

}
