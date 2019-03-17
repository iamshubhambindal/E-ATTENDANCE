package com.minor.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minor.attendance.Models.student;

import java.util.Objects;

public class StudentLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText email,password,studenttypec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        mAuth = FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.StudentEmail);
        password=(EditText)findViewById(R.id.editText4);
        studenttypec=(EditText)findViewById(R.id.studenttypec);
        Button button=(Button)findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                        .addOnCompleteListener(StudentLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("students").child(task.getResult().getUser().getUid());


                                    mDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            if(dataSnapshot.exists()){

                                                Log.e("StudentLogin", "onDataChange: "+dataSnapshot.getValue().toString());
                                                student s=dataSnapshot.getValue(student.class);
                                                Log.e("StudentLogin", "onDataChange: "+s.studentype);

                                                if(s.studentype.equals("STUDENT")){
                                                    Intent intentResident = new Intent(StudentLogin.this, Students.class);
                                                    startActivity(intentResident);

                                                }

                                                else{

                                                    Toast.makeText(StudentLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                            else{
                                                Toast.makeText(StudentLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                // ...


                             @Override
                              public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                              });

                         }
            }});
        }
    });
}
    }

