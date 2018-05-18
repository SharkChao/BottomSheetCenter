package com.winning.bottomsheetlibrary;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MarkViewGroup extends LinearLayout{

    private TopView mTopView;

    public MarkViewGroup(Context context,TopView topView) {
        super(context);
        mTopView = topView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mTopView.setDispatchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}
