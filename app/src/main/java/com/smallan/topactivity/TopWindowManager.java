package com.smallan.topactivity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by An on 2017/12/26.
 */

public class TopWindowManager {
    private Context mContext;
    private WindowManager mWindowManager;
    private TopView mTopView;
    private WindowManager.LayoutParams mParams;

    public TopWindowManager(Context context) {
        this.mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }
    
    private static TopWindowManager mTopWindowManager = null;
    private TopWindowManager (){}
    public static TopWindowManager getInstance(Context context){
        synchronized (TopWindowManager.class) {
            if(mTopWindowManager == null) {
                mTopWindowManager = new TopWindowManager(context);
            }
        }
        return mTopWindowManager;
    }

    public void addView() {
        if (mTopView == null) {
            mTopView = new TopView(mContext);
            if (mParams == null) {
                mParams=new WindowManager.LayoutParams();
                if (Build.VERSION.SDK_INT > 25) {
                    mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                }
            //    mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                mParams.format = PixelFormat.RGBA_8888;
                mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                mParams.gravity = Gravity.LEFT|Gravity.TOP;
                mParams.width =  WindowManager.LayoutParams.WRAP_CONTENT;
                mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                mParams.x = 0;
                mParams.y = 0;
            }

            mTopView.setLayoutParams(mParams);
            mWindowManager.addView(mTopView,mParams);
        }
    }

    public void removeView() {
        if (mTopView != null) {
            mWindowManager.removeView(mTopView);
            mTopView = null;
        }
    }

    public void setContent(TopContentBean bean) {
        if (mTopView != null) {
            TextView tvContent1 = mTopView.findViewById(R.id.tv_content1);
            TextView tvContent2 = mTopView.findViewById(R.id.tv_content2);
            tvContent1.setText(bean.getPackageName());
            tvContent2.setText(bean.getClassName());
        }

    }


    public boolean isShowing() {
        return  mTopView  !=null;
    }
}
