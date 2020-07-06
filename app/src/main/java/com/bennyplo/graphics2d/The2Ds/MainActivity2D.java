package com.bennyplo.graphics2d;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2D extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //replace the view with my custom designed view
        MyView2D myView = new MyView2D(this);
        setContentView(myView);
    }

}
