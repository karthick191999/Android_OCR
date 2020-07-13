package com.example.mad_proj;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Italian_frag extends Fragment {
    @Nullable
    TextView textView;
    Button button;
    TextToSpeech textToSpeech;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.italian_frag,container,false);
        textView = viewGroup.findViewById(R.id.text_italian);
        button = viewGroup.findViewById(R.id.speaker_italian);
        Translate_frag activity = (Translate_frag) getActivity();
        assert activity != null;
        String text = activity.getText();
        String URL_l = "https://api.mymemory.translated.net/get?q="+text+"&langpair=en|it";
        translate_using_api(URL_l,1);
        textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
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
                String st = textView.getText().toString();
                if(st.isEmpty())
                {
                    Toast.makeText(getActivity(),"Translation did not work",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH,null);

                }
            }
        });
        return viewGroup;
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

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
        textView.setText(value);
    }
}
