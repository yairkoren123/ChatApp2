package com.example.chatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.R;
import com.example.chatapp.the_class.The_status;
import com.squareup.picasso.Picasso;


public class ScreenSlidePageFragment extends Fragment {
    The_status the_status_pos = new The_status();

    // layout
    ImageView image_status_pos ;
    TextView name_status_pos , time_status_pos;

    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }
    public ScreenSlidePageFragment(The_status arrayList) {
        this.the_status_pos = arrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image_status_pos = view.findViewById(R.id.imageView_status_full);
        name_status_pos = view.findViewById(R.id.textView_name_full);
        time_status_pos = view.findViewById(R.id.textView_time_full);

        Log.d("pos_frag", "onViewCreated: " + the_status_pos.toString());
        Picasso.get()
                .load(the_status_pos.getImage_status())
                .placeholder(R.drawable.background1)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(image_status_pos);

        name_status_pos.setText(the_status_pos.getName());
        time_status_pos.setText(the_status_pos.getTime_for_the_image());


    }
}