package com.nemo.caideng.widget;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.databinding.ViewSeekNumBinding;

/* JADX INFO: loaded from: classes.dex */
public class SeekNumView extends FrameLayout implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private ProgressListener progressListener;
    ViewSeekNumBinding viewSeekNumBinding;

    public interface ProgressListener {
        void onProgress(int i);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public ProgressListener getProgressListener() {
        return this.progressListener;
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public SeekNumView(Context context) {
        super(context);
    }

    public SeekNumView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public void setSeekName(String str) {
        this.viewSeekNumBinding.tvSeekName.setText(str);
    }

    public int getProgress() {
        return this.viewSeekNumBinding.sbSeekPro.getProgress();
    }

    public void setProgress(int i) {
        this.viewSeekNumBinding.sbSeekPro.setProgress(i);
    }

    public void setColor(int i) {
        ((GradientDrawable) ((ClipDrawable) ((LayerDrawable) this.viewSeekNumBinding.sbSeekPro.getProgressDrawable()).getDrawable(1)).getDrawable()).setColor(i);
        ((GradientDrawable) this.viewSeekNumBinding.sbSeekPro.getThumb()).setColor(i);
    }

    private void init() {
        ViewSeekNumBinding viewSeekNumBindingInflate = ViewSeekNumBinding.inflate(LayoutInflater.from(getContext()));
        this.viewSeekNumBinding = viewSeekNumBindingInflate;
        addView(viewSeekNumBindingInflate.getRoot());
        this.viewSeekNumBinding.sbSeekPro.setOnSeekBarChangeListener(this);
        this.viewSeekNumBinding.tvSeekAdd.setOnClickListener(this);
        this.viewSeekNumBinding.tvSeekReduce.setOnClickListener(this);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        this.viewSeekNumBinding.tvSeekProNum.setText(i + "%");
        ProgressListener progressListener = this.progressListener;
        if (progressListener != null) {
            progressListener.onProgress(i);
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        this.viewSeekNumBinding.sbSeekPro.setEnabled(z);
        this.viewSeekNumBinding.tvSeekAdd.setEnabled(z);
        this.viewSeekNumBinding.tvSeekReduce.setEnabled(z);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == C0575R.id.tv_seek_add) {
            this.viewSeekNumBinding.sbSeekPro.setProgress(this.viewSeekNumBinding.sbSeekPro.getProgress() + 1);
        } else {
            if (id != C0575R.id.tv_seek_reduce) {
                return;
            }
            this.viewSeekNumBinding.sbSeekPro.setProgress(this.viewSeekNumBinding.sbSeekPro.getProgress() - 1);
        }
    }
}
