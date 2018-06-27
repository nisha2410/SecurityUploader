package com.arek.securityuploader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LENOVO-PC on 3/4/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewvHolder>
{
    private Context mcontext;
    private List<Upload> muploads;
    public ImageAdapter(Context context, List<Upload> uploads)
    {
        mcontext=context;
        muploads=uploads;
    }
    @Override
    public ImageViewvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.image_item,parent,false);
        return new ImageViewvHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewvHolder holder, int position) {
        Upload uploadCurrent=muploads.get(position);
        //holder.textname.setText(uploadCurrent.getmName());
        Picasso.with(mcontext)
                .load(uploadCurrent.getiUri())
                .placeholder(R.mipmap.ic_launcher)
                .fit().centerCrop().into(holder.imageView);
         }

    @Override
    public int getItemCount() {
        return muploads.size() ;
    }

    public class ImageViewvHolder extends RecyclerView.ViewHolder {

      //  public TextView textname;
        public ImageView imageView;

        public ImageViewvHolder(View itemView) {
            super(itemView);
         //   textname = (TextView) itemView.findViewById(R.id.textname);
            imageView = (ImageView) itemView.findViewById(R.id.imgv);

        }
    }

}
