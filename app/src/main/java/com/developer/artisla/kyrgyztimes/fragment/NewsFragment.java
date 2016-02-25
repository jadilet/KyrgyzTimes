package com.developer.artisla.kyrgyztimes.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.developer.artisla.kyrgyztimes.EndlessRecyclerOnScrollListener;

import com.developer.artisla.kyrgyztimes.R;
import com.developer.artisla.kyrgyztimes.adapter.NewsCustomAdapter;
import com.developer.artisla.kyrgyztimes.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.widget.Toast;

/**
 * Created by artisla on 2/16/16.
 */
public class NewsFragment extends Fragment {

    private final static String TITLE = "title";
    private final static String PAGE = "page";
    private final String SITE_URL = "http://kyrgyztimes.kg";
    private RecyclerView mRecyclerView;
    private NewsCustomAdapter mAdapter;
    private Button reloadBt;
    private List<News> newsList;

    public static NewsFragment newInstance(int page, String title) {
        NewsFragment videoFragment = new NewsFragment();
        Bundle args = new Bundle();

        args.putInt(PAGE, page);
        args.putString(TITLE, title);
        videoFragment.setArguments(args);
        return videoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.news_tab, container, false);

        newsList = new ArrayList<>();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.newsRv);

        reloadBt = (Button) view.findViewById(R.id.reloadBt);

        mRecyclerView.setHasFixedSize(true);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                // scroll end
                loadData(current_page);

                Toast.makeText(view.getContext(), "onLoadMore ", Toast.LENGTH_SHORT).show();

                mAdapter.notifyDataSetChanged();

                // Toast.makeText(view.getContext(), "test", Toast.LENGTH_SHORT).show();
            }
        });


        new HtmlParserManager().execute(SITE_URL);


        mAdapter = new NewsCustomAdapter(newsList);
        mRecyclerView.setAdapter(mAdapter);


        reloadBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsList.clear();
                new HtmlParserManager().execute(SITE_URL);
            }
        });

        return view;
    }

    // this function fetches data from site pagination
    public void loadData(int page) {

        new HtmlParserManager().execute("http://kyrgyztimes.kg/index.php?cstart=" + page + "&");
    }


    private class HtmlParserManager extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            try {

                Document document = Jsoup.connect(strings[0]).get();
                Elements content = document.getElementsByClass("col2").select("article");

                for (Element element : content) {
/*
                       Log.d("In content news header ", element.select("div.border-header-main").text());
                       Log.d("In content comment",element.select("span").first().tagName("a").text());
                       Log.d("In content news title ", element.select("span").select("a[title]").text());
                       Log.d("In content news href",element.select("span").select("a[title]").attr("href"));*/

                    News news = new News();

                    news.setTitle(element.select("span").select("a[title]").text());
                    news.setComment(Integer.parseInt(element.select("span").first().tagName("a").text()));
                    news.setHeader(element.select("div.border-header-main").text());
                    news.setNewsUrl(SITE_URL + element.select("span").select("a[title]").attr("href"));

        /*            Log.d("title ", news.getTitle());
                    Log.d("comment ", String.valueOf(news.getComment()));
                    Log.d("header ", news.getHeader());
                    Log.d("url ", news.getNewsUrl());
*/

                    if (!TextUtils.isEmpty(news.getHeader())){
                        News newsHeader = new News();
                        newsHeader.setHeader(news.getHeader());
                        newsList.add(newsHeader);
                        Log.d("Header: ","news=" + news.getHeader());
                    }
                    news.setHeader("");
                    newsList.add(news);
                }
                return true;
                //   Log.d("Content news ",content.toString());
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
            mAdapter.notifyDataSetChanged();
            if (!b) {
                mRecyclerView.setVisibility(View.GONE);
                reloadBt.setVisibility(View.VISIBLE);
            }
        }
    }
}
