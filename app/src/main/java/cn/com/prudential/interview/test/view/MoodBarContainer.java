package cn.com.prudential.interview.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.com.prudential.interview.test.R;
import cn.com.prudential.interview.test.bean.DayOfWeek;
import cn.com.prudential.interview.test.bean.MoodIndex;

public class MoodBarContainer extends LinearLayout {

    private MoodBar mBarSaturday, mBarSunday, mBarMonday, mBarTuesday,
            mBarWednesday, mBarThursday, mBarFriday;
    private final View.OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof MoodBar) {
                MoodBar bar = (MoodBar) v;
                bar.cleanAnim();
                MoodIndex info = bar.getMoodInfo();
                if (info == null) {
                    return;
                }
                boolean selected = info.isSelected();
                info.setSelected(!selected);
                bar.updateState(info, false);
                if (!selected) {
                    for (MoodIndex temp : mMoodIndexList) {
                        if (temp == info) {
                            continue;
                        }
                        if (temp.isSelected()) {
                            temp.setSelected(false);
                            updateState(temp, false);
                        }
                    }
                }
            }
        }
    };

    private final List<MoodIndex> mMoodIndexList = new ArrayList<>();

    public MoodBarContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.view_moodbar_container, this);
        mBarSaturday = findViewById(R.id.saturday);
        mBarSaturday.setOnClickListener(mOnClickListener);
        mBarSunday = findViewById(R.id.sunday);
        mBarSunday.setOnClickListener(mOnClickListener);
        mBarMonday = findViewById(R.id.monday);
        mBarMonday.setOnClickListener(mOnClickListener);
        mBarTuesday = findViewById(R.id.tuesday);
        mBarTuesday.setOnClickListener(mOnClickListener);
        mBarWednesday = findViewById(R.id.wednesday);
        mBarWednesday.setOnClickListener(mOnClickListener);
        mBarThursday = findViewById(R.id.thursday);
        mBarThursday.setOnClickListener(mOnClickListener);
        mBarFriday = findViewById(R.id.friday);
        mBarFriday.setOnClickListener(mOnClickListener);
    }

    public void setData(List<MoodIndex> list) {
        mMoodIndexList.clear();

        if (list != null) {
            Iterator<MoodIndex> it = list.iterator();
            while (it.hasNext()) {
                MoodIndex info = it.next();
                if ((info == null) || !info.isValid()) {
                    it.remove();
                }
            }
        }

        if ((list != null) && (list.size() == DayOfWeek.values().length)) {
            mMoodIndexList.addAll(list);
            postUpdate(0);
        }
    }

    private void postUpdate(int index) {
        if (mMoodIndexList == null) {
            return;
        }
        if ((index < 0) || (index >= mMoodIndexList.size())) {
            return;
        }
        updateState(mMoodIndexList.get(index), true);
        post(() -> postUpdate(index + 1));
    }

    private void updateState(MoodIndex info, boolean init) {
        if (info == null) {
            return;
        }

        MoodBar bar;
        DayOfWeek dayOfWeek = info.getDayOfWeek();
        switch (dayOfWeek) {
            case SATURDAY:
                bar = mBarSaturday;
                break;
            case SUNDAY:
                bar = mBarSunday;
                break;
            case MONDAY:
                bar = mBarMonday;
                break;
            case TUESDAY:
                bar = mBarTuesday;
                break;
            case WEDNESDAY:
                bar = mBarWednesday;
                break;
            case THURSDAY:
                bar = mBarThursday;
                break;
            case FRIDAY:
                bar = mBarFriday;
                break;
            default:
                bar = null;
                break;
        }
        if (bar != null) {
            bar.updateState(info, init);
        }
    }

}
