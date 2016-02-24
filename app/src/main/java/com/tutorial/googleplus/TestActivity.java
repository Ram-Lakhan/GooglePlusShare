package com.tutorial.googleplus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.plus.PlusShare;

/**
 * Created by android-Ram Lakhan on 23/2/16.
 **/

public class TestActivity extends Activity {

    private String imageUrl = "http://i.forbesimg.com/media/lists/companies/google_416x416.jpg";
    private String description = "Google Plus share Tutorial";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        Button btnShare = (Button) findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPackageInstalled("com.google.android.apps.plus", mContext)) {
                    if (imageUrl == null) {
                        imageUrl = "";
                    }
                    if (description == null) {
                        description = "";
                    }

                    Intent shareIntent = new PlusShare.Builder(TestActivity.this)
                            .setType("text/plain")
                            .setText("ABC")
                            .setContentUrl(Uri.parse("http://i.forbesimg.com/media/lists/companies/google_416x416.jpg"))
                            .getIntent();

                    startActivityForResult(shareIntent, 0);
                } else {
                    Toast.makeText(mContext, "Application not found", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_FOR_GOOGLE_PLUS) {
//            if (resultCode == RESULT_OK) {
////                finish();
//            } else {
//                Toast.makeText(mContext, "Post Cancel?", Toast.LENGTH_LONG).show();
////                finish();
//            }
//        }
//    }
}
