package com.example.hw2;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw2.databinding.UserViewHolderBinding;


public class UserViewHolder extends RecyclerView.ViewHolder {
    public UserViewHolderBinding binding;

    public UserViewHolder(@NonNull UserViewHolderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}