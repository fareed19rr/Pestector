package com.example.pestector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pestector.Data;
import com.example.pestector.R;

import java.util.ArrayList;


public class List_Adapter extends ArrayAdapter<Data> {

    public List_Adapter(Context context, ArrayList<Data> dataArrayList){

        super(context, R.layout.list_item, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Data data = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        ImageView imageView = convertView.findViewById(R.id.pest_image);
        TextView pestName = convertView.findViewById(R.id.pest_name);
        TextView description = convertView.findViewById(R.id.description);

        imageView.setImageResource(data.imageId);
        pestName.setText(data.pest_name);
        description.setText(data.description);


        return convertView;
    }
}