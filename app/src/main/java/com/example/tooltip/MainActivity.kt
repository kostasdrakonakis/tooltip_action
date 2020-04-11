package com.example.tooltip

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.util.Linkify
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.kostasdrakonakis.ActionTooltip
import com.github.kostasdrakonakis.ActionTooltip.Companion.anchorAt
import com.github.kostasdrakonakis.android.views.TooltipActionView
import com.github.kostasdrakonakis.enums.TooltipPosition
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var tooltipActionView: TooltipActionView
    private lateinit var tooltip: ActionTooltip
    private var view: TooltipActionView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hello_world.setOnClickListener { tooltipActionView.hide() }
        tooltip = anchorAt(this, R.id.fragment_text)
            .setForeverVisible(true)
            .setPadding(50, 50, 50, 50)
            .setDrawableRight(R.drawable.ic_backup_white_24dp)
            .setAutoLinkMask(Linkify.EMAIL_ADDRESSES)
            .setTextColorId(R.color.colorPrimaryDark)
            .setLinkTextColor(Color.WHITE)
            .setText("Hello from Fragment email@example.com")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sample_menu, menu)
        Handler().post {
            tooltipActionView = anchorAt(this@MainActivity, R.id.action_item2)
                .setPadding(50)
                .setPositionTo(TooltipPosition.BOTTOM)
                .setBackgroundColorId(R.color.green)
                .setForeverVisible(true)
                .setCustomView(R.layout.tooltip_view)
                .show()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_item1 -> {
                view = tooltip.show()
                true
            }
            R.id.action_item2 -> {
                view?.hide()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}