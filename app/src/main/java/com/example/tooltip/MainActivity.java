package com.example.tooltip;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
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

        ActionTooltip.anchorAt(this, R.id.fragment_text)
                .setForeverVisible(true)
                .setPadding(50, 50, 50, 50)
                .setDrawableRight(R.drawable.ic_backup_white_24dp)
                .setAutoLinkMask(Linkify.EMAIL_ADDRESSES)
                .setTextColorId(R.color.colorPrimaryDark)
                .setLinkTextColor(Color.WHITE)
                .setText("Hello from Fragment email@example.com").show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_menu, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                tooltipActionView = ActionTooltip.anchorAt(MainActivity.this, R.id.action_item2)
                        .setPadding(50)
                        .setPositionTo(TooltipPosition.BOTTOM)
                        .setBackgroundColorId(R.color.green)
                        .setForeverVisible(true)
                        .setCustomView(R.layout.tooltip_view)
                        .show();
            }
        });
        return true;
    }
}
