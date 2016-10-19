package com.example.pgutierrezd.lab5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView browser;
    EditText url;
    Button buttonIr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonIr = (Button) findViewById(R.id.buttonIr);
        browser = (WebView) findViewById(R.id.webkit);

        browser.getSettings().setJavaScriptEnabled(true);
        //browser.loadUrl("http://www.google.com");
        //String html = "<html><body><strong>Hola mundo desde webkit</strong></body></html>";
        //browser.loadData(html,"text/html","UTF-8");
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });

        buttonIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = (EditText) findViewById(R.id.url);
                boolean urlFinal = URLUtil.isValidUrl(url.getText().toString());
                if(urlFinal) {
                    browser.loadUrl(url.getText().toString());
                }else{
                    Toast.makeText(view.getContext(),"Tu URL no es valida",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
