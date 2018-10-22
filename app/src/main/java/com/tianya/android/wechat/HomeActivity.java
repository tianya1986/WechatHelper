package com.tianya.android.wechat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tianya.android.wechat.core.WeChatActivity;
import com.tianya.android.wechat.util.AccessibilityHelper;
import com.tianya.android.wechat.util.PermisionUtil;

public class HomeActivity extends WeChatActivity {

    private final static String TAG = "HomeActivity";

    private static final String[] PERMISSIONS              = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int      REQUEST_CODE_PERMISSIONS = 100;

    private Button mNextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wechat_activity_home);
        setBackTitle(R.string.wechat_title);

        //        setRightTitle(R.string.wechat_next);
        // 请求授权
        PermisionUtil.requestPermissions(this, REQUEST_CODE_PERMISSIONS, PERMISSIONS);

        // 打开无障碍设置界面
        findViewById(R.id.open_accessibility).setOnClickListener(view -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));

        // 下一步
        mNextButton = findViewById(R.id.next);
        mNextButton.setOnClickListener(view -> onNext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AccessibilityHelper.isAccessibilityOpen(mContext)) {
            mNextButton.setClickable(true);
            mNextButton.setEnabled(true);
        }
    }

    private void onNext() {
        // 跳转到自动添加好友配置界面
        startActivity(new Intent(this, AutoAddFriendActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            for (int result : grantResults) {
                if (result == -1) {
                    Toast.makeText(mContext, R.string.wechat_auto_add_friend_need_external_permissions, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
            }
        }
    }


}
