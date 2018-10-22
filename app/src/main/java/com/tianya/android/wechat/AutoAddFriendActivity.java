package com.tianya.android.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tianya.android.wechat.core.WeChatActivity;

/**
 * 功能选择界面
 */
public class AutoAddFriendActivity extends WeChatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wechat_activity_auto_add_friend);

        setBackTitle(R.string.wechat_auto_add_friend);
        setBackDrawableLeft(R.drawable.general_top_icon_back_transparent_normal_android);
        findViewById(R.id.open_wechat).setOnClickListener((view) -> {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            startActivity(intent);
        });

    }

    @Override
    public void onBackClick(View v) {
        finish();
    }
}
