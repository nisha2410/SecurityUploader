package com.arek.securityuploader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import java.util.List;

/**
 * Created by LENOVO-PC on 3/5/2018.
 */

public  class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>
{
    private Context vcontext;
    private List<UploadVideo> vuploads;
    public VideoAdapter(Context context, List<UploadVideo> uploads)
    {
        vcontext=context;
        vuploads=uploads;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(vcontext).inflate(R.layout.video_item,parent,false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        UploadVideo uploadC=vuploads.get(position);
      /*  String filepath=uploadC.getvUri();
        Uri urI = Uri.parse(filepath);
        holder.videoView.setVideoURI(urI);
        //VideoView post_image = (VideoView) mView.findViewById(R.id.post_image);
        post_image.requestFocus();
        post_image.start();

        holder.videoView.setVideoURI();*/

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //UploadVideo uploadC=vuploads.get(position);

        //holder.textname.setText(uploadCurrent.getmName());
        //holder.videoView.setVideoPath(uploadC.getvUri());
    }

    @Override
    public int getItemCount() {
        return vuploads.size();
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder {

        //  public TextView textname;
        public VideoView videoView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            //   textname = (TextView) itemView.findViewById(R.id.textname);
            videoView = (VideoView) itemView.findViewById(R.id.vdv);

        }
    }

}
