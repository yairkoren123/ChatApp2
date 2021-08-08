package com.example.chatapp.Message;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chatapp.Apdters.Message_Adapter;
import com.example.chatapp.R;
import com.example.chatapp.the_class.Singleton;
import com.example.chatapp.the_class.The_messages;
import com.example.chatapp.the_class.The_profile;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Messages_Fragment extends Fragment {

    View view;
    The_profile the_profile_to_sent_massage = new The_profile();
    Singleton singleton = Singleton.getInstance();
    The_profile the_profile = new The_profile();

    RecyclerView recyclerView;


    // layout
    TextView  name_to_send_massage;

    EditText text_to_send;
    ImageButton send_button;

    String text_send_now = "";

    String to_search_by_phones = "";

    // firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference mRef = db.collection("Messages");
    DocumentReference mRef_2 = db.collection("Messages").document("942634To73171");

    Message_Adapter adapter;


    ArrayList<The_messages> arrayList_from_now = new ArrayList<>();

    DocumentSnapshot value_main;
    private Object ArrayList;

    public Messages_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_messages_, container, false);

        TextView textView = view.findViewById(R.id.textView2);
        name_to_send_massage = view.findViewById(R.id.name_to_send_massage);
        text_to_send = view.findViewById(R.id.et_send_messages);
        send_button = view.findViewById(R.id.button_send_messages);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleton.setGo_massage_from_find(null);
                // todo hide the key board
                Navigation.findNavController(view).navigate(R.id.action_messages_Fragment_to_host_Tabs_Fragment);
            }
        });



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Log.d("see", "onResume: lo");
                singleton = Singleton.getInstance();
                singleton.setGo_massage_from_find(null);
                Navigation.findNavController(view).navigate(R.id.action_messages_Fragment_to_host_Tabs_Fragment);

                //Navigation.findNavController(view).navigate(R.id.action_messages_Fragment_to_host_Tabs_Fragment);
//                Fragment fragment = Messages_Fragment.this;
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                fragmentTransaction.remove(fragment);
//                fragmentTransaction.commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        singleton = Singleton.getInstance();
        the_profile = singleton.getThe_profile();
        the_profile_to_sent_massage = singleton.getGo_massage_from_find();
        if (the_profile_to_sent_massage != null) {
            name_to_send_massage.setText(the_profile_to_sent_massage.getName());
        }
        setUpRecyclerView();
        //get_doc();
        // need to Search by two options (me_phone + To + phone_other
    }
    private void get_doc(){
        if (!the_profile.getPhone().equals("") && !the_profile_to_sent_massage.getPhone().equals("")) {

            to_search_by_phones = the_profile.getPhone() + "To" + the_profile_to_sent_massage.getPhone();
            Log.d("phonesss", "onViewCreated: " + to_search_by_phones);

            db.collection("Messages").document(to_search_by_phones).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, FirebaseFirestoreException error) {
                    if (value.exists()) {
                        Log.d("mess", "onEvent: 1" + value.get("text").toString());

                        go_get_massage(value);

                    } else {
                        // if we don't find we go to other option
                        Log.d("mess", "onEvent:  on ");
                        to_search_by_phones = the_profile_to_sent_massage.getPhone() + "To" + the_profile.getPhone();
                        db.collection("Messages").document(to_search_by_phones).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value.exists()) {

                                    Log.d("mess", "onEvent: 2 on " + value.get("Array_messages"));
                                    go_get_massage(value);

                                } else {
                                    ArrayList<The_messages> the_messagesArrayList = new ArrayList<>();
                                    The_messages the_messages = new The_messages();
                                    Map<String, Object> nestedData = new HashMap<>();
                                    the_messages.setText("hi3i");
                                    the_messagesArrayList.add(the_messages);

                                    nestedData.put("Array_messages", the_messagesArrayList);
                                    // create dec
                                    db.collection("Messages").document(to_search_by_phones)
                                            .set(nestedData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isComplete()) {
                                                Log.d("m1", "onComplete: we add array list  to d :" + to_search_by_phones);
                                            } else {
                                                Log.d("m1", "onComplete :  " + task.getException().getMessage());

                                                // error
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            e.getMessage().toString();
                                        }
                                    });
                                }
                            }
                        });
                        {
                        }
                    }
                }
            });
        }
    }
    private void go_get_massage(DocumentSnapshot value) {
        value_main = value;
        arrayList_from_now = (ArrayList<The_messages>) value.get("Array_messages");
        setUpRecyclerView();


    }

    private void setUpRecyclerView() {
        Query query_2 ;


        Query query = mRef.orderBy("text", Query.Direction.DESCENDING);
//        query = mRef.whereNotEqualTo("text","wad");
//        query = mRef.whereEqualTo("Array_messages","0");
//
        FirestoreRecyclerOptions<The_messages> options = new FirestoreRecyclerOptions.Builder<The_messages>()
                .setQuery(query,The_messages.class)
                .build();

//        FirestoreRecyclerOptions<ArrayList<The_messages>> options = new FirestoreRecyclerOptions.Builder<ArrayList<The_messages>>()
//                .setQuery(query,ArrayList.getClass())
//                .build();



        Log.d("hiwww", "setUpRecyclerView: " + options.toString());


        FirestoreRecyclerOptions<The_messages> options2 = new FirestoreRecyclerOptions.Builder<The_messages>()
                .setQuery(query, The_messages.class)
                .build();
        //options = mRef.whereArrayContains("12313");

        //String dataSnapshot =  options.getSnapshots().get(1).getFrom_user_phone();
        //Log.d("getvale", "setUpRecyclerView: " + dataSnapshot);

        Class[] options1 = arrayList_from_now.toArray(new Class[]{FirestoreRecyclerOptions.class});



        adapter = new Message_Adapter(options);
        recyclerView = getView().findViewById(R.id.recyclerview_messages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
