package com.sct.tictactoe;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {
    Switch vibrationSwitch;
    Switch soundSwitch;

    private LinearLayout rateUs, feedback;
    private ImageView backBtn;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_settings);

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

        vibrationSwitch = (Switch) findViewById(R.id.vibration_switch);
        soundSwitch = (Switch) findViewById(R.id.sound_switch);

        backBtn = (ImageView) findViewById(R.id.settings_back_btn);
        rateUs = (LinearLayout) findViewById(R.id.rate_us_layout);
        feedback = (LinearLayout) findViewById(R.id.feedback_layout);

        if (MyServices.VIBRATION_CHECK) {
            vibrationSwitch.setChecked(true);
        } else if (!MyServices.VIBRATION_CHECK) {
            vibrationSwitch.setChecked(false);
        }

        vibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MyServices.VIBRATION_CHECK = true;
                } else {
                    MyServices.VIBRATION_CHECK = false;
                }
            }
        });

        if (MyServices.SOUND_CHECK) {
            soundSwitch.setChecked(true);
        } else if (!MyServices.SOUND_CHECK) {
            soundSwitch.setChecked(false);
        }

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MyServices.SOUND_CHECK = true;
                } else {
                    MyServices.SOUND_CHECK = false;
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askRatings();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("Tic Tac Toe Feedback");
            }
        });

    }

    void askRatings() {
//        ReviewManager manager = ReviewManagerFactory.create(this);
//        Task<ReviewInfo> request = manager.requestReviewFlow();
//        request.addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                // We can get the ReviewInfo object
//                ReviewInfo reviewInfo = task.getResult();
//                Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
//                flow.addOnCompleteListener(task2 -> {
//                    // The flow has finished. The API does not indicate whether the user
//                    // reviewed or not, or even whether the review dialog was shown. Thus, no
//                    // matter the result, we continue our app flow.
//                });
//            } else {
//
//                //Toast.makeText(getBaseContext(), "App does'nt uploaded on Play Store", Toast.LENGTH_SHORT).show();
//                // There was some problem, continue regardless of the result.
//            }
//        });
    }

    public void composeEmail(String subject) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:vimalku637@gmail.com")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(intent, "Send feedback"));
            }
        } catch (ActivityNotFoundException e) {
            //TODO smth
        }
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