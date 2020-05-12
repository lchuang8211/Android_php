package com.example.android_to_php;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity  {
    final private URL uriAPI =  new URL("http://114.47.23.252/page2.php");

    private View.OnClickListener btn_send_click=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Log.i("JSON","Send按鍵觸發");
            postDatatest(txtinput.getText().toString());
            webView.loadUrl("http://114.47.23.252/page2.php");
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

    public MainActivity() throws MalformedURLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitialComponent();
        webView_all();

    }
    JSONObject jsonPhonein;
    String data;
    private void postDatatest(String input)  {
        try {
            //手機輸入字串(input)轉成 JSON格式
            jsonPhonein = new JSONObject();
            jsonPhonein.put("string_input", input);
            Log.i("JSON","JSON包裝成功");

//            sendPostDataToInternet(data);
            myAsyncTask myAsyncTask = new myAsyncTask();
            myAsyncTask.execute(data);
            Log.i("JSON","myAsyncTask成功");
        }catch(JSONException je) {
            System.err.println(je);
            Log.i("JSON","JSON包裝失敗");
        }
    }
    private String getDatatest() {  //
        String Datatest = null;
        try {
            Datatest = jsonPhonein.getString("string_input");
        } catch (JSONException e) {
            System.err.println(e);
            Log.i("JSON", "解析JSON失敗");
        }
        txtoutput.setText(Datatest);
        Log.i("JSON", "解析JSON成功");
        return Datatest;
    }

    class myAsyncTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            HttpURLConnection httpConnection = null;
            OutputStream os = null;
            data = jsonPhonein.toString();  // JSON檔 轉乘要傳的 String
            Log.i("JSON", "in httpConnection");
            try {
                Log.i("JSON", "try httpConnection");
                httpConnection = (HttpURLConnection) uriAPI.openConnection();
                httpConnection.setRequestMethod("POST");//設定訪問方式 POST/GET
                httpConnection.setRequestProperty("Content-Type","application/json;charset=utf-8");
                Log.i("JSON", "post httpConnection");
                //httpConnection.setDoInput(false);
                httpConnection.setDoOutput(true);// 使用 URL 連線進行輸出

                httpConnection.setConnectTimeout(15000); //連線時間
                //open
                httpConnection.connect();
                Log.i("JSON", "connect OK  httpConnection");
                ////////////////////////////////////////////////////////////////////////////////////////////////////////
                //setup send
                os = new BufferedOutputStream(httpConnection.getOutputStream());
                os.write(data.getBytes());
                Log.i("JSON", "write OK  httpConnection");
                //clean up
                os.flush();
                ////////////////////////////////////////////////////////////////////////////////////////////////////////
                Log.i("JSON", "CLOSE httpConnection");

            }catch (ProtocolException e){
                e.printStackTrace();
                System.err.println(e+"___1");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println(e+"___2");
            }catch (Exception e){
                System.err.println(e+"___3");
            }

            httpConnection.disconnect();
            Log.i("JSON", "disconnect httpConnection");
            return null;
        }
    }



    private void InitialComponent() {
        txtoutput = findViewById(R.id.txtoutput);
        txtinput = findViewById(R.id.txtinput);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(btn_send_click);
        btn_get = findViewById(R.id.btn_get);
        btn_get.setOnClickListener(btn_get_click);
    }

    private void webView_all() {
        webView = findViewById(R.id.webView);
        webView.loadUrl("http://114.47.23.252/page2.php");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);  // 取得網頁JS效果
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setBuiltInZoomControls(true);       //是否支持手指縮放
    }

    EditText txtinput;
    TextView txtoutput;
    Button btn_send,btn_get;
    WebView webView;
}
