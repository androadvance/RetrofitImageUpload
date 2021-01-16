package com.example.retrofitimageupload;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_chooseimage,btn_uploadimage;
    EditText eT_title;
    ImageView imageView;
    Bitmap bitmap;
    public static final int IMG_REQUEST = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_chooseimage = findViewById(R.id.chooseimage);
        btn_uploadimage = findViewById(R.id.uploadimage);
        eT_title = findViewById(R.id.title);
        imageView = findViewById(R.id.imageview);

        btn_chooseimage.setOnClickListener(this);
        btn_uploadimage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.chooseimage:
                selectImage();
                break;

            case R.id.uploadimage:
                uploadImage();
                break;
        }
    }

    public void uploadImage(){

        String image = imagetoString();

        String Title = eT_title.getText().toString();
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<ImageClass>> call = apiInterface.uploadimage(Title,image);

        call.enqueue(new Callback<List<ImageClass>>() {
            @Override
            public void onResponse(Call<List<ImageClass>> call, Response<List<ImageClass>> response) {

                ImageClass imageClass = (ImageClass) response.body();
                Toast.makeText(MainActivity.this, "Server Response "+imageClass.getResponse(), Toast.LENGTH_SHORT).show();
                imageView.setVisibility(View.GONE);
                eT_title.setVisibility(View.GONE);
                btn_chooseimage.setEnabled(true);
                btn_uploadimage.setEnabled(false);
                eT_title.setText("");
            }

            @Override
            public void onFailure(Call<List<ImageClass>> call, Throwable t) {

            }
        });

    }

    public void selectImage(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && requestCode == RESULT_OK && data != null){

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                eT_title.setVisibility(View.VISIBLE);
                btn_chooseimage.setEnabled(false);
                btn_uploadimage.setEnabled(true);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // compress Image
    public String imagetoString(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagebyte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagebyte,Base64.DEFAULT);
    }
}
