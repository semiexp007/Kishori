package com.uietsocial.kishori;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class postActivity extends AppCompatActivity {
    ImageView imageView;
    Button mpostbtn;
    EditText mComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView=findViewById(R.id.img);
        mpostbtn=findViewById(R.id.postButton);
        mComment=findViewById(R.id.imgtext);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(postActivity.this)
                        .crop()//Allow dimmed layer to have a circle inside
                        .maxResultSize(500,300)	    //Let the user to resize crop bounds
                        .galleryOnly()
                        .start();
            }
        });

        mpostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savepost();
            }
        });
    }

    private void savepost() {

        StorageReference firepath= FirebaseStorage.getInstance().getReference().child("postImage").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        UploadTask uploadTask =firepath.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {


            // Handle unsuccessful uploads
        }).addOnSuccessListener(taskSnapshot -> firepath.getDownloadUrl().addOnSuccessListener(uri -> {

            Date date = new Date();
            Date newDate = new Date(date.getTime());
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            String stringdate = dt.format(newDate);
            Map newImage = new HashMap();
            newImage.put("postImageUrl", uri.toString());
            newImage.put("comment",mComment.getText().toString());
            newImage.put("postedby",FirebaseAuth.getInstance().getCurrentUser().getUid());
            newImage.put("nameofuser",getIntent().getStringExtra("name"));
            newImage.put("userImageUrl",getIntent().getStringExtra("userImageUrl"));
            newImage.put("date",stringdate);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference().child("posts");
            reference.push().setValue(newImage);
            Toast.makeText(postActivity.this,"Posted",Toast.LENGTH_SHORT).show();
            mComment.setText("");
            imageView.setImageResource(R.drawable.gallary);




        }));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Activity.RESULT_OK==resultCode ){
            final Uri imageUri=data.getData();


            imageView.setImageURI(imageUri);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {

        // startActivity(new Intent( Searchadd.this,Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
        return super.onSupportNavigateUp();
    }
}