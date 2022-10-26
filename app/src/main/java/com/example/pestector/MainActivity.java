package com.example.pestector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button tentangButton, daftarButton, deteksiButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tentangButton = (Button)findViewById(R.id.button_tentang);
        tentangButton.setOnClickListener(this);
        daftarButton = (Button)findViewById(R.id.button_daftar);
        daftarButton.setOnClickListener(this);
        deteksiButton = (Button)findViewById(R.id.button_deteksi);
        deteksiButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button_tentang){
                startActivity(new Intent(this, About.class));
        }
        else if (v.getId()==R.id.button_daftar){
                startActivity(new Intent(this, DaftarHama.class));
        }
        else if (v.getId()==R.id.button_deteksi){
                startActivity(new Intent(this, Deteksi.class));
        }
    }
}