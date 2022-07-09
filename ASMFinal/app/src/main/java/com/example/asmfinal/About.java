package com.example.asmfinal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.asmfinal.Adapter.ViewpagerAdapter;
import com.example.asmfinal.Model.IntroModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class About extends AppCompatActivity {
    ViewPager screen_viewpager;
    ViewpagerAdapter viewpagerAdapter;
    ArrayList<IntroModel> dataintro;
    TabLayout tabLayout;
    Button btnbd, btnnext;
    int position=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        screen_viewpager= findViewById(R.id.screen_viewpager);
        btnbd= findViewById(R.id.btnbd);
        btnnext= findViewById(R.id.btn_next);
        tabLayout = findViewById(R.id.tab);
        btnbd.setVisibility(View.INVISIBLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(About.this,R.color.Trang));
        dataintro = new ArrayList<>();
        dataintro.add(new IntroModel("Quản Lí Khoản Thu"," Có phải bạn luôn tự hỏi tiền đã thu bao nhiêu?\n \uD83D\uDC49App sẽ quản lí số tiền mà bạn đã thu lại",R.drawable.intro1));
        dataintro.add(new IntroModel("Quản Lí Khoản Chi"," Bạn luôn hỏi: tiền lương bay đi đâu hết rồi?\n \uD83D\uDC49App sẽ quản lí số tiền mà bạn đã chi ra",R.drawable.intro2));
        dataintro.add(new IntroModel("Thống Kê "," Bạn đau đầu với việc cân đối thu chi trong tháng?\n \uD83D\uDC49App sẽ thống kê lại cả khoản thu và chi giúp bạn",R.drawable.intro3));
        viewpagerAdapter= new ViewpagerAdapter(About.this,dataintro);
        screen_viewpager.setAdapter(viewpagerAdapter);
        tabLayout.setupWithViewPager(screen_viewpager);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=screen_viewpager.getCurrentItem();
                if(position <dataintro.size()){
                   position++;
                   screen_viewpager.setCurrentItem(position);
                }
                if(position==dataintro.size()-1){
                    load();
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()== dataintro.size()-1){
                    load();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        btnbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(About.this,MainActivity.class);
                startActivity(i);
            }
        });
    }


    private void load() {
        btnnext.setVisibility(View.INVISIBLE);
        btnbd.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);

    }
}
