package com.example.happymind;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText email, pass;
    TextView signUp;
    Button buttonLogin;
    String emailPattern="[a-zA-Z0-9.-_]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextEmailin);
        pass = findViewById(R.id.editTextPassin);
        buttonLogin = findViewById(R.id.buttonSignIn);
        signUp=findViewById(R.id.tvSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill = email.getText().toString();
                String passs = pass.getText().toString();
                if(TextUtils.isEmpty(emaill)||TextUtils.isEmpty(passs)){
                    Toast.makeText(SignInActivity.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                } else if (!emaill.matches(emailPattern)) {
                    Toast.makeText(SignInActivity.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.signInWithEmailAndPassword(emaill, passs).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                try {
                                    Intent intent = new Intent(SignInActivity.this, MainUI.class);
                                    startActivity(intent);
                                    finish();
                                } catch (Exception e) {
                                    Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }       }
        });
    }
}