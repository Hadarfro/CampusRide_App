package com.example.projectcampusride.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectcampusride.R;
import com.example.projectcampusride.SettingsActivity;

public class PassengerMainActivity extends AppCompatActivity {
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);

        userId = getIntent().getStringExtra("USER_ID");
        ImageButton settingsButton = findViewById(R.id.settings_button);
        ImageButton notificationButton = findViewById(R.id.notification_button);

        settingsButton.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        notificationButton.setOnClickListener(v -> startActivity(new Intent(this, NotificationsActivity.class)));
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        findViewById(R.id.my_rides_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, MyRidesActivity.class);
            intent.putExtra("passenger_id", userId);
            startActivity(intent);
        });


        findViewById(R.id.create_ride_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchRideActivity.class);
            intent.putExtra("passenger_id", userId);
            startActivity(intent);
        });
    }
}