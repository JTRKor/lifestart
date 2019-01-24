package kr.co.orng15.lifestartapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressPopupActivity extends AppCompatActivity {

    @BindView(R.id.web_addr) WebView web_addr;
    @BindView(R.id.daum_result) TextView daum_result;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_popup);
        ButterKnife.bind(this);

        init_webView();
        handler = new Handler();
    }

    //@SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public void init_webView() {
        web_addr = findViewById(R.id.web_addr);
        web_addr.getSettings().setJavaScriptEnabled(true);
        web_addr.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web_addr.addJavascriptInterface(new AndroidBridge(), "TestApp1");
        web_addr.setWebChromeClient(new WebChromeClient());
        web_addr.loadUrl("http://112.219.131.13:13809/lifestart/daum_address.do");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    daum_result.setText(String.format("(%s) %s %s", arg1, arg2, arg3));
                    init_webView();
                    Intent intent = getIntent();
                    intent.putExtra("addr", daum_result.getText().toString());
                    setResult(RESULT_OK, intent);
                    Log.d("hjh", "daum_result="+daum_result.getText().toString());
                    finish();
                }
            });
        }
    }
}
