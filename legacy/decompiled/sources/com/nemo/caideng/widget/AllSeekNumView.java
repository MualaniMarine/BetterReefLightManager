package com.nemo.caideng.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.databinding.ViewAllSeekNumBinding;
import com.nemo.caideng.widget.SeekNumView;

/* JADX INFO: loaded from: classes.dex */
public class AllSeekNumView extends FrameLayout implements SeekNumView.ProgressListener {
    private boolean canSend;
    private SeekNumView[] seekNumViews;
    private SparseIntArray sparseIntArray;
    private long touchSend;
    private ValueListener valueListener;
    private ViewAllSeekNumBinding viewAllSeekNumBinding;

    public interface ValueListener {
        void onValueChange(byte[] bArr);
    }

    public ValueListener getValueListener() {
        return this.valueListener;
    }

    public void setValueListener(ValueListener valueListener) {
        this.valueListener = valueListener;
    }

    public AllSeekNumView(Context context) {
        super(context);
        this.seekNumViews = new SeekNumView[6];
        this.sparseIntArray = new SparseIntArray();
    }

    public AllSeekNumView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.seekNumViews = new SeekNumView[6];
        this.sparseIntArray = new SparseIntArray();
        init();
    }

    private void init() {
        this.sparseIntArray.put(0, 0);
        this.sparseIntArray.put(1, 2);
        this.sparseIntArray.put(2, 5);
        this.sparseIntArray.put(3, 3);
        this.sparseIntArray.put(4, 1);
        this.sparseIntArray.put(5, 4);
        ViewAllSeekNumBinding viewAllSeekNumBindingInflate = ViewAllSeekNumBinding.inflate(LayoutInflater.from(getContext()));
        this.viewAllSeekNumBinding = viewAllSeekNumBindingInflate;
        addView(viewAllSeekNumBindingInflate.getRoot());
        this.viewAllSeekNumBinding.svSeek1.setColor(getResources().getColor(C0575R.color.color_progress_1));
        this.viewAllSeekNumBinding.svSeek2.setColor(getResources().getColor(C0575R.color.color_progress_3));
        this.viewAllSeekNumBinding.svSeek3.setColor(getResources().getColor(C0575R.color.color_progress_6));
        this.viewAllSeekNumBinding.svSeek4.setColor(getResources().getColor(C0575R.color.color_progress_4));
        this.viewAllSeekNumBinding.svSeek5.setColor(getResources().getColor(C0575R.color.color_progress_2));
        this.viewAllSeekNumBinding.svSeek6.setColor(getResources().getColor(C0575R.color.color_progress_5));
        this.seekNumViews[0] = this.viewAllSeekNumBinding.svSeek1;
        this.seekNumViews[1] = this.viewAllSeekNumBinding.svSeek2;
        this.seekNumViews[2] = this.viewAllSeekNumBinding.svSeek3;
        this.seekNumViews[3] = this.viewAllSeekNumBinding.svSeek4;
        this.seekNumViews[4] = this.viewAllSeekNumBinding.svSeek5;
        this.seekNumViews[5] = this.viewAllSeekNumBinding.svSeek6;
        for (SeekNumView seekNumView : this.seekNumViews) {
            seekNumView.setProgressListener(this);
        }
    }

    public void setEnable(int i) {
        int i2 = 0;
        for (SeekNumView seekNumView : this.seekNumViews) {
            seekNumView.setEnabled(false);
            seekNumView.setVisibility(8);
            if (i2 < i) {
                seekNumView.setEnabled(true);
                seekNumView.setVisibility(0);
            }
            i2++;
        }
        if (i == 3) {
            this.viewAllSeekNumBinding.svSeek3.setColor(getResources().getColor(C0575R.color.color_progress_4));
        }
    }

    public byte[] getValues() {
        byte[] bArr = new byte[6];
        byte b = 0;
        while (true) {
            SeekNumView[] seekNumViewArr = this.seekNumViews;
            if (b >= seekNumViewArr.length) {
                return bArr;
            }
            bArr[b] = (byte) seekNumViewArr[b].getProgress();
            b = (byte) (b + 1);
        }
    }

    public void setValues(byte[] bArr) {
        int i = 0;
        while (true) {
            SeekNumView[] seekNumViewArr = this.seekNumViews;
            if (i >= seekNumViewArr.length) {
                return;
            }
            seekNumViewArr[i].setProgress(bArr[i]);
            i++;
        }
    }

    public void reSet() {
        setValues(new byte[6]);
    }

    @Override // com.nemo.caideng.widget.SeekNumView.ProgressListener
    public void onProgress(int i) {
        ValueListener valueListener = this.valueListener;
        if (valueListener == null || !this.canSend) {
            return;
        }
        valueListener.onValueChange(getValues());
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            this.touchSend = 0L;
            this.canSend = true;
            onProgress(0);
        } else {
            this.canSend = false;
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
