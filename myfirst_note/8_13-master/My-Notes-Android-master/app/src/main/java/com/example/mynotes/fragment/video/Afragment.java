package com.example.mynotes.fragment.video;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.example.mynotes.R;
import com.example.mynotes.fragment.new_secondfragment;
import com.example.mynotes.fragment.note.MainActivity;
import com.example.mynotes.fragment.note.NotesRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static java.lang.Thread.sleep;


public class Afragment extends AppCompatActivity implements Detector.ImageListener {
    public static final String TAG = "TAG";
    private Uri uri;
    int i = 0;

    VideoView videoView;
    SurfaceView cameraview;
    CameraDetector detector;
    View view;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;
    String userID;
    NotesRecyclerAdapter notesRecyclerAdapter;
    private DatabaseReference mDatabase;
    List<Face> faces = new ArrayList<>();
    Frame frame;
    Float anger, contempt, disgust, fear, joy, sadness, surprise;
    FloatingActionButton Next;
    float anger1 = 0;

    float contempt1 = 0;

    float disgust1 = 0;

    float fear1 = 0;
    float joy1 = 0;

    float sadness1 = 0;

    float surpise1 = 0;
    private DocumentReference documentReference = fStore.document("notes/face");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play);
        videoView = findViewById(R.id.videoView);
        Bundle bundle = getIntent().getExtras();
        view = findViewById(R.id.view3);
        cameraview = findViewById(R.id.camera_view);
        view.setBackgroundColor(Color.parseColor("#6495ED"));
        Next = findViewById(R.id.next);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);

        Uri videouri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/test-55cf4.appspot.com/o/%E8%AA%B2%E7%A8%8B%E5%85%A7%E5%AE%B9%E4%B8%8D%E6%98%93%E7%90%86%E8%A7%A3.mp4?alt=media&token=422c5af0-b750-4b0c-86ae-a6f7d99084d8");
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(videouri);


        detector = new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, cameraview);
        detector.setImageListener(this);
        //detector.setDetectSmile(true);
        detector.setDetectAllEmotions(true);
        //detector.setDetectAllAppearances(true);
        detector.setMaxProcessRate(60);
        detector.start();


    }

    @Override
    protected void onStart() {
        super.onStart();
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(Afragment.this, "ERROR", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return;
                }

            }
        });
    }


    @Override
    public void onImageResults(List<Face> faces, Frame frame, float v) {
        int a;
        float max = 0;

        if (faces.size() == 0) {
            view.setBackgroundColor(Color.parseColor("#FF0000"));
            videoView.pause();
        } else {
            view.setBackgroundColor(Color.parseColor("#00FF00"));
            videoView.start();
            i++;
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String s = String.valueOf(i);
            DocumentReference documentReference = fStore.collection("mood").document("face" + userId + s);
            Face face = faces.get(0);

            anger = Float.valueOf(face.emotions.getAnger());
            contempt = Float.valueOf(face.emotions.getContempt());
            disgust = Float.valueOf(face.emotions.getDisgust());
            fear = Float.valueOf(face.emotions.getFear());
            joy = Float.valueOf(face.emotions.getJoy());
            sadness = Float.valueOf(face.emotions.getSadness());
            surprise = Float.valueOf(face.emotions.getSurprise());
            Map<String, Float> user = new HashMap<>();
            anger1 = (anger + anger1) / 2;
            contempt1 = (contempt1 + contempt) / 2;
            disgust1 = (disgust1 + disgust) / 2;
            fear1 = (fear1 + fear) / 2;
            joy1 = (joy1 + joy) / 2;
            sadness1 = (sadness1 + sadness) / 2;
            surpise1 = (surpise1 + surprise) / 2;

            float b[] = {anger1, contempt1, disgust1, fear1, joy1, sadness1, surpise1};
            for (a = 0; a < 7; a++) {

                if (b[a] > max) max = b[a];
            }

            user.put("anger", anger);
            user.put("contempt", contempt);
            user.put("disgust", disgust);
            user.put("fear", fear);
            user.put(" joy", joy);
            user.put("sadness", sadness);
            user.put("surprise", surprise);
            user.put("anger1", anger1);
            user.put("contempt1", contempt1);
            user.put("disgust1", disgust1);
            user.put("fear1", fear1);
            user.put(" joy1", joy1);
            user.put("sadness1", sadness1);
            user.put("surprise1", surpise1);
            user.put("max", max);
            documentReference.set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.d(TAG, e.toString());
                        }
                    });

        }

    }

    @Override
    public void onBackPressed() {
        if (detector.isRunning()) {
            detector.stop();
        }
        float max = 0;
int h;
        float b[] = {anger1, contempt1, disgust1, fear1, joy1, sadness1, surpise1};
        for (h = 0; h < 7; h++) {

            if (b[h] > max) max = b[h];
        }

        Intent intent = new Intent(this, end.class);

        Bundle bundle = new Bundle();
        if (max == anger1) {
            bundle.putString("anger", "anger");
        }else if (max == contempt1){ bundle.putString("contempt", "contempt");
        } else if (max == disgust1) {
            bundle.putString("disgust", "disgust");
        }else if  (max == fear1) {
            bundle.putString("fear", "fear");
        }else if (max == joy1) {
            bundle.putString("joy", "joy");
        }else if (max == sadness1) {
            bundle.putString("sadness", "sadness");
        }else if (max == surpise1) {
            bundle.putString("surpise", "surpise");
        }

        intent.putExtras(bundle);


        if (bundle != null) {
            Toast.makeText(Afragment.this, "not NULL", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Afragment.this, "NULL", Toast.LENGTH_SHORT).show();
        }
        if (max != 0) {
            Toast.makeText(Afragment.this, "not NULL1", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Afragment.this, "NULL1", Toast.LENGTH_SHORT).show();
        }

        startActivity(intent);

        super.onBackPressed();
    }

}