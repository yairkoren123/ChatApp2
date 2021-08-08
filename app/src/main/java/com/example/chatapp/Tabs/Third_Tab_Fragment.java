package com.example.chatapp.Tabs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.Activites.ChatActivity;
import com.example.chatapp.R;
import com.example.chatapp.the_class.Singleton;
import com.example.chatapp.the_class.The_profile;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.grpc.Compressor;

import static android.app.Activity.RESULT_OK;


public class Third_Tab_Fragment extends Fragment {

    The_profile the_profile = new The_profile();

    Singleton singleton = Singleton.getInstance();

    String phone = "";

    //layout

    Button selected_image, upload;

    Activity activity = getActivity();

    ImageView imageView_the_profile;
    TextView name;

    private Uri filePath;

    Bitmap Bitimage;
    private final int PICK_IMAGE_REQUEST = 71;

    // firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myref = database.getReference();
    FirebaseFirestore fStore;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseAuth fAuth;




    public Third_Tab_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_third__tab_, container, false);

//        selected_image = view.findViewById(R.id.button_selected_profile_image_tab3);
        name = view.findViewById(R.id.name_tab3_profile);
        imageView_the_profile = view.findViewById(R.id.imageView_tab3);
        upload = view.findViewById(R.id.button2);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        singleton =  Singleton.getInstance();
        the_profile = singleton.getThe_profile();
        Log.d("image_now", "onResume: " +the_profile.getImage());

        String url = the_profile.getImage();
        if (url.equals("") || url == null){
            imageView_the_profile.setImageResource(R.drawable.no_image);

        }else {
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
            .into(imageView_the_profile);

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        singleton = Singleton.getInstance();
        the_profile = singleton.getThe_profile();

        phone = the_profile.getPhone();

        name.setText(the_profile.getName() + "  " + the_profile.getPhone());

        Log.d("image_now", "onResume: " +the_profile.getImage());

        String url = the_profile.getImage();
        if (url.equals("") || url == null){
            imageView_the_profile.setImageResource(R.drawable.no_image);

        }else {
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.background1)
                    .into(imageView_the_profile);

        }


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_image();
            }
        });


        imageView_the_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    private void upload_image() {
        singleton = Singleton.getInstance();
        the_profile = singleton.getThe_profile();

        database = FirebaseDatabase.getInstance();
        myref = database.getReference();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = database.getReference();
        Log.d("upload", "upload_image: " + the_profile.getPhone());

//        mDatabaseRef.child("USERS").child("" + the_profile.getPhone().toString()).child("the_profile_status_short").setValue("2");

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        storageReference = FirebaseStorage.getInstance().getReference();


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            singleton = Singleton.getInstance();

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Bitmap bitmap = null;
                filePath = data.getData();
                if (filePath != null) {


                    //uriToBitmap(filePath);
                    Log.d("Bitimage_", "onActivityResult: " + Bitimage);

                    if (Bitimage == null || Bitimage.equals("")){
                        Log.d("Bitimage_", "onActivityResult:1 " + Bitimage);
                    }
                    imageView_the_profile.setImageBitmap(Bitimage);


//                    Bitmap bmImg = BitmapFactory.decodeFile(filePath.getPath());
//
//                    Bitmap myBitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath());
//
//                    imageView_the_profile.setImageBitmap();
//
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
//
//                        imageView_the_profile.setImageBitmap(bitmap);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    try {
                        StorageReference ref = storageReference.child("USERS/" + phone);
                        UploadTask uploadTask = ref.putFile(filePath);

                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (task.isSuccessful()) {
                                    //here the upload of the image finish
                                    imageView_the_profile.setImageBitmap(Bitimage);

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
                                    documentReference.update("image", downloadUri.toString());

                                    singleton.getThe_profile().setImage(downloadUri.toString());
                                    Log.d("image_up", "onComplete: image upload  " + singleton.getThe_profile().getImage());
                                    //Toast.makeText(getActivity(), "Uploaded" + downloadUri.toString(), Toast.LENGTH_SHORT).show();
                                } else {
                                    // Handle failures

                                }
                            }

                            ;

                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    private void uriToBitmap(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getActivity().getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitimage = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            imageView_the_profile.setImageBitmap(Bitimage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}