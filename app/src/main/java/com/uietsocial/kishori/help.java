package com.uietsocial.kishori;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import papaya.in.sendmail.SendMail;

public class help extends AppCompatActivity {

    EditText memail,mmesage;
    Button msend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        memail=findViewById(R.id.messemail);
        mmesage=findViewById(R.id.emailmessage);
        msend=findViewById(R.id.sendus);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMail mail = new SendMail("csjmuprojectkishori@gmail.com", "Kishori@123",
                        memail.getText().toString(),"Help Desk TeamKishori ","We received your mail we will notify you regarding your query soon"+
                        System.getProperty("line.separator")+
                        System.getProperty("line.separator")+
                        System.getProperty("line.separator")+"Thank you "+System.getProperty("line.separator")+ "Team Kishori");
                mail.execute();
                SendMail mail2 = new SendMail("csjmuprojectkishori@gmail.com", "Kishori@123",
                        "akmadheshiya90@gmail.com","Help Desk TeamKishori ",mmesage.getText().toString()+System.getProperty("line.separator")
                        +"by"+System.getProperty("line.separator")+memail.getText().toString());
                mail2.execute();
                memail.setText("");
                mmesage.setText("");
                Toast.makeText(help.this,"Thank you will notify you soon ",Toast.LENGTH_SHORT).show();
            }

        });

    }
    @Override
    public boolean onSupportNavigateUp() {

        // startActivity(new Intent( Searchadd.this,Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
        return super.onSupportNavigateUp();
    }
}