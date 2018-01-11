package com.example.staffonechristian.seeksubstitute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    Button submit;
    EditText name,email,password,confirmPassword,subject,country,qualification,bio;
    FirebaseAuth firebaseAuth;
    LinearLayout progressLinear, wholeLinear;
    public static Activity registration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        submit = findViewById(R.id.submitEmail);
        name= findViewById(R.id.nameR);
        email = findViewById(R.id.emailR);
        password = findViewById(R.id.passwordR);
        confirmPassword = findViewById(R.id.confirmPasswordR);
        subject= findViewById(R.id.subjectR);
        country= findViewById(R.id.countryR);
        qualification= findViewById(R.id.qualificationR);
        bio = findViewById(R.id.bioR);
        progressLinear = findViewById(R.id.progressLinear);
        wholeLinear = findViewById(R.id.wholeLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        registration = this;


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nameS,emailS,passS,confirmPassS,subjectS,countryS,qualificationS,bioS;
                nameS=name.getText().toString().trim();
                emailS = email.getText().toString().trim();
                passS = password.getText().toString().trim();
                confirmPassS = confirmPassword.getText().toString().trim();
                subjectS=subject.getText().toString().trim();
                countryS=country.getText().toString().trim();
                qualificationS=qualification.getText().toString().trim();
                bioS = bio.getText().toString().trim();
                if(confirmPassS.equals(passS))
                {
                    if(name.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter Name before updating profile", Toast.LENGTH_LONG).show();
                    }
                    else if(subject.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter Subject before updating profile", Toast.LENGTH_LONG).show();
                    }
                    else if(country.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter country before updating profile", Toast.LENGTH_LONG).show();
                    }
                    else if(qualification.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Enter qualification before updating profile", Toast.LENGTH_LONG).show();
                    }
                    else if (email != null && passS != null && confirmPassS != null ) {

                        wholeLinear.setVisibility(View.GONE);
                        progressLinear.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(emailS, passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                    UserData userData = new UserData();
                                  //  userData.setBio(bio.getText().toString().trim());
                                    userData.setEmail(emailS);
                                    userData.setFullname(nameS);
                                    userData.setSubject(subjectS);
                                    userData.setCountry(countryS);
                                    userData.setBio(bioS);
                                    userData.setQualification(qualificationS);
                                    databaseReference.child("UsersData").child(FirebaseAuth.getInstance()
                                            .getCurrentUser()
                                            .getUid())
                                            .setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("email",emailS);
                                            bundle.putString("pass",passS);
                                            //  bundle.putString("name",nameS);
                                            // bundle.putString("country",countryS);
                                            //bundle.putString("sub",subjectS);
                                            //bundle.putString("quali",qualificationS);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }
                                    });


                                } else {
                                    wholeLinear.setVisibility(View.VISIBLE);
                                    progressLinear.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Try again Please", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }else{
                        Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter same passowrd in both field", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
