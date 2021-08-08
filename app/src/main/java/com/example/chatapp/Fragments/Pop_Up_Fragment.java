package com.example.chatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.R;
import com.example.chatapp.the_class.Singleton;
import com.example.chatapp.the_class.The_profile;
import com.squareup.picasso.Picasso;


public class Pop_Up_Fragment extends Fragment {

    // layout
    ConstraintLayout main_constrain;
    TextView name_of_popUP;
    ImageView image_of_popUp , massage_image__button_popUP;

    The_profile the_profile_popUp = new The_profile();

    Singleton singleton = Singleton.getInstance();





    public Pop_Up_Fragment() {
        // Required empty public constructor
    }

    public Pop_Up_Fragment(The_profile the_profile_popUp) {
        this.the_profile_popUp = the_profile_popUp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pop__up_, container, false);

        main_constrain = view.findViewById(R.id.constrain_popUP);
        name_of_popUP = view.findViewById(R.id.name_profile_popUP);
        image_of_popUp = view.findViewById(R.id.image_profile_popUP);
        massage_image__button_popUP = view.findViewById(R.id.send_message_button_popUP);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main_constrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // close the current frag
                Fragment fragment = Pop_Up_Fragment.this;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();
            }
        });

        massage_image__button_popUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleton = Singleton.getInstance();
                singleton.setGo_massage_from_find(the_profile_popUp);
                Navigation.findNavController(view).navigate(R.id.action_host_Tabs_Fragment_to_messages_Fragment);
            }
        });
        // name of popUP
        name_of_popUP.setText(the_profile_popUp.getName());
        // image of popUP
        String url = the_profile_popUp.getImage().toString();
        if (url ==  null || url == "" ){
            // no image
            image_of_popUp.setImageResource(R.drawable.no_image);
        }else {
            // we have a image
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(image_of_popUp);
        }






    }
}