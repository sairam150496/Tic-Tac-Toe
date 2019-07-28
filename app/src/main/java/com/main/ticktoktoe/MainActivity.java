package com.main.ticktoktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends Activity {
    private AdView mAdView,ad2;
    Button go,skip;
    EditText player_x,player_o;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this,
                "ca-app-pub-5762492684085512~4254659419");
        mAdView = findViewById(R.id.adView);
        ad2 = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        ad2.loadAd(adRequest2);
        player_x = findViewById(R.id.player1);
        player_o = findViewById(R.id.player2);
        go = findViewById(R.id.go);
        skip = findViewById(R.id.skip);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("player_1",player_x.getText().toString());
                Log.i("player_2",player_o.getText().toString());

                Intent intent = new Intent(MainActivity.this,Game.class);
                intent.putExtra("x",player_x.getText().toString());
                intent.putExtra("o",player_o.getText().toString());
                startActivity(intent);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Game.class);
                intent.putExtra("x","");
                intent.putExtra("o","");
                startActivity(intent);
            }
        });
    }


}