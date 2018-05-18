package com.winning.bottomsheetlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.winning.bottomsheetlibrary.utils.SettingsCompat;

import static android.content.Context.WINDOW_SERVICE;

public class TopView extends LinearLayout {
    private ViewGroup mViewGroup;
    private MotionEvent mEvent;
    BottomClickListener mListener;
    private WindowManager mWindowManager;

    public TopView(Context context) {
        super(context);
    }

    public TopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        showFloatingView(context);
    }

    public TopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        showFloatingView(context);
    }

    public void setDispatchEvent(MotionEvent ev){
        mEvent = ev;
        boolean b = setClickBottom(mViewGroup, mEvent);
        if (!b){
            mListener.onclick();
            if (mWindowManager !=null){
               if (this.getWindowId() != null){
//                   mWindowManager.removeView(this);
               }
            }
        }
    }
    /**
     * 计算指定的 View 在屏幕中的坐标。
     */
    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }

    public void setParentView(ViewGroup view){
        mViewGroup = view;
    }
    public   boolean setClickBottom(ViewGroup viewGroup,MotionEvent ev){
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            RectF rectF = calcViewScreenLocation(child);
            boolean contains = rectF.contains(ev.getRawX(), ev.getRawY());
            if (contains && child.getId() ==  R.id.root){
                return true;
            }
        }
        return false;
    }

    public interface  BottomClickListener{
        void onclick();
    }
    public void register(BottomClickListener listener){
        mListener = listener;
    }
    private void showFloatingView(Context activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(activity)) {
                showFloatView(activity);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                activity.startActivity(intent);
            }
        } else {
            showFloatView(activity);
        }
    }

    private void showFloatView(Context activity) {
        mWindowManager = (WindowManager) activity.getSystemService(WINDOW_SERVICE);
        MarkViewGroup  floatView = new MarkViewGroup(activity,this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.START | Gravity.TOP;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        params.width = 1;
        params.height = 1;
        params.x = 0;
        params.y = 0;
        mWindowManager.addView(floatView, params);
    }

}

