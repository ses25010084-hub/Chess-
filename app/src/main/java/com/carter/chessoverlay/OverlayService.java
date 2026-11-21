package com.carter.chessoverlay;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.webkit.WebViewAssetLoader;
import androidx.webkit.WebViewClientCompat;

public class OverlayService extends Service {
    private WindowManager wm;
    private View panel;
    private WindowManager.LayoutParams params;
    @Override public void onCreate() {
        super.onCreate(); wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        LinearLayout shell = new LinearLayout(this); shell.setOrientation(LinearLayout.VERTICAL); shell.setBackgroundColor(0xff111923);
        LinearLayout bar = new LinearLayout(this); bar.setGravity(Gravity.CENTER_VERTICAL); bar.setPadding(12,4,8,4); bar.setBackgroundColor(0xff202d3b);
        TextView label = new TextView(this); label.setText("♟  CHESS STUDY     ⋮"); label.setTextColor(0xffeaf5f1); label.setTextSize(14); label.setPadding(4,8,4,8);
        TextView close = new TextView(this); close.setText("  ×  "); close.setTextColor(0xffffffff); close.setTextSize(22);
        bar.addView(label, new LinearLayout.LayoutParams(0, -2, 1)); bar.addView(close);
        WebView web = new WebView(this); web.setBackgroundColor(0xff111923);
        WebSettings ws = web.getSettings(); ws.setJavaScriptEnabled(true); ws.setDomStorageEnabled(false); ws.setAllowFileAccess(false); ws.setAllowContentAccess(false); ws.setSupportZoom(false);
        WebViewAssetLoader loader = new WebViewAssetLoader.Builder().addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this)).build();
        web.setWebViewClient(new WebViewClientCompat(){ @Override public android.webkit.WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request){ return loader.shouldInterceptRequest(request.getUrl()); }});
        web.loadUrl("https://appassets.androidplatform.net/assets/www/index.html");
        shell.addView(bar, new LinearLayout.LayoutParams(-1,48)); shell.addView(web, new LinearLayout.LayoutParams(-1,0,1));
        params = new WindowManager.LayoutParams( WindowManager.LayoutParams.MATCH_PARENT, (int)(getResources().getDisplayMetrics().heightPixels*0.78), WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL; params.y = 80;
        panel = shell; wm.addView(panel, params);
        close.setOnClickListener(v -> stopSelf());
        bar.setOnTouchListener(new View.OnTouchListener(){ float sx,sy; int ox,oy; public boolean onTouch(View v, MotionEvent e){ if(e.getAction()==MotionEvent.ACTION_DOWN){sx=e.getRawX();sy=e.getRawY();ox=params.x;oy=params.y;return true;} if(e.getAction()==MotionEvent.ACTION_MOVE){params.x=ox+(int)(e.getRawX()-sx);params.y=oy+(int)(e.getRawY()-sy);wm.updateViewLayout(panel,params);return true;} return e.getAction()==MotionEvent.ACTION_UP; }});
    }
    @Override public void onDestroy(){ if(panel!=null){ try{wm.removeView(panel);}catch(Exception ignored){} panel=null;} super.onDestroy(); }
    @Override public IBinder onBind(Intent intent){return null;}
}
