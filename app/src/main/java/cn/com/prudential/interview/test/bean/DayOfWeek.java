package cn.com.prudential.interview.test.bean;

import cn.com.prudential.interview.test.R;

public enum DayOfWeek {
    MONDAY(R.string.monday, R.color.primary_text_color),
    TUESDAY(R.string.tuesday, R.color.primary_text_color),
    WEDNESDAY(R.string.wednesday, R.color.primary_text_color),
    THURSDAY(R.string.thursday, R.color.primary_text_color),
    FRIDAY(R.string.friday, android.R.color.white),
    SATURDAY(R.string.saturday, R.color.primary_text_color),
    SUNDAY(R.string.sunday, R.color.primary_text_color);

    private final int mStrResId;
    private final int mTextColorResId;

    DayOfWeek(int strResId, int textColorResId) {
        mStrResId = strResId;
        mTextColorResId = textColorResId;
    }

    int getStrResId() {
        return mStrResId;
    }

    int getTextColorResId() {
        return mTextColorResId;
    }
}
