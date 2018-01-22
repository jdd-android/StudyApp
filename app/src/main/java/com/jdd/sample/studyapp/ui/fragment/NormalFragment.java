package com.jdd.sample.studyapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdd.sample.studyapp.R;

/**
 * @author lc. 2018-01-22 10:03
 * @since 0.5.1
 *
 * https://developer.android.com/guide/components/fragments.html
 */

public class NormalFragment extends Fragment {

    private static final String TAG = NormalFragment.class.getSimpleName();

    private View mRoot;

    public static NormalFragment newInstance() {
        return new NormalFragment();
    }

    @Override
    public void onAttach(Context context) {
        Log.v(TAG, "onAttach...");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onCreate...");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView...");
        mRoot = inflater.inflate(R.layout.fragment_normal, container, false);
        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onActivityCreated...");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        Log.v(TAG, "onDestroyView...");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy...");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.v(TAG, "onDetach...");
        super.onDetach();
    }
}
