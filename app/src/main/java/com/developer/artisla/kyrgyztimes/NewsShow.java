package com.developer.artisla.kyrgyztimes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.developer.artisla.kyrgyztimes.model.News;
import com.developer.artisla.kyrgyztimes.model.Videos;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        News news =(News) getIntent().getSerializableExtra("News");

        new HtmlParserManager().execute(news.getNewsUrl());

    }


    private class HtmlParserManager extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                Document document = Jsoup.connect(strings[0]).get();

                Elements content = document.select("div#dle-content");

                Log.d("content ",content.toString());



                return true;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            if (!b) {
            }

        }
    }


}
