package com.example.rochambeau;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class PlayInstructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_play_instructions);

        final Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.accent));

        // Set the background color of the current scaffold to accent color.
        getWindow().setBackgroundDrawableResource(R.color.accent);

        // Set the system navigation bar to accent color.
        window.setNavigationBarColor(this.getResources().getColor(R.color.accent));

        // Get the passed in boolean from the previous activity.
        final boolean useMusic = getIntent().getBooleanExtra("useMusic", false);

        final String whatIsIt = "An easy, fast game that everyone probably already knows. But I like to be comprehensive, so here we go with some rock-paper-scissors instructions. Rock-paper-scissors is a quick win-loose game that is often used to determine who will go first or who will win some other small privilege.";

        final String bestFor = "Two players. But you could have a giant rock-paper-scissors tournament with tons of people!";

        final String whatYouNeed = "Nothing! Well, technically speaking, each player needs to use their two hands.";

        final String howToPlay = "In rock-paper-scissors, two players will each randomly choose one of three hand signs: rock (made by making a fist), paper (made by laying your hand flat), or scissors (made by holding out two fingers to look like scissors). Both players show their signs at the same time to see who will win. Here are the rules that determine which sign beats another:";

        final String[] points = {
                "• Rock wins over scissors (because rock smashes scissors)",
                "• Scissors wins over paper (because scissors cut paper)",
                "• Paper wins over rock (because paper covers rock)",
        };

        final String conclusion = "If both players show the same sign, it's a tie. And that's basically the whole game! It's often played in a best-two-out-of-three format as a quick contest to decide who gets to go first or something like that.";

        final TextView whatIsItText = findViewById(R.id.whatIsIt);
        final TextView bestForText = findViewById(R.id.bestFor);
        final TextView whatYouNeedText = findViewById(R.id.whatYouNeed);
        final TextView howToPlayText = findViewById(R.id.howToPlay);
        final TextView point1 = findViewById(R.id.point_1);
        final TextView point2 = findViewById(R.id.point_2);
        final TextView point3 = findViewById(R.id.point_3);
        final TextView conclusionText = findViewById(R.id.conclusion);

        // Set the text.
        whatIsItText.setText(whatIsIt);
        bestForText.setText(bestFor);
        whatYouNeedText.setText(whatYouNeed);
        howToPlayText.setText(howToPlay);
        point1.setText(points[0]);
        point2.setText(points[1]);
        point3.setText(points[2]);
        conclusionText.setText(conclusion);

        final Button next = findViewById(R.id.start_button);

        next.setOnClickListener(v -> startActivity(new Intent(PlayInstructions.this, SoloMode.class).putExtra("useMusic", useMusic)));
    }
}