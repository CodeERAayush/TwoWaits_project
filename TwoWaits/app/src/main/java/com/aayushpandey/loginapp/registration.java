package com.aayushpandey.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registration extends AppCompatActivity {
private TextView login;
EditText email,pass,conformpass;
Button signup;
String emailPattern="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
ProgressDialog progressDialog;
FirebaseAuth mAuth;
FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        login=findViewById(R.id.alreadyaccount);
//        Intent intent = new Intent();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(new Intent(registration.this,MainActivity.class));
            }
        });
        email=findViewById(R.id.email);
        pass=findViewById(R.id.Pass);
        conformpass=findViewById(R.id.confirmpassword);
        signup=findViewById(R.id.Signup);
        progressDialog=new ProgressDialog(this);
       mAuth=FirebaseAuth.getInstance();
       mUser=mAuth.getCurrentUser();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }

            private void performAuth() {
                String Email=email.getText().toString();
                String password=pass.getText().toString();
                String confPass=conformpass.getText().toString();

                if(!Email.matches(emailPattern)){
                    email.setError("Enter correct Email");

                }else if(password.isEmpty()||password.length()<6){
                    pass.setError("Enter proper password");
                }else if(!password.equals(confPass)){
                    conformpass.setError("password not match!");
                }else{
                    progressDialog.setMessage("please wait while Registration...");
                    progressDialog.setTitle("Registration");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                sendUserToNextActivity();
                                Toast.makeText(registration.this, "registration successfull", Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(registration.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(registration.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}