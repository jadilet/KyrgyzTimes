package com.developer.artisla.kyrgyztimes.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.artisla.kyrgyztimes.R;
import com.developer.artisla.kyrgyztimes.adapter.VideoCustomAdapter;
import com.developer.artisla.kyrgyztimes.model.Videos;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artisla on 2/16/16.
 */
public class VideoFragment extends Fragment {
    private final static String TITLE = "title";
    private final static String PAGE = "page";
    private final String SITE_URL = "http://kyrgyztimes.kg";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Button reloadVideoBt;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Videos> videosList = new ArrayList<Videos>();


    public static VideoFragment newInstance(int page, String title) {
        VideoFragment videoFragment = new VideoFragment();
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
        View view = inflater.inflate(R.layout.video_tab, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.videosRv);
        reloadVideoBt = (Button) view.findViewById(R.id.reloadVideoBt);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VideoCustomAdapter(videosList);
        mRecyclerView.setAdapter(mAdapter);


        new HtmlParserManager().execute(SITE_URL);

        reloadVideoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videosList.clear();
                new HtmlParserManager().execute(SITE_URL);
            }
        });


        return view;
    }
    
    private class HtmlParserManager extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                Document document = Jsoup.connect(strings[0]).get();


                Elements videosImage = document.getElementsByClass("videostory").tagName("a").tagName("img");
                Elements popups = document.getElementsByClass("popup").tagName("iframe").tagName("iframe");


                int i = 0;
                for (Element videoImage : videosImage) {

                    Videos videos = new Videos();
                    videos.setTitle(videoImage.select("center").text());

                    videos.setImagePath("http:" + videoImage.select("img[src]").attr("src"));
                    videos.setVideoUrl(popups.select("iframe[src]").get(i++).attr("src"));

                    videosList.add(videos);

                    //     android.util.Log.d("src    ", videoImage.select("img[src]").attr("src"));
                    //     android.util.Log.d("title  ", videoImage.select("center").text());
                    //     Log.d("iframe " + i, popups.select("iframe[src]").get(i++).attr("src"));
                }

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
         //   showVideoList(videosList);


            mAdapter.notifyDataSetChanged();
            if (!b) {
                mRecyclerView.setVisibility(View.GONE);
                reloadVideoBt.setVisibility(View.VISIBLE);
            }

        }
    }
}
