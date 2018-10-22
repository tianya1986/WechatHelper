package com.tianya.android.wechat.core;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.tianya.android.wechat.R;
import com.tianya.android.wechat.ui.HeaderView;

public class WeChatActivity extends Activity implements HeaderView.OnHeaderViewListener {

    protected Context          mContext;
    private   ConstraintLayout mConstraintLayout;
    private   HeaderView       mHeaderView;

    @Override
    public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, mConstraintLayout, true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        super.setContentView(R.layout.wechat_layout_root);
        mConstraintLayout = findViewById(R.id.wechat_content_root);

        mHeaderView = findViewById(R.id.wechat_header);
        if (mHeaderView != null) {
            mHeaderView.setOnHeaderViewListener(this);
        }
    }

    protected void setBackTitle(@StringRes int resId) {
        checkHeadView();
        mHeaderView.setBackTitle(resId);
    }

    protected void setBackDrawableLeft(@DrawableRes int resId) {
        checkHeadView();
        mHeaderView.setBackDrawableLeft(resId);
    }

    protected void setRightTitle(@StringRes int resId) {
        checkHeadView();
        mHeaderView.setRightTitle(resId);
    }

    private void checkHeadView() {
        if (mHeaderView == null) {
            throw new NullPointerException("Your layout xml must add HeaderView");
        }
    }

    @Override
    public void onBackClick(View v) {

    }

    @Override
    public void onRightClick(View v) {

    }

    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
        return true;

    }
}