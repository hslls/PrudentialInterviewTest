package cn.com.prudential.interview.test.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import cn.com.prudential.interview.test.R;
import cn.com.prudential.interview.test.bean.MoodIndex;

public class DayOfWeekTag extends AppCompatTextView {

    public DayOfWeekTag(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void update(MoodIndex info) {
        Resources res = getResources();
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) params;
            int size, bottomMargin;
            if (info.isSelected()) {
                size = ViewGroup.LayoutParams.WRAP_CONTENT;
                bottomMargin = 0;
            } else {
                size = res.getDimensionPixelSize(R.dimen.day_of_week_size);
                bottomMargin = res.getDimensionPixelSize(R.dimen.day_of_week_margin_bottom);
            }
            if ((lp.width != size) || (lp.height != size) || (lp.bottomMargin != bottomMargin)) {
                lp.width = size;
                lp.height = size;
                lp.bottomMargin = bottomMargin;
                setLayoutParams(lp);
            }
        }
        setBackgroundResource(info.getStrBgResId());
    }
}
