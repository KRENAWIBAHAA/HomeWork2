package com.example.homework2;

public class MainActivity extends AppCompatActivity {

    private ImageView userImage;
    private TextView firstName, lastName, age, email, city, country;
    private Button seeNextUser, addUserToCollection, viewCollection;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userImage = findViewById(R.id.userImage);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        seeNextUser = findViewById(R.id.seeNextUser);
        addUserToCollection = findViewById(R.id.addUserToCollection);
        viewCollection = findViewById(R.id.viewCollection);

        seeNextUser.setOnClickListener(v -> fetchNewUser());
        addUserToCollection.setOnClickListener(v -> addUserToDatabase());
        viewCollection.setOnClickListener(v -> openUsersActivity());

        fetchNewUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchNewUser();
    }

    private void fetchNewUser() {
        disableButtons();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService service = retrofit.create(UserService.class);
        Call<UserResponse> call = service.getRandomUser();

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentUser = response.body().getResults().get(0).toUser();
                    updateUI(currentUser);
                    enableButtons();
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void updateUI(User user) {
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        age.setText(String.valueOf(user.getAge()));
        email.setText(user.getEmail());
        city.setText(user.getCity());
        country.setText(user.getCountry());
        Picasso.get().load(user.getPictureUrl()).into(userImage);
    }

    private void showError() {
        firstName.setText("error");
        lastName.setText("error");
        age.setText("error");
        email.setText("error");
        city.setText("error");
        country.setText("error");
        enableButtons();
    }

    private void addUserToDatabase() {
        if ("error".equals(firstName.getText().toString())) {
            Toast.makeText(this, "Cannot add user due to error", Toast.LENGTH_SHORT).show();
            return;
        }

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-database").build();
        new Thread(() -> {
            if (db.userDao().getUserById(currentUser.getId()) == null) {
                db.userDao().insertAll(currentUser);
                runOnUiThread(() -> Toast.makeText(this, "User added to collection", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(this, "User already exists in collection", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void openUsersActivity() {
        Intent intent = new Intent(this, UsersActivity.class);
        startActivity(intent);
    }

    private void disableButtons() {
        seeNextUser.setEnabled(false);
        addUserToCollection.setEnabled(false);
        viewCollection.setEnabled(false);
    }

    private void enableButtons() {
        seeNextUser.setEnabled(true);
        addUserToCollection.setEnabled(true);
        viewCollection.setEnabled(true);
    }
}
