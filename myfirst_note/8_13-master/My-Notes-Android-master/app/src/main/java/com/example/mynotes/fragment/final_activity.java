package com.example.mynotes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mynotes.R;
import com.example.mynotes.fragment.note.MainActivity;
import com.example.mynotes.fragment.video.end;

public class final_activity extends AppCompatActivity {

    Button note, video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        note = (Button) findViewById(R.id.button2);
        video =(Button) findViewById(R.id.button4);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass( final_activity.this, MainActivity.class);
                startActivity(intent);

               
            }


        });
             video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass( final_activity.this, end.class);
                    startActivity(intent);


                }

             });
    }
}
