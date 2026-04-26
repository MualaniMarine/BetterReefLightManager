package com.gyf.loadview;

import android.graphics.Color;

/* JADX INFO: loaded from: classes.dex */
public class LoadManager {
    private static final LoadManager mInstance = new LoadManager();
    private int mDefaultLoadingColor;
    private int mEmptyImageColor;
    private boolean mEmptyImageColorEnabled;
    private int mEmptyTextColor;
    private int mErrorNetImageColor;
    private boolean mErrorNetImageColorEnabled;
    private int mErrorNetTextColor;
    private int mFailImageColor;
    private boolean mFailImageColorEnabled;
    private int mFailTextColor;
    private int mImageColor;
    private float mImageViewHeight;
    private float mImageViewWidth;
    private Boolean mIsEmptyClickable;
    private Boolean mIsErrorNetClickable;
    private Boolean mIsFailClickable;
    private Boolean mIsLoadingClickable;
    private float mLoadingViewHeight;
    private float mLoadingViewWidth;
    private int mTextColor;
    private int mTextSize;
    private int mLoadingLayoutId = 0;
    private CharSequence mFailText = "加载失败,点击重试";
    private CharSequence mErrorNetText = "网络错误";
    private CharSequence mEmptyText = "数据为空";
    private int mGravity = 17;
    private int mLoadingGravity = 17;
    private int mImageTextGravity = 17;
    private int mMargin = 0;
    private int mLoadingLeftMargin = 0;
    private int mLoadingTopMargin = 0;
    private int mLoadingRightMargin = 0;
    private int mLoadingBottomMargin = 0;
    private int mImageTextLeftMargin = 0;
    private int mImageTextTopMargin = 0;
    private int mImageTextRightMargin = 0;
    private int mImageTextBottomMargin = 0;
    private int mImageLeftMargin = 0;
    private int mImageTopMargin = 0;
    private int mImageRightMargin = 0;
    private int mImageBottomMargin = 0;
    private int mTextLeftMargin = 0;
    private int mTextTopMargin = 100;
    private int mTextRightMargin = 0;
    private int mTextBottomMargin = 0;
    private int mFailRes = C0539R.drawable.icon_load_fail;
    private int mErrorNetRes = C0539R.drawable.icon_load_error_net;
    private int mEmptyRes = C0539R.drawable.icon_load_emtpy;

    public LoadManager() {
        int color = Color.parseColor("#000000");
        this.mTextColor = color;
        this.mFailTextColor = color;
        this.mErrorNetTextColor = color;
        this.mEmptyTextColor = color;
        int color2 = Color.parseColor("#000000");
        this.mImageColor = color2;
        this.mFailImageColor = color2;
        this.mErrorNetImageColor = color2;
        this.mEmptyImageColor = color2;
        this.mDefaultLoadingColor = 0;
        this.mTextSize = 16;
        this.mFailImageColorEnabled = false;
        this.mErrorNetImageColorEnabled = false;
        this.mEmptyImageColorEnabled = false;
        this.mImageViewWidth = -2.0f;
        this.mImageViewHeight = -2.0f;
        this.mLoadingViewWidth = -2.0f;
        this.mLoadingViewHeight = -2.0f;
        this.mIsLoadingClickable = true;
        this.mIsFailClickable = true;
        this.mIsErrorNetClickable = true;
        this.mIsEmptyClickable = true;
    }

    public static LoadManager getInstance() {
        return mInstance;
    }

    public LoadManager setLoadingLayoutId(int i) {
        this.mLoadingLayoutId = i;
        return this;
    }

    public int getLoadingLayoutId() {
        return this.mLoadingLayoutId;
    }

    public LoadManager setFail(CharSequence charSequence, int i) {
        this.mFailText = charSequence;
        this.mFailRes = i;
        return this;
    }

    public LoadManager setErrorNet(CharSequence charSequence, int i) {
        this.mErrorNetText = charSequence;
        this.mErrorNetRes = i;
        return this;
    }

    public LoadManager setEmpty(CharSequence charSequence, int i) {
        this.mEmptyText = charSequence;
        this.mEmptyRes = i;
        return this;
    }

    public LoadManager setFailRes(int i) {
        this.mFailRes = i;
        return this;
    }

    public int getFailRes() {
        return this.mFailRes;
    }

    public LoadManager setErrorNetRes(int i) {
        this.mErrorNetRes = i;
        return this;
    }

    public int getErrorNetRes() {
        return this.mErrorNetRes;
    }

    public LoadManager setEmptyRes(int i) {
        this.mEmptyRes = i;
        return this;
    }

    public int getEmptyRes() {
        return this.mEmptyRes;
    }

    public LoadManager setFailText(CharSequence charSequence) {
        this.mFailText = charSequence;
        return this;
    }

    public CharSequence getFailText() {
        return this.mFailText;
    }

    public LoadManager setErrorNetText(CharSequence charSequence) {
        this.mErrorNetText = charSequence;
        return this;
    }

    public CharSequence getErrorNetText() {
        return this.mErrorNetText;
    }

    public LoadManager setEmptyText(CharSequence charSequence) {
        this.mEmptyText = charSequence;
        return this;
    }

    public CharSequence getEmptyText() {
        return this.mEmptyText;
    }

    public LoadManager setTextColor(int i) {
        this.mTextColor = i;
        this.mFailTextColor = i;
        this.mErrorNetTextColor = i;
        this.mEmptyTextColor = i;
        return this;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public LoadManager setFailTextColor(int i) {
        this.mFailTextColor = i;
        return this;
    }

    public int getFailTextColor() {
        return this.mFailTextColor;
    }

    public LoadManager setErrorNetTextColor(int i) {
        this.mErrorNetTextColor = i;
        return this;
    }

    public int getErrorNetTextColor() {
        return this.mErrorNetTextColor;
    }

    public LoadManager setEmptyTextColor(int i) {
        this.mEmptyTextColor = i;
        return this;
    }

    public int getEmptyTextColor() {
        return this.mEmptyTextColor;
    }

    public LoadManager setImageColor(int i) {
        this.mImageColor = i;
        this.mFailImageColor = i;
        this.mErrorNetImageColor = i;
        this.mEmptyImageColor = i;
        return this;
    }

    public int getImageColor() {
        return this.mImageColor;
    }

    public LoadManager setFailImageColor(int i) {
        this.mFailImageColor = i;
        return this;
    }

    public int getFailImageColor() {
        return this.mFailImageColor;
    }

    public LoadManager setErrorNetImageColor(int i) {
        this.mErrorNetImageColor = i;
        return this;
    }

    public int getErrorNetImageColor() {
        return this.mErrorNetImageColor;
    }

    public LoadManager setEmptyImageColor(int i) {
        this.mEmptyImageColor = i;
        return this;
    }

    public int getEmptyImageColor() {
        return this.mEmptyImageColor;
    }

    public LoadManager setDefaultLoadingColor(int i) {
        this.mDefaultLoadingColor = i;
        return this;
    }

    public int getDefaultLoadingColor() {
        return this.mDefaultLoadingColor;
    }

    public LoadManager setTextSize(int i) {
        this.mTextSize = i;
        return this;
    }

    public int getTextSize() {
        return this.mTextSize;
    }

    public LoadManager setGravity(int i) {
        this.mGravity = i;
        this.mLoadingGravity = i;
        this.mImageTextGravity = i;
        return this;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public LoadManager setLoadingGravity(int i) {
        this.mLoadingGravity = i;
        return this;
    }

    public int getLoadingGravity() {
        return this.mLoadingGravity;
    }

    public LoadManager setImageTextGravity(int i) {
        this.mImageTextGravity = i;
        return this;
    }

    public int getImageTextGravity() {
        return this.mImageTextGravity;
    }

    public LoadManager setMargins(int i) {
        return setMargins(i, i, i, i);
    }

    public LoadManager setMargins(int i, int i2, int i3, int i4) {
        this.mImageTextLeftMargin = i;
        this.mImageTextTopMargin = i2;
        this.mImageTextRightMargin = i3;
        this.mImageTextBottomMargin = i4;
        this.mLoadingLeftMargin = i;
        this.mLoadingTopMargin = i2;
        this.mLoadingRightMargin = i3;
        this.mLoadingBottomMargin = i4;
        return this;
    }

    public LoadManager setImageTextMargins(int i, int i2, int i3, int i4) {
        this.mImageTextLeftMargin = i;
        this.mImageTextTopMargin = i2;
        this.mImageTextRightMargin = i3;
        this.mImageTextBottomMargin = i4;
        return this;
    }

    public LoadManager setLoadingMargins(int i, int i2, int i3, int i4) {
        this.mLoadingLeftMargin = i;
        this.mLoadingTopMargin = i2;
        this.mLoadingRightMargin = i3;
        this.mLoadingBottomMargin = i4;
        return this;
    }

    public int getLoadingLeftMargin() {
        return this.mLoadingLeftMargin;
    }

    public int getLoadingTopMargin() {
        return this.mLoadingTopMargin;
    }

    public int getLoadingRightMargin() {
        return this.mLoadingRightMargin;
    }

    public int getLoadingBottomMargin() {
        return this.mLoadingBottomMargin;
    }

    public int getImageTextLeftMargin() {
        return this.mImageTextLeftMargin;
    }

    public int getImageTextTopMargin() {
        return this.mImageTextTopMargin;
    }

    public int getImageTextRightMargin() {
        return this.mImageTextRightMargin;
    }

    public int getImageTextBottomMargin() {
        return this.mImageTextBottomMargin;
    }

    public LoadManager setImageMargins(int i, int i2, int i3, int i4) {
        this.mImageLeftMargin = i;
        this.mImageTopMargin = i2;
        this.mImageRightMargin = i3;
        this.mImageBottomMargin = i4;
        return this;
    }

    public LoadManager setTextMargins(int i, int i2, int i3, int i4) {
        this.mTextLeftMargin = i;
        this.mTextTopMargin = i2;
        this.mTextRightMargin = i3;
        this.mTextBottomMargin = i4;
        return this;
    }

    public int getImageLeftMargin() {
        return this.mImageLeftMargin;
    }

    public int getImageTopMargin() {
        return this.mImageTopMargin;
    }

    public int getImageRightMargin() {
        return this.mImageRightMargin;
    }

    public int getImageBottomMargin() {
        return this.mImageBottomMargin;
    }

    public int getTextLeftMargin() {
        return this.mTextLeftMargin;
    }

    public int getTextTopMargin() {
        return this.mTextTopMargin;
    }

    public int getTextRightMargin() {
        return this.mTextRightMargin;
    }

    public int getTextBottomMargin() {
        return this.mTextBottomMargin;
    }

    public LoadManager setImageColorEnabled(boolean z) {
        this.mFailImageColorEnabled = z;
        this.mErrorNetImageColorEnabled = z;
        this.mEmptyImageColorEnabled = z;
        return this;
    }

    public LoadManager setFailImageColorEnabled(boolean z) {
        this.mFailImageColorEnabled = z;
        return this;
    }

    public boolean isFailImageColorEnabled() {
        return this.mFailImageColorEnabled;
    }

    public LoadManager setErrorNetImageColorEnabled(boolean z) {
        this.mErrorNetImageColorEnabled = z;
        return this;
    }

    public boolean isErrorNetImageColorEnabled() {
        return this.mErrorNetImageColorEnabled;
    }

    public LoadManager setEmptyImageColorEnabled(boolean z) {
        this.mEmptyImageColorEnabled = z;
        return this;
    }

    public boolean isEmptyImageColorEnabled() {
        return this.mEmptyImageColorEnabled;
    }

    public LoadManager setImageViewSize(float f, float f2) {
        this.mImageViewWidth = f;
        this.mImageViewHeight = f2;
        return this;
    }

    public float getImageViewWidth() {
        return this.mImageViewWidth;
    }

    public float getImageViewHeight() {
        return this.mImageViewHeight;
    }

    public LoadManager setLoadingViewSize(float f, float f2) {
        this.mLoadingViewWidth = f;
        this.mLoadingViewHeight = f2;
        return this;
    }

    public float getLoadingViewWidth() {
        return this.mLoadingViewWidth;
    }

    public float getLoadingViewHeight() {
        return this.mLoadingViewHeight;
    }

    public Boolean getLoadingClickable() {
        return this.mIsLoadingClickable;
    }

    public LoadManager isLoadingClickable(Boolean bool) {
        this.mIsLoadingClickable = bool;
        return this;
    }

    public LoadManager isLoadClickable(Boolean bool) {
        this.mIsLoadingClickable = bool;
        this.mIsFailClickable = bool;
        this.mIsErrorNetClickable = bool;
        this.mIsEmptyClickable = bool;
        return this;
    }

    public Boolean getFailClickable() {
        return this.mIsFailClickable;
    }

    public LoadManager isFailClickable(Boolean bool) {
        this.mIsFailClickable = bool;
        return this;
    }

    public Boolean getErrorNetClickable() {
        return this.mIsErrorNetClickable;
    }

    public LoadManager isErrorNetClickable(Boolean bool) {
        this.mIsErrorNetClickable = bool;
        return this;
    }

    public Boolean getEmptyClickable() {
        return this.mIsEmptyClickable;
    }

    public LoadManager isEmptyClickable(Boolean bool) {
        this.mIsEmptyClickable = bool;
        return this;
    }
}
