package com.example.asmfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Icon extends AppCompatActivity {
    Animation topAnimation,bottomAnimation;
    ImageView logo;
    TextView ten,loimoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_icon);
        topAnimation = AnimationUtils.loadAnimation(Icon.this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(Icon.this,R.anim.bottom_animation);
        logo=findViewById(R.id.logo);
        ten=findViewById(R.id.ten);
        loimoi=findViewById(R.id.loimoi);
        logo.setAnimation(topAnimation);
        ten.setAnimation(bottomAnimation);
        loimoi.setAnimation(bottomAnimation);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Icon.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                Pair[] pairs = new Pair[2];
                pairs[0]= new Pair<View,String>(logo,"logo_transition");
                pairs[1]= new Pair<View,String>(ten,"ten_transition");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Icon.this,pairs);
                    startActivity(i,options.toBundle());

                }

            }
        },5000);

    }
}
