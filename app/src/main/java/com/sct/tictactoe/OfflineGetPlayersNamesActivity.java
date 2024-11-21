package com.sct.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;

public class OfflineGetPlayersNamesActivity extends AppCompatActivity implements View.OnTouchListener {
    private String playerOne, playerTwo;

    private EditText playerOneName, playerTwoName;
    private Button playerOneButton, playerTwoButton;
    private ImageView BackBtn;
    private LinearLayout playerOneLayout, playerTwoLayout;
    boolean islayout = true;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_offline_get_players_names);

        // Initialize the Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {});

        // Set test device IDs
        //        RequestConfiguration configuration = new RequestConfiguration.Builder()
//                .setTestDeviceIds(Arrays.asList("B4C405009710ADFB5DB53F28D42F48A7"))
//                .build();
//        MobileAds.setRequestConfiguration(configuration);

        // Find the AdView and load the ad
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        BackBtn = (ImageView) findViewById(R.id.player_names_back_btn);
        playerOneName = (EditText) findViewById(R.id.player_one_name_edttxt);
        playerTwoName = (EditText) findViewById(R.id.player_two_name_edttxt);
        playerOneButton = (Button) findViewById(R.id.player_one_btn);
        playerTwoButton = (Button) findViewById(R.id.player_two_btn);
        playerOneLayout = (LinearLayout) findViewById(R.id.player_one_layout);
        playerTwoLayout = (LinearLayout) findViewById(R.id.player_two_layout);

        playerOneButton.setOnTouchListener(this);
        playerOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(playerOneName.getText().toString())) {
                    Toast.makeText(getBaseContext(), "Enter Name", Toast.LENGTH_LONG).show();
                } else {
                    islayout = false;
                    playerOneLayout.setVisibility(View.GONE);
                    playerTwoLayout.setVisibility(View.VISIBLE);
                    slideUp(playerTwoLayout);
                    playerOne = playerOneName.getText().toString();
                }
            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        playerTwoButton.setOnTouchListener(this);
        playerTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(playerTwoName.getText().toString())) {
                    Toast.makeText(getBaseContext(), "Enter Name", Toast.LENGTH_LONG).show();
                } else {

                    playerTwo = playerTwoName.getText().toString();
                    Intent intent = new Intent(OfflineGetPlayersNamesActivity.this, ChooseSymbolActivity.class);
                    intent.putExtra("p1", playerOne);
                    intent.putExtra("p2", playerTwo);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (islayout) {
            if (v == playerOneButton) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setAlpha(0.5f);
                } else {
                    v.setAlpha(1f);
                }
            }
        } else if (!islayout) {
            if (v == playerTwoButton) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setAlpha(0.5f);
                } else {
                    v.setAlpha(1f);
                }
            }
        }
        return false;
    }

    // slide the view from below itself to the current position
    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}