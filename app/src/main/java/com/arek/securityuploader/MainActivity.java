package com.arek.securityuploader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
{
    EditText et1,et2,et3,et4;
    String name,mob,pass,repass;
    SharedPreferences sp;
    boolean result;
    SharedPreferences.Editor spe;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText)findViewById(R.id.Name);
        et2=(EditText)findViewById(R.id.Mobileno);
        et3=(EditText)findViewById(R.id.Password);
        et4=(EditText)findViewById(R.id.rePassword);
        sp=getSharedPreferences("mydata",MODE_PRIVATE);
        spe=sp.edit();
    }
    protected void onStart()
    {
        super.onStart();
        result=sp.getBoolean("k",false);
        if(result) {
            finish();
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }

    }

    public void Register(View v)
    {
        pd=new ProgressDialog(this);
        name = et1.getText().toString().trim();
        mob = et2.getText().toString().trim();
        pass = et3.getText().toString().trim();
        repass = et4.getText().toString().trim();
        if (name.isEmpty()) {
            et1.setError("Empty");
            et1.requestFocus();
        } else if (mob.isEmpty()) {
            et2.setError("Empty");
            et2.requestFocus();
        } else if (pass.isEmpty()) {
            et3.setText("Empty");
            et3.requestFocus();
        } else if (repass.isEmpty()) {
            et4.setText("Empty");
            et4.requestFocus();
        } else if (repass.equals(pass))
        {
            pd.setMessage("Please wait.....");
            pd.show();
            String password="";
            char[] val= pass.toCharArray();
            for(int i=val.length-1;i>=0;i--)
            {
                password=password+val[i];
            }

            MyDataHandler my = new MyDataHandler(name, password);
            FirebaseDatabase fd = FirebaseDatabase.getInstance();
            DatabaseReference dr = fd.getReference("Contact_info").child(mob);
            dr.setValue(my);
            spe.putBoolean("k",true);
            spe.commit();
            finish();
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            //   finish();

            Toast.makeText(MainActivity.this, "Registration done", Toast.LENGTH_SHORT).show();
        } else {
            et3.setText("");
            et4.setText("");
            Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
            et3.requestFocus();

        }
    }

}