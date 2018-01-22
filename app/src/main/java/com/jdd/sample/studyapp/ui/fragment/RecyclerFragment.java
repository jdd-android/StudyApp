package com.jdd.sample.studyapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jdd.sample.studyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lc. 2018-01-22 10:03
 * @since 0.5.1
 */

public class RecyclerFragment extends Fragment {

    private View mRoot;
    private RecyclerView mRecyclerView;

    private List<String> mDataList = new ArrayList<>();

    public static RecyclerFragment newInstance() {
        return new RecyclerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_recycler, container, false);
        mRecyclerView = mRoot.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        for (int i = 0; i < 20; i++) {
            mDataList.add("ITEM-" + i);
        }

        mRecyclerView.setAdapter(new MyRecyclerAdapter());
        return mRoot;
    }

    private class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.list_item_recycler, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.setText(mDataList.get(position));
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextTv;

        private MyViewHolder(View view) {
            super(view);
            mTextTv = view.findViewById(R.id.tv);
            view.setOnClickListener(this);
        }

        private void setText(String text) {
            mTextTv.setText(text);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), mTextTv.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
