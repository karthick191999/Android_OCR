package com.example.mad_proj;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.PagerTitleStrip;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Translate_frag extends AppCompatActivity {
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    String text;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_frag);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#14b1ab"));
        actionBar.setBackgroundDrawable(colorDrawable);
        ArrayList<String> list = getIntent().getStringArrayListExtra("list");
        text = list.get(0);
        text = text.toLowerCase();
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
    public String getText()
    {
        return text;

    }

}

class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


    public ScreenSlidePagerAdapter(@NonNull FragmentManager fm,int behaviour) {
        super(fm,behaviour);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0)
        return new Tamil_frag();
        else
            return new Italian_frag();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "Tamil";
        else
            return "Italiano";
    }

    @Override
    public int getCount() {
        return 2;
    }
}