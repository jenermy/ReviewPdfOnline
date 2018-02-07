package com.example.reviewpdf;

import android.content.Intent;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


//可参考这个构建文档https://www.cnblogs.com/iPing9/p/7154753.html
//https://github.com/mozilla/pdf.js
public class MainActivity extends AppCompatActivity {
    private WebView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pdfView = (WebView)findViewById(R.id.pdfView);
        WebSettings webSettings = pdfView.getSettings();
        webSettings.setSavePassword(false);
        // 开启 javascript 功能；原因：站点采用了 javascript
        webSettings.setJavaScriptEnabled(true);
        // 这句解决本地跨域问题，如果你的 PDF 文件在站点里，是不需要的，但是，我们一般情况是加载站点外部 PDF 文件
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setBuiltInZoomControls(true);
        pdfView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                handler.proceed();
                //super.onReceivedSslError(view, handler, error);
            }
        });



        // demo code
        /*
         * "file:///android_asset/pdf-website/index.html?pdf="这里是固定的，当然 `pdf-website`
         * 取决于开发者自己目录名称
         * 参数：pdf = 这里是 PDF 文件路径
         */
        /*
        * pdf/packt-gradle-for-android.pdf  指的是你asset的文件夹下pdf文件夹中的
        * */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //index1.html可以加载本地和服务器的pdf，但是加载不出来中文的
                //viewer.html可以加载本地和服务器的pdf,中英文都可以，但是需要改一点东西
                /**加载服务器的pdf时，出错：file origin does not match viewer，
                 * 解决办法：在pdf/generic/web/viewer.js找到file origin does not match viewer这句，在HOSTED_VIEWER_ORIGINS数组里加入你自己的服务器部分地址，比如http://xinet.haha.com'
                 * var HOSTED_VIEWER_ORIGINS = ['null', 'http://mozilla.github.io', 'https://mozilla.github.io','http://xinet.haha.com'];
                 *validateFileURL = function validateFileURL(file) {
                 *try {
                 *
                 //     var viewerOrigin = new URL(window.location.href).origin || 'null';
                 var viewerOrigin = 'http://xinet.haha.com';
                 if (HOSTED_VIEWER_ORIGINS.indexOf(viewerOrigin) >= 0) {
                 return;  //让服务器的文件跳过检查，直接返回，就可以打开了
                 }
                 var fileOrigin = new URL(file, window.location.href).origin;
                 if (fileOrigin !== viewerOrigin) {
                 throw new Error('file origin does not match viewer\'s' + 'viewerOrigin:' + viewerOrigin + ' ' + 'fileOrigin:' + fileOrigin);
                 }
                 }
                 */
                /**
                 * 解决电子签章不显示的问题：
                 *在pdf.worker.js文件中找到如下代码段，将this.setFlags(AnnotationFlag.HIDDEN);注释掉就会显示电子签章，反之不显示
                 * 在pdf/generic/build/pdf.worker.js搜索this.setFlags或者AnnotationFlag.HIDDEN，找到
                 *  if (data.fieldType === 'Sig') {
                 //      this.setFlags(_util.AnnotationFlag.HIDDEN); 注释掉这句就可以了
                 }
                 */
//                pdfWebView.loadUrl("file:///android_asset/pdf/generic/web/viewer.html?file=" + "../../151635381628312151.pdf");
//                  pdfView.loadUrl("file:///android_asset/pdf/index1.html?file=" + "packt-gradle-for-android.pdf");
                pdfView.loadUrl("file:///android_asset/pdf/generic/web/viewer.html?file=" + "http://xinet.XXX.com/old_pdf/151635381628312151.pdf");
            }
        });
    }
}
