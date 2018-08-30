package com.example.lenovo.teslogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Gallery2Activity extends AppCompatActivity {


    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery2);

        Intent intent = getIntent();

        int position = intent.getExtras().getInt("id");
        ImageAdapter adapter = new ImageAdapter(this);

        imageView = (ImageView)findViewById(R.id.mV);
        imageView.setImageResource(adapter.image[position]);

    }
}


