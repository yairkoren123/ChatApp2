package com.example.chatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.The_Interfaces.CheckTheData;
import com.example.chatapp.R;
import com.example.chatapp.the_class.Singleton;
import com.example.chatapp.the_class.The_profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class SignUp_Fragment extends Fragment {

    // interface

    CheckTheData go_to_check;

    Button create;
    EditText et_pass,et_email,et_phone;

    FirebaseAuth mAuth;


    String email = "",phone = "",password = "";

    public SignUp_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        create = getView().findViewById(R.id.Create_accout_up);

        et_email = getView().findViewById(R.id.email_up);
        et_pass = getView().findViewById(R.id.pass_up);
        et_phone = getView().findViewById(R.id.phone_up);

        go_to_check = (CheckTheData) getActivity();



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = et_pass.getText().toString().trim();
                email = et_email.getText().toString().trim();
                phone = et_phone.getText().toString().trim();
                if (password.equals("") || email.equals("") || phone.equals("")) {
                    Toast.makeText(getActivity(), "Empty failed.",
                            Toast.LENGTH_SHORT).show();
                } else {

                    mAuth = FirebaseAuth.getInstance();

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("UP", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        email = mAuth.getCurrentUser().getEmail();
                                        set_in_Firestore();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("UP", "createUserWithEmail:failure  : " + task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }


    private void set_in_Firestore(){



        Singleton singleton = Singleton.getInstance();

        The_profile the_profile = new The_profile();


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Log.d("time", "onComplete: " + dtf.format(now));


        the_profile.setLast_connected(dtf.format(now));
        the_profile.setName(email);
        the_profile.setPhone(phone);
        the_profile.setNow_connected(true);


        ArrayList<String> s = new ArrayList<>();




        the_profile.setFriend_of_the_profile(s);


        singleton.setThe_profile(the_profile);

        FirebaseFirestore db = FirebaseFirestore.getInstance();






        Task<Void> future = db.collection("USERS").document(phone).set(the_profile);


//        Intent intent = new Intent(getActivity(), ChatActivity.class);
//        startActivity(intent);
//        getActivity().finish();


        //onupdate(4);
        //getActivity().onBackPressed();

        // call the interface
        go_to_check.start_now();

    }

}