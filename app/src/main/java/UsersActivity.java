import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework2.R;

import java.util.Collections;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreate(R.layout.activity_users);

        recyclerView = recyclerView.findViewById();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-database").build();
        new Thread(() -> {
            List<User> users = db.userDao().getAll();
            runOnUiThread(() -> {
                userAdapter = new UserAdapter(Collections.singletonList(users));
                recyclerView.setAdapter(userAdapter);
            });
        }).start();
    }
}
