package cn.ruicz.basecore.manager;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import cn.ruicz.basecore.R;

/**
 * Created by CLW on 2017/3/20.
 * 图片加载
 */

public enum ImageLoadManager {
    INSTANCE;

    RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.imageholder)                //加载成功之前占位图
            .error(R.mipmap.imageholder)                    //加载错误之后的错误图
//指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.DATA);        //只缓存原来分辨率的图片

    public void load(Fragment fragment, String imgStr, ImageView imageView) {
        Glide.with(fragment)
                .load(imgStr)
                .apply(options)
                .into(imageView);
    }


    public void load(Context context, String imgStr, ImageView imageView) {
        Glide.with(context)
                .load(imgStr)
                .apply(options)
                .into(imageView);
    }

    public void load(Context context, String base64, ImageView imageView, boolean isBase64) {
        Glide.with(context)
                .load(Base64.decode(base64, Base64.NO_WRAP))
                .apply(options)
                .into(imageView);
    }


    public void load(Context context, String imgStr, ImageView imageView, Transformation... transformation) {
        Glide.with(context)
                .load(imgStr)
                .apply(options.transforms(transformation))
                .into(imageView);
    }

    public void load(Fragment fragment, @DrawableRes int imgStr, ImageView imageView) {
        Glide.with(fragment)
                .load(imgStr)
                .into(imageView);
    }

    public void load(Context context, @DrawableRes int imgStr, ImageView imageView) {
        Glide.with(context)
                .load(imgStr)
                .into(imageView);
    }

    public void load(Context context, @DrawableRes int imgStr, ImageView imageView, Transformation... transformation) {
        Glide.with(context)
                .load(imgStr)
                .apply(options.transforms(transformation))
                .into(imageView);
    }

    public void load(Fragment fragment, Uri imgStr, ImageView imageView) {
        Glide.with(fragment)
                .load(imgStr)
                .apply(options)
                .into(imageView);
    }

    public void load(Context context, Uri imgStr, ImageView imageView) {
        Glide.with(context)
                .load(imgStr)
                .apply(options)
                .into(imageView);
    }

    public void load(Context context, Uri imgStr, ImageView imageView, Transformation... transformation) {
        Glide.with(context)
                .load(imgStr)
                .apply(options.transforms(transformation))
                .into(imageView);
    }
}
