package com.example.lenovo.teslogin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    public Integer [] image = {
      R.drawable.contoh1,R.drawable.contoh2,R.drawable.contoh3,R.drawable.contoh4,R.drawable.contoh5

    };

    public ImageAdapter (Context c){
        context = c;
    }


    @Override
    public int getCount() {
        return image.length;

    }

    @Override
    public Object getItem(int position) {
        return image[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context) ;
        imageView.setImageResource(image [position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(240,240));
        return imageView;
    }
}
