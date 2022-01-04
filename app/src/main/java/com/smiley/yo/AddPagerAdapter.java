package com.smiley.yo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AddPagerAdapter extends FragmentStateAdapter {
    public AddPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new AddPostFragment();
            case 2:
                return new AddServiceFragment();
        }
        return new AddPostFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
