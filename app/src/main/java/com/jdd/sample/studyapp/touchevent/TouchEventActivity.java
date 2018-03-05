package com.jdd.sample.studyapp.touchevent;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.widget.TextView;

import com.jdd.sample.studyapp.R;
import com.jdd.sample.studyapp.ui.BaseActivity;

/**
 * @author lc
 */
public class TouchEventActivity extends BaseActivity {

    LogCallBack logCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);

        setToolbarAsClose(v -> finish());
        setTitle("TouchEventActivity");

        initView();
    }

    private void initView() {
        ParentView parentView1 = findViewById(R.id.parent_1);
        ParentView parentView2 = findViewById(R.id.parent_2);
        ChildView childView1 = findViewById(R.id.childView1);
        ChildView childView2 = findViewById(R.id.childView2);

        parentView1.setTag("ParentView1");
        parentView2.setTag("ParentView2");
        childView1.setTag("childView1");
        childView2.setTag("childView2");

        TextView logTv = findViewById(R.id.tv_log);
        logCallBack = (tag, content) -> {
            logTv.append(tag + "  " + content + "\n");
        };
        parentView1.setLogCallBack(logCallBack);
        parentView2.setLogCallBack(logCallBack);
        childView1.setLogCallBack(logCallBack);
        childView2.setLogCallBack(logCallBack);

        findViewById(R.id.btn_clear_log).setOnClickListener(view -> {
            logTv.setText("");
        });
        findViewById(R.id.btn_setting).setOnClickListener(view -> {
            showTouchSettingDialog(parentView1, parentView2, childView1, childView2);
        });
    }

    private void showTouchSettingDialog(ParentView parentView1,
                                        ParentView parentView2,
                                        ChildView childView1,
                                        ChildView childView2) {

        String[] itemText = {
                "parentView1 intercept touch event",
                "parentView1 consume touch event",
                "parentView2 intercept touch event",
                "parentView2 consume touch event",
                "childView1 consume touch event",
                "childView2 consume touch event"};

        boolean[] itemSelectState = {
                parentView1.isInterceptTouchEvent(),
                parentView1.isConsumeTouchEvent(),
                parentView2.isInterceptTouchEvent(),
                parentView2.isConsumeTouchEvent(),
                childView1.isConsumeTouchEvent(),
                childView2.isConsumeTouchEvent()
        };

        new AlertDialog.Builder(this)
                .setMultiChoiceItems(itemText, itemSelectState, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        switch (which) {
                            case 0:
                                parentView1.setInterceptTouchEvent(isChecked);
                                break;
                            case 1:
                                parentView1.setConsumeTouchEvent(isChecked);
                                break;
                            case 2:
                                parentView2.setInterceptTouchEvent(isChecked);
                                break;
                            case 3:
                                parentView2.setConsumeTouchEvent(isChecked);
                                break;
                            case 4:
                                childView1.setConsumeTouchEvent(isChecked);
                                break;
                            case 5:
                                childView2.setConsumeTouchEvent(isChecked);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .setPositiveButton("确定", null)
                .show();

    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        logCallBack.log("Activity", "dispatchTouchEvent " + eventString(ev));
//        return super.dispatchTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        logCallBack.log("Activity", "onTouchEvent " + eventString(event));
//        return super.onTouchEvent(event);
//    }

    private String eventString(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            default:
                return "";
        }
    }
}
