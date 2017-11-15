package com.example.staffonechristian.seeksubstitute;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecondCreateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecondCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondCreateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView timeImage,calendarImage;
    TextView calendarText;
    public static TextView timeText;
    private OnFragmentInteractionListener mListener;
    static String eventTimeString;
    public SecondCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SecondCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondCreateFragment newInstance() {
        SecondCreateFragment fragment = new SecondCreateFragment();
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
        View view = inflater.inflate(R.layout.fragment_second_create, container, false);
        // Inflate the layout for this fragment
        timeImage = view.findViewById(R.id.timeIcon);
        calendarImage = view.findViewById(R.id.calendarIcon);
        timeText = view.findViewById(R.id.timeText);
        calendarText = view.findViewById(R.id.calendarText);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        calendarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getFragmentManager();
                TimePicker timePicker = new TimePicker();
                timePicker.show(fm,"TimePicker");
            }
        });
        timeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getFragmentManager();
                TimePicker timePicker = new TimePicker();
                timePicker.show(fm,"TimePicker");
            }
        });

        CreateSchedule.nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(timeText.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getContext(),"Please Enter Time before moving on.",Toast.LENGTH_LONG).show();
                }else if(calendarText.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(),"Please Enter Date before moving on.",Toast.LENGTH_LONG).show();
                }else {
                    CreateSchedule.scheduleData.setDate(calendarText.getText().toString());
                    CreateSchedule.scheduleData.setTime(timeText.getText().toString());
                    Fragment fragment = LastCreateFragment.newInstance();
                    FragmentTransaction(fragment);
                }
            }
        });

    }
    public void FragmentTransaction(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(fragment.getClass().toString());
        transaction.commit();
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        FragmentManager fm = getActivity().getFragmentManager();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(fm, "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            calendarText.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(year));
        }
    };



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
