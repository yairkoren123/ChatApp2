package com.example.chatapp.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.Fragments.ScreenSlidePageFragment;
import com.example.chatapp.R;
import com.example.chatapp.the_class.Singleton;
import com.example.chatapp.the_class.The_status;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class Auto_ViewPager_Activity extends AppCompatActivity {

    ViewPager2 viewPager;
    ImageButton close;
    ArrayList<The_status> status_list = new ArrayList<>();



    int currentPage = 0;
    Singleton singleton;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2500; // time in milliseconds between successive task executions.

    boolean loop = false;

    private int NUM_PAGES_array ;


    private FragmentStateAdapter pagerAdapter;

    private Activity activity;


    ArrayList<String> images = new ArrayList<>();
    //String[] imageId = {R.drawable.background1, R.drawable.background1, R.drawable.background1, R.drawable.background1};
    String[] imagesName = {"image1", "image2", "image3", "image4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_view_pager);


        singleton = Singleton.getInstance();

        activity = Auto_ViewPager_Activity.this;


        status_list = singleton.getClick_status_array();
        Log.d("auto_array_size", "onCreate: " + status_list.size());

        NUM_PAGES_array = status_list.size();

        for (The_status link_image : status_list) {
            images.add(link_image.getImage_status());
        }

        close = findViewById(R.id.close_view_pager_activity);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        viewPager = findViewById(R.id.viewPager_auto);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.d("koo1", "onPageSelected: " + position);

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("koo2", "onPageSelected: " + position);
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                Log.d("koo3", "onPageSelected: " + state);
                //viewPager.setCurrentItem(state,true);
            }
        });



        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                Log.d("oio", "run: now  " + currentPage);
                if (currentPage == NUM_PAGES_array){
                    activity.finish();
                }

//                if (currentPage == NUM_PAGES_array -1) {
//                    currentPage = 0;
//                }
                    viewPager.setCurrentItem(currentPage++, true);




                Log.d("oio", "run:  3  " + currentPage);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

    @Override
    public void onBackPressed() {
    if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }




    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        int NUM_PAGES ;
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
            this.NUM_PAGES = status_list.size();
        }

        @Override
        public Fragment createFragment(int position) {
            Log.d("createFragment", "createFragment: hey " + position );
            return new ScreenSlidePageFragment(status_list.get(position));
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}


//class CustomAdapter extends FragmentStateAdapter {
//
//    private Activity activity;
//    private Integer[] imagesArray;
//    private String[] namesArray;
//    private ArrayList<The_status> theStatuseslist = new ArrayList<>();
//
//    public CustomAdapter(@NonNull FragmentActivity fragmentActivity , ArrayList<The_status> the_statuses ,
//                         String[] names) {
//
//        super(fragmentActivity);
//        this.theStatuseslist = the_statuses;
//        this.namesArray = names;
//    }
//
//
////        LayoutInflater inflater = ((Activity)activity).getLayoutInflater();
////
////        View viewItem = inflater.inflate(R.layout.auto_item, container, false);
////        ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView_full);
////        //imageView.setImageResource(imagesArray[position]);
////        Picasso.get()
////                .load(theStatuseslist.get(position).getImage_status().toString())
////                .fit()
////                .into(imageView);
////        TextView textView1 = (TextView) viewItem.findViewById(R.id.textView_full);
////        textView1.setText(namesArray[position]);
////        ((ViewPager)container).addView(viewItem);
//
//       // return viewItem;
//
//
//
//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        return null;
//    }
//
//    @Override
//    public int getItemCount() {
//        return theStatuseslist.size();
//    }
//}