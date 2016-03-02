package com.example.test.popmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2015/12/7.
 */
public  class MovieAdapter extends BaseAdapter {
    private List<MovieContent> data;
    private LayoutInflater inflater;
    private ImageLoder imageLoder;
    public MovieAdapter(Activity context,List<MovieContent> mlist){
        data=mlist;
        inflater=LayoutInflater.from(context);
        imageLoder=new ImageLoder();
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null) {
            viewHolder=new ViewHolder();
            convertView = inflater.inflate(R.layout.item, null);
            viewHolder.imageView=(ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }else{
          viewHolder= (ViewHolder)convertView.getTag();
        }
        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        String url="http://image.tmdb.org/t/p/w185/"+data.get(position).posterUrl;
        viewHolder.imageView.setTag(url);
        imageLoder.showImageByAsyncTask(viewHolder.imageView, "http://image.tmdb.org/t/p/w185/" + data.get(position).posterUrl);

        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
    }
}

