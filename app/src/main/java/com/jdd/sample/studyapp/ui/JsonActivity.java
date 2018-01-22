package com.jdd.sample.studyapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jdd.sample.studyapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonActivity extends AppCompatActivity {

    private static final String TAG = JsonActivity.class.getSimpleName();

    private TextView mJsonTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("JsonActivity");

        mJsonTv = findViewById(R.id.tv_json);

        try {
            printJson(loadJsonFromAssets("home.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void printJson(JSONObject jsonObject) throws JSONException {
        int code = jsonObject.getInt("code");
        String msg = jsonObject.getString("msg");
        JSONArray data = jsonObject.getJSONArray("data");

        Log.v(TAG, "JSON:" + jsonObject.toString());

        mJsonTv.setText(jsonObject.toString());
    }

    private JSONObject loadJsonFromAssets(String filename) {
        try {
            InputStream is = getResources().getAssets().open(filename);
            String jsonString = isToString(is);
            return new JSONObject(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String isToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}
