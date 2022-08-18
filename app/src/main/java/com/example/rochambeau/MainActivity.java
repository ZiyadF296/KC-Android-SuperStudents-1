package com.example.rochambeau;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);

        final Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.accent));

        // Set the background color of the current scaffold to accent color.
        getWindow().setBackgroundDrawableResource(R.color.accent);

        // Set the system navigation bar to accent color.
        window.setNavigationBarColor(this.getResources().getColor(R.color.accent));

        final String musicMessage = "Do you want to enable music sounds in the background?";

        // Set the UI text.
        final TextView musicDescription = findViewById(R.id.music_description);
        musicDescription.setText(musicMessage);

        final AtomicBoolean useMusic = new AtomicBoolean(false);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        final Switch musicControl = findViewById(R.id.music_control);

        // Listen to musicControl changes and set the variable [useMusic] to it's
        // corresponding state.
        musicControl.setOnCheckedChangeListener((buttonView, isChecked) -> useMusic.set(isChecked));

        final Button startButton = findViewById(R.id.play_button);

        startButton.setOnClickListener(view -> {
            // Start the play instructions activity and pass to it the variable [useMusic].

            final android.content.Intent intent = new android.content.Intent(this, PlayInstructions.class);

            intent.putExtra("useMusic", useMusic.get());

            startActivity(intent);
        });
    }
}