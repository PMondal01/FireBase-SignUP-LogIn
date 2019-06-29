package com.example.loginwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_email, editText_pass;
    private Button button;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign in Activity");

        editText_email=findViewById(R.id.email_signup);
        editText_pass=findViewById(R.id.pass_signup);
        progressBar=findViewById(R.id.progress);

        button=findViewById(R.id.btn_signup);

        button.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {

        switch ((view.getId()))
        {


            case R.id.btn_signup:
                Log.d("key","Hello");
                userRegister();

                break;




        }

    }

    private void userRegister() {

        String email= editText_email.getText().toString().trim();
        String password=editText_pass.getText().toString().trim();

        if(email.isEmpty())
        {
            editText_email.setError("Enter email ");
            editText_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editText_email.setError("Enter Valid email");
            editText_email.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            editText_email.setError("Enter password ");
            editText_email.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            editText_email.setError("Enter Valid password 6 digit");
            editText_email.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {


                    Toast.makeText(getApplicationContext(),"Registration Done",Toast.LENGTH_LONG).show();


                } else {

                           if(task.getException()instanceof FirebaseAuthUserCollisionException)
                           {
                               Toast.makeText(getApplicationContext(),"User already Registered",Toast.LENGTH_LONG).show();

                           }else
                           {
                               Toast.makeText(getApplicationContext(),"Error :" +task.getException().getMessage(),Toast.LENGTH_LONG).show();

                           }

                }

            }
        });
    }
}
