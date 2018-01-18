package com.jdd.sample.studyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.jdd.sample.studyapp.data.Person;
import com.jdd.sample.studyapp.ui.BroadcastActivity;
import com.jdd.sample.studyapp.ui.DataDisplayActivity;
import com.jdd.sample.studyapp.ui.DownloadActivity;
import com.jdd.sample.studyapp.ui.ListActivity;
import com.jdd.sample.studyapp.ui.ProviderActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "=== onCreate ===");

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("MainActivity");

        Log.e(TAG, "thread: " + Thread.currentThread().getName());
    }

    public void goDownloadActivity(View view) {
        startActivity(new Intent(this, DownloadActivity.class));
    }

    public void goBroadcastActivity(View view) {
        startActivity(new Intent(this, BroadcastActivity.class));
    }

    public void goProviderActivity(View view) {
        startActivity(new Intent(this, ProviderActivity.class));
    }

    public void goDataActivity(View view) {
        Person person = new Person("呵呵", 10);
        Bundle bundle = new Bundle();
        bundle.putString("bundleStr", "string");
        bundle.putInt("bundleInt", 110);
        DataDisplayActivity.open(this, person, "extraString", bundle);
    }

    public void goListActivity(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "=== onRestart ===");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "=== onStart ===");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "=== onResume ===");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "=== onPause ===");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "=== onStop ===");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "=== onDestroy ===");
    }

}
