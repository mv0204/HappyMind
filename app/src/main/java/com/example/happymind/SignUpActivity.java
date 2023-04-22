package com.example.happymind;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpActivity extends AppCompatActivity {
    EditText name,email,pass,cPass;
    Button buttonSignUp;
    ImageView img;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String emailPattern="[a-zA-Z0-9.-_]+@[a-z]+\\.+[a-z]+";

    ActivityResultLauncher<String > galleryLauncher;
    ProgressDialog progressDialog;
    Uri imageURI;
    String imageuri;
    TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().hide();

        setContentView(R.layout.activity_sign_up);
        name=findViewById(R.id.editTextName);
        email=findViewById(R.id.editTextEmail);
        pass=findViewById(R.id.editTextPass);
        cPass=findViewById(R.id.editTextCPass);
        buttonSignUp=findViewById(R.id.buttonSignUp);
        img=findViewById(R.id.imageView);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Establishing The Account");
//        progressDialog.setCancelable(false);

        signIn=findViewById(R.id.tvSignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });

        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();

        galleryLauncher=registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        imageURI=uri;
                    img.setImageURI(uri);

                    }
                });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryLauncher.launch("image/*");
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String namee=name.getText().toString();
            String passs=pass.getText().toString();
            String cPasss=cPass.getText().toString();
            String emaill=email.getText().toString();
            String status="Hi I am using this application";
            if(TextUtils.isEmpty(namee)||TextUtils.isEmpty(emaill)||TextUtils.isEmpty(passs)||TextUtils.isEmpty(cPasss)){
                Toast.makeText(SignUpActivity.this, "Enter Valid Information", Toast.LENGTH_SHORT).show();
            }
            else if(!emaill.matches(emailPattern)){
                email.setError("Enter a Valid Email");
                }
            else if (passs.length()<6) {
            pass.setError("Enter Long Pass");
            } else if (!passs.equals(cPasss)) {
                pass.setError("Pass Don't Matches");
                cPass.setError("Pass Don't Matches");
            }
            else {
                auth=FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(emaill,passs).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          String  id =task.getResult().getUser().getUid();
                          DatabaseReference reference=database.getReference().child("user").child(id);
                          StorageReference storageReference=storage.getReference("Upload").child(id);

                          if(imageURI!=null){
                              storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                  @Override
                                  public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                      if(task.isSuccessful()){
                                          storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                              @Override
                                              public void onSuccess(Uri uri) {
                                                imageuri=uri.toString();
                                                 Users users=new Users(imageuri,emaill,namee,passs,id,status);
                                                reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                   if(task.isSuccessful()){
                                                       Intent intent=new Intent(getApplicationContext(),MainUI.class);
                                                       startActivity(intent);
                                                       finish();
                                                   }
                                                   else {
                                                       Toast.makeText(SignUpActivity.this, "Error Creating User", Toast.LENGTH_SHORT).show();
                                                   }
                                                    }
                                                });
                                              }
                                          });
                                      }
                                  }
                              });
                          }
                          else {
                            String status="Hey I am using App";
                            imageuri="https://firebasestorage.googleapis.com/v0/b/happy-mind-ce92b.appspot.com/o/pngwing.png?alt=media&token=5388c026-4de8-4507-8ed4-0e4a1b1012d4";
                            Users users=new Users(imageuri,emaill,namee,passs,id,status);
                              reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                      if(task.isSuccessful()){
                                          Intent intent=new Intent(getApplicationContext(),MainUI.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                      else {
                                          Toast.makeText(SignUpActivity.this, "Error Creating User", Toast.LENGTH_SHORT).show();
                                      }
                                  }
                              });
                          }
                      }else {
                          Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      }
                    }
                });
            }
            }
        });




    }
}