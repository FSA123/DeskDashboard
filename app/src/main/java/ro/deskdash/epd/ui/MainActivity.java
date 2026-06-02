package ro.deskdash.epd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ro.deskdash.epd.R;
import ro.deskdash.epd.service.DashboardService;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Auto-start on launch — EPD display can't render standard Android views,
        // so we can't rely on the user tapping a button.
        startForegroundService(new Intent(this, DashboardService.class));

        Button startBtn = findViewById(R.id.btn_start);
        Button stopBtn  = findViewById(R.id.btn_stop);
        TextView statusTv = findViewById(R.id.tv_status);

        statusTv.setText("Service started");

        startBtn.setOnClickListener(v -> {
            startForegroundService(new Intent(this, DashboardService.class));
            statusTv.setText("Service started");
        });

        stopBtn.setOnClickListener(v -> {
            stopService(new Intent(this, DashboardService.class));
            statusTv.setText("Service stopped");
        });
    }
}
