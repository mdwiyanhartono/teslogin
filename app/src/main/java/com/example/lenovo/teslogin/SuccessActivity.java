package com.example.lenovo.teslogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {

    GridView gridView;
    TextView textView, textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

       /* textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        Bundle b = getIntent().getExtras();
        textView.setText(b.getString("username"));
        textView2.setText(b.getString("bank"));
        textView3.setText(b.getString("branch"));*/

        gridView = (GridView)findViewById(R.id.gridv);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Gallery2Activity.class);
                intent.putExtra("id",position);
                startActivity(intent);
            }
        });
    }
}
