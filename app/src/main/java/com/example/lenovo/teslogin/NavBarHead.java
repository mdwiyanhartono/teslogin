package com.example.lenovo.teslogin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class NavBarHead extends AppCompatActivity {
    Bundle b = getIntent().getExtras();

    TextView textView1,textView2,textView3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main2);

        textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.textView1);
        textView3 = findViewById(R.id.textView2);
        //Bundle b = getIntent().getExtras();
        textView1.setText(b.getString("username"));
        textView2.setText(b.getString("bank"));
        textView3.setText(b.getString("branch"));

    }
}
