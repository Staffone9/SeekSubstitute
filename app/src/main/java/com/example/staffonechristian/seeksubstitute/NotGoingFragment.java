package com.example.staffonechristian.seeksubstitute;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotGoingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotGoingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotGoingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private android.support.v7.widget.RecyclerView scheduleRecyclerView;
    ArrayList<ScheduleData> scheduleDatas;
    ArrayList<CountrySchoolSubjectData> countrySchoolSubjectDatas;
    ScheduleAdaptor scheduleAdaptor;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean notgoing=false;
    LinearLayout progressLinear;

    SwipeRefreshLayout swipeRefreshLayout;
    private OnFragmentInteractionListener mListener;

    public NotGoingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment NotGoingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotGoingFragment newInstance() {
        NotGoingFragment fragment = new NotGoingFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_not_going, container, false);
        // Inflate the layout for this fragment

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        scheduleDatas = new ArrayList<>();
        countrySchoolSubjectDatas = new ArrayList<>();
        scheduleRecyclerView = view.findViewById(R.id.scheduleRecycler);
        progressLinear = view.findViewById(R.id.progressLinear);
        scheduleAdaptor = new ScheduleAdaptor(scheduleDatas,getContext(),"notgoing");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        scheduleRecyclerView.setLayoutManager(linearLayoutManager);
        scheduleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scheduleRecyclerView.setAdapter(scheduleAdaptor);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressLinear.setVisibility(View.VISIBLE);
        finalRead();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame, NotGoingFragment.newInstance()).commit();
            }
        });
    }


    private void finalRead() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query scheduleDb;

        scheduleDb = databaseReference.child("seeksubstitute").child("NotGoingSchedule");


        scheduleDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {

                    for (DataSnapshot oneSnap:dataSnapshot.getChildren()) {
                        //    Toast.makeText(getContext(),"key"+oneSnap.getValue(String.class),Toast.LENGTH_SHORT).show();
                        ScheduleData scheduleData = new ScheduleData();
                        scheduleData = oneSnap.getValue(ScheduleData.class);

                        scheduleData.setTimeLong(oneSnap.child(scheduleData.getCountry()+"_"+scheduleData.getSchoolName()).getValue(Long.class));
                    //    Toast.makeText(getContext(),"check"+scheduleData.getTimeLong(),Toast.LENGTH_LONG).show();
                        scheduleDatas.add(scheduleData);
                        swipeRefreshLayout.setRefreshing(false);
                        scheduleAdaptor.notifyDataSetChanged();
                        progressLinear.setVisibility(View.GONE);
                    }
                }else{
                    progressLinear.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
