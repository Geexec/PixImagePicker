package com.fxn.utility.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class LockableBottomSheetBehavior<v extends View> extends BottomSheetBehavior<v> {

    boolean swipeEnabled = true;

//    public LockableBottomSheetBehavior(boolean swipeEnabled) {
//        super();
//        this.swipeEnabled = swipeEnabled;
//    }


    @Override
    public void setState(int state) {
        super.setState(state);
        if(state == BottomSheetBehavior.STATE_EXPANDED) {
            swipeEnabled = false;
        }
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
        this.swipeEnabled = swipeEnabled;
    }

    public LockableBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull v child, @NonNull MotionEvent event) {
        if (swipeEnabled) {
            return super.onInterceptTouchEvent(parent, child, event);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull v child, @NonNull MotionEvent event) {
        if (swipeEnabled) {
            return super.onTouchEvent(parent, child, event);
        }
        return false;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull v child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        if (swipeEnabled) {
            return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
        }
        return false;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull v child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (swipeEnabled) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull v child, @NonNull View target, int type) {
        if (swipeEnabled) {
            super.onStopNestedScroll(coordinatorLayout, child, target, type);
        }
    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull v child, @NonNull View target, float velocityX, float velocityY) {
        if (swipeEnabled) {
            return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
        }
        return false;
    }
}
