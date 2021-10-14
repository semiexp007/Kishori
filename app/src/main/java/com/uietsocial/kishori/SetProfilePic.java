package com.uietsocial.kishori;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SetProfilePic extends AppCompatActivity {
 ImageView msetprofile;
 Button msave;
 String puri;
    Uri resultUri;
    String usercat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile_pic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        msetprofile=findViewById(R.id.profilepicset);
        usercat=getIntent().getStringExtra("usercat");
        msave=findViewById(R.id.save);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference().child("user").child(usercat).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profileImageUrl");
      reference.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
              String url=snapshot.getValue().toString();
              Glide.with(getApplication()).load(url).into(msetprofile);
          }

          @Override
          public void onCancelled(@NonNull @NotNull DatabaseError error) {

          }
      });

        msetprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(SetProfilePic.this)
                        .crop()
                        .cropOval()	    		//Allow dimmed layer to have a circle inside
                        .maxResultSize(1080,1080)	    //Let the user to resize crop bounds
                        .galleryOnly()
                        .start();//We have to define what image provider we want to us//Final image resolution will be less than 1080 x 1080(Optional)
            }
        });



        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              saveimage();
            }
        });
    }

    private void saveimage() {



        StorageReference firepath= FirebaseStorage.getInstance().getReference().child("profileimage").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Bitmap bitmap = ((BitmapDrawable) msetprofile.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        UploadTask uploadTask =firepath.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            finish();

            // Handle unsuccessful uploads
        }).addOnSuccessListener(taskSnapshot -> firepath.getDownloadUrl().addOnSuccessListener(uri -> {
            Map newImage = new HashMap();
            newImage.put("profileImageUrl", uri.toString());
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference().child("user").child(usercat).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            reference.updateChildren(newImage);
            Toast.makeText(SetProfilePic.this,"Updated",Toast.LENGTH_SHORT).show();

            finish();


        }));




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Activity.RESULT_OK==resultCode ){
            final Uri imageUri=data.getData();
           resultUri=imageUri;

           msetprofile.setImageURI(resultUri);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}