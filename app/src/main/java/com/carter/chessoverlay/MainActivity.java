package com.carter.chessoverlay;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(28, 36, 28, 28);
        root.setBackgroundColor(Color.rgb(16,21,29));
        TextView title = new TextView(this);
        title.setText("CHESS STUDY OVERLAY"); title.setTextColor(Color.WHITE); title.setTextSize(24); title.setGravity(Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(-1, 70));
        TextView desc = new TextView(this);
        desc.setText("Offline chessboard + Stockfish analysis\n\nUse for practice, imported positions and post-game review. This app does not read live games or play moves for you.");
        desc.setTextColor(Color.LTGRAY); desc.setTextSize(16); desc.setPadding(4,20,4,24);
        root.addView(desc, new LinearLayout.LayoutParams(-1, -2));
        Button start = new Button(this); start.setText("ENABLE FLOATING PANEL");
        root.addView(start, new LinearLayout.LayoutParams(-1, 58));
        TextView note = new TextView(this); note.setText("Android will ask for permission to display over other apps. You can stop the panel from its close button."); note.setTextColor(Color.GRAY); note.setPadding(4,24,4,0);
        root.addView(note, new LinearLayout.LayoutParams(-1,-2));
        start.setOnClickListener(v -> {
            if (!Settings.canDrawOverlays(this)) {
                Intent permission = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivity(permission);
            } else {
                startService(new Intent(this, OverlayService.class));
                finish();
            }
        });
        setContentView(root);
    }
    @Override protected void onResume() { super.onResume(); }
}
