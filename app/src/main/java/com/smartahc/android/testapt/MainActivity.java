package com.smartahc.android.testapt;

import android.os.Bundle;
import android.smartahc.com.apt_annotation.BindView;
import android.smartahc.com.apt_library.BindViewTools;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Leero on 2018/12/12.
 * Desc:
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvName)
    TextView test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindViewTools.bind(this);

        if (test == null) {
            Log.e("111", "fuck null");
        } else {
            test.setText("大帅B");
        }
    }
}
