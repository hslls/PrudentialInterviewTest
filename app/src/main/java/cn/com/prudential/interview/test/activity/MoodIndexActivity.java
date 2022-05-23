package cn.com.prudential.interview.test.activity;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.com.prudential.interview.test.R;
import cn.com.prudential.interview.test.bean.DayOfWeek;
import cn.com.prudential.interview.test.bean.MoodIndex;
import cn.com.prudential.interview.test.bean.MoodState;
import cn.com.prudential.interview.test.view.MoodBarContainer;
import cn.com.prudential.interview.test.view.TitleBar;

public class MoodIndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_index);

        Resources res = getResources();
        int statusBarHeight = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        int marginTop = res.getDimensionPixelSize(R.dimen.title_bar_margin_top) - statusBarHeight;

        TitleBar titleBar = findViewById(R.id.titleBar);
        titleBar.setMargin(0, marginTop, 0, 0);
        titleBar.setTitle(res.getString(R.string.title));

        MoodBarContainer container = findViewById(R.id.mood_bar_container);
        container.postDelayed(() -> container.setData(mockData()), 500);
    }

    private List<MoodIndex> mockData() {
        List<MoodIndex> list = new ArrayList<>();
        list.add(new MoodIndex(86, DayOfWeek.SATURDAY, MoodState.NORMAL));
        list.add(new MoodIndex(80, DayOfWeek.SUNDAY, MoodState.NORMAL));
        list.add(new MoodIndex(35, DayOfWeek.MONDAY, MoodState.SAD));
        list.add(new MoodIndex(90, DayOfWeek.TUESDAY, MoodState.HAPPY));
        list.add(new MoodIndex(92, DayOfWeek.WEDNESDAY, MoodState.HAPPY));
        list.add(new MoodIndex(97, DayOfWeek.THURSDAY, MoodState.HAPPY));
        list.add(new MoodIndex(81, DayOfWeek.FRIDAY, MoodState.NORMAL));
        return list;
    }

}
