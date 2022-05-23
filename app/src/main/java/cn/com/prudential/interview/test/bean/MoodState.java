package cn.com.prudential.interview.test.bean;

import cn.com.prudential.interview.test.R;

public enum MoodState {
    SAD(R.drawable.bg_mood_bar_sad, R.drawable.bg_mood_bar_sad_selected, R.drawable.icon_mood_sad, R.drawable.icon_mood_sad, R.color.mood_sad_selected),
    NORMAL(R.drawable.bg_mood_bar_normal, R.drawable.bg_mood_bar_normal_selected, R.drawable.icon_mood_normal, R.drawable.icon_mood_normal_selected, R.color.mood_normal_selected),
    HAPPY(R.drawable.bg_mood_bar_happy, R.drawable.bg_mood_bar_happy_selected, R.drawable.icon_mood_happy, R.drawable.icon_mood_happy_selected, R.color.mood_happy_selected);

    private final int mBarBgResId;
    private final int mBarSelectedBgResId;
    private final int mIconResId;
    private final int mSelectedIconResId;
    private final int mSelectedTextColorResId;

    MoodState(int barBgResId, int barSelectedBgResId, int iconResId, int selectedIconResId,
              int selectedTextColorResId) {
        mBarBgResId = barBgResId;
        mBarSelectedBgResId = barSelectedBgResId;
        mIconResId = iconResId;
        mSelectedIconResId = selectedIconResId;
        mSelectedTextColorResId = selectedTextColorResId;
    }

    int getBarBgResId() {
        return mBarBgResId;
    }

    int getBarSelectedBgResId() {
        return mBarSelectedBgResId;
    }

    int getIconResId() {
        return mIconResId;
    }

    int getSelectedIconResId() {
        return mSelectedIconResId;
    }

    int getSelectedTextColorResId() {
        return mSelectedTextColorResId;
    }

}
