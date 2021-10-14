package com.uietsocial.kishori.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.model.Message;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    ImageView msendbutton;
EditText messagebox;
RecyclerView recyclerView;
MessageAdopter messageadopter;
List<Message>messageList;
String Senderroom, Receiverroom,SenderName,  URL="https://fcm.googleapis.com/fcm/send";
    String RUid,SUid;
RequestQueue requestQueue;
FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String name=getIntent().getStringExtra("Username");
        RUid=getIntent().getStringExtra("UserUid");
        SenderName=getIntent().getStringExtra("UserName");

        requestQueue= Volley.newRequestQueue(this);
        SUid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        msendbutton=findViewById(R.id.sendmessage);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        messageList=new ArrayList<>();

        recyclerView=findViewById(R.id.messageView);

        messagebox=findViewById(R.id.message);
        Senderroom=SUid+RUid;
        Receiverroom=RUid +SUid;

        messagebox.setBackgroundResource(android.R.color.transparent);
        messageadopter=new MessageAdopter(this,messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageadopter);
        database=FirebaseDatabase.getInstance();

        database.getReference().child("chats")
                .addValueEventListener(new ValueEventListener() {
             @Override
         public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
           messageList.clear();
          for(DataSnapshot snapshot1:snapshot.getChildren())
          {
              String recid=snapshot1.child("receiverId").getValue().toString();
              String sendId=snapshot1.child("senderId").getValue().toString();
              String MesId=snapshot1.getKey();
              String mess=snapshot1.child("message").getValue().toString();
              String read=snapshot1.child("read").getValue().toString();
              long timeStamp=snapshot1.child("timeStamp").getValue(long.class);

            Message message=new Message(mess,MesId,recid,sendId,timeStamp,read);

            if(message.getSenderId().equals(SUid)&& message.getReceiverId().equals(RUid) ||
                    message.getSenderId().equals(RUid)&& message.getReceiverId().equals(SUid))
            {
                messageList.add(message);
            }

          }

        messageadopter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull @NotNull DatabaseError error) {

    }
});

        msendbutton.setOnClickListener(view -> {
            String messagetxt=messagebox.getText().toString();
            Date date=new Date();
            DatabaseReference ref= database.getReference().child("chats");
            String msgkey=ref.push().getKey();
            Message message=new Message(messagetxt,msgkey,RUid,SUid,date.getTime(),"false");


            ref.child(msgkey).setValue(message).addOnSuccessListener(unused -> {
                        messagebox.setText("");

                        sendNotification(messagetxt);

                    });
        });

    }

    private void sendNotification(String messagetxt) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("to","/topics/"+RUid);
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("title","Message form "+SenderName);
            jsonObject1.put("body",messagetxt);
            jsonObject.put("notification",jsonObject1);

            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                  Map<String,String> map=new HashMap<>();
                  map.put("content-type","application/json");
                  map.put("authorization","key=AAAAhKZKYf4:APA91bFomwWw_Ifn2exe2-g6Wyd-8t9xa293TszhRTF2WA5KYK4DuSGUoyERF5pmZTJs2QnzTS-zgvQGiOcqwYfJjuIw4B4-mW4rjJ8rab4VtFYNAE8dJqbEF0bVncKK-dK0v4fFCImi");

                  return map;


                }
            };
            requestQueue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}