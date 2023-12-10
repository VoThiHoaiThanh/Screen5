package com.example.airquality;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapActivity extends AppCompatActivity {
    Button Home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Home= findViewById(R.id.button_Screen5);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("mypackage");
        String User = bundle.getString("user");
        String token = bundle.getString("token");
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, DashBoardActivity.class);
                Bundle mybundle = new Bundle();
                mybundle.putString("token",token);
                mybundle.putString("user",User);
                intent.putExtra("mypackage",mybundle);
                startActivity(intent);
            }
        });
    }
}