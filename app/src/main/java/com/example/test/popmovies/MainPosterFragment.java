package com.example.test.popmovies;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/16.
 */
public class MainPosterFragment extends Fragment implements AdapterView.OnItemClickListener {

    String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=1d1cd40875197d9e76c870167c8aa3c9";
    private GridView posterView;
    List<MovieContent> posterList;
    private boolean isTwoPage;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        new ImageDownloadTask().execute(URL);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_poster_fragment,container,false);
        posterView = (GridView)view.findViewById(R.id.gridview);
        posterView.setOnItemClickListener(this);
        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.content_layout)!=null){
            isTwoPage=true;
        }else{
            isTwoPage=false;
        }
    }

    private  List<MovieContent> getJsonData(String url) {
        posterList = new ArrayList();
        try {
            String jsonString = readString(new URL(url).openStream());
            JSONObject jsonObject;
            MovieContent content;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    content=new MovieContent();
                    content.posterUrl=jsonObject.getString("poster_path");
                    content.title = jsonObject.getString("title");
                    content.release_date = jsonObject.getString("vote_average");
                    content.vote_average = jsonObject.getString("release_date");
                    content.overview = jsonObject.getString("overview");

                    posterList.add(content);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return posterList;
    }

    private String readString(InputStream in) {
        InputStreamReader isr = null;
        String result = "";
        String line = "";
        try {
            isr = new InputStreamReader(in, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(isr);
        try {
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    class  ImageDownloadTask extends AsyncTask<String, Void, List<MovieContent>> {


        protected List<MovieContent> doInBackground(String... addresses) {
            return getJsonData(addresses[0]);
        }

        @Override
        protected void onPostExecute(List<MovieContent> posterList) {
            super.onPostExecute(posterList);
            MovieAdapter adapter = new MovieAdapter(getActivity(), posterList);
            posterView.setAdapter(adapter);
        }

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MovieContent movieContent=posterList.get(position);
       if(isTwoPage){
          ContentFragment contentFragment=(ContentFragment)getFragmentManager().findFragmentById(R.id.content_fragment);
          contentFragment.refresh(movieContent.title,movieContent.vote_average,movieContent.release_date,movieContent.overview,movieContent.posterUrl);
     }else {

           Intent intent = new Intent(getActivity(), ContentActivity.class);
           String title = movieContent.title;
           String vote_average = movieContent.vote_average;
           String release_date = movieContent.release_date;
           String overview = movieContent.overview;
           String posterUrl = movieContent.posterUrl;
           intent.putExtra("title", title);
           intent.putExtra("vote_average", vote_average);
           intent.putExtra("release_date", release_date);
           intent.putExtra("overview", overview);
           intent.putExtra("posterUrl", posterUrl);
           startActivity(intent);
       }
    }
}
