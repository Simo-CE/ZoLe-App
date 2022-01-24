package com.smiley.yo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

public class ProfileFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        addFragment(view);
        ImageButton btnUpdateUser=view.findViewById(R.id.btn_update_user);
        //Hiding the appbar
        //getSupportActionBar().hide();
        btnUpdateUser.setOnClickListener(View -> updateUser());
        return view;
    }

    private void updateUser() {
      //  FragmentTransaction fr=getParentFragmentManager().beginTransaction();
        Fragment newFragment = new UpdateUserFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_profile, newFragment);
        fragmentTransaction.commit();
      //  fr.replace(R.id.fragment_profile,newFragment);
      //  fr.addToBackStack(null);
       // fr.commit();
    }

    private void addFragment(View view){
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabLayout);
        ProfilePagerAdapter adapter = new ProfilePagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ProfileSerFragment(), "Services");
        adapter.addFragment(new ProfilePostFragment(), "Post");
        adapter.addFragment(new ProfileRevFragment(),"Reviews");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}