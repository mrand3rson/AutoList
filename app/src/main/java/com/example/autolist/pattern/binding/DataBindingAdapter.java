package com.example.autolist.pattern.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.autolist.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Andrei on 07.08.2018.
 */

public class DataBindingAdapter {
    @BindingAdapter("imageResource")
    public static void setImageFromUrl(ImageView imageView, String url){
        if (url != null) {
            Picasso.with(imageView.getContext())
                    .load(url)
                    .error(android.R.drawable.ic_dialog_alert)
                    .into(imageView);
        } else {
            Picasso.with(imageView.getContext())
                    .load(android.R.drawable.ic_menu_camera)
                    .error(android.R.drawable.ic_dialog_alert)
                    .into(imageView);
        }
    }
}
