package com.example.aura.submission3_kamus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aura.submission3_kamus.database.KamusDataHelper;
import com.example.aura.submission3_kamus.helper.SharedPreference;
import com.example.aura.submission3_kamus.model.KamusModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {

    private TextView loading;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getSupportActionBar().hide();

        loading = findViewById(R.id.load);
        progressBar = findViewById(R.id.progress_bar);

        new LoadDataKamus().execute();
    }



    private class LoadDataKamus extends AsyncTask<Void, Integer, Void> {
        KamusDataHelper kamusDataHelper;
        SharedPreference sharedPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusDataHelper = new KamusDataHelper(LoadingActivity.this);
            sharedPreference = new SharedPreference(LoadingActivity.this);
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = sharedPreference.getFirstRun();
            if (firstRun) {
                ArrayList<KamusModel> kamusDataEnglish = preLoadData(R.raw.english_indonesia);
                ArrayList<KamusModel> kamusDataIndonesia = preLoadData(R.raw.indonesia_english);

                publishProgress((int) progress);

                kamusDataHelper.open();

                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) / (kamusDataEnglish.size() + kamusDataIndonesia.size());

                kamusDataHelper.insertTransaction(kamusDataEnglish, true);
                progress += progressDiff;
                publishProgress((int) progress);

                kamusDataHelper.insertTransaction(kamusDataIndonesia, false);
                progress += progressDiff;
                publishProgress((int) progress);

                kamusDataHelper.close();
                sharedPreference.setFirstRun(false);

                publishProgress((int) maxprogress);
            } else {
                loading.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
                try {
                    synchronized (this) {
                        this.wait(1000);
                        publishProgress((int) maxprogress);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(i);

            finish();
        }
    }

    public ArrayList<KamusModel> preLoadData(int data) {
        ArrayList<KamusModel> kamusDataModels = new ArrayList<>();
        BufferedReader reader;
        try {
            Resources resources = getResources();
            InputStream raw_dict = resources.openRawResource(data);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            String line = null;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");
                KamusModel kamusDataModel;
                kamusDataModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusDataModels.add(kamusDataModel);
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kamusDataModels;
    }
}
