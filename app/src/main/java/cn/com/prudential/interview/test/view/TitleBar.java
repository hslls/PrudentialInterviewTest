package cn.com.prudential.interview.test.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.prudential.interview.test.R;

public class TitleBar extends RelativeLayout {

    private TextView mTvTitle;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.view_titlebar, this);
        int paddingLR = getResources().getDimensionPixelSize(R.dimen.title_bar_padding_lr);
        setPadding(paddingLR, 0, paddingLR, 0);

        mTvTitle = findViewById(R.id.title);
        findViewById(R.id.back).setOnClickListener(view -> {
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        });
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setMargin(int left, int top, int right, int bottom) {
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) params;
            lp.leftMargin = left;
            lp.topMargin = top;
            lp.rightMargin = right;
            lp.bottomMargin = bottom;
            setLayoutParams(lp);
        }
    }
}
