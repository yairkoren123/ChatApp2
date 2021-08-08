package com.example.chatapp.Tabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chatapp.Activites.Auto_ViewPager_Activity;
import com.example.chatapp.Apdters.Acount_Adapter;
import com.example.chatapp.Apdters.Status_Adapter;
import com.example.chatapp.R;
import com.example.chatapp.The_Interfaces.CheckTheData;
import com.example.chatapp.The_Interfaces.Start_Tab_two;
import com.example.chatapp.the_class.Singleton;
import com.example.chatapp.the_class.The_profile;
import com.example.chatapp.the_class.The_status;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.DateTime;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class Sec_Tab_Fragment extends Fragment implements Status_Adapter.ItemClickListener, Start_Tab_two {

    // layout
    FloatingActionButton fab;

    Status_Adapter adapter;
    private Uri filePath;


    ArrayList<The_profile> all_the_account_status_friends = new ArrayList<>();
    ArrayList<The_profile> all_profiles_friend = new ArrayList<>();

    The_status the_status = new The_status();
    ArrayList<The_status> array_of_one_friend_status = new ArrayList<>();

    ArrayList<The_status> update_for_friend_status_array = new ArrayList<>();

    ArrayList<The_status> array_to_send_up = new ArrayList<>();

    Singleton singleton = Singleton.getInstance();

    The_profile the_profile = new The_profile();

    ArrayList<Integer> index_to_remove = new ArrayList<>();


    String phone = "";

    private final int PICK_IMAGE_REQUEST = 71;

    // firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myref = database.getReference();
    FirebaseFirestore fStore;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseAuth fAuth;
    // time

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;

    LocalDateTime now;
    DateTime date;

    public Sec_Tab_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sec__tab_, container, false);

        fab = view.findViewById(R.id.fab_add_status);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        singleton = Singleton.getInstance();
        the_profile = new The_profile();
        the_profile = singleton.getThe_profile();
        phone = the_profile.getPhone();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // todo dialog pick in camera or gallery

                // gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }
        });

        all_profiles_friend = singleton.getFriend_of_profile_by_class();
        Log.d("size_class", "onViewCreated: " + all_profiles_friend.size());

        // todo
        now = LocalDateTime.now();
        for (The_profile one_friend : all_profiles_friend) {
            boolean is_there = false;
            if (one_friend.getStatuses_of_the_profile().size() != 0) {
                // if one friend have status (many or SOLO) :
                array_of_one_friend_status = one_friend.getStatuses_of_the_profile();
                all_the_account_status_friends.add(one_friend);
                for (The_status one_status : array_of_one_friend_status) {


                    String time_of_the_status = one_status.getTime_for_the_image();
                    //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    //Date date;
                    Log.d("time", "onComplete: " + dtf.format(now));
                    String[] wow = time_of_the_status.split(" ");
                    if (wow[0].contains("/")) {
                        wow[0] = wow[0].replace("/", "-");
                    }
                    time_of_the_status = wow[0];


                    if (date_comp(time_of_the_status, dtf.format(now))) {
                        // true - the time is past and we need to remove from the list (date_comp)
                        update_for_friend_status_array.add(one_status);
                    } else {
//                        // false - we need to add to the list of status and show the images (date_comp)
//                        if (is_there == false) {
//                            for (The_profile e : all_the_account_status_friends) {
//                                if (e.getName().equals(one_friend.getName())) {
//                                    is_there = true;
//                                    break;
//                                }
//
//                            }
//                            if (is_there == false) {
//                                //one_friend.setStatuses_of_the_profile(array_of_one_friend_status);
//                                // todo update in database
//                                all_the_account_status_friends.add(one_friend);
//                            }
                    }
                }
                array_to_send_up = (ArrayList<The_status>) array_of_one_friend_status.clone();
                //  todo remove the items
                Log.d("bas", "onViewCreated: " + array_to_send_up.equals(array_of_one_friend_status));
                Log.d("removs", "onViewCreated: " + array_to_send_up.size());

                if (update_for_friend_status_array.size() >= 1 ) {
                    for (The_status q : array_to_send_up) {
                        for (The_status z : update_for_friend_status_array) {
                            if (q.getTime_for_the_image().equals(z.getTime_for_the_image())) {
                                Log.d("removs_ob", "onViewCreated: remove :  " + z.getName());

                                array_of_one_friend_status.remove(z);
                                break;
                            }
                        }
                    }


                    // sent up
                    fStore = FirebaseFirestore.getInstance();

                    DocumentReference documentReference = fStore.collection("USERS")
                            .document(one_friend.getPhone());

                    documentReference.update("statuses_of_the_profile", array_of_one_friend_status)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(getActivity().getApplicationContext(),"status reset",Toast.LENGTH_LONG)
                                            .show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.getMessage().toString();
                        }
                    });

                    // array_to_send_up == the current user array
                    // all_the_account_status_friends == all the account friend with status

                    Log.d("we_are_size", "onViewCreated: yee  " + array_of_one_friend_status.size() );
                    Log.d("we_are_size", "onViewCreated: yee  2  " + all_the_account_status_friends.size() );

                    for (The_profile s : all_the_account_status_friends){
                        if (s.getName().equals(array_of_one_friend_status.get(0).getName())){
                            s.setStatuses_of_the_profile(array_of_one_friend_status);
                            Log.d("we_upgrade_array", "onViewCreated: yee");
                        }
                    }
                }

            }
            // if the current profile status list is 0

        }



            Log.d("status_final_size","onViewCreated: "+all_the_account_status_friends.size());

            // set up the RecyclerView
            RecyclerView recyclerView = getView().findViewById(R.id.rec_host_tab2);


            recyclerView.setLayoutManager(new

            LinearLayoutManager(getContext()));
            adapter =new

            Status_Adapter(getContext(),all_the_account_status_friends);
            adapter.setClickListener_Status(this);
            recyclerView.setAdapter(adapter);


}
    public boolean date_comp (String one , String two ) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(one);
            Date date2 = sdf.parse(two);
            boolean moreThanDay = Math.abs(date1.getTime() - date2.getTime()) > MILLIS_PER_DAY;
            if (moreThanDay){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.getMessage().toString();
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fAuth = FirebaseAuth.getInstance();
        the_status = new The_status();
        fStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        singleton = Singleton.getInstance();

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Bitmap bitmap = null;
            filePath = data.getData();
            if (filePath != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                    //imageView_the_profile.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    StorageReference ref = storageReference.child("USERS/" + phone);
                    UploadTask uploadTask = ref.putFile(filePath);

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (task.isSuccessful()) {
                                //here the upload of the image finish
                            }

                            // Continue the task to get a download url
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {

                                Uri downloadUri = task.getResult(); //this is the download url that you need to pass to your database
                                //Pass the url to your reference
                                DocumentReference documentReference = fStore.collection("USERS").document(phone);
                                //documentReference.update("statuses_of_the_profile", downloadUri.toString());

                                 now = LocalDateTime.now();
                                Log.d("time", "onComplete: " + dtf.format(now));


                                the_status.setTime_for_the_image(dtf.format(now));
                                the_status.setName(the_profile.getName());
                                the_status.setImage_status(downloadUri.toString());

                                documentReference.update("statuses_of_the_profile", FieldValue.arrayUnion(the_status))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(getActivity().getApplicationContext(),"status add",Toast.LENGTH_LONG)
                                                        .show();
                                            }
                                        });



                                singleton.getThe_profile().setImage(downloadUri.toString());
                                Log.d("image_up", "onComplete: image upload" + singleton.getThe_profile().getImage());
                                //Toast.makeText(getActivity(), "Uploaded" + downloadUri.toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle failures

                            }
                        }

                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // from interface recyclerview on item click

    @Override
    public void onItemClick(View view, int position) {
        // start the viewpager Auto
        Log.d("click_status", "onItemClick: item click : " +
                all_the_account_status_friends.get(position).toString());

        ArrayList<The_status> the_status_click = all_the_account_status_friends.get(position)
                .getStatuses_of_the_profile();

        Log.d("click_status", "onItemClick: item click : " + the_status_click.toString());

        singleton = Singleton.getInstance();
        singleton.setClick_status_array(the_status_click);
        Intent intent = new Intent(getActivity(), Auto_ViewPager_Activity.class);
        startActivity(intent);
    }

    // from interface
    @Override
    public void go_ahead() {
        Log.d("go_go_go", "go_ahead: work" );
    }
}