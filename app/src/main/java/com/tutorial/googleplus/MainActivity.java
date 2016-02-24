package com.tutorial.googleplus;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by android-Ram Lakhan on 23/2/16.
 **/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShare = (Button) findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AsyncTask to  Image and Text in share_button click.
                new shareImageTextGPlus().execute();
            }
        });
    }

    /**
     * Create shareImageTextGPlus AsyncTask is share on Google Plus Text and Image.
     */
    private class shareImageTextGPlus extends AsyncTask<Void, Void, Void> {

        /***
         * @param params
         * @return
         */
        @Override
        protected Void doInBackground(Void... params) {
            // Initialize File default null
            File pictureFile = null;

            // Check Google+ app install to executed below code.
            if (isGooglePlusInstalled()) {
                try {
                    File rootSdDirectory = Environment.getExternalStorageDirectory();
//
                    pictureFile = new File(rootSdDirectory, "attachment.jpg");
                    if (pictureFile.exists()) {
                        pictureFile.delete();
                        pictureFile.mkdirs();
                        pictureFile.createNewFile();
                    }
//                    pictureFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(pictureFile);

                    // Set URL to static image path as web.
                    URL url = new URL("https://gadgetunlocker.com/wp-content/uploads/2015/05/Android-M.png");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);
                    connection.connect();
                    InputStream in = connection.getInputStream();

                    byte[] buffer = new byte[1024];
                    int size = 0;
                    while ((size = in.read(buffer)) > 0) {
                        fos.write(buffer, 0, size);
                    }
                    fos.close();

                    Uri pictureUri = Uri.fromFile(pictureFile);

                    // Intent to post text and image share on Google Plus post activity.
                    Intent shareIntent = ShareCompat.IntentBuilder.from(MainActivity.this)
                            .setText("Hello Android!")
                            .setType("image/jpeg")
                            .setStream(pictureUri)
                            .getIntent()
                            .setPackage("com.google.android.apps.plus");
                    startActivity(shareIntent);

                    // .setStream(pictureUri).getIntent()

                } catch (Exception e) {
                    System.out.print(e);
                    // e.printStackTrace();
                }
            }
            return null;
        }

        /**
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!isGooglePlusInstalled()) {
                // application is not install in device as message.
                Toast.makeText(MainActivity.this, "App not Install", Toast.LENGTH_LONG).show();

                // startActivity as link to open google plus in play store link
                final String appPackageName = "com.google.android.apps.plus"; // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        }
    }

    // isGooglePlusInstalled function is check Google+ app Install or not.
    public boolean isGooglePlusInstalled() {
        try {
            getPackageManager().getApplicationInfo("com.google.android.apps.plus", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
