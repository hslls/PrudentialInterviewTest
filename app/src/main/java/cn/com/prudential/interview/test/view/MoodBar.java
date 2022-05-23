package cn.com.prudential.interview.test.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import cn.com.prudential.interview.test.R;
import cn.com.prudential.interview.test.bean.MoodIndex;
import cn.com.prudential.interview.test.bean.MoodState;

public class MoodBar extends ConstraintLayout {

    private static int MOOD_BAR_TOTAL_HEIGHT = 0;
    private static int MOOD_BAR_TOTAL_HEIGHT_SELECTED = 0;
    private static int MOOD_BAR_ANIM_INIT_HEIGHT = 0;

    private View mViewBar;
    private TextView mTvNum;
    private ImageView mIvIcon;
    private DayOfWeekTag mTvDayOfWeekBg;
    private TextView mTvmTvDayOfWeekText;

    private MoodIndex mMoodIndex;

    private final Animator.AnimatorListener mAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            if (animation == mBarAlphaAnimator) {
                mBarAlphaAnimator = null;
            } else if (animation == mBarGrowAnimator) {
                mBarGrowAnimator = null;
                performNumAlphaAnim();
            } else if (animation == mNumAlphaAnimator) {
                mNumAlphaAnimator = null;
                setEnabled(true);
            }
        }
    };
    private final Animation.AnimationListener mAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == mIconScaleAnimator) {
                mIconScaleAnimator = null;
                performBarGrowAnim();
                performDayOfWeekScaleAnim();
            } else if (animation == mDayOfWeekTextScaleAnimator) {
                mDayOfWeekTextScaleAnimator = null;
            } else if (animation == mDayOfWeekBgScaleAnimator) {
                mDayOfWeekBgScaleAnimator = null;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
    private boolean mAnimCancelled;
    private ObjectAnimator mBarAlphaAnimator;
    private Animation mIconScaleAnimator;
    private ObjectAnimator mBarGrowAnimator;
    private Animation mDayOfWeekTextScaleAnimator;
    private Animation mDayOfWeekBgScaleAnimator;
    private ObjectAnimator mNumAlphaAnimator;

    public MoodBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setEnabled(false);
        initStaticVariables();
        inflate(context, R.layout.view_moodbar, this);
        mViewBar = findViewById(R.id.bar);
        mTvNum = findViewById(R.id.num);
        mIvIcon = findViewById(R.id.icon);
        mTvDayOfWeekBg = findViewById(R.id.day_of_week_bg);
        mTvmTvDayOfWeekText = findViewById(R.id.day_of_week_text);
    }

    private void initStaticVariables() {
        if (MOOD_BAR_TOTAL_HEIGHT == 0) {
            Resources res = getResources();
            int height = res.getDimensionPixelSize(R.dimen.mood_bar_container_height);
            MOOD_BAR_TOTAL_HEIGHT = height - res.getDimensionPixelSize(R.dimen.mood_bar_margin_bottom);
            MOOD_BAR_TOTAL_HEIGHT_SELECTED = height - res.getDimensionPixelSize(R.dimen.mood_bar_selected_margin_bottom);
            MOOD_BAR_ANIM_INIT_HEIGHT = res.getDimensionPixelSize(R.dimen.mood_bar_anim_init_height);
        }
    }

    public void updateState(MoodIndex info, boolean init) {
        if (info == null) {
            return;
        }

        mMoodIndex = info;
        final boolean doAnim = init;
        boolean selected = info.isSelected();
        Resources res = getResources();
        final int barInitHeight = doAnim ? MOOD_BAR_ANIM_INIT_HEIGHT : getBarRealHeight();
        ViewGroup.LayoutParams barParams = mViewBar.getLayoutParams();
        if (barParams instanceof MarginLayoutParams) {
            MarginLayoutParams lp = (MarginLayoutParams) barParams;
            int barWidth = mMoodIndex.testShadow() ? res.getDimensionPixelSize(R.dimen.mood_bar_with_shadow_selected_width) :
                    (selected ? res.getDimensionPixelSize(R.dimen.mood_bar_selected_width) :
                            res.getDimensionPixelSize(R.dimen.mood_bar_width));
            int bottomMargin = (selected ?
                    res.getDimensionPixelSize(R.dimen.mood_bar_selected_margin_bottom) :
                    res.getDimensionPixelSize(R.dimen.mood_bar_margin_bottom));
            if ((lp.width != barWidth) || (lp.height != barInitHeight) ||
                    lp.bottomMargin != bottomMargin) {
                lp.width = barWidth;
                lp.height = barInitHeight;
                lp.bottomMargin = bottomMargin;
                mViewBar.setLayoutParams(lp);
            }
        }
        mViewBar.setBackgroundResource(info.getBarBgResId());

        ViewGroup.LayoutParams iconParams = mIvIcon.getLayoutParams();
        if (iconParams instanceof MarginLayoutParams) {
            MarginLayoutParams lp = (MarginLayoutParams) iconParams;
            int normalBottomMargin = res.getDimensionPixelSize(R.dimen.mood_bar_icon_margin_bottom);
            int selectedBottomMargin = normalBottomMargin + res.getDimensionPixelSize(R.dimen.mood_bar_selected_border);
            int bottomMargin = (selected ? selectedBottomMargin : normalBottomMargin);
            if (lp.bottomMargin != bottomMargin) {
                lp.bottomMargin = bottomMargin;
                mIvIcon.setLayoutParams(lp);
            }
        }
        mIvIcon.setImageResource(info.getIconResId());

        if (doAnim) {
            performAnim();
        } else {
            updateNum();
            updateDayOfWeek();
        }
    }

    private void updateNum() {
        if (mMoodIndex == null) {
            return;
        }

        mTvNum.setText((mMoodIndex.getMoodState() == MoodState.SAD) ?
                "" : String.valueOf(mMoodIndex.getNumber()));
        mTvNum.setTextSize(mMoodIndex.isSelected() ? 24 : 20);
    }

    private void updateDayOfWeek() {
        if ((mMoodIndex == null) || mMoodIndex.testShadow()) {
            return;
        }

        Resources res = getResources();
        mTvmTvDayOfWeekText.setText(res.getText(mMoodIndex.getStrResId()));
        mTvmTvDayOfWeekText.setTextColor(res.getColor(mMoodIndex.getTextColorResId(), null));

        mTvDayOfWeekBg.update(mMoodIndex);
    }

    public MoodIndex getMoodInfo() {
        return mMoodIndex;
    }

    private int getBarRealHeight() {
        if (mMoodIndex == null) {
            return 0;
        }
        return mMoodIndex.getNumber() * (mMoodIndex.isSelected() ?
                MOOD_BAR_TOTAL_HEIGHT_SELECTED : MOOD_BAR_TOTAL_HEIGHT) / MoodIndex.MAX_NUMBER;
    }

    private void setBarHeight(int height) {
        ViewGroup.LayoutParams params = mViewBar.getLayoutParams();
        if ((params == null) || (params.height == height)) {
            return;
        }
        params.height = height;
        mViewBar.setLayoutParams(params);
    }

    private void performAnim() {
        if (mAnimCancelled) {
            return;
        }

        mBarAlphaAnimator = ObjectAnimator.ofFloat(mViewBar, "alpha", 0, 1);
        mBarAlphaAnimator.setDuration(getResources().getInteger(R.integer.bar_alpha_anim_duration));
        mBarAlphaAnimator.addListener(mAnimatorListener);
        mBarAlphaAnimator.start();

        mIconScaleAnimator = createScaleFromCenterAnim();
        mIconScaleAnimator.setAnimationListener(mAnimationListener);
        mIvIcon.startAnimation(mIconScaleAnimator);
    }

    private void performBarGrowAnim() {
        if (mAnimCancelled) {
            return;
        }

        mBarGrowAnimator = ObjectAnimator.ofInt(this, "barHeight",
                MOOD_BAR_ANIM_INIT_HEIGHT, getBarRealHeight());
        mBarGrowAnimator.setDuration(500);
        mBarGrowAnimator.addListener(mAnimatorListener);
        mBarGrowAnimator.start();
    }

    private void performDayOfWeekScaleAnim() {
        if ((mMoodIndex == null) || mAnimCancelled) {
            return;
        }

        updateDayOfWeek();

        mDayOfWeekTextScaleAnimator = createScaleFromCenterAnim();
        mDayOfWeekTextScaleAnimator.setAnimationListener(mAnimationListener);
        mTvmTvDayOfWeekText.startAnimation(mDayOfWeekTextScaleAnimator);

        mDayOfWeekBgScaleAnimator = createScaleFromCenterAnim();
        mDayOfWeekBgScaleAnimator.setAnimationListener(mAnimationListener);
        mTvDayOfWeekBg.startAnimation(mDayOfWeekBgScaleAnimator);
    }

    private void performNumAlphaAnim() {
        if (mAnimCancelled) {
            return;
        }

        updateNum();

        mNumAlphaAnimator = ObjectAnimator.ofFloat(mTvNum, "alpha", 0, 1);
        mNumAlphaAnimator.setDuration(100);
        mNumAlphaAnimator.addListener(mAnimatorListener);
        mNumAlphaAnimator.start();
    }

    public void cleanAnim() {
        mAnimCancelled = true;
        if (mBarAlphaAnimator != null) {
            mBarAlphaAnimator.cancel();
            mBarAlphaAnimator = null;
        }
        if (mIconScaleAnimator != null) {
            mIconScaleAnimator.cancel();
            mIconScaleAnimator = null;
        }
        if (mBarGrowAnimator != null) {
            mBarGrowAnimator.cancel();
            mBarGrowAnimator = null;
        }
        if (mDayOfWeekTextScaleAnimator != null) {
            mDayOfWeekTextScaleAnimator.cancel();
            mDayOfWeekTextScaleAnimator = null;
        }
        if (mDayOfWeekBgScaleAnimator != null) {
            mDayOfWeekBgScaleAnimator.cancel();
            mDayOfWeekBgScaleAnimator = null;
        }
        if (mNumAlphaAnimator != null) {
            mNumAlphaAnimator.cancel();
            mNumAlphaAnimator = null;
        }
    }

    private Animation createScaleFromCenterAnim() {
        Animation scale = new ScaleAnimation(0, 1, 0, 1, 0.5f, 0.5f);
        Animation alpha = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        set.setDuration(getResources().getInteger(R.integer.bar_alpha_anim_duration));
        set.setInterpolator(getContext(), android.R.anim.accelerate_interpolator);
        return set;
    }
}
