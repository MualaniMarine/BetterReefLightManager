package com.nemo.caideng.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.model.TimeLuminance;

/* JADX INFO: loaded from: classes.dex */
public class TimeLuminanceAdapter extends BaseQuickAdapter<TimeLuminance, BaseViewHolder> {
    public TimeLuminanceAdapter() {
        super(C0575R.layout.item_time_luminance_list);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, TimeLuminance timeLuminance) {
        baseViewHolder.setText(C0575R.id.tv_time, timeLuminance.getHour() + getContext().getString(C0575R.string.hour) + timeLuminance.getMinute() + getContext().getString(C0575R.string.minute));
        baseViewHolder.setText(C0575R.id.tv_luminance, timeLuminance.getLuminanceStr());
    }
}
