package com.winning.bottomsheetlibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class BottomSheet {

    private Builder mBuilder;
    public BottomSheet(Builder builder) {
        mBuilder = builder;
    }

    public static class Builder implements TopView.BottomClickListener {

        private boolean isShowed;
        private String title;
        private View anyView;
        private  TopView mView;
        private  ViewGroup mRootView;
        private  TextView mTvTitle;
        private  RecyclerView mRvList;
        private  TextView mTvCancel;
        private ArrayList<String> contentList = new ArrayList<>();
        private  BottomAdapter mAdapter;
        private OnItemSelectListener mOnItemSelectListener;

        public Builder(View anyView){
            mRootView = findSuitableParent(anyView);
             mView = (TopView) LayoutInflater.from(anyView.getContext()).inflate(R.layout.bottom_sheet_layout_list, mRootView, false);
             mTvTitle = mView.findViewById(R.id.tvTitle);
            mRvList = mView.findViewById(R.id.rvList);
            mTvCancel = mView.findViewById(R.id.tvCancel);
            mRvList.setLayoutManager(new LinearLayoutManager(anyView.getContext(), LinearLayoutManager.VERTICAL, false));
            mRvList.addItemDecoration(new RecycleViewDivider(anyView.getContext(),LinearLayoutManager.VERTICAL));
            mAdapter = new BottomAdapter(R.layout.bottom_sheet_layout_item);
            mRvList.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (mOnItemSelectListener != null){
                        mOnItemSelectListener.onSelect(position);
                        dismiss();
                    }
                }
            });
            mTvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            mView.setParentView(mRootView);
            mView.register(this);
        }

        public Builder setTitle(String title) {
            this.title = title;
            if (title != null && !title.equals("")){
                mTvTitle.setVisibility(View.VISIBLE);
                mTvTitle.setText(title);
            }else {
                mTvTitle.setVisibility(View.GONE);
            }
            return this;
        }
        public Builder setListData(ArrayList<String>list){
            mAdapter.setNewData(list);
            return this;
        }
        public Builder setListData(String[]string){
            mAdapter.setNewData(Arrays.asList(string));
            return this;
        }
        public Builder setOnItemClickListener(OnItemSelectListener listener){
            mOnItemSelectListener =listener;
            return this;
        }

        /**
         * 向上追溯找到id为content的FrameLayout
         * */
        private  ViewGroup findSuitableParent(View view) {
            ViewGroup fallback = null;
            do {
                if (view instanceof FrameLayout) {
                    if (view.getId() == android.R.id.content) {
                        return (ViewGroup) view;
                    } else {
                        fallback = (ViewGroup) view;
                    }
                }
                if (view != null) {
                    final ViewParent parent = view.getParent();
                    view = parent instanceof View ? (View) parent : null;
                }
            } while (view != null);
            return fallback;
        }
        public void show(){
            if (mView.getParent() != null){
               mRootView.removeView(mView);
            }
            mRootView.addView(mView);
            isShowed = true;
        }
        private void dismiss(){
            ObjectAnimator ani = ObjectAnimator.ofFloat(mView,"translationY",0,mView.getHeight());
            ani.setDuration(800);
            ani.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRootView.removeView(mView);
                    mView = null;
                }
            });
            ani.start();
            isShowed = false;
        }
        public BottomSheet create(){
           return new BottomSheet(this);
        }

        @Override
        public void onclick() {
            if (isShowed){
                dismiss();
            }
        }
    }
    public void show(){
        mBuilder.show();
    }

}
