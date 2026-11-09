package com.myapp.app;
import android.app.*;import android.content.*;import android.graphics.*;import android.net.Uri;import android.os.*;import android.view.*;import android.webkit.*;import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
public class MainActivity extends AppCompatActivity{
WebView w;ProgressBar pb;
int px(float v){return (int)(v*getResources().getDisplayMetrics().density);}
@Override protected void onCreate(Bundle st){super.onCreate(st);Window win=getWindow();
win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
if(Build.VERSION.SDK_INT>=28){WindowManager.LayoutParams a=win.getAttributes();a.layoutInDisplayCutoutMode=WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;win.setAttributes(a);}
win.setStatusBarColor(Color.parseColor("#000000"));win.setNavigationBarColor(Color.parseColor("#000000"));
FrameLayout root=new FrameLayout(this);w=new WebView(this);w.setBackgroundColor(Color.parseColor("#000000"));
SwipeRefreshLayout sr=new SwipeRefreshLayout(this);sr.addView(w);sr.setEnabled(true);sr.setOnRefreshListener(()->{w.reload();sr.setRefreshing(false);});root.addView(sr);
pb=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);pb.setVisibility(View.GONE);root.addView(pb,new FrameLayout.LayoutParams(-1,8));
setContentView(root);
WebSettings t=w.getSettings();t.setJavaScriptEnabled(true);t.setDomStorageEnabled(true);t.setAllowFileAccess(true);t.setAllowContentAccess(true);t.setAllowUniversalAccessFromFileURLs(true);t.setAllowFileAccessFromFileURLs(true);
t.setSupportZoom(true);t.setBuiltInZoomControls(true);t.setDisplayZoomControls(false);t.setUseWideViewPort(true);t.setLoadWithOverviewMode(true);t.setGeolocationEnabled(true);t.setCacheMode(WebSettings.LOAD_CACHE_ONLY);t.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
t.setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120 Safari/537.36");
w.setWebViewClient(new WebViewClient(){
public boolean shouldOverrideUrlLoading(WebView v,WebResourceRequest r){Uri u=r.getUrl();String c=u.getScheme();if("http".equals(c)||"https".equals(c)||"file".equals(c))return false;try{startActivity(new Intent(Intent.ACTION_VIEW,u));}catch(Exception e){}return true;}
public void onReceivedError(WebView v,WebResourceRequest r,WebResourceError e){if(true&&r.isForMainFrame())new AlertDialog.Builder(MainActivity.this).setMessage("لا يوجد اتصال بالإنترنت").setPositiveButton("إعادة المحاولة",(d,i)->w.reload()).setNegativeButton("إغلاق",null).show();}});
w.setWebChromeClient(new WebChromeClient(){
public void onProgressChanged(WebView v,int p){if(true){pb.setVisibility(p<100?View.VISIBLE:View.GONE);pb.setProgress(p);}}
public void onPermissionRequest(PermissionRequest r){r.grant(r.getResources());}
public void onGeolocationPermissionsShowPrompt(String o,GeolocationPermissions.Callback c){c.invoke(o,true,false);}});
if(Build.VERSION.SDK_INT>=23)requestPermissions(new String[]{"android.permission.CAMERA","android.permission.RECORD_AUDIO","android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_COARSE_LOCATION","android.permission.POST_NOTIFICATIONS","android.permission.READ_MEDIA_IMAGES","android.permission.READ_MEDIA_VIDEO","android.permission.READ_MEDIA_AUDIO","android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE","android.permission.ACTIVITY_RECOGNITION","android.permission.READ_PHONE_STATE"},1);

w.loadUrl("file:///android_asset/index.html");}
@Override public void onBackPressed(){if(true&&w.canGoBack()){w.goBack();return;}
if(true){new AlertDialog.Builder(this).setMessage("هل تريد الخروج؟").setPositiveButton("نعم",(d,i)->finish()).setNegativeButton("لا",null).show();return;}super.onBackPressed();}}
