package com.nemo.caideng.adapter;

import android.widget.CheckedTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.model.LocalWifiInfo;

/* JADX INFO: loaded from: classes.dex */
public class LocalWifiInfoAdapter extends BaseQuickAdapter<LocalWifiInfo, BaseViewHolder> {
    public LocalWifiInfoAdapter() {
        super(C0575R.layout.item_wifi_list);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, LocalWifiInfo localWifiInfo) {
        baseViewHolder.setText(C0575R.id.tv_wifi_name, localWifiInfo.getScanResult().SSID);
        ((CheckedTextView) baseViewHolder.getView(C0575R.id.rb_radio)).setChecked(localWifiInfo.isChecked());
    }
}
