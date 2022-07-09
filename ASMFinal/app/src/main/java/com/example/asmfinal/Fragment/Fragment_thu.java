package com.example.asmfinal.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.asmfinal.Adapter.Tabadapter;
import com.example.asmfinal.R;
import com.example.asmfinal.TabFragment.TabFragment1;
import com.example.asmfinal.TabFragment.TabFragmentkhoanthu;
import com.google.android.material.tabs.TabLayout;


public class Fragment_thu extends Fragment {
    private Tabadapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_thu,container,false);
        viewPager =view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        adapter = new Tabadapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new TabFragmentkhoanthu(), "Khoản Thu");
        adapter.addFragment(new TabFragment1(), "Loại Thu");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.icontien);
        tabLayout.getTabAt(1).setIcon(R.drawable.icontutien);
        return view;
    }
}
