package es.lost2found.lost2foundUI.openDataUI;

import android.os.Bundle;
import es.lost2found.R;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class OpenDataActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_data);

        WebView webView = (WebView) this.findViewById(R.id.webview);
        webView.loadUrl("https://ressources.data.sncf.com/explore/dataset/objets-trouves-gares/?sort=date&q=T%C3%A9l%C3%A9phone+Portable+Paris+Montparnasse");

        Toast.makeText(OpenDataActivity.this, "Cargando...", Toast.LENGTH_SHORT).show();

        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}