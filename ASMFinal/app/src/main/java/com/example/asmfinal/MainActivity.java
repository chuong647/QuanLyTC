package com.example.asmfinal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmfinal.Fragment.Fragment_chi;
import com.example.asmfinal.Fragment.Fragment_thongke;
import com.example.asmfinal.Fragment.Fragment_thu;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    DrawerLayout drawerlayout;
   Toolbar toolbar;
    NavigationView navigation;
    ActionBarDrawerToggle drawerToggle;
     TextView txttenface,txtemailface;
     CircleImageView profile_image;
    private GoogleApiClient googleApiClient;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.Xam));
        drawerlayout=findViewById(R.id.drawerlayout);
        toolbar=findViewById(R.id.toolbar);
        navigation=findViewById(R.id.navigation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = setupdrawertoogle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerlayout.addDrawerListener(drawerToggle);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(MainActivity.this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
        View header = navigation.getHeaderView(0);
        txttenface= header.findViewById(R.id.txttenface);
        txtemailface=header.findViewById(R.id.txtemailface);
        profile_image=header.findViewById(R.id.profile_image);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            String tenface = bundle.getString("tenface");
            String emailface = bundle.getString("emailface");
            String imgface = bundle.getString("imgameface");
            txttenface.setText(tenface);
            txtemailface.setText(emailface);
            Picasso.with(MainActivity.this).load(imgface).into(profile_image);
        }
        if(savedInstanceState == null){
            navigation.setCheckedItem(R.id.khoanthu);
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Fragment_thu()).commit();
        }
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment fragment = null;
//                Class fragmentclass = null;
                switch (item.getItemId()){
                    case R.id.khoanthu:
//                        fragmentclass = Fragment_thu.class;
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Fragment_thu()).commit();
                        break;
                    case R.id.khoanchi:
//                        fragmentclass = Fragment_chi.class;
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Fragment_chi()).commit();
                        break;
                    case R.id.thongke:
//                        fragmentclass = Fragment_thongke.class;
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Fragment_thongke()).commit();
                        break;
                    case R.id.logout:
                        logout();
                        break;
                    case R.id.about:
                        Intent i = new Intent(MainActivity.this,About.class);
                        startActivity(i);
                        break;
                    case R.id.exit:
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startActivity(startMain);
                        finish();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Fragment_thu()).commit();
//                        fragmentclass = Fragment_thu.class;
                }

//                try {
//                    fragment = (Fragment) fragmentclass.newInstance();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                FragmentManager fragmentManager= getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.framelayout,fragment).commit();
                item.setCheckable(true);
                setTitle(item.getTitle());
                drawerlayout.closeDrawers();
                return true;
            }
        });
    }

    private void logout() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "Logout Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void xuatdulieu(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            txtemailface.setText(account.getEmail());
            txttenface.setText(account.getDisplayName());
            Uri personPhoto = account.getPhotoUrl();
            Picasso.with(MainActivity.this).load(personPhoto).into(profile_image);
        }else {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
    }

    private ActionBarDrawerToggle setupdrawertoogle(){
        return new ActionBarDrawerToggle(MainActivity.this,drawerlayout,toolbar,R.string.Open,R.string.Close);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            xuatdulieu(result);
        }else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    xuatdulieu(result);
                }
            });
        }
    }
}
