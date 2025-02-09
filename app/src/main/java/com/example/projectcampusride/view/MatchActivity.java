package com.example.projectcampusride.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectcampusride.R;
import com.example.projectcampusride.RateDriverActivity;
import com.example.projectcampusride.view.NotificationsActivity;
import com.example.projectcampusride.view.PassengerMainActivity;

public class MatchActivity extends AppCompatActivity {

    private Button btnShowDriverNumber, btnPayment, btnRateDriver, btnBack;
    private ImageView settingsIcon, notificationIcon;
    private String driverPhoneNumber, rideId, driverId; // משתנה למספר הנסיעה

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        // קבלת מספר הטלפון של הנהג ומספר הנסיעה מה-Intent
        Intent intent = getIntent();
        driverPhoneNumber = intent.getStringExtra("DRIVER_PHONE_NUMBER");
        rideId = intent.getStringExtra("rideId"); // קבלת ה-RideID
        driverId = intent.getStringExtra("driverId");

        // הצגת מספר הנסיעה במסך ההתאמה
        if (rideId != null) {
            // להציג את מספר הנסיעה ב-TextView או ככותרת במסך ההתאמה
            TextView rideIdTextView = findViewById(R.id.rideIdTextView); // TextView להציג את מספר הנסיעה
            rideIdTextView.setText("מספר נסיעה: " + rideId);
        }

        // אתחול של כפתורים
        initializeButtons();

        // אתחול של האייקונים
        initializeIcons();

        // הגדרת מאזיני קליקים
        setButtonListeners();
        setIconListeners();
    }

    private void initializeButtons() {
        btnShowDriverNumber = findViewById(R.id.showDriverNumberButton);
        btnPayment = findViewById(R.id.paymentButton);
        btnRateDriver = findViewById(R.id.rateDriverButton);
    }

    private void initializeIcons() {
        settingsIcon = findViewById(R.id.settings_button);
        notificationIcon = findViewById(R.id.notification_button);
    }

    private void setButtonListeners() {
        // הצגת מספר הטלפון של הנהג
        btnShowDriverNumber.setOnClickListener(v -> {
            if (driverPhoneNumber != null && !driverPhoneNumber.isEmpty()) {
                Toast.makeText(this, "מספר הטלפון של הנהג הוא: " + driverPhoneNumber, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "מספר הנהג אינו זמין", Toast.LENGTH_SHORT).show();
            }
        });

        // מעבר לתשלום בביט
        btnPayment.setOnClickListener(v -> {
            Toast.makeText(this, "מעבר לתשלום בביט", Toast.LENGTH_SHORT).show();
            Intent launchBitIntent = getPackageManager().getLaunchIntentForPackage("il.co.isracard.bit");
            if (launchBitIntent != null) {
                startActivity(launchBitIntent); // פתיחת אפליקציית ביט
            } else {
                Toast.makeText(this, "לא נמצא אפליקציית ביט במכשיר", Toast.LENGTH_LONG).show();
            }
        });

        // דירוג נהג
        btnRateDriver.setOnClickListener(v -> {
            Intent intent = new Intent(MatchActivity.this, RateDriverActivity.class);
            intent.putExtra("driverId", driverId);
            startActivity(intent);
        });
    }

    private void setIconListeners() {
        settingsIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MatchActivity.this, com.example.projectcampusride.SettingsActivity.class);
            startActivity(intent);
        });

        notificationIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MatchActivity.this, NotificationsActivity.class);
            startActivity(intent);
        });

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());
    }
}
