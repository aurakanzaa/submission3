package com.example.aura.submission3_kamus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aura.submission3_kamus.helper.Config;

public class DetailActivity extends AppCompatActivity {

    private TextView tword;
    private TextView ttranslate;
    String word, translate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tword = (TextView) findViewById(R.id.word);
        ttranslate = (TextView) findViewById(R.id.translate);
        Intent i = getIntent();
        word = i.getStringExtra(Config.BUNDLE_WORD);
        translate = i.getStringExtra(Config.BUNDLE_TRANSLATE);
        tword.setText(word);
        ttranslate.setText(translate);

    }
}
