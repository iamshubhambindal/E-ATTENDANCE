package com.minor.attendance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minor.attendance.Models.Faculty;


public class create_faculty extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText email1,password1,name,facultyid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_faculty);
        Button button=(Button)findViewById(R.id.button11);
        email1=(EditText)findViewById(R.id.editText11);
        name=(EditText)findViewById(R.id.editText5);
        password1=(EditText)findViewById(R.id.editText6);
        facultyid=(EditText)findViewById(R.id.editText9);
        mAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email1.getText().toString().trim(), password1.getText().toString().trim())
                        .addOnCompleteListener(create_faculty.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

// ...
                                    mDatabase = FirebaseDatabase.getInstance().getReference();

                                    Faculty faculty=new Faculty(name.getText().toString(),email1.getText().toString());
                                    Log.e("Create Faculty", "onComplete: "+faculty.name);
                                    Toast.makeText(create_faculty.this, "Authentication Successful.",
                                            Toast.LENGTH_SHORT).show();

                                    mDatabase.child("faculties").child(facultyid.getText().toString()).setValue(faculty);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("create faculty activity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(create_faculty.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });
            }
        });
    }
}
