package com.maruf.design;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListData extends AppCompatActivity {


   FloatingActionButton fab;
    FloatingActionButton out;
   RecyclerAdapter recyclerAdapter;
   DatabaseReference database= FirebaseDatabase.getInstance().getReference();
   ArrayList<dataMahasiswa> mahasiswalist;
   RecyclerView datalist;
   EditText searchview;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        mAuth=FirebaseAuth.getInstance();
        fab=findViewById(R.id.fab);
        datalist= findViewById(R.id.datalist);
        out = findViewById(R.id.out);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(ListData.this, Activity_register.class));
                finish();

            }
        });

        RecyclerView.LayoutManager linearLayout= new LinearLayoutManager(this);
        datalist.setLayoutManager(linearLayout);
        datalist.setItemAnimator(new DefaultItemAnimator());





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListData.this, inform.class));
            }
        });
        showData("");

        searchview=findViewById(R.id.et_serach);
        searchview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }


            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()!= null){
                    showData(s.toString());

                }else{
                    showData("");
                }

            }
        });



    }
    private void showData(String data){
        database.child("Mahasiswa").orderByChild("nama").startAt(data).endAt(data+"\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mahasiswalist= new ArrayList<>();
                        for (DataSnapshot item : snapshot.getChildren()){
                            dataMahasiswa mhs= item.getValue(dataMahasiswa.class);
                            mhs.setKey(item.getKey());
                            mahasiswalist.add(mhs);
                        }
                        recyclerAdapter = new RecyclerAdapter(mahasiswalist, ListData.this);
                        datalist.setAdapter(recyclerAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}
