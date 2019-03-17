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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minor.attendance.Models.Faculty;
import com.minor.attendance.Models.student;

public class FacultyLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText email,password,facultytype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);
        mAuth = FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.Facultyemail);
        password=(EditText)findViewById(R.id.editText3);
        facultytype=(EditText)findViewById(R.id.facultytype1);
        Button button=(Button)findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                        .addOnCompleteListener(FacultyLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("faculties").child(task.getResult().getUser().getUid());


                                    mDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            if(dataSnapshot.exists()){

                                                Log.e("FacultyLogin", "onDataChange: "+dataSnapshot.getValue().toString());
                                                Faculty f=dataSnapshot.getValue(Faculty.class);
                                                Log.e("FacultyLogin", "onDataChange: "+f.facultytype);

                                                if(f.facultytype.equals("FACULTY")){
                                                    Intent intentResident = new Intent(FacultyLogin.this, FacultyPage.class);
                                                    startActivity(intentResident);

                                                }

                                                else{

                                                    Toast.makeText(FacultyLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                            else{
                                                Toast.makeText(FacultyLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
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
