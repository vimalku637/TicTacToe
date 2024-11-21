package com.sct.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;

public class AiChooseSymbolActivity extends AppCompatActivity implements View.OnTouchListener {
    private ImageView BackBtn, CrossImg, CrossRadioImg, CircleImg, CircleRadioImg;
    private Button ContinueBtn;

    int PICK_SIDE;
    private String playerName;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_ai_choose_symbol);

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

        playerName = getIntent().getStringExtra("p1");

        BackBtn = (ImageView) findViewById(R.id.ai_pick_side_back_btn);
        CrossImg = (ImageView) findViewById(R.id.ai_pick_side_cross_img);
        CircleImg = (ImageView) findViewById(R.id.ai_pick_side_circle_img);
        CrossRadioImg = (ImageView) findViewById(R.id.ai_pick_side_cross_radio);
        CircleRadioImg = (ImageView) findViewById(R.id.ai_pick_side_circle_radio);

        ContinueBtn = (Button) findViewById(R.id.ai_pick_side_continue_btn);

        // CrossRadioImg.setOnTouchListener(this);
        CrossRadioImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_SIDE = 0;
                CrossRadioImg.setImageResource(R.drawable.ic_radio_button_checked);
                CircleRadioImg.setImageResource(R.drawable.ic_radio_button_unchecked);
                CircleImg.setAlpha(0.3f);
                CrossImg.setAlpha(1.0f);
                //Intent intent = new Intent(.this,Ch.class);
                // startActivity(intent);
            }
        });

        // CircleRadioImg.setOnTouchListener(this);
        CircleRadioImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_SIDE = 1;
                CircleRadioImg.setImageResource(R.drawable.ic_radio_button_checked);
                CrossRadioImg.setImageResource(R.drawable.ic_radio_button_unchecked);
                CrossImg.setAlpha(0.3f);
                CircleImg.setAlpha(1.0f);

                //Intent intent = new Intent(.this,Ch.class);
                // startActivity(intent);
            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //Intent intent = new Intent(.this,Ch.class);
                // startActivity(intent);
            }
        });

        ContinueBtn.setOnTouchListener(this);
        ContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AiChooseSymbolActivity.this, AiGameActivity.class);
                intent.putExtra("p1", playerName);
                intent.putExtra("ps", PICK_SIDE);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == ContinueBtn) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setAlpha(0.5f);
            } else {
                v.setAlpha(1f);
            }
        }
        return false;
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
