package com.example.projectcampusride.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectcampusride.R;
import com.example.projectcampusride.RideAdapter;
import com.example.projectcampusride.SettingsActivity;
import com.example.projectcampusride.controllers.DriverController;
import com.example.projectcampusride.controllers.PassengerController;
import com.example.projectcampusride.models.Ride;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyRidesActivity extends AppCompatActivity {
    private ListView ridesListView;
    private PassengerController passengerController;
    private String passengerId;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides);

        ridesListView = findViewById(R.id.rides_list_view);
        ImageButton settingsButton = findViewById(R.id.settings_button);
        ImageButton notificationButton = findViewById(R.id.notification_button);
        passengerController = new PassengerController();
        passengerId = currentUser.getUid();

        settingsButton.setOnClickListener(v -> startActivity(new Intent(this, com.example.projectcampusride.SettingsActivity.class)));
        notificationButton.setOnClickListener(v -> startActivity(new Intent(this, NotificationsActivity.class)));
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        settingsButton.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        notificationButton.setOnClickListener(v -> startActivity(new Intent(this, NotificationsActivity.class)));
        loadPassengerRides();

        // הוספת Listener ללחיצה על פריט ברשימה
        ridesListView.setOnItemClickListener((parent, view, position, id) -> {
            Ride selectedRide = (Ride) parent.getItemAtPosition(position);
            // שלח את ה-RideID למסך התאמה לנהג
            Intent intent = new Intent(MyRidesActivity.this, MatchActivity.class);
            intent.putExtra("driverId", selectedRide.getDriverId());
            intent.putExtra("rideId", selectedRide.getRideId());  // שליחה של RideID
            startActivity(intent);
        });
    }

    private void loadPassengerRides() {
        passengerController.getPassengerRides(passengerId, new PassengerController.PassengerRidesCallback() {
            @Override
            public void onSuccess(List<Ride> rides) {
                updateListView(rides);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(MyRidesActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateListView(List<Ride> rides) {
        RideDriverAdapter adapter = new RideDriverAdapter(this, rides);
        ridesListView.setAdapter(adapter);
    }

}

