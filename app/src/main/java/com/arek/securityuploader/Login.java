package com.arek.securityuploader;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Login extends AppCompatActivity
{
    EditText et1,et2;
    FirebaseDatabase fd;
    DatabaseReference dr;
    static String cno,pass,ps;
    MyDataHandler md;
    String pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et1=(EditText)findViewById(R.id.Uname);
        et2=(EditText)findViewById(R.id.password);
    }
    public void Login(View v)
    {
        cno = et1.getText().toString().trim();
        pass = et2.getText().toString().trim();
        if (cno.isEmpty())
        {

            et1.setError("Empty");
            et1.requestFocus();
        } else if (pass.isEmpty())
        {
            et2.setError("Empty");
            et2.requestFocus();
        } else
        {
            pswd="";
            char[] passw=pass.toCharArray();
            for(int i=passw.length-1;i>=0;i--)
            {
                pswd=pswd+passw[i];
            }
           // et2.setText(pswd);
            fd = FirebaseDatabase.getInstance();
            dr = fd.getReference("Contact_info");
            dr.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        String keys = ds.getKey();
                        md = ds.getValue(MyDataHandler.class);
                        ps = md.getPassword();
                        if (keys.equals(cno) && ps.equals(pswd))
                        {
                            Intent i = new Intent(Login.this, FingerPrint.class);
                            startActivity(i);
                        }
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            });
        }
        et1.setText("");
        et2.setText("");
    }

}
