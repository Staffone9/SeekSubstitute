package com.example.staffonechristian.seeksubstitute;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  createLecture();
        Fragment fragment;

        fragment = new FragmentSchedule();
        TransactionOfFragment(fragment);


    }
    public void TransactionOfFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

    private void createLecture() {
        ScheduleData scheduleData = new ScheduleData();
        scheduleData.setCountry("Australia");
        scheduleData.setDate("July 10,2018");
        scheduleData.setTime("11 : 30 AM");
        scheduleData.setSchoolName("University of Melbourne");
        scheduleData.setSirID(FirebaseAuth.getInstance().getCurrentUser().getUid());
        scheduleData.setSubject("Mobile Application Development");
        scheduleData.setSirName("James Austin");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference scheduleDb = databaseReference.child("seeksubstitute").child("schedule").child(scheduleData.getCountry())
                .child(scheduleData.getSchoolName()).child(scheduleData.getSubject()).push();
        scheduleData.setLectureId(scheduleDb.getKey());
        scheduleDb.setValue(scheduleData);


        CountrySchoolSubjectData countrySchoolSubjectData = new CountrySchoolSubjectData();
        countrySchoolSubjectData.setSubject(scheduleData.getSubject());
        countrySchoolSubjectData.setCountry(scheduleData.getCountry());
        countrySchoolSubjectData.setSchool(scheduleData.getSchoolName());

        DatabaseReference writeDb = databaseReference.child("seeksubstitute").child("ScheduleDetail").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push();
        writeDb.setValue(countrySchoolSubjectData);
    }
}
