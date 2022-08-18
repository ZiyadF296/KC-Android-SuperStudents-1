package com.example.rochambeau;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class SoloMode extends AppCompatActivity {
    int totalPlayerWins = 0;

    int maxMatch = 3;
    int totalMatch = 0;

    // Store the computer choice:
    // 1 = Rock
    // 2 = Paper
    // 3 = Scissor
    int computerChoice = 0;

    final AtomicInteger playerChoice = new AtomicInteger(); // Same policy as computer choice.

    Toast toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_solo_mode);

        final Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.accent));

        // Set the background color of the current scaffold to accent color.
        getWindow().setBackgroundDrawableResource(R.color.accent);

        // Set the system navigation bar to accent color.
        window.setNavigationBarColor(this.getResources().getColor(R.color.accent));

        disablePlayerControls(true);

        final ImageView userRock = findViewById(R.id.player_rock);
        final ImageView userPaper = findViewById(R.id.player_paper);
        final ImageView userScissor = findViewById(R.id.player_scissor);

        userRock.setOnClickListener(view -> {
            if (computerChoice != 0) {
                playerChoice.set(1);
                disablePlayerControls(false);
                assortWinner();
            }
        });

        userPaper.setOnClickListener(view -> {
            if (computerChoice != 0) {
                playerChoice.set(2);
                disablePlayerControls(false);
                assortWinner();
            }
        });

        userScissor.setOnClickListener(view -> {
            if (computerChoice != 0) {
                playerChoice.set(3);
                disablePlayerControls(false);
                assortWinner();
            }
        });

        computeComputerChoice();
    }

    @SuppressLint({ "DefaultLocale", "ShowToast" })
    private void assortWinner() {
        // Compare the player choice with the computer choice.
        final int winner = computerWinner(playerChoice.get(), computerChoice);

        // Remove all toasts before adding a new one.
        Toast.makeText(this, "", Toast.LENGTH_SHORT).cancel();

        if (winner == 1) {
            totalPlayerWins++;
            totalMatch++;

            if (toastMessage != null) {
                toastMessage.cancel();
            }

            // Show a toast message to the user.
            toastMessage = Toast.makeText(getApplicationContext(),
                    "You won this round!", Toast.LENGTH_SHORT);
            toastMessage.show();
        } else if (winner == 2) {

            if (toastMessage != null) {
                toastMessage.cancel();
            }

            // Show a toast message to the user.
            toastMessage = Toast.makeText(getApplicationContext(),
                    "Dang, computer won this round!", Toast.LENGTH_SHORT);
            toastMessage.show();
            totalMatch++;
        } else {
            if (toastMessage != null) {
                toastMessage.cancel();
            }

            // Show a toast message to the user.
            toastMessage = Toast.makeText(getApplicationContext(),
                    "It's a tie!", Toast.LENGTH_SHORT);
            toastMessage.show();
        }

        // Update the UI.
        final TextView scoreText = findViewById(R.id.score);

        scoreText.setText(String.format("%d - %d", totalPlayerWins, totalMatch - totalPlayerWins));

        // Check to see if this was the final match.
        if (totalMatch == maxMatch) {
            if (toastMessage != null) {
                toastMessage.cancel();
            }

            if (totalPlayerWins > totalMatch - totalPlayerWins) {
                toastMessage = Toast.makeText(getApplicationContext(),
                        "You won the game!", Toast.LENGTH_SHORT);

            } else {
                toastMessage = Toast.makeText(getApplicationContext(),
                        "Computer won the game!", Toast.LENGTH_SHORT);

            }

            toastMessage.show();

            finish();
            return;
        }

        computeComputerChoice();
    }

    @SuppressLint("SetTextI18n")
    private void computeComputerChoice() {
        disablePlayerControls(true);

        final TextView statusIndicator = findViewById(R.id.current_state);

        statusIndicator.setText("Computer is picking...");

        final Handler mainHandler = new Handler(Looper.getMainLooper());

        final Runnable myRunnable = () -> {
            final Random random = new Random();

            // We will store the computer choice in this variable.
            computerChoice = random.nextInt(3) + 1;

            System.out.println("Choice: " + computerChoice);

            disablePlayerControls(false);
            playerChoice.set(0);

            statusIndicator.setText("Your turn! Pick wisely!");
        };

        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mainHandler.post(myRunnable);
            }
        }, 3000);
    }

    private void disablePlayerControls(boolean disable) {
        final ImageView userRock = findViewById(R.id.player_rock);
        final ImageView userPaper = findViewById(R.id.player_paper);
        final ImageView userScissor = findViewById(R.id.player_scissor);

        final ImageView computerRock = findViewById(R.id.computer_rock);
        final ImageView computerPaper = findViewById(R.id.computer_paper);
        final ImageView computerScissor = findViewById(R.id.computer_scissor);

        userRock.setAlpha(Float.parseFloat(disable ? "0.5" : "1"));
        userPaper.setAlpha(Float.parseFloat(disable ? "0.5" : "1"));
        userScissor.setAlpha(Float.parseFloat(disable ? "0.5" : "1"));

        computerRock.setAlpha(Float.parseFloat(!disable ? "0.5" : "1"));
        computerPaper.setAlpha(Float.parseFloat(!disable ? "0.5" : "1"));
        computerScissor.setAlpha(Float.parseFloat(!disable ? "0.5" : "1"));
    }

    // 0 means draw
    // 1 means player wins
    // 2 means computer wins
    //
    // Variable a is the player choice.
    // Variable b is the computer choice.
    private static int computerWinner(int a, int b) {
        // Rock = 1
        // Paper = 2
        // Scissor = 3

        if (a == b) {
            return 0;
        } else if (a == 1 && b == 3) {
            return 1;
        } else if (a == 1 && b == 2) {
            return 2;
        } else if (a == 2 && b == 1) {
            return 2;
        } else if (a == 2 && b == 3) {
            return 1;
        } else if (a == 3 && b == 1) {
            return 1;
        } else if (a == 3 && b == 2) {
            return 2;
        } else {
            return 0;
        }
    }
}