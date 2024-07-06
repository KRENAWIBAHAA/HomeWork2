package com.example.hw2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hw2.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    UserDB db;

    public ActivityMainBinding binding;
    public Button button, button2, button3;
    public Root users;
    public ImageView image;
    public TextView First, Last, Age, Email, City, Country;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = UserDB.getInstance(this);


        image = binding.imageView;
        button = binding.button;
        button2 = binding.button2;
        button3 = binding.button3;
        First = binding.First;
        Last = binding.Last;
        Age = binding.Age;
        Email = binding.Email;
        City = binding.City;
        Country = binding.Country;

        button.setOnClickListener(v -> {
            button.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            fetchUserData();
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchUserData();
    }

    private void fetchUserData() {
        Retrofit retrofit = UserAPIClient.getClient();
        UserService service = retrofit.create(UserService.class);
        Call<Root> callAsync = service.getUsers(null, null, null);
        callAsync.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                context=MainActivity.this;
                users = response.body();
                assert users != null;
                List<Result> usersList = users.results;
                Result res = usersList.get(0);
                Glide.with(MainActivity.this).load(res.picture.medium).into(image);
                First.setText(res.name.first);
                Last.setText(res.name.last);
                Age.setText(String.valueOf(res.dob.age));
                Email.setText(res.email);
                City.setText(res.location.city);
                Country.setText(res.location.country);
                enableButtons();
                button2.setOnClickListener(v -> {
                    button.setEnabled(false);
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                    User user = new User();
                    user.picture = res.picture.medium;
                    user.firstName = res.name.first;
                    user.lastName = res.name.last;
                    user.country = res.location.country;
                    user.city = res.location.city;
                    db.usersDao().insertUser(user);
                    enableButtons();
                });
                button3.setOnClickListener(v -> {
                    button.setEnabled(false);
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                    Intent intent = new Intent(context,UsersActivity.class);
                    startActivity(intent);
                    enableButtons();
                });

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable throwable) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Error")
                        .setMessage("Request failed " + throwable.getMessage())
                        .setPositiveButton(android.R.string.yes, null)
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                Glide.with(MainActivity.this).load(R.drawable.error).into(image);
                First.setText("error");
                Last.setText("error");
                Age.setText("error");
                Email.setText("error");
                City.setText("error");
                Country.setText("error");
                enableButtons();
            }
        });
    }

    public void enableButtons() {
        button.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
    }
}