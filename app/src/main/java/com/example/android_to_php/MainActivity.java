package com.example.android_to_php;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitialComponent();
    }

    private void InitialComponent() {
        txtoutput = findViewById(R.id.txtoutput);
        txtinput = findViewById(R.id.txtinput);
    }

    EditText txtinput;
    TextView txtoutput;
}
