package com.github.kostasdrakonakis.listeners;

import android.view.View;

public interface DisplayListener {
    void onViewDisplayed(View view);

    void onViewHidden(View view);
}
