package cn.com.prudential.interview.test.bean;

import cn.com.prudential.interview.test.R;

public class MoodIndex {

    private static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 100;

    private final int mNumber;
    private final DayOfWeek mDayOfWeek;
    private final MoodState mMoodState;

    private boolean mSelected;
    private boolean mTestShadow;

    public MoodIndex(int number, DayOfWeek dayOfWeek, MoodState moodState) {
        mNumber = number;
        mDayOfWeek = dayOfWeek;
        mMoodState = moodState;
    }

    public int getNumber() {
        return mNumber;
    }

    public DayOfWeek getDayOfWeek() {
        return mDayOfWeek;
    }

    public MoodState getMoodState() {
        return mMoodState;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public int getIconResId() {
        return mSelected ? mMoodState.getSelectedIconResId() : mMoodState.getIconResId();
    }

    public int getBarBgResId() {
        if (mTestShadow) {
            return R.drawable.bg_mood_bar_with_shadow_selected;
        }
        return mSelected ? mMoodState.getBarSelectedBgResId() : mMoodState.getBarBgResId();
    }

    public int getStrResId() {
        return mDayOfWeek.getStrResId();
    }

    public int getTextColorResId() {
        if (mSelected) {
            return mMoodState.getSelectedTextColorResId();
        } else {
            return mDayOfWeek.getTextColorResId();
        }
    }

    public int getStrBgResId() {
        if (mSelected) {
            return R.drawable.bg_day_of_week_selected;
        } else {
            return (mDayOfWeek == DayOfWeek.FRIDAY) ? R.drawable.bg_black_round :
                    R.drawable.bg_transparent_round;
        }
    }

    public boolean isValid() {
        if ((mNumber < MIN_NUMBER) || (mNumber > MAX_NUMBER)) {
            return false;
        }
        if ((mDayOfWeek == null) || (mMoodState == null)) {
            return false;
        }
        return true;
    }

    public boolean testShadow() {
        return mTestShadow;
    }

    public void setTestShadow(boolean testShadow) {
        mTestShadow = testShadow;
    }
}
