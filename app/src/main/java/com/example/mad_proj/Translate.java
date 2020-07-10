package com.example.mad_proj;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Translate extends AppCompatActivity {

    int tamil = 0;
    int italian = 1;
   TextView textView1;
   StringBuilder sb=new StringBuilder();
   Button button;
   TextToSpeech textToSpeech;
   TextView textView2;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        textView1 = findViewById(R.id.text_tamil);
        textView2 = findViewById(R.id.text_italian);
        button = findViewById(R.id.speaker);
        ArrayList<String> list = getIntent().getStringArrayListExtra("list");
        String text = list.get(0);
        text = text.toLowerCase();

        Log.d("Text",text);
        String URL_l = "";

        URL_l = "https://api.mymemory.translated.net/get?q="+text+"&langpair=en|ta";
        translate_using_api(URL_l,tamil);
        URL_l = "https://api.mymemory.translated.net/get?q="+text+"&langpair=en|it";
        translate_using_api(URL_l,italian);
        textToSpeech = new TextToSpeech(Translate.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i==TextToSpeech.SUCCESS)
                {
                    int ttsLang = textToSpeech.setLanguage(Locale.ITALIAN);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    }
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String st = textView2.getText().toString();
                if(st.isEmpty())
                {
                    Toast.makeText(Translate.this,"Translation did not work",Toast.LENGTH_SHORT).show();
                }
                else
                {
                        textToSpeech.speak(st,TextToSpeech.QUEUE_FLUSH,null);

                }
            }
        });
        /*for(String s:list)
        {
            text = s;
            text = text.toLowerCase();
            URL_l = "https://api.mymemory.translated.net/get?q="+text+"&langpair=en|it";
            Log.d("URL",URL_l);
            Log.d("text",text);
            translate_using_api(URL_l);
        }*/

        //textView.setText(sb.toString());
       // String endpt = "https://translate.yandex.net/api/v1.5/tr.json/translate?"
        Log.d("CHeck",URL_l);
        //asyncTask= (RequestTask) new RequestTask(MainActivity.this ).execute(URL);
    }
    public void translate_using_api(String URL_l, final int code)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_l, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response",response);
                parseData(response,code);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
    public void parseData(String output,int code) {
        String value="";
        try {
            JSONObject temp=new JSONObject(output);
            temp=temp.getJSONObject("responseData");

            //Log.d("TEMp is:",temp.toString());
            value=temp.getString("translatedText");
//			value=temp.getJS("translatedText").toString();
            Log.d("VAL",value);
            Log.d("TEMp is:",temp.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(code==tamil)
        textView1.setText(value.toString());
        else
            textView2.setText(value);
        sb.append(value);
        sb.append("\n");
    }

}