package com.example.chatapp.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.The_Interfaces.CheckTheData;
import com.example.chatapp.Fragments.SignUp_Fragment;
import com.example.chatapp.R;
import com.example.chatapp.the_class.Singleton;
import com.example.chatapp.the_class.The_profile;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CheckTheData{

    Singleton singleton ;

    The_profile the_profile = new The_profile();

    ArrayList<The_profile> friend_of_profile_by_class = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    //layout
    EditText pass_text , email_text;
    Button go_Chat , sign_Up;

    // firebase
    private FirebaseAuth mAuth;

    // val
    String email = "";
    String pass = "";

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            FirebaseDatabase.getInstance().setPersistenceEnabled(true);



            pass_text = findViewById(R.id.pass_main);
        email_text = findViewById(R.id.email_main);

        go_Chat = findViewById(R.id.Create_accout_main);
        sign_Up = findViewById(R.id.signUp_main);

        mAuth = FirebaseAuth.getInstance();


        go_Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();

                email = email_text.getText().toString().trim();
                pass = pass_text.getText().toString().trim();

                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Empty failed 1.",
                            Toast.LENGTH_SHORT).show();
                } else {


                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TAG", "signInWithEmail:success");
                                        go_next();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d("TAG", "signInWithEmail:failure :  " + task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }

        });

        sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_Up.setVisibility(View.INVISIBLE);
                go_Chat.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.singup_mail, SignUp_Fragment.class, null,"sign")
                        .addToBackStack("sign")
                        .commit();
            }
        });
    }

    private void go_next(){

        Singleton singleton = Singleton.getInstance();

        Log.d("pp", "go_next: " + email);
        The_profile the_profile = new The_profile();
        the_profile.setName(email);
        singleton.setThe_profile(the_profile);

//        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
//        startActivity(intent);
//        finish();

        update_account(); // new
    }

        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if(currentUser != null){

                singleton = Singleton.getInstance();


                The_profile the_profile = new The_profile();

                the_profile = singleton.getThe_profile();


                email = currentUser.getEmail().toString();

                the_profile.setName(email);
                the_profile.setNow_connected(true);

                singleton.setThe_profile(the_profile);

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference docRef = db.collection("cities").document("LA");
// asynchronously retrieve the document
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });


                Log.d("see", "onStart: user :" + currentUser.getEmail());
//                Intent intent = new Intent(MainActivity.this,ChatActivity.class);
//                startActivity(intent);
//                finish();

               update_account(); // new





            }


    }


    @Override
    public void onBackPressed() {

        SignUp_Fragment test = (SignUp_Fragment) getSupportFragmentManager().findFragmentByTag("sign");


        if (test != null && test.isVisible()) {
            //DO STUFF
            go_Chat.setVisibility(View.VISIBLE);
            sign_Up.setVisibility(View.VISIBLE);
            super.onBackPressed();

        }
        else {
            //Whatever
            super.onBackPressed();
        }
    }


    private String phone ="";

    ArrayList<Object> friend_list_first = new ArrayList<>();
    ArrayList<String> friend_list = new ArrayList<>();
    ArrayList<The_profile> the_full_account_array = new ArrayList<>();


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

                ArrayList<String> a = the_profile.getFriend_of_the_profile();
                for (String x : a) {
                    Log.d("asdas", "onComplete: " + x);
                    friend_list.add(x.toString().trim());

                }

                set_now_connected();


//                for (The_profile profile_now : the_full_account_array){
//                    if (profile_now.getName().equals(the_profile.getName())){
//                        phone = profile_now.getPhone();
//                        the_profile.setPhone(phone);
//                        the_profile.setName(email);
//                    }
//
//                }
//                singleton.setThe_profile(the_profile);

            }
        });
    }

    private void set_now_connected(){
        DatabaseReference ref= FirebaseDatabase.getInstance()
                .getReference().child("USERS").child(phone);
        Map<String, Object> updates2 = new HashMap<String,Object>();

        updates2.put("now_connected", false);

        Log.d("upuser", "onSuccess: yes yes yes to phone " + phone);

        ref.updateChildren(updates2);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myref = database.getReference();


        get_friend_profile();


//        myref.child("USERS").child(phone).updateChildren(updates2).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull  Task<Void> task) {
//                Log.d("upuser", "onSuccess:2 yes yes yes");
//            }
//        });
    }
    private void get_friend_profile() {

        the_profile = singleton.getThe_profile();

        //todo get the user like the names_list(the_friend_of_the_profile_update) from the data base
        the_full_account_array = singleton.getAll_account();

        Log.d("all_account_size", "update_friend_list_adapter_tab_one: " + the_full_account_array.size());


        // enter all the profiles(that your friends) to the (the friends)


        db.collection("USERS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                friend_list = singleton.getThe_profile().getFriend_of_the_profile();
                Log.d("qwqw", "setadpter1: " +friend_list.size());


                for (String name_to_search : friend_list) {

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
                singleton = Singleton.getInstance();
                singleton.setFriend_of_profile_by_class(friend_of_profile_by_class);
                Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                startActivity(intent);
                finish();

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


    @Override
    public void start_now() {
        Log.d("menome", "start_now: yeee " );
        update_account();
    }
}
