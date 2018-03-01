package com.example.administrator.voicetotextandthentranslate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.voicetotextandthentranslate.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final int REQ_CODE_SPEECH = 100;
    private ActivityMainBinding mainBinding;
    private CustomSTT customSTT;
    public static String languageString = Locale.ENGLISH.toString();
    public static String speakingLanguage = "English";
    public static String translationLanguage = "Chinese";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);              // 替換原setContentView方法
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            initOnClickListener();
        } else {
            init();
            initOnClickListener();
        }
    }

    private void init() {
        customSTT = new CustomSTT(this, mainBinding, languageString);
        mainBinding.buttonSpeech.setOnClickListener(this);
    }

    public void initOnClickListener() {

        mainBinding.englishCustomToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainBinding.englishCustomToggleButton.setChecked(true);
                mainBinding.chineseCustomToggleButton.setChecked(false);
                mainBinding.japaneseCustomToggleButton.setChecked(false);
                mainBinding.koreanCustomToggleButton.setChecked(false);
                languageString = Locale.ENGLISH.toString();
                speakingLanguage = "English";
                translationLanguage = "Chinese";
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        recreate();
                    }
                }, 100);
            }
        });

        mainBinding.chineseCustomToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainBinding.englishCustomToggleButton.setChecked(false);
                mainBinding.chineseCustomToggleButton.setChecked(true);
                mainBinding.japaneseCustomToggleButton.setChecked(false);
                mainBinding.koreanCustomToggleButton.setChecked(false);
                languageString = Locale.TAIWAN.toString();
                speakingLanguage = "Chinese";
                translationLanguage = "English";
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        recreate();
                    }
                }, 100);
            }
        });

        mainBinding.japaneseCustomToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainBinding.englishCustomToggleButton.setChecked(false);
                mainBinding.chineseCustomToggleButton.setChecked(false);
                mainBinding.japaneseCustomToggleButton.setChecked(true);
                mainBinding.koreanCustomToggleButton.setChecked(false);
                languageString = Locale.JAPANESE.toString();
                speakingLanguage = "Japanese";
                translationLanguage = "Chinese";
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        recreate();
                    }
                }, 100);
            }
        });

        mainBinding.koreanCustomToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainBinding.englishCustomToggleButton.setChecked(false);
                mainBinding.chineseCustomToggleButton.setChecked(false);
                mainBinding.japaneseCustomToggleButton.setChecked(false);
                mainBinding.koreanCustomToggleButton.setChecked(true);
                languageString = Locale.KOREA.toString();
                speakingLanguage = "Korean";
                translationLanguage = "Chinese";
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        recreate();
                    }
                }, 100);
            }
        });
    }

    @Override
    public void onClick(View v) {
        customSTT.startCustomSTT();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_SPEECH) {
            if(resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                mainBinding.textViewSpeechResult.setText(result.get(0));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                }
                else {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(customSTT != null) {
            customSTT.stopCustomSTT();
            customSTT = null;
        }
        super.onDestroy();
    }
}
