package com.yqq.dictwoapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyDownLoadService extends IntentService {
    Toast toast;
    ProgressBar progressBar;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyDownLoadService(String name) {
        super(name);
    }
    public MyDownLoadService(){
        super("MyDownLoadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        toast = new Toast(this);
        View view = View.inflate(this, R.layout.mydownload_tt_layout,null);
        toast.setView(view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    //其它线程
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
//            http://xxxxx/xx.apk
            String urlStr = intent.getStringExtra("urlStr");
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode()!=HttpURLConnection.HTTP_OK){
                throw new RuntimeException("服务器连接失败，错误代码："+conn.getResponseCode());
            }
            InputStream in = conn.getInputStream();
            int length = conn.getContentLength();
            progressBar.setMax(length);//////////////////////
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), System.currentTimeMillis()+".apk");
            FileOutputStream out = new FileOutputStream(file);
            byte[] bytes = new byte[50*1024];
            int count;
            int startProgress = 0;
            while ((count=in.read(bytes))!=-1){
                out.write(bytes,0,count);
                startProgress +=count;
                progressBar.setProgress(startProgress);///////////////
                toast.show();//////////////
            }
            out.close();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
