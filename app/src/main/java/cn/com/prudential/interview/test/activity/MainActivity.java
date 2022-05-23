package cn.com.prudential.interview.test.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cn.com.prudential.interview.test.R;
import cn.com.prudential.interview.test.bean.DayOfWeek;
import cn.com.prudential.interview.test.bean.MoodIndex;
import cn.com.prudential.interview.test.bean.MoodState;
import cn.com.prudential.interview.test.view.MoodBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoodBar bar = findViewById(R.id.bar);
        MoodIndex mi = new MoodIndex(97, DayOfWeek.TUESDAY, MoodState.HAPPY);
        mi.setSelected(true);
        mi.setTestShadow(true);
        bar.updateState(mi, false);

        findViewById(R.id.btn).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MoodIndexActivity.class)));
    }

}