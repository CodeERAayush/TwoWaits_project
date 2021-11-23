package com.aayushpandey.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPass extends AppCompatActivity {
private Button forgetbtn;
private EditText email;
 String Useremail;
private FirebaseAuth auth;
    String emailPattern="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        auth = FirebaseAuth.getInstance();
        email=findViewById(R.id.Recover);
        forgetbtn=findViewById(R.id.SendRec);

        progressDialog =new ProgressDialog(this);
        forgetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private void validateData() {
        Useremail=email.getText().toString();
        if(Useremail.isEmpty()){
           email.setError("Enter the email!");
        }else{
            forgetPassw();
        }
    }

    private void forgetPassw() {



                if (!Useremail.matches(emailPattern)) {
                    email.setError("Enter correct Email");

                } else {
                    progressDialog.setMessage("Sending Email......");
                    progressDialog.setTitle("Reset");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    auth.sendPasswordResetEmail(Useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                   Toast.makeText(forgetPass.this, "Check your mail", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(forgetPass.this,MainActivity.class));
                   finish();
               }else{
                                progressDialog.dismiss();
                   Toast.makeText(forgetPass.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
               }
                        }
                    });
                }
//               if(task.isSuccessful()){
//                   Toast.makeText(forgetPass.this, "Check your mail", Toast.LENGTH_SHORT).show();
//                   startActivity(new Intent(forgetPass.this,MainActivity.class));
//                   finish();
//               }else{
//                   Toast.makeText(forgetPass.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//               }
//            }
//        });
//    }
}}