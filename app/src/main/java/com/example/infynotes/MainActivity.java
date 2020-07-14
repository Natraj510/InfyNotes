package com.example.infynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    long Delay=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        Timer RunSplash=new Timer();

        TimerTask ShowSplash= new TimerTask() {
            @Override
            public void run() {

                finish();

                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);

            }
        };

        RunSplash.schedule(ShowSplash,Delay);

    }
}
