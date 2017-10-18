package com.example.staffonechristian.seeksubstitute;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by staffonechristian on 2017-10-18.
 */

public class ScheduleAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        ArrayList<ScheduleData> scheduleDatas = new ArrayList<ScheduleData>();
        Context context;
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout_schedule,parent,false);
                return new ScheduleViewHolder(view);
        }

        public ScheduleAdaptor(ArrayList<ScheduleData> data, Context context){
            this.context = context;
            scheduleDatas = data;

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ScheduleData scheduleData = scheduleDatas.get(position);
            ((ScheduleViewHolder)holder).subject.setText(scheduleData.getSubject());
            ((ScheduleViewHolder)holder).time.setText(scheduleData.getTime());
            ((ScheduleViewHolder)holder).date.setText(scheduleData.getDate());
            ((ScheduleViewHolder)holder).country.setText(scheduleData.getCountry());
            ((ScheduleViewHolder)holder).schoolName.setText(scheduleData.getSchoolName());

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

            public ScheduleViewHolder(View itemView) {
                super(itemView);
                this.subject =  itemView.findViewById(R.id.subjectName);
                this.time = itemView.findViewById(R.id.time);
                this.date = itemView.findViewById(R.id.date);
                this.country = itemView.findViewById(R.id.countryName);
                this.schoolName = itemView.findViewById(R.id.schoolName);

            }
        }
}
