package com.example.happymind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.happymind.Notes.JournalMainActivity;

public class MainUI extends AppCompatActivity {
LinearLayout chat,pills,journal,yoga,food,music,chatb,doc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        chat=findViewById(R.id.chatButton);
        pills=findViewById(R.id.pillButton);
        journal=findViewById(R.id.journalButton);
        yoga=findViewById(R.id.yogabtn);
        food=findViewById(R.id.foodbtn);
        music=findViewById(R.id.musicbtn);
        chatb=findViewById(R.id.chat);
        doc=findViewById(R.id.doct);
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Doctor.class));
            }
        });
        chatb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BotChat.class));
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Music.class));
            }
        });
        yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Yoga.class));
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Food.class));
            }
        });
        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainUI.this, JournalMainActivity.class));
            }
        });


        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainUI.this,ChatMain.class));
            }
        });
        pills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainUI.this,PillReminder.class));
            }
        });


    }
}