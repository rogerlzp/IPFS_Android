package com.codeest.geeknews.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codeest.geeknews.R;


/**
 * Created by jiajia on 2017/4/11.
 */
public class GlideImageLoader implements ImageLoader {
    @Override
    public void init(Context context) {
    }

    @Override
    public void displayImage(Context context,String url, ImageView img, int default_pic) {
       // Glide.with(context).load(url).placeholder(R.drawable.ic_image_holder).error(R.drawable.ic_image_holder).into(img);
        Glide.with(context).load(url).placeholder(R.drawable.icon_placeholder).into(img);
       // Glide.with(context).load(url).placeholder(R.drawable.ic_image_holder).error(R.drawable.ic_image_holder).into(img);
    }

    @Override
    public void displayImage(Context context, int drawable, ImageView img, int default_pic) {
        Glide.with(context).load(drawable).placeholder(R.drawable.icon_placeholder).into(img);
       // Glide.with(context).load(drawable).placeholder(R.drawable.ic_image_holder).error(R.drawable.ic_image_holder).into(img);


    }

    @Override
    public void resume(Context context) {
        Glide.with(context).resumeRequests();
    }

    @Override
    public void pause(Context context) {
        Glide.with(context).pauseRequests();
    }
}
