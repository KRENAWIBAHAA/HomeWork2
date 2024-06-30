public interface UserService {
    @GET("api/")
    Call<UserResponse> getRandomUser();
}
