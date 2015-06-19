package com.example.tts.app;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;


public class MainActivity extends ActionBarActivity implements TextToSpeech.OnInitListener  {

    EditText mViewContent;
    Button mActionSpeak;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewContent = (EditText) findViewById(R.id.content);
        mActionSpeak = (Button) findViewById(R.id.action_speak);
        mActionSpeak.setOnClickListener(onClickSpeak);

        tts = new TextToSpeech(this, this);

    }

    View.OnClickListener onClickSpeak = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //String content = mViewContent.getText().toString();
            String content = "This is only a test.";
            tts.speak(content, TextToSpeech.QUEUE_FLUSH, null);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.ENGLISH);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                mActionSpeak.setEnabled(true);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
}
