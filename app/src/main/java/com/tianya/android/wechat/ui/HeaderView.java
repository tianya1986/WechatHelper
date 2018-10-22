package com.tianya.android.wechat.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tianya.android.wechat.R;

public class HeaderView extends FrameLayout {

    /**
     * 返回按钮
     */
    private TextView mBack;
    private TextView mRight;

    public HeaderView(@NonNull Context context) {
        this(context, null);
    }

    public HeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.wechat_layout_header, this, true);
        mBack = findViewById(R.id.wechat_button_back);
        mBack.setOnClickListener(mClickListener);

        mRight = findViewById(R.id.wechat_header_right);
        mRight.setOnClickListener(mClickListener);
    }

    public void setBackTitle(@StringRes int resId) {
        mBack.setText(resId);
    }

    public void setBackDrawableLeft(@DrawableRes int resId) {
        Drawable drawable = getContext().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mBack.setCompoundDrawables(drawable, null, null, null);
    }

    public void setRightTitle(@StringRes int resId){
        mRight.setText(resId);
    }

    private OnClickListener mClickListener = this::onClick;

    private OnHeaderViewListener mListener;

    public void setOnHeaderViewListener(OnHeaderViewListener listener) {
        this.mListener = listener;
    }

    private void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.wechat_button_back:
                if (mListener != null) {
                    mListener.onBackClick(view);
                }
                break;
            case R.id.wechat_header_right:
                if (mListener != null) {
                    mListener.onRightClick(view);
                }
                break;
        }
    }

    public interface OnHeaderViewListener {
        void onBackClick(View v);

        void onRightClick(View v);
    }
}
