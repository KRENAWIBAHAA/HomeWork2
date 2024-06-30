@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String city;
    private String country;
    private String pictureUrl;

    // Constructor, getters and setters...
}
