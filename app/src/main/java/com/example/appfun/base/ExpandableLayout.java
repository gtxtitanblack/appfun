package com.example.appfun.base;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.appfun.R;


/**
 * INKE APP
 * Created by guhj on 2016/5/25.
 * Update Version
 * Date         Developer           Comment
 */
public class ExpandableLayout extends ViewGroup {
    public static final int HIDE_MODEL_CENTER = 0;
    public static final int HIDE_MODEL_TOP = 1;
    public static final int HIDE_MODEL_BOTTOM = 2;


    private boolean mInLayout;
    private float mExpandable;
    private boolean mIsOpen;

    private int mHideModel;
    private long mDuratione;
    private TimeInterpolator mInterpolator;

    private ValueAnimator mAnimator;

    public ExpandableLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mInterpolator = new AccelerateDecelerateInterpolator(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ExpandableLayout, 0, 0);
        try {
            LayoutInflater factory = LayoutInflater.from(context);
            mHideModel = a.getInteger(R.styleable.ExpandableLayout_el_hideMode, HIDE_MODEL_CENTER);
            mDuratione = a.getInteger(R.styleable.ExpandableLayout_el_duratione, 400);//默认400毫秒
        } finally {
            a.recycle();
        }
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ExpandableLayout can host only one direct child");
        }

        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ExpandableLayout can host only one direct child");
        }
        super.addView(child, index);
    }

    @Override
    public void addView(View child, LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ExpandableLayout can host only one direct child");
        }
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(child, index, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 0) {
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) (getChildAt(0).getMeasuredHeight() * mExpandable), MeasureSpec.AT_MOST));
        } else {
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.AT_MOST));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() > 0) {
            mInLayout = true;
            View childView = getChildAt(0);
            if (changed) {
                childView.forceLayout();
            }
            int top = 0;
            switch (mHideModel) {
                case HIDE_MODEL_CENTER:
                    top = (getHeight() - childView.getMeasuredHeight()) / 2;
                    break;
                case HIDE_MODEL_TOP:
                    break;
                case HIDE_MODEL_BOTTOM:
                    top = getHeight() - childView.getMeasuredHeight();
                    break;
            }
            childView.layout(0, top, getMeasuredWidth(), top + getMeasuredHeight());
            mInLayout = false;
        }
    }

    @Override
    public void requestLayout() {
        if (!mInLayout) {
            super.requestLayout();
        }
    }

    public int getHideModel() {
        return mHideModel;
    }

    public void setHideModel(int hideModel) {
        this.mHideModel = hideModel;
    }

    public long getDuration() {
        return mDuratione;
    }

    public void setDuration(long duration) {
        this.mDuratione = duration;
    }

    public TimeInterpolator getInterpolator() {
        return mInterpolator;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        if (interpolator == null) {
            throw new NullPointerException();
        }
        this.mInterpolator = interpolator;
    }

    public void open(boolean isAnim) {
        if (mIsOpen) return;
        mIsOpen = true;
        if (isAnim) {
            animScrollTo(1);
        } else {
            mExpandable = 1;
            super.requestLayout();
        }
    }

    public void close(boolean isAnim) {
        if (!mIsOpen) return;
        mIsOpen = false;
        if (isAnim) {
            animScrollTo(0);
        } else {
            mExpandable = 0;
            super.requestLayout();
        }
    }

    public void toggle(boolean isAnim) {
        if (mIsOpen) {
            close(isAnim);
        } else {
            open(isAnim);
        }
    }

    private void animScrollTo(float toValue) {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }

        if (toValue < 0) {
            toValue = 0;
        } else if (toValue > 1) {
            toValue = 1;
        }

        mAnimator = ValueAnimator.ofFloat(mExpandable, toValue);
        mAnimator.setInterpolator(mInterpolator);
        long duration = (long) (mDuratione * Math.abs(toValue - mExpandable));
        mAnimator.setDuration(duration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mExpandable = (float) animation.getAnimatedValue();
                if (mExpandable < 0) {
                    mExpandable = 0;
                } else if (mExpandable > 1) {
                    mExpandable = 1;
                }
                requestLayout();
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (getChildCount() > 0) {
                    getChildAt(0).setVisibility(VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (getChildCount() > 0) {
                    getChildAt(0).setVisibility(mExpandable == 0 ? INVISIBLE : VISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mAnimator.start();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, mExpandable, mIsOpen, mHideModel, mDuratione);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        mExpandable = ss.mExpandable;
        mIsOpen = ss.mIsOpen;
        mHideModel = ss.mHideModel;
        mDuratione = ss.mDuratione;
        super.requestLayout();
    }

    private static class SavedState extends BaseSavedState {
        private float mExpandable;
        private boolean mIsOpen;

        private int mHideModel;
        private long mDuratione;

        SavedState(Parcelable superState, float expandable, boolean isOpen, int hideModel, long duratione) {
            super(superState);
            this.mExpandable = expandable;
            this.mIsOpen = isOpen;
            this.mHideModel = hideModel;
            this.mDuratione = duratione;
        }

        public SavedState(Parcel in) {
            super(in);
            mExpandable = in.readFloat();
            mIsOpen = in.readInt() == 1;
            mHideModel = in.readInt();
            mDuratione = in.readLong();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(mExpandable);
            out.writeInt(mIsOpen ? 1 : 0);
            out.writeInt(mHideModel);
            out.writeLong(mDuratione);
        }

        public static final Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
