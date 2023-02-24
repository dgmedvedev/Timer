package demo.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;
    private int seconds = 0;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // получаем значения из метода onSaveInstanceState()
        // например после изменения ориентации экрана
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
        }
        textViewTimer = findViewById(R.id.textViewTimer);
        Button buttonStart = findViewById(R.id.buttonStart);
        Button buttonPause = findViewById(R.id.buttonPause);
        Button buttonReset = findViewById(R.id.buttonReset);

        buttonStart.setOnClickListener(view -> isRunning = true);
        buttonPause.setOnClickListener(view -> isRunning = false);
        buttonReset.setOnClickListener(view -> {
            isRunning = false;
            seconds = 0;
        });

        runTimer();
    }

    // метод onSaveInstanceState() сохраняет текущее состояние активности
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunning);
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int sec = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, sec);
                textViewTimer.setText(time);

                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}