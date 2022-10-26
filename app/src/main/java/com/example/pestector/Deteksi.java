package com.example.pestector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.pestector.databinding.ActivityDeteksiBinding;
import com.example.pestector.ml.Model3;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;

import android.util.Log;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Deteksi extends AppCompatActivity implements View.OnClickListener {
    ActivityDeteksiBinding Binding;
    private Button button_kamera, button_galeri;
    private TextView title, description;
    private ImageView imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binding = ActivityDeteksiBinding.inflate(getLayoutInflater());
        setContentView(Binding.getRoot());


        button_kamera = (Button)findViewById(R.id.button_kamera);
        button_galeri = (Button)findViewById(R.id.button_galeri);
        title = (TextView)findViewById(R.id.text_deteksi);
        description = (TextView)findViewById(R.id.text_deteksi_deskripsi);

        button_kamera.setOnClickListener(this);
        button_galeri.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_galeri:
                pickImageFromGallery();
                break;
            case R.id.button_kamera:
                pickImageFromKamera();
                break;
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 101);
    }

    private void pickImageFromKamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(captureImage.getWidth(), captureImage.getHeight());
            captureImage = ThumbnailUtils.extractThumbnail(captureImage, dimension, dimension);
            Binding.viewImageDeteksi.setImageBitmap(captureImage);
            Bitmap resized = Bitmap.createScaledBitmap(captureImage, 224, 224, true);
            Bitmap newResized = resized.copy(Bitmap.Config.ARGB_8888,true);
            MLModel(newResized);
        }
        if (requestCode == 101) {

            Uri galleryImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), galleryImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
            Binding.viewImageDeteksi.setImageBitmap(bitmap);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
            MLModel(resized);
        }
    }

    private void MLModel(Bitmap resized){
        try {
            Bitmap image = resized;
            Model3 model = Model3.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
            tensorImage.load(image);
            ByteBuffer byteBuffer = tensorImage.getBuffer();


            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model3.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();

            // Releases model resources if no longer used.
            model.close();

            int index = 0;
            float max = 0;

            for(int i =0; i <confidences.length; i++){
                if(confidences[i]>max){
                    max = confidences[i];
                    index = i;
                }
            }

            Log.d("message", "Array:" + Arrays.toString(confidences));


            if(index==0){
                Log.d("message", "Aphids");
                Binding.textDeteksi.setText("Aphids");
                Binding.textDeteksiDeskripsi.setText("Terdeteksi Kutu Daun");
                Binding.textDeteksiDeskripsi.setVisibility(View.VISIBLE);
            }else if(index==1){
                Log.d("message", "Armyworm");
                Binding.textDeteksi.setText("Armyworm");
                Binding.textDeteksiDeskripsi.setText("Terdeteksi Ulat Grayak");
                Binding.textDeteksiDeskripsi.setVisibility(View.VISIBLE);
            }else if(index == 2){
                Log.d("message", "Beetle");
                Binding.textDeteksi.setText("Beetle");
                Binding.textDeteksiDeskripsi.setText("Terdeteksi Kumbang");
                Binding.textDeteksiDeskripsi.setVisibility(View.VISIBLE);
            }else if(index == 3){
                Log.d("message", "Bollworm");
                Binding.textDeteksi.setText("Bollworm");
                Binding.textDeteksiDeskripsi.setText("Terdeteksi Cacing Tambang");
                Binding.textDeteksiDeskripsi.setVisibility(View.VISIBLE);
            }else if(index == 4){
                Log.d("message", "Grasshopper");
                Binding.textDeteksi.setText("Grasshopper");
                Binding.textDeteksiDeskripsi.setText("Terdeteksi Belalang");
                Binding.textDeteksiDeskripsi.setVisibility(View.VISIBLE);
            }else if(index == 5){
                Log.d("message", "Mites");
                Binding.textDeteksi.setText("Mites");
                Binding.textDeteksiDeskripsi.setText("Terdeteksi Tungau");
                Binding.textDeteksiDeskripsi.setVisibility(View.VISIBLE);
            }else if(index == 6){
                Log.d("message", "Mosquito");
                Binding.textDeteksi.setText("Mosquito");
                Binding.textDeteksiDeskripsi.setText("Terdeteksi Nyamuk");
                Binding.textDeteksiDeskripsi.setVisibility(View.VISIBLE);
            }else if(index == 7){
                Log.d("message", "Sawfly");
                Binding.textDeteksi.setText("Sawfly");
                Binding.textDeteksiDeskripsi.setText("Terdeteksi Lalat Gergaji");
                Binding.textDeteksiDeskripsi.setVisibility(View.VISIBLE);
            }else if(index == 8){
                Log.d("message", "Stem Borer");
                Binding.textDeteksi.setText("Stem Borer");
                Binding.textDeteksiDeskripsi.setText("Terdeteksi Penggerek Batang");
                Binding.textDeteksiDeskripsi.setVisibility(View.VISIBLE);
            }
        } catch (IOException e) {
            // TODO Handle the
            System.out.print("Error");
        }
    }
}
