package com.smallan.topactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CheckBox mCheckBox;
    private boolean isCheck;
    private static final int REQUEST_CODE = 1;
    public static final  String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCheckBox = (CheckBox) findViewById(R.id.check_activity);
        checkVersion();
        initData();
    }


    private void initData() {
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheck = b;
                startTop(isCheck);
            }
        });

    }


    /**
     * 检测Android版本是否需要开启权限
     */
    private void checkVersion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.canDrawOverlays(this)) {
                mCheckBox.setChecked(false);
                startActivityForResult(
                        new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()))
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                        REQUEST_CODE
                );
            }

        }
    }

    /**
     * @param ischeck
     */
    private void startTop(boolean ischeck) {
        if (checkAccessibility()) {
            if (ischeck) {
                startService(new Intent(MainActivity.this,TopActivityService.class).putExtra(TopActivityService.TYPE,
                        TopActivityService.TYPE_OPEN));
            }else {
                startService(new Intent(MainActivity.this,TopActivityService.class).putExtra(TopActivityService.TYPE,
                        TopActivityService.TYPE_CLOSE));
            }
        }else {
            mCheckBox.setChecked(false);
        }
    }


    public boolean checkAccessibility() {
        // 判断辅助功能是否开启
        if (!isAccessibilitySettingsOn()) {
            // 引导至辅助功能设置页面
            startActivity(
                    new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            );
            Toast.makeText(MainActivity.this, "请先开启 \"TopActivity\" 的辅助功能", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    /**
     * @return
     * 判断是否开启辅助功能
     */
    private boolean isAccessibilitySettingsOn() {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(getPackageName().toLowerCase());
            }
        }

        return false;
    }


}
