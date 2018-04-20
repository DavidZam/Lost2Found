package es.lost2found.lost2foundUI.openDataUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import es.lost2found.R;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class OpenDataActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_data);

        WebView webView = (WebView) this.findViewById(R.id.webview);
        webView.loadUrl("Poner la url de francia");

    }
}
