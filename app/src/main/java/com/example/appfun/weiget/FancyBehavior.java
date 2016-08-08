package com.example.appfun.weiget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zx on 2016/3/2.
 */
public class FancyBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public FancyBehavior() {

    }

    public FancyBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
