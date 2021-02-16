package com.davidkirsch.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {

    //Declare component variables
    Button endButton, restartButton;
    TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        // init variables
        endButton = findViewById(R.id.btn_end_screen_end);
        restartButton = findViewById(R.id.btn_end_screen_restart);
        messageTextView = findViewById(R.id.tv_end_screen_message);

        messageTextView.setText(getIntent().getStringExtra("message"));


        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EndScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}