package com.ffish.dlapkpluginc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ffish.dlapkdemo.HostClassForModel3;
import com.ryg.dynamicload.DLBasePluginActivity;


public class MainActivity extends DLBasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HostClassForModel3.methodForModel3(that);
            }
        });
    }

}
