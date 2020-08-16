package com.example.mynotes.fragment.video;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mynotes.R;
import com.example.mynotes.fragment.final_activity;
import com.example.mynotes.fragment.new_MainActivity;
import com.example.mynotes.fragment.note.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class end extends AppCompatActivity {
    TextView mood,mood1,mood2,mood3,mood4,mood5,mood6;
    private  Button face1;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    DocumentReference documentReference = fStore.collection("face").document(userId );
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facepage);

        final Bundle bundle = getIntent().getExtras();
        mood = findViewById(R.id.textView10);
        mood1= findViewById(R.id.textView1);
        mood2= findViewById(R.id.textView2);
        mood3= findViewById(R.id.textView3);
        mood4= findViewById(R.id.textView4);
        mood5= findViewById(R.id.textView5);
        mood6= findViewById(R.id.textView6);

        face1 = (Button) findViewById(R.id.button2);




        if (bundle != null) {
            final String anger= bundle.getString("anger");
            final String contempt= bundle.getString(" contempt");
            final String disgust= bundle.getString("disgust");
            final String fear= bundle.getString("fear");
            final String joy= bundle.getString("joy");
            final String sadness= bundle.getString("sadness");
            final String surpise= bundle.getString("surpise");
            mood1.setText(anger);
            mood2.setText(contempt);
            mood3.setText(disgust);
            mood4.setText(fear);
            mood5.setText(joy);
            mood6.setText(sadness);
            mood.setText(surpise);
            face1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass( end.this, new_MainActivity.class);
                    Map<String, Object> user = new HashMap<>();
                    if(disgust !=null) user.put("mood",disgust);
                    if(anger!= null) user.put("mood",anger);
                    if(contempt != null)user.put("mood",contempt);
                    if(fear !=null)user.put("mood",fear);
                    if(joy !=null) user.put("mood",joy);
                    if(sadness!=null) user.put("mood",sadness);
                    if(surpise!=null) user.put("mood",surpise);



                    documentReference.set(user);


                    startActivity(intent);


                }


            });

        }




    }
}

