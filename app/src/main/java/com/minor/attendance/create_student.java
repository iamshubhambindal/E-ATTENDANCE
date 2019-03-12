package com.minor.attendance;

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
import com.minor.attendance.Models.student;

public class create_student extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText email2,password2,name,studentid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);
        Button button1=(Button)findViewById(R.id.button12);
        email2=(EditText)findViewById(R.id.editText12);
        password2=(EditText)findViewById(R.id.editText8);
        mAuth = FirebaseAuth.getInstance();
        name =(EditText)findViewById(R.id.editText7);
        studentid =(EditText)findViewById(R.id.editText10);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Create Student", "onClick: "+email2.getText().toString()+password2.getText().toString());
                mAuth.createUserWithEmailAndPassword(email2.getText().toString().trim(), password2.getText().toString().trim())
                        .addOnCompleteListener(create_student.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    mDatabase = FirebaseDatabase.getInstance().getReference();

                                    student student1=new student(name.getText().toString(),email2.getText().toString());
                                    Log.e("Create Student", "onComplete: "+student1.name);
                                    Toast.makeText(create_student.this, "Authentication Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    mDatabase.child("students").child(studentid.getText().toString()).setValue(student1);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("create student activity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(create_student.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });
            }
        });
    }
}
