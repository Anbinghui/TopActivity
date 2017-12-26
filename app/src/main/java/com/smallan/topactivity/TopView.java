package com.smallan.topactivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by An on 2017/12/26.
 */

public class TopView extends LinearLayout {
    private Context mContext;
    private WindowManager mWindowManager;
    private   TextView tvContent1;
    private   TextView tvContent2;
    private LinearLayout llTopView;
    public TopView(Context context) {
        super(context);
        init(context);
    }



    private void init(Context context) {
        this.mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.flow_window_activity,this);
        tvContent1 = findViewById(R.id.tv_content1);
        tvContent2 = findViewById(R.id.tv_content2);
        llTopView = findViewById(R.id.ll_topview);

        llTopView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mContext.startService(new Intent(mContext,TopActivityService.class)
                        .putExtra(TopActivityService.TYPE,TopActivityService.TYPE_CLOSE));
                return false;
            }
        });

    }
}
