package com.example.chatapp.Tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.Activites.ChatActivity;
import com.example.chatapp.Activites.Find_Friends_Activity;
import com.example.chatapp.Apdters.Acount_Adapter;
import com.example.chatapp.Fragments.Pop_Up_Fragment;
import com.example.chatapp.R;
import com.example.chatapp.The_Interfaces.CheckTheData;
import com.example.chatapp.The_Interfaces.ItemClickListener;
import com.example.chatapp.The_Interfaces.Start_Tab_two;
import com.example.chatapp.the_class.Singleton;
import com.example.chatapp.the_class.The_messages;
import com.example.chatapp.the_class.The_profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class First_Tab_Fragment extends Fragment implements ItemClickListener {
    // layout

    The_profile the_profile_selected = new The_profile();

    RecyclerView recyclerView;

    ArrayList<String> the_friend_of_the_profile_update = new ArrayList<>();

    ArrayList<String> friend_String_names = new ArrayList<>();



    Acount_Adapter adapter;
    Fragment fragment;
    FrameLayout frame_of_pop_up;
    The_profile the_profile = new The_profile();

    Singleton singleton = Singleton.getInstance();

    ArrayList<The_profile> theProfileArrayList_all =  new ArrayList<>();

    ArrayList<The_profile> the_friends = new ArrayList<>();

    ArrayList<The_profile> friend_of_profile_by_class = new ArrayList<>();

    //firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    // interface
    //Start_Tab_two go_now;


    public First_Tab_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first__tab_, container, false);

        //interface

        return view;
    }

    @Override
    public void onResume() {
        Log.d("reee", "onResume: lo");
        singleton = Singleton.getInstance();



        the_friend_of_the_profile_update = singleton.getThe_profile().getFriend_of_the_profile();

        The_profile the_profile_selected_from_find = singleton.getGo_massage_from_find();

        if (the_profile_selected_from_find != null) {


            boolean is_there = false;
            for (String x : the_friend_of_the_profile_update) {
                if (the_profile_selected_from_find.getName().equals(x)) {
                    is_there = true;
                    break;
                }
            }
            if (is_there == false) {
                the_friend_of_the_profile_update.add(the_profile_selected_from_find.getName());
                Log.d("adds1", "onResume: " + the_profile_selected_from_find.getName());

                singleton.getThe_profile().setFriend_of_the_profile(the_friend_of_the_profile_update);

                for (String s : the_friend_of_the_profile_update) {
                    Log.d("adds", "onResume: " + s);

                }
                // update the friend list in the data base
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("USERS").document(the_profile.getPhone())
                        .update("friend_of_the_profile", FieldValue.arrayUnion(the_profile_selected_from_find.getName()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                friend_String_names = the_friend_of_the_profile_update;
                                singleton.getThe_profile().setFriend_of_the_profile(the_friend_of_the_profile_update);

                                Toast.makeText(getActivity().getApplicationContext(),"add friend to the list",Toast.LENGTH_LONG)
                                        .show();
                                get_friend_profile();
                            }
                        });

                // update the value of the friend of the profile
            }
            is_there = false;
        }


        super.onResume();
    }


    private void get_friend_profile() {

        the_profile = singleton.getThe_profile();

        //todo get the user like the names_list(the_friend_of_the_profile_update) from the data base
        theProfileArrayList_all = singleton.getAll_account();

        Log.d("all_account_size", "update_friend_list_adapter_tab_one: " + theProfileArrayList_all.size());


        // enter all the profiles(that your friends) to the (the friends)


        db.collection("USERS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                friend_String_names = singleton.getThe_profile().getFriend_of_the_profile();
                Log.d("qwqw", "setadpter1: " +friend_String_names.size());


                for (String name_to_search : friend_String_names) {

                    Log.d("ppp", "onComplete: " + task.toString());
                    Log.d("nnn1", "onComplete: " + task.getResult().getDocuments());
                    for (DocumentSnapshot dataSnapshot : task.getResult().getDocuments()) {
                        String name_the_currnt_doc = dataSnapshot.get("name").toString();
                        Log.d("names", "onComplete: " + name_the_currnt_doc);

                        if (name_to_search.equals(name_the_currnt_doc)) {
                            Log.d("yep", "onComplete: " + name_to_search);
                            The_profile the_profile_friend = dataSnapshot.toObject(The_profile.class);
                            Log.d("friend_profile_ww", "onComplete: " + the_profile_friend.getName());
                            // check is is already in the list .. if not we add


                            boolean is_there = false;

                            if (friend_of_profile_by_class.size() != 0) {

                                The_profile need_add = new The_profile();

                                for (The_profile q : friend_of_profile_by_class) {

                                    if (the_profile_friend.getName().equals(q.getName())) {
                                        is_there = true;
                                        break;
                                    }
//                                }else if (q.getName().equals("")){
//                                    // empty
//                                    is_there = true;
//                                    break;
//                                }
                                    need_add = the_profile_friend;
                                }

                                if (is_there == false) {
                                    // we add the profile to the friend profile
                                    friend_of_profile_by_class.add(need_add);
                                    Log.d("string_profilew", "onComplete: " + need_add.toString());
                                }
                            }else {
                                // is friend_of_profile_by_class.size == 0
                                friend_of_profile_by_class.add(the_profile_friend);
                            }


                        }
                    }
                }
                setadpter();


//                for (The_profile s : theProfileArrayList_all) {
//                    for (String friends : friend_String_names) {
//                        if (friends.equals(s.getName())) {
//                            the_friends.add(s);
//                            Log.d("friend_list_add", "update_friend_list_adapter_tab_one: " + s.getName());
//                        }
//                    }
//                }

            }
        });
    }
    private void setadpter(){
        Boolean status_show = false;



        if (friend_of_profile_by_class.size() == 0) {


        }else {

            singleton = Singleton.getInstance();

            singleton.setFriend_of_profile_by_class(friend_of_profile_by_class);



            Log.d("qwqw", "setadpter2: " + friend_of_profile_by_class.get(0).toString());
            //go_now.go_ahead();

            adapter = new Acount_Adapter(getContext(), friend_of_profile_by_class, status_show);
            adapter.setClickListener(this::onItemClick);

            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        FloatingActionButton fab = getView().findViewById(R.id.fab_search);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Find_Friends_Activity.class);
                startActivity(intent);
            }
        });



        frame_of_pop_up = view.findViewById(R.id.pop_up_first_mail);
        frame_of_pop_up.setVisibility(View.INVISIBLE);


        // data to populate the RecyclerView with

        // set up the RecyclerView
        recyclerView = getView().findViewById(R.id.rec_host_tab1);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        Boolean status_show = false;
        Singleton singleton = Singleton.getInstance();


//
//        the_friends = new ArrayList<>();
//
//        for (The_profile s : theProfileArrayList_all ){
//            for (String friends : friend_String_names){
//
//                if (friends.equals(s.getName())){
//                    the_friends.add(s);
//                }
//            }
//        }
//        adapter = new Acount_Adapter(getContext(),the_friends ,status_show);
//        adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);

        get_friend_profile();

    }


    @Override
    public void onItemClick(View view, int position) {

            Log.d("pos_first", "onItemClick: click on " + position + " view " + view.getId());

            int imageID = view.getId();

            // TODO GIVE THE PROFILE TO THE FRAGMENT
            // if click on the image
            if (imageID == R.id.image_account_row) {
                frame_of_pop_up.setVisibility(View.VISIBLE);
                Log.d("s", "onItemClick: yes yes yes ");
                fragment = new Pop_Up_Fragment(friend_of_profile_by_class.get(position));
                PopUpreplce(fragment);

            } else if (frame_of_pop_up.getVisibility() == View.VISIBLE) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                frame_of_pop_up.setVisibility(View.INVISIBLE);

            } else {
                the_profile_selected = friend_of_profile_by_class.get(position);
                singleton.setGo_massage_from_find(the_profile_selected);
                Navigation.findNavController(view).navigate(R.id.action_host_Tabs_Fragment_to_messages_Fragment);
            }

    }

    public void PopUpreplce(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.pop_up_first_mail, someFragment, "my_popup");
        transaction.addToBackStack("my_popup");
        transaction.commit();
    }
}

