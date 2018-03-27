package com.tippen.fama.wmtippen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(this);
        //List<Match> list = dbHelper.parseGroupMatches(0);
        setContentView(R.layout.activity_main);
    }

    public void onClickTippuebersicht(View view){
        Intent intent = new Intent(view.getContext(), OverviewActivity.class);
        startActivity(intent);
    }

    public void onClickTippabgeben(View view) {
        Intent intent = new Intent(view.getContext(), HandinActivity.class);
        //intent.setData();
        startActivity(intent);
    }
}
