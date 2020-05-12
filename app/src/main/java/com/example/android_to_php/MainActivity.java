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
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity  {
    final private URL urlAPI =  new URL("http://118.171.121.119/page3.php");
    InputStream inputStream = null;

    String urlWithParams = urlAPI.toString();

    private View.OnClickListener btn_send_click=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Log.i("JSON","Send按鍵觸發");
            postDatatest(txtinput.getText().toString());
//            webView.loadUrl("http://118.171.121.119/page3.php?phoneDataJson="+txtinput.getText().toString());
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
    Gson gson;
    String data;
    private void postDatatest(String input)  {
        try {
            //手機輸入字串(input)轉成 JSON格式
            jsonPhonein = new JSONObject();
            jsonPhonein.put("phoneDataJson", input);
            Log.i("JSON","JSON包裝成功");
            data = input;
//            sendPostDataToInternet(data);
            myAsyncTask myAsyncTask = new myAsyncTask();
            myAsyncTask.execute(data);
            Log.i("JSON","myAsyncTask 執行緒開啟");
        }catch(JSONException je) {
            System.err.println(je);
            Log.i("JSON","JSON包裝失敗");
        }
    }
    private String getDatatest(){
        try {

        }catch (Exception ex){
            ex.printStackTrace();
        }
        String Datatest = null;
        try {
            Datatest = jsonPhonein.getString("phoneDataJson");
        } catch (JSONException e) {
            System.err.println(e);
            Log.i("JSON", "解析JSON失敗");
        }
        txtoutput.setText(Datatest);
        Log.i("JSON", "解析JSON成功 "+ jsonPhonein);
        return Datatest;
    }
    String phoneDataJson="";
    StringBuilder response;
    class myAsyncTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... strings) {
//            HttpURLConnection urlConnection = null;
//            OutputStream out = null;
//            InputStream getinputStream = null;
//            gson = new Gson();
//            String phoneDataJson = gson.toJson(data);

//            Log.i("JSON","Gson toJson:"+ phoneDataJson);
//            Log.i("JSON","Gson toJson:"+ gson.toJson(data));
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) urlAPI.openConnection();
                Log.i("JSON","urlConnection :"+ urlConnection);
                /* optional request header */
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                /* optional request header */
                urlConnection.setRequestProperty("Accept", "application/json");
                /* for Get request */
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.setDoInput(true);    //允許輸入流，即允許下載
                urlConnection.setDoOutput(true);   //允許輸出流，即允許上傳
                urlConnection.setUseCaches(true); //設置是否使用緩存
                phoneDataJson = jsonPhonein.toString();
                Log.i("JSON","Json tostring :"+ phoneDataJson);
                urlConnection.connect();
//                Log.i("JSON","urlConnection.connect(): "+ urlConnection);
//                int statusCode = urlConnection.getResponseCode();
//                Log.i("JSON","statusCode : "+ statusCode);
                 //200 represents HTTP OK //
//                if (statusCode == 200) {
//                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
//                }
                //os = urlConnection.getOutputStream();
//                Log.i("JSON","os urlConnection.getOutputStream() :" + os);

//                OutputStream out = urlConnection.getOutputStream();
//                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
//                bw.write(phoneDataJson);
                DataOutputStream writer = new DataOutputStream( urlConnection.getOutputStream() );
                writer.writeBytes(phoneDataJson);
                writer.flush();

//                bw.flush();
//                out.close();
                urlConnection.getOutputStream().close();
//                bw.close();
                writer.close();

//                //Get Response
                InputStream getinputStream = urlConnection.getInputStream();
                Log.i("JSON","getinputStream  :" + getinputStream);
                BufferedReader reader = new BufferedReader(new InputStreamReader(getinputStream));
                String line;
                response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                    System.err.println(line);
                    response.append('\r');
                }

                reader.close();
                urlConnection.disconnect();
                Log.i("JSON", "disconnect urlConnection");
            }catch (Exception e){
                System.err.println(e + "+___3");
            }


            return null;
        }
    }

    private void InitialComponent() {
        phoneshowdata= findViewById(R.id.phoneshowdata);
        txtoutput = findViewById(R.id.txtoutput);
        txtinput = findViewById(R.id.txtinput);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(btn_send_click);
        btn_get = findViewById(R.id.btn_get);
        btn_get.setOnClickListener(btn_get_click);
    }

    private void webView_all() {
        webView = findViewById(R.id.webView);
        webView.loadUrl("http://118.171.121.119/page3.php");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);  // 取得網頁JS效果
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setBuiltInZoomControls(true);       //是否支持手指縮放
    }

    EditText txtinput;
    TextView txtoutput,phoneshowdata;
    Button btn_send,btn_get;
    WebView webView;
}
