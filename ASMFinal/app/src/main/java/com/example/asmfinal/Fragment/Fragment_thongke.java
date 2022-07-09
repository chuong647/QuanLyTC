package com.example.asmfinal.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.asmfinal.R;
import com.example.asmfinal.TabFragment.TabFragmentTK_Chi;
import com.example.asmfinal.TabFragment.TabFragmentTK_Thu;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class Fragment_thongke extends Fragment {
    BottomNavigationView bnv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragmentthongke,container,false);
            bnv = view.findViewById(R.id.bnv);
            if(savedInstanceState == null){
                bnv.setSelectedItemId(R.id.thonkethu);
                loadFragment(new TabFragmentTK_Thu());
            }
       bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               Fragment fragment;
               switch (item.getItemId()){
                   case R.id.thonkethu:
                       fragment = new TabFragmentTK_Thu();
                       loadFragment(fragment);
//                       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new TabFragmentTK_Thu()).commit();
                       return true;
                   case R.id.thonkechi:
                       fragment = new TabFragmentTK_Chi();
                       loadFragment(fragment);
//                       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new TabFragmentTK_Chi()).commit();
                       return true;
               }

               return false;
           }
       });
        return view;
    }


private void loadFragment(Fragment fragment) {
    // load fragment
    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fr_l, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
}
}
