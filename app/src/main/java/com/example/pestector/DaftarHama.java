package com.example.pestector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.example.pestector.databinding.ActivityDaftarHamaBinding;

import java.util.ArrayList;

public class DaftarHama extends AppCompatActivity {
    ActivityDaftarHamaBinding Binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binding = ActivityDaftarHamaBinding.inflate(getLayoutInflater());
        setContentView(Binding.getRoot());

        final String[] description = {"Terdeteksi Kutu Daun",
                "Terdeteksi Ulat Grayak",
                "Terdeteksi Kumbang",
                "Terdeteksi Cacing Tambang",
                "Terdeteksi Belalang",
                "Terdeteksi Tungau",
                "Terdeteksi Nyamuk",
                "Terdeteksi Lalat Gergaji",
                "Terdeteksi Penggerek Batang"};
        final String[] pest_name = {"Aphids","Armyworm","Beetle","Bollworm","Grasshopper","Mites","Mosquito","Sawfly","Stem Borer"};
        final int imageId[] = {R.drawable.aphids, R.drawable.armyworm, R.drawable.beetle,
                R.drawable.bollworm, R.drawable.grasshopper, R.drawable.mites,
                R.drawable.mosquito, R.drawable.sawfly, R.drawable.stem_borer,};

        ArrayList<Data> dataArrayList = new ArrayList<>();

        for(int i = 0; i < imageId.length;i++){
            Data data = new Data(pest_name[i], description[i], imageId[i]);
            dataArrayList.add(data);
        }

        List_Adapter list_adapter = new List_Adapter(DaftarHama.this, dataArrayList);
        Binding.listview.setAdapter(list_adapter);
        Binding.listview.setClickable(true);
        Binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(DaftarHama.this, ViewHama.class);
                i.putExtra("name", pest_name[position]);
                i.putExtra("description", description[position]);
                i.putExtra("imageId", imageId[position]);
                startActivity(i);
            }
        });
    }
}
