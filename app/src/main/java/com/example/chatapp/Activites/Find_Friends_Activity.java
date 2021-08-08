package com.example.chatapp.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chatapp.Apdters.Acount_Adapter;
import com.example.chatapp.Apdters.Status_Adapter;
import com.example.chatapp.R;
import com.example.chatapp.The_Interfaces.ItemClickListener;
import com.example.chatapp.the_class.Singleton;
import com.example.chatapp.the_class.The_profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Find_Friends_Activity extends AppCompatActivity implements ItemClickListener {

    RecyclerView recyclerView_search_friends ;
    Button button_search;
    EditText the_friend_text;


    The_profile the_profile_selected = new The_profile();

    Acount_Adapter adapter;

    ArrayList<The_profile> result_from_search_array = new ArrayList<>();

    ArrayList<The_profile> res_with_search = new ArrayList<>();

    ArrayList<String> the_friend_of_the_profile = new ArrayList<>();





    String email = "";
    String text_to_search = "";
    Singleton singleton = Singleton.getInstance();
    The_profile the_profile = new The_profile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        the_friend_text = findViewById(R.id.serach_friend_ac);
        button_search = findViewById(R.id.button_serach_friend_ac);

        recyclerView_search_friends = findViewById(R.id.rec_find_friends_ac);

        the_profile = singleton.getThe_profile();
        email = the_profile.getName();
        do_next();

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!the_friend_text.getText().toString().trim().equals("")) {
                    // not empty
                    text_to_search = the_friend_text.getText().toString().trim();

                    do_next();
                    Log.d("size_res", "onClick: " + res_with_search.size());

                } else {
                    Toast.makeText(Find_Friends_Activity.this, "no values ", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });





    }

    private void do_next(){
        Singleton singleton = Singleton.getInstance();
        the_profile = singleton.getThe_profile();


        email = the_profile.getName();

        result_from_search_array = singleton.getAll_account();


        res_with_search = new ArrayList<>();
        for (The_profile p : result_from_search_array){
            //Log.d("nameqq", "do_next: " + p.getName());

            if (p.getName().contains(text_to_search)){
                res_with_search.add(p);
                Log.d("nameqq", "do_next: " + p.getName());
            }

        }

        if (res_with_search.size() >= 1){


            Log.d("popw", "onClick: " + res_with_search.get(0));
            // set up the RecyclerView
            boolean show_status = true;

            recyclerView_search_friends.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new Acount_Adapter(getApplicationContext(), res_with_search,show_status);
            adapter.setClickListener(this::onItemClick);
            recyclerView_search_friends.setAdapter(adapter);

            adapter.notifyDataSetChanged();

        }else {
            boolean show_status = true;


            recyclerView_search_friends.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new Acount_Adapter(getApplicationContext(), res_with_search,show_status);
            adapter.setClickListener(this::onItemClick);
            recyclerView_search_friends.setAdapter(adapter);

            adapter.notifyDataSetChanged();



        }

    }

    @Override
    public void onItemClick(View view, int position) {
        // on the adapter



        Toast.makeText(this," you click on " + res_with_search.get(position).getName()
                , Toast.LENGTH_SHORT ).show();

        the_profile = singleton.getThe_profile();

        if (the_profile.getName().equals(res_with_search.get(position).getName())){
            Toast.makeText(Find_Friends_Activity.this,"you cant send massage to yourself",
                    Toast.LENGTH_SHORT)
                    .show();
        }else {


            the_profile_selected = new The_profile();

            the_profile_selected = res_with_search.get(position);

            singleton = Singleton.getInstance();
            singleton.setGo_massage_from_find(the_profile_selected);


            finish();
        }

    }


}