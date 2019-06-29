package com.example.loginwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_email, editText_pass;
    private Button button_login;
    private TextView textView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText_email=findViewById(R.id.email_login);
        editText_pass=findViewById(R.id.pass_login);
        button_login=findViewById(R.id.btn_login_login);
        textView=findViewById(R.id.textview);
        progressBar=findViewById(R.id.progress);

        button_login.setOnClickListener(this);
        textView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {

        switch ((view.getId()))
        {


            case R.id.btn_login_login:

                userLogin();
                break;


            case R.id.textview:

                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;
        }




    }

    private void userLogin() {

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

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {





                if(task.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);


                   Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                   intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                    progressBar.setVisibility(View.GONE);

                }else
                {
                    Toast.makeText(getApplicationContext(),"Error :" +task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }

            }
        });
    }


}























