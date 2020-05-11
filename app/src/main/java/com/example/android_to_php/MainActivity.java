package com.example.android_to_php;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private View.OnClickListener btn_send_click=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Log.i("JSON","Send按鍵觸發");
            postDatatest(txtinput.getText().toString());
            Log.i("JSON","Send按鍵結束");
        }
    };
    private View.OnClickListener btn_get_click = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Log.i("JSON","Get按鍵觸發");
            txtoutput.setText(getDatatest());
            Log.i("JSON","Get按鍵結束");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitialComponent();

    }
    JSONObject jsonin;
    private void postDatatest(String input)  {
        try {

            jsonin = new JSONObject();
            jsonin.put("string_input", input);
            Log.i("JSON","包裝成功");
        }catch(JSONException je) {
            System.err.println(je);
            Log.i("JSON","包裝失敗");
        }
    }
    private String getDatatest() {
        String Datatest = null;
        try {
            Datatest = jsonin.getString("string_input");
        } catch (JSONException e) {
            System.err.println(e);
            Log.i("JSON", "解析JSON失敗");
        }
        txtoutput.setText(Datatest);
        Log.i("JSON", "解析成功");
        return Datatest;
    }


    private void InitialComponent() {
        txtoutput = findViewById(R.id.txtoutput);
        txtinput = findViewById(R.id.txtinput);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(btn_send_click);
        btn_get = findViewById(R.id.btn_get);
        btn_get.setOnClickListener(btn_get_click);
    }

    EditText txtinput;
    TextView txtoutput;
    Button btn_send,btn_get;
}
