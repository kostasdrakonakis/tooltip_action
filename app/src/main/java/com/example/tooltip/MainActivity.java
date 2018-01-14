package com.example.tooltip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.kostasdrakonakis.ActionTooltip;
import com.github.kostasdrakonakis.android.views.TooltipActionView;
import com.github.kostasdrakonakis.enums.TooltipPosition;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = getLayoutInflater().inflate(R.layout.tooltip_view, null);
        final TooltipActionView tooltipActionView =
                ActionTooltip.anchorAt(this, findViewById(R.id.hello_world))
                        .padding(100, 100, 100, 100)
                        .setPositionTo(TooltipPosition.TOP)
                        .setForeverVisible(true)
                        .setCustomView(view).show();

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        MainActivity.this,
                        "Button inside tooltip clicked",
                        Toast.LENGTH_SHORT)
                        .show();
                tooltipActionView.hide();
            }
        });

        findViewById(R.id.hello_world).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tooltipActionView.hide();
            }
        });
    }
}
