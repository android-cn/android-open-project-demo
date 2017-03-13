package com.ffish.dlapkpluginb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ffish.dlapkplugininterface.InterfaceManager;
import com.ryg.dynamicload.DLBasePluginActivity;

public class MainActivity extends DLBasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        that.setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterfaceManager.getInstance().getHostInterface().methodInHost();
            }
        });
    }

}
