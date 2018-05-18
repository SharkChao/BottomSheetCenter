package com.winning.bottomsheetlibrary;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class BottomAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public BottomAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvContent,item);
    }
}
