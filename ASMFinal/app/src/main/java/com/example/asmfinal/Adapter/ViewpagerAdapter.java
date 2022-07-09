package com.example.asmfinal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.asmfinal.Model.IntroModel;
import com.example.asmfinal.R;

import java.util.ArrayList;

public class ViewpagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<IntroModel> dataintro;

    public ViewpagerAdapter(Context context, ArrayList<IntroModel> dataintro) {
        this.context = context;
        this.dataintro = dataintro;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.introitem,null);
        TextView tilte = view.findViewById(R.id.intro_title);
        TextView loigioithieu = view.findViewById(R.id.intro_description);
        ImageView anh = view.findViewById(R.id.imgintro);
        tilte.setText(dataintro.get(position).getTilte());
        loigioithieu.setText(dataintro.get(position).getLoinoi());
        anh.setImageResource(dataintro.get(position).getHinh());
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return dataintro.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
