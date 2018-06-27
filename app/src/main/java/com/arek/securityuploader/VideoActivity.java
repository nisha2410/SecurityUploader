package com.arek.securityuploader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    private RecyclerView vRecycleView;
    private VideoAdapter vadapter;
    ProgressBar vProgressCircle;

    private DatabaseReference vDatabaseRef;
    private List<UploadVideo> vUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        vRecycleView = (RecyclerView) findViewById(R.id.rv1);
        vRecycleView.setHasFixedSize(true);
        vRecycleView.setLayoutManager(new LinearLayoutManager(this));
        vProgressCircle = (ProgressBar) findViewById(R.id.prgb1);
        vUploads = new ArrayList<>();
        vDatabaseRef = FirebaseDatabase.getInstance().getReference("Contact_info").child(Login.cno).child("video");

        vDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                vUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadVideo upload = postSnapshot.getValue(UploadVideo.class);
                    vUploads.add(upload);
                }
                vadapter = new VideoAdapter(VideoActivity.this, vUploads);
                vRecycleView.setAdapter(vadapter);
                vProgressCircle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(VideoActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
