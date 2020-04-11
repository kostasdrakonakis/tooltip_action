package com.example.tooltip;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.kostasdrakonakis.ActionTooltip;
import com.github.kostasdrakonakis.android.views.TooltipActionView;
import com.github.kostasdrakonakis.enums.TooltipPosition;

public class MainActivity extends AppCompatActivity {

    private TooltipActionView tooltipActionView;
    private ActionTooltip tooltip;
    private TooltipActionView view;

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

        tooltip = ActionTooltip.anchorAt(this, R.id.fragment_text)
                .setForeverVisible(true)
                .setPadding(50, 50, 50, 50)
                .setDrawableRight(R.drawable.ic_backup_white_24dp)
                .setAutoLinkMask(Linkify.EMAIL_ADDRESSES)
                .setTextColorId(R.color.colorPrimaryDark)
                .setLinkTextColor(Color.WHITE)
                .setText("Hello from Fragment email@example.com");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_item1:
                view = tooltip.show();
                return true;
            case R.id.action_item2:
                view.hide();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
