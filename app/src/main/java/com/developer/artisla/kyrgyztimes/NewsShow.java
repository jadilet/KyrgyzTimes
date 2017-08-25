package com.developer.artisla.kyrgyztimes;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.ImageView;
import com.developer.artisla.kyrgyztimes.model.News;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsShow extends AppCompatActivity {

    private static final String SITE_URL = "http://kyrgyztimes.kg/";

    private TextView contentTv;
    private ImageView newsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        contentTv = (TextView) findViewById(R.id.contentShowTv);
        newsIv = (ImageView) findViewById(R.id.newsShowIv);

        News news =(News) getIntent().getSerializableExtra("News");

        toolbar.setTitle(news.getTitle());
        setSupportActionBar(toolbar);

        new HtmlParserManager().execute(news.getNewsUrl());

    }

    public void loadImage(String path){
        Picasso.with(this).load(Uri.parse(SITE_URL + path)).into(newsIv);
    }


    private class HtmlParserManager extends AsyncTask<String, Void, String> {

        private String imagePath="";
        private StringBuilder stringBuilder;

        @Override
        protected String doInBackground(String... strings) {

            stringBuilder = new StringBuilder();

            try {
                Document document = Jsoup.connect(strings[0]).get();

                Elements content = document.select("div[class=news-text clearfix]");
                imagePath = content.select("img[src]").attr("src");
                Log.d("ImagePath ",imagePath);

                for(Element element:content) {
                    Log.d("content br ", element.tagName("br").text());
                    stringBuilder.append(element.tagName("br").text());
                }

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            contentTv.setText(result);
            loadImage(imagePath);
        }
    }
}
