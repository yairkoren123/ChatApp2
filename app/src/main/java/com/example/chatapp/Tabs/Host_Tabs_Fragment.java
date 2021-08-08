package com.example.chatapp.Tabs;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chatapp.Apdters.Tab_Adapter;
import com.example.chatapp.Fragments.SignUp_Fragment;
import com.example.chatapp.R;
import com.example.chatapp.the_class.Singleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;


public class Host_Tabs_Fragment extends Fragment{

    ViewPager viewPager;
    TabLayout tabLayout;
    Tab_Adapter adapter;
    Singleton singleton = Singleton.getInstance();

    @Override
    public void onResume() {
        Singleton singleton = Singleton.getInstance();
        // go to the massage from the find friends
        if (singleton.getGo_massage_from_find() != null){
            Navigation.findNavController(getView()).navigate(R.id.action_host_Tabs_Fragment_to_messages_Fragment);
        }
        super.onResume();
    }

    public Host_Tabs_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_host__tabs_, container, false);


        adapter = new Tab_Adapter(getActivity().getSupportFragmentManager());

        TextView textView = view.findViewById(R.id.textView);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_host_Tabs_Fragment_to_messages_Fragment);
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = getView().findViewById(R.id.viewpager_tabs);
        setupViewPager(viewPager);


        tabLayout = getView().findViewById(R.id.tab_lay);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        adapter.notifyDataSetChanged();

        First_Tab_Fragment frag1 = new First_Tab_Fragment();
        Sec_Tab_Fragment frag2 = new Sec_Tab_Fragment();
        Third_Tab_Fragment frag3 = new Third_Tab_Fragment();



        Log.d("id_frags1", "setupViewPager: 1 : " + frag1.getId());
        Log.d("id_frags2", "setupViewPager: 1 : " + frag2.getId());
        Log.d("id_frags3", "setupViewPager: 1 : " + frag3.getId());


        adapter.addFragment(frag1, "Chat");
        adapter.addFragment(frag2, "Status");
        adapter.addFragment(frag3, "Profile");

        Fragment myFragment = (Fragment) adapter.instantiateItem(viewPager, viewPager.getCurrentItem());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Fragment myFragment = (Fragment) adapter.instantiateItem(viewPager, viewPager.getCurrentItem());
                Log.d("haha", "setupViewPager: " + viewPager.getCurrentItem());


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Log.d("haha", "setupViewPager: " + myFragment.getId());

        viewPager.setAdapter(adapter);
        Log.d("now_view", "setupViewPager: " + viewPager.getCurrentItem());
        adapter.notifyDataSetChanged();

    }



}