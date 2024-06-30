import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id = :userId")
    User getUserById(String userId);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
