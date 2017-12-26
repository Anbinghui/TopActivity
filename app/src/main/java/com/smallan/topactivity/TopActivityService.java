package com.smallan.topactivity;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by An on 2017/12/25.
 */

public class TopActivityService extends AccessibilityService {
    public static final String TYPE="SERVICE_TYPE";
    public static final String TYPE_OPEN="SERVICE_OPEN";
    public static final String TYPE_CLOSE="SERVICE_CLOSE";
    public static final String TAG="TopActivityService";
    private TopWindowManager mTopWindowManager;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.d(TAG, "onAccessibilityEvent: " + accessibilityEvent.getPackageName());
        if(accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            TopContentBean bean  = new TopContentBean(accessibilityEvent.getPackageName().toString(),
                    accessibilityEvent.getClassName().toString());
            if (mTopWindowManager != null) {
                mTopWindowManager.setContent(bean);
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mTopWindowManager == null) {
            Log.d(TAG,"onStartCommand");
            mTopWindowManager = TopWindowManager.getInstance(this);
        }
        String type = intent.getStringExtra(TYPE);
        if (type != null) {
            if(TextUtils.equals(type,TYPE_OPEN)) {
                Log.d(TAG,"onStartCommand1");
                mTopWindowManager.addView();
            }else if(TextUtils.equals(type,TYPE_CLOSE)) {
                Log.d(TAG,"onStartCommand2");
                mTopWindowManager.removeView();
            }
        }
        return super.onStartCommand(intent, flags, startId);

    }

    private void startView() {


    }
}
