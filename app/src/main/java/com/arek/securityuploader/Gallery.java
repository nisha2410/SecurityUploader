package com.arek.securityuploader;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;

public class Gallery extends AppCompatActivity {
    public static final int  PICK_IMAGE = 111,PICK_VIDEO = 101;;
    VideoView vv;
    ImageView iv;
    Uri uri;
    private DatabaseReference dr1,dr2;
    private StorageReference sr;
    ProgressDialog pd;
    Intent i;
    Button b1, b2,b3,b4;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        b1=(Button)findViewById(R.id.imgv);
        b2=(Button)findViewById(R.id.video);
        b3=(Button)findViewById(R.id.up1);
        b4=(Button)findViewById(R.id.up2);
        iv=(ImageView)findViewById(R.id.imageView);
        vv=(VideoView)findViewById(R.id.vv1) ;
        dr1= FirebaseDatabase.getInstance().getReference("Contact_info").child(Login.cno).child("images");
        dr2=FirebaseDatabase.getInstance().getReference("Contact_info").child(Login.cno).child("video");
        sr=FirebaseStorage.getInstance().getReference(Login.cno);
        i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setType("image/*");
                startActivityForResult(i, PICK_IMAGE);
            }
        });

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                i.setType("video/*");
                startActivityForResult(i,PICK_VIDEO);
            }
        });
        b3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(Gallery.this,ImagesActivity.class);
                startActivity(i);

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(Gallery.this,VideoActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO) {
            if (resultCode == RESULT_OK) {
                iv.setVisibility(View.GONE);vv.setVisibility(View.VISIBLE);
                uri = data.getData();
                if (uri != null) {
                    //displaying a progress dialog while upload is going on
                    pd = new ProgressDialog(this);
                    pd.setTitle("Video Uploading");
                    pd.show();
                    Calendar c=Calendar.getInstance();
                    int hour=c.get(Calendar.HOUR_OF_DAY);
                    int mint=c.get(Calendar.MINUTE);
                    int sec=c.get(Calendar.SECOND);
                    String pics=hour+""+mint+""+sec;

                    StorageReference vref=sr.child("Videos");
                    StorageReference v1ref=vref.child(pics + "." + getImageExtension(uri));
                    v1ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            //if the upload is successfull
                            //hiding the progress dialog
                            pd.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            UploadVideo uploadVideo = new UploadVideo(taskSnapshot.getDownloadUrl().toString());
                            String uploadId = dr2.push().getKey();
                            dr2.child(uploadId).setValue(uploadVideo);
                        }
                    }).addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception exception)
                        {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            pd.dismiss();
                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            //displaying percentage in progress dialog
                            pd.setMessage(" Video Uploaded " + ((int) progress) + "%...");
                        }
                    });
                }
                else
                {
                    Toast.makeText(this, "Please select Video...", Toast.LENGTH_SHORT).show();
                }
                vv.setVideoURI(uri);
                vv.requestFocus();
                vv.start();
            }
        } else if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                vv.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
                uri = data.getData();
                if (uri != null) {
                    //displaying a progress dialog while upload is going on
                    pd= new ProgressDialog(this);
                    pd.setTitle(" Image Uploading");
                    pd.show();
                    Calendar c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int mint = c.get(Calendar.MINUTE);
                    int sec = c.get(Calendar.SECOND);
                    String pics = hour + "" + mint + "" + sec;

                    StorageReference riversRef = sr.child("images");
                    StorageReference riversRef1 = riversRef.child(pics + "." + getImageExtension(uri));

                    riversRef1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            pd.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(taskSnapshot.getDownloadUrl().toString());
                            String uploadId = dr1.push().getKey();
                            dr1.child(uploadId).setValue(upload);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            pd.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            pd.setMessage("Image Uploaded " + ((int) progress) + "%...");
                        }
                    });
                } else {
                    Toast.makeText(this, "Please select an image......", Toast.LENGTH_SHORT).show();
                }
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    iv.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private String getImageExtension(Uri uri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mtp=MimeTypeMap.getSingleton();
        return mtp.getExtensionFromMimeType(cr.getType(uri));
    }
}
