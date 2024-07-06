package com.example.hw2;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hw2.databinding.UserViewHolderBinding;

import java.util.List;



public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private final List<User> dataSet;

    public UserRecyclerViewAdapter(@NonNull List<User> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        UserViewHolderBinding binding = UserViewHolderBinding.inflate(layoutInflater, parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = dataSet.get(position);
        Glide.with(holder.itemView.getContext())
                .load(user.picture)
                .apply(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.error))
                .into(holder.binding.imageViewUserIcon);
        holder.binding.textViewUserFirstName.setText(user.firstName);
        holder.binding.textViewUserLastName.setText(user.lastName);
        holder.binding.textViewUserCountry.setText(user.country);
        holder.binding.textViewUserCity.setText(user.city);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
