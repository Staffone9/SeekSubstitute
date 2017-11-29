package com.example.staffonechristian.seeksubstitute;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.staffonechristian.seeksubstitute.Notification.NotificationHandling;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by staffonechristian on 2017-10-18.
 */

public class ScheduleAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        ArrayList<ScheduleData> scheduleDatas = new ArrayList<ScheduleData>();
        Context context;
        String from;
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout_schedule,parent,false);
                return new ScheduleViewHolder(view);
        }

        public ScheduleAdaptor(ArrayList<ScheduleData> data, Context context,String from){
            this.context = context;
            scheduleDatas = data;
            this.from = from;

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            final ScheduleData scheduleData = scheduleDatas.get(position);
            if(scheduleData!=null) {
                ((ScheduleViewHolder) holder).subject.setText(scheduleData.getSubject());
                ((ScheduleViewHolder) holder).time.setText(scheduleData.getTime());
                ((ScheduleViewHolder) holder).date.setText(scheduleData.getDate());
                ((ScheduleViewHolder) holder).country.setText(scheduleData.getCountry());
                ((ScheduleViewHolder) holder).schoolName.setText(scheduleData.getSchoolName());


                    ((ScheduleViewHolder) holder).fullLinear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            if(from.equals("going")) {
                                alert.setTitle("Confirmation");
                                alert.setMessage("Are you sure you don't want to go in this lecture?");
                            }else{
                                alert.setTitle("Confirmation");
                                alert.setMessage("Are you sure you will substitute this lecture?");
                            }
                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                //    Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
                                    if(from.equals("going")) {
                                        CancelLectur(scheduleData);
                                    }else{
                                        SubstitueLecture(scheduleData);
                                    }
                                    dialog.dismiss();
                                }
                            });

                            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                               //     Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });

                            alert.show();


                        }
                    });


            }
        }

    private void SubstitueLecture(final ScheduleData scheduleData) {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        ObjectMapper oMapper = new ObjectMapper();
        //  Toast.makeText(context,"Sir id"+scheduleData.getSirId(),Toast.LENGTH_LONG).show();
        final Map<String, Object> map = oMapper.convertValue(scheduleData, Map.class);


        long time = scheduleData.getTimeLong();

        map.put(scheduleData.getCountry()+"_"+scheduleData.getSchoolName(),time);
        map.put(scheduleData.getSchoolName()+"_"+scheduleData.getSubject(),time);
        map.put(scheduleData.getCountry()+"_"+scheduleData.getSubject(),time);
        map.put(scheduleData.getCountry()+"_"+scheduleData.getSchoolName(),time);
        map.put(scheduleData.getCountry()+"_"+scheduleData.getSchoolName()
                +"_"+scheduleData.getSubject(),time);
        map.put(FirebaseAuth.getInstance().getCurrentUser().getUid(),-1 *(new Date().getTime()));


        databaseReference.child("seeksubstitute")
                .child("schedule")
                .child(scheduleData.getLectureId())
                .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                databaseReference.child("seeksubstitute")
                        .child("NotGoingSchedule")
                        .child(scheduleData.getLectureId())
                        .removeValue();

                //fragment refresh
                if(from.equals("going")) {
                    android.support.v4.app.FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.frame, FragmentSchedule.newInstance()).commit();
                }else{
                    android.support.v4.app.FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.frame, NotGoingFragment.newInstance()).commit();
                }

            }
        });

    }

    private void CancelLectur(final ScheduleData scheduleData) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        ObjectMapper oMapper = new ObjectMapper();
      //  Toast.makeText(context,"Sir id"+scheduleData.getSirId(),Toast.LENGTH_LONG).show();
        final Map<String, Object> map = oMapper.convertValue(scheduleData, Map.class);


        long time = scheduleData.getTimeLong();

        map.put(scheduleData.getCountry()+"_"+scheduleData.getSchoolName(),time);
        map.put(scheduleData.getSchoolName()+"_"+scheduleData.getSubject(),time);
        map.put(scheduleData.getCountry()+"_"+scheduleData.getSubject(),time);
        map.put(scheduleData.getCountry()+"_"+scheduleData.getSchoolName(),time);
        map.put(scheduleData.getCountry()+"_"+scheduleData.getSchoolName()
                +"_"+scheduleData.getSubject(),time);
        map.put(FirebaseAuth.getInstance().getCurrentUser().getUid(),-1 *(new Date().getTime()));


        databaseReference.child("seeksubstitute")
                .child("NotGoingSchedule")
                .child(scheduleData.getLectureId())
                .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                databaseReference.child("seeksubstitute")
                        .child("schedule")
                        .child(scheduleData.getLectureId())
                        .removeValue();

                NotificationSending(scheduleData);
                //fragment refresh
                android.support.v4.app.FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.frame, FragmentSchedule.newInstance()).commit();

            }
        });

    }

    private void NotificationSending(ScheduleData scheduleData) {




        SimpleDateFormat month_date = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");


        Date date = null;
        try {
            date = sdf.parse(scheduleData.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String month_name = month_date.format(date);
        NotificationHandling notificationHandling = new NotificationHandling();
        notificationHandling.SendNotification("Lecture "+scheduleData.getSubject()+" is available for substitute on "+month_name);

    }

    @Override
        public int getItemCount() {
                if(scheduleDatas==null)
                {
                    return 0;
                }else{
                    return scheduleDatas.size();
                }
        }

        public static class ScheduleViewHolder extends RecyclerView.ViewHolder{

            TextView subject;
            TextView time;
            TextView date;
            TextView country;
            TextView schoolName;
            LinearLayout fullLinear;

            public ScheduleViewHolder(View itemView) {
                super(itemView);
                this.subject =  itemView.findViewById(R.id.subjectName);
                this.time = itemView.findViewById(R.id.time);
                this.date = itemView.findViewById(R.id.date);
                this.country = itemView.findViewById(R.id.countryName);
                this.schoolName = itemView.findViewById(R.id.schoolName);
                this.fullLinear = itemView.findViewById(R.id.fullLinear);

            }
        }
}
