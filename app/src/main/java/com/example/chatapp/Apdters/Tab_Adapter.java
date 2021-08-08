package com.example.chatapp.Apdters;


import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class Tab_Adapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> ArrayList_title = new ArrayList<>();



    public Tab_Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return  fragmentArrayList.get(position);

    }

    public void setCurrentItem(int x){
    }


    public void addFragment(Fragment fragment,String title){
        fragmentArrayList.add(fragment);
        ArrayList_title.add(title);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ArrayList_title.get(position);
    }
}
