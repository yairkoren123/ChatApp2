package com.example.chatapp.Activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.Apdters.Tab_Adapter;
import com.example.chatapp.R;
import com.example.chatapp.Tabs.Host_Tabs_Fragment;
import com.example.chatapp.Tabs.Sec_Tab_Fragment;
import com.example.chatapp.The_Interfaces.Start_Tab_two;
import com.example.chatapp.the_class.Singleton;
import com.example.chatapp.the_class.The_messages;
import com.example.chatapp.the_class.The_profile;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.okhttp.internal.DiskLruCache;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.grpc.Compressor;

public class ChatActivity extends AppCompatActivity implements Start_Tab_two {

    // firebase
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("USERS").document("SF");

    ArrayList<Object> friend_list_first = new ArrayList<>();
    ArrayList<String> friend_list = new ArrayList<>();



    ArrayList<The_profile>friend_of_profile_by_class = new ArrayList<>();

    String email = "";
    String phone = "";

    The_profile the_profile = new The_profile();
    Singleton singleton;
    Bundle s ;


    The_profile me = null;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



    ArrayList<The_profile> the_full_account_array = new ArrayList<>();

    //Firebaseins firebaseInstance ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        The_messages the_messages = new The_messages();

        Singleton singleton = Singleton.getInstance();

        email = singleton.getThe_profile().getName();

        update_account();


        now_online();
    }

    private void now_online(){

        singleton = Singleton.getInstance();
        //the_profile = singleton.getThe_profile();


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        LocalDateTime now = LocalDateTime.now();
        Log.d("timess", "onComplete: " + dtf.format(now));


        the_profile.setLast_connected("online");
        Log.d("timess", "onComplete: " + phone);


        db.collection("USERS").document(phone)
                .update("last_connected","online")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("sus", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("sus", "Error writing document", e);
                    }
                });


    }

    @Override
    protected void onStop() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        LocalDateTime now = LocalDateTime.now();
        Log.d("time", "onComplete: " + dtf.format(now));


        the_profile.setLast_connected(dtf.format(now));

        db.collection("USERS").document(phone)
                .update("last_connected",dtf.format(now))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("sus", "DocumentSnapshot successfully written!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("sus", "Error writing document", e);
                    }
                });
        super.onStop();

    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        LocalDateTime now = LocalDateTime.now();
        Log.d("time", "onComplete: " + dtf.format(now));


        the_profile.setLast_connected(dtf.format(now));

        db.collection("USERS").document(phone)
                .update("last_connected","online")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("sus", "DocumentSnapshot successfully written!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("sus", "Error writing document", e);
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        LocalDateTime now = LocalDateTime.now();
        Log.d("timeqq", "onComplete: " + now.toString());


        the_profile.setLast_connected(dtf.format(now));

        db.collection("USERS").document(phone)
                .update("last_connected",dtf.format(now))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("sus", "DocumentSnapshot successfully written!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("sus", "Error writing document", e);
                    }
                });
    }


    private void update_account (){


        singleton = Singleton.getInstance();

        the_profile = singleton.getThe_profile();

        phone = the_profile.getPhone();
        email = mAuth.getCurrentUser().getEmail().toString();

        // phone now is "";
        Log.d("phone?", "update_account: no " + phone);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("USERS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                Log.d("ppp", "onComplete: " + task.toString());
                Log.d("nnn1", "onComplete: " + task.getResult().getDocuments());
                for (DocumentSnapshot dataSnapshot : task.getResult().getDocuments()) {

                    List<DocumentSnapshot> document = task.getResult().getDocuments();
                    for (DocumentSnapshot dataSnapshot1 : document) {

                        if (dataSnapshot1.exists()) {
                            // convert document to POJO
                            The_profile the_profile_get = dataSnapshot1.toObject(The_profile.class);
                            Log.d("comper", "onComplete: now : " + email + "   !+  : " + the_profile_get.getName());
                            if (the_profile_get.getName().equals(email)) {
                                singleton.setThe_profile(the_profile_get);
                            }

                            Log.d("name_ss", "onComplete: " + the_profile_get.getPhone());
                            Boolean is_there = false;
                            for (The_profile is_there_profile : the_full_account_array) {

                                if (is_there_profile.getName().equals(the_profile_get.getName())) {
                                    is_there = true;
                                    break;
                                }

                            }
                            if (is_there == false) {
                                the_full_account_array.add(the_profile_get);
                            }
                        }
                    }
                }
                singleton.setAll_account(the_full_account_array);

                singleton = Singleton.getInstance();
                email = singleton.getThe_profile().getName();

                the_profile = singleton.getThe_profile();
                phone = the_profile.getPhone();


                for (The_profile s : the_full_account_array) {
                    if (s.getName().equals(email)) {
                        the_profile = s;
                        break;
                    }
                }


                friend_list= the_profile.getFriend_of_the_profile();

                friend_of_profile_by_class =  singleton.getFriend_of_profile_by_class();
                for (String s : friend_list){
                    for (The_profile p : the_full_account_array){
                        if (s.equals(p.getName())){
                            friend_of_profile_by_class.add(p);
                        }
                    }
                }

                singleton.setThe_profile(the_profile);
                if (the_profile.getLast_connected().equals("online")){

                }else {
                    set_now_connected();
                }
            }
        });
    }

    private void set_now_connected() {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child("USERS").child(phone);
        Map<String, Object> updates2 = new HashMap<String, Object>();

        updates2.put("now_connected", false);

        Log.d("upuser", "onSuccess: yes yes yes to phone " + phone);

        ref.updateChildren(updates2);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myref = database.getReference();


    }


    @Override
    public void onBackPressed() {
        Log.d("backpress", "onBackPressed: ye");
        super.onBackPressed();
    }

    @Override
    public void go_ahead() {

//        Sec_Tab_Fragment frag = (Sec_Tab_Fragment)
//                getSupportFragmentManager().findFragmentByTag("sec");
//        frag.go_ahead();
    }
}