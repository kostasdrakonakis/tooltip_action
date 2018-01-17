package com.example.tooltip;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.github.kostasdrakonakis.ActionTooltip;
import com.github.kostasdrakonakis.android.views.TooltipActionView;
import com.github.kostasdrakonakis.enums.TooltipPosition;

public class MainActivity extends AppCompatActivity {

    private TooltipActionView tooltipActionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.hello_world).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tooltipActionView.hide();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_menu, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                View view = findViewById(R.id.action_item2);

                tooltipActionView = ActionTooltip.anchorAt(MainActivity.this, view)
                        .setPadding(50, 50, 50, 50)
                        .setPositionTo(TooltipPosition.BOTTOM)
                        .setForeverVisible(true)
                        .setText("I am anchored to MenuItem")
                        .show();
            }
        });
        return true;
    }
}
