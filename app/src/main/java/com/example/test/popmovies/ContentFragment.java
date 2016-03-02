package com.example.test.popmovies;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by Administrator on 2015/12/16.
 */
public class ContentFragment extends Fragment implements View.OnClickListener {
    private VideoView videoView;
    private Button play;
    private Button pause;
    private View view;
    private ImageButton collectButton;
    String image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.content_fragment,container,false);
        collectButton=(ImageButton)view.findViewById(R.id.collect);
        play=(Button)view.findViewById(R.id.play);
        pause=(Button)view.findViewById(R.id.pause);
        videoView=(VideoView)view.findViewById(R.id.viedo_view);
        collectButton.setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initVideoPath();
    }

    private void initVideoPath() {
        File file=new File("D:\\","app.mp4");
        videoView.setVideoPath(file.getPath());
    }

    public void refresh(String title,String vote_average,String release_date,String overview,String posterUrl){
       TextView titleText= (TextView) view.findViewById(R.id.title);
       TextView dataText=(TextView)view.findViewById(R.id.release_data);
       TextView voteText=(TextView)view.findViewById(R.id.vote_average);
       TextView overviewText=(TextView)view.findViewById(R.id.plot_synopsis);
       ImageView imageView= (ImageView) view.findViewById(R.id.movie_poseter);
        image="http://image.tmdb.org/t/p/w185/"+posterUrl;
       imageView.setTag(image);
       new ImageLoder().showImageByAsyncTask(imageView, image);
       titleText.setText(title);
       voteText.setText(release_date);
       dataText.setText(vote_average);
       overviewText.setText(overview);
   }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect:

            SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            if (sp.getBoolean("checked", true)) {
                editor.putBoolean("checked", false);
                collectButton.setImageResource(R.drawable.ab);
                editor.putString("image", image);
                editor.commit();
            } else {
                collectButton.setImageResource(R.drawable.ac);
                editor.clear();
                editor.commit();
            }
                break;
            case R.id.play:
                if(!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.pause:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
        }

}

    @Override
    public void onDestroy() {
        if(videoView!=null){
            videoView.suspend();
        }
    }
}
