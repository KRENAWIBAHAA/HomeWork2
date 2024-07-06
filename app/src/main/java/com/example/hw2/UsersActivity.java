package com.example.hw2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw2.databinding.UserActivityBinding;

import java.util.List;


public class UsersActivity extends AppCompatActivity {
    UserActivityBinding binding;
    UserDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UserActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        );
        db = UserDB.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<User> users=db.usersDao().getAll();
           UserRecyclerViewAdapter recyclerViewAdapter = new UserRecyclerViewAdapter(users);
           binding.recyclerView.setAdapter(recyclerViewAdapter);

        }
    }



