package com.example.branchmemo;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.text.SimpleDateFormat;

public class FirstFragment extends Fragment {
    private static Handler mHandler ;
    TextView Date_top_1, Date_top_2, Date_bottom;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        Date_top_1 = view.findViewById(R.id.a_view);
        Date_top_2 = view.findViewById(R.id.time_view);
        Date_bottom = view.findViewById(R.id.date_view);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Calendar cal = Calendar.getInstance() ;
                SimpleDateFormat ap = new SimpleDateFormat("a");
                SimpleDateFormat time = new SimpleDateFormat("hh:mm");
                SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
                Date_top_1.setText(ap.format(cal.getTime()));
                Date_top_2.setText(time.format(cal.getTime()));
                Date_bottom.setText(date.format(cal.getTime()));
            }
        };

        class NewRunnable implements Runnable {
            Calendar cal = Calendar.getInstance();

            @Override
            public void run() {
                while (true) {
                    mHandler.sendEmptyMessage(0) ;
                    try {
                        Thread.sleep(1000) ;
                    } catch (Exception e) {
                        e.printStackTrace() ;
                    }
                }
            }
        }
        NewRunnable nr = new NewRunnable() ;
        Thread t = new Thread(nr) ;
        t.start() ;


        view.findViewById(R.id.newBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.actionBar.setDisplayHomeAsUpEnabled(true);
                MainActivity.actionBar.setDisplayShowHomeEnabled(true);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }
}