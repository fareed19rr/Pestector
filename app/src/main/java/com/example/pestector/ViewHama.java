package com.example.pestector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pestector.databinding.ActivityViewHamaBinding;

public class ViewHama extends AppCompatActivity{
    ActivityViewHamaBinding Binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binding = ActivityViewHamaBinding.inflate(getLayoutInflater());
        setContentView(Binding.getRoot());

        Intent intent = this.getIntent();

        if(intent != null){
            String name = intent.getStringExtra("name");
            String description = intent.getStringExtra("description");
            int imageId = intent.getIntExtra("imageId", R.drawable.aphids);

            Binding.detailName.setText(name);
            Binding.detailDesc.setText(description);
            Binding.detailImage.setImageResource(imageId);
        }
    }
}