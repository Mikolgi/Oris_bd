public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Integer experience;
    private String car;
    private String car_color;

    public User(Long id, String firstName, String lastName, Integer age, Integer experience, String car, String car_color) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.experience = experience;
        this.car = car;
        this.car_color = car_color;
    }

    public Integer getExperience() {
        return experience;
    }

    public String getCar() {
        return car;
    }

    public String getCar_color() {
        return car_color;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

}
