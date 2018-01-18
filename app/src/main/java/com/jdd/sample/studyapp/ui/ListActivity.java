package com.jdd.sample.studyapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jdd.sample.studyapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView mListView;

    private MyAdapter mAdapter;

    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("ListActivity");

        mListView = findViewById(R.id.listView);
        mAdapter = new MyAdapter();

        mData.addAll(fakeData());
        mListView.setAdapter(mAdapter);
    }


    private List<String> fakeData() {
        return Arrays.asList("小华哥", "徐翔", "张伟", "陈君才");
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(ListActivity.this).inflate(R.layout.list_item_person, parent, false);
            }

            TextView nameTv = convertView.findViewById(R.id.tv_name);
            nameTv.setText(mData.get(position));

            return convertView;
        }
    }
}
