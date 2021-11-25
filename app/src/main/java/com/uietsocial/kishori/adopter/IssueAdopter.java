package com.uietsocial.kishori.adopter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uietsocial.kishori.R;
import com.uietsocial.kishori.Searchadd;
import com.uietsocial.kishori.addTages;
import com.uietsocial.kishori.model.issueName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueAdopter extends RecyclerView.Adapter<IssueAdopter.ViewHolder>{

    Context Searchadd;
    List<String>listIssue;
    ProgressDialog progressDialog2;

    public IssueAdopter(Searchadd Searchadd, List<String> listIssue) {
        this.Searchadd= Searchadd;
        this.listIssue = listIssue;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(Searchadd).inflate(R.layout.search_issue_item,parent,false);
        return new IssueAdopter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  IssueAdopter.ViewHolder holder, int position) {


      holder.issuedis.setText(listIssue.get(position));
   holder.addIssue.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {

           String issuetoadd=holder.issuedis.getText().toString();
           Intent intent=new Intent(Searchadd, addTages.class);
           intent.putExtra("Issue",issuetoadd);
           Searchadd.startActivity(intent);

       }
   });
        List<String>lt=new ArrayList<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("IssueWithTags").child(holder.issuedis.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


               lt.clear();

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    String tag=snapshot1.getKey();
                    lt.add(tag);
                }
                String[] items = new String[lt.size()];
               for (int i=0; i<lt.size(); i++){
                   items[i]=lt.get(i);
               }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Searchadd.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);
holder.spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(Searchadd, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});
                holder.spin.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.

  



    }

    @Override
    public int getItemCount() {

        return listIssue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView issuedis;
        ImageView addIssue;
        Spinner spin;
        FirebaseAuth auth;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            issuedis=itemView.findViewById(R.id.issue);
            addIssue=itemView.findViewById(R.id.addbtn);
            spin=itemView.findViewById(R.id.spinner1);


            auth=FirebaseAuth.getInstance();






                     /*String uid= auth.getCurrentUser().getUid();


                    FirebaseDatabase database2=FirebaseDatabase.getInstance();
                    DatabaseReference reference2=database2.getReference().child("Issues");

                    reference2.child(issuetoadd).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {
                            String data=snapshot.getValue().toString();
                            int  i=Integer.parseInt(data);

                            FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database3.getReference().child("Issues");
                            i += 1;
                            data = Integer.toString(i);
                            ref.child(issuetoadd).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(Searchadd.getApplicationContext(), "Issue will be solve soon", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull  DatabaseError error) {

                        }
                    });




                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference reference=database.getReference().child("user").child("Student").child(uid).child("Issue");
                    reference.child(issuetoadd).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {

                            String data="0";
                            if(snapshot.exists())
                            {
                                data=snapshot.getValue().toString();
                            }


                            int  i=Integer.parseInt(data);

                            FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database3.getReference().child("user").child("Student").child(uid).child("Issue").child(issuetoadd);
                            i += 1;
                            data = Integer.toString(i);

                            ref.setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull  DatabaseError error) {

                        }
                    });





                }
            });*/
        }
    }

}
