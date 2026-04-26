package com.gyf.loadview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.view.GravityCompat;

/* JADX INFO: loaded from: classes.dex */
public class LoadView extends FrameLayout {
    private Context mContext;
    private LoadStatus mCurrentStatus;
    private int mDefaultLoadingColor;
    private int mEmptyImageColor;
    private boolean mEmptyImageColorEnabled;
    private int mEmptyRes;
    private CharSequence mEmptyText;
    private int mEmptyTextColor;
    private int mErrorNetImageColor;
    private boolean mErrorNetImageColorEnabled;
    private int mErrorNetRes;
    private CharSequence mErrorNetText;
    private int mErrorNetTextColor;
    private int mFailImageColor;
    private boolean mFailImageColorEnabled;
    private int mFailRes;
    private CharSequence mFailText;
    private int mFailTextColor;
    private int mImageBottomMargin;
    private int mImageLeftMargin;
    private int mImageRightMargin;
    private int mImageTextBottomMargin;
    private int mImageTextGravity;
    private int mImageTextLeftMargin;
    private int mImageTextRightMargin;
    private int mImageTextTopMargin;
    private int mImageTopMargin;
    private ImageView mImageView;
    private float mImageViewHeight;
    private float mImageViewWidth;
    private Boolean mIsEmptyClickable;
    private Boolean mIsErrorNetClickable;
    private Boolean mIsFailClickable;
    private boolean mIsLoading;
    private Boolean mIsLoadingClickable;
    private LinearLayout mLinearLayout;
    private View mLoadView;
    private int mLoadingBottomMargin;
    private int mLoadingGravity;
    private int mLoadingLeftMargin;
    private int mLoadingRightMargin;
    private int mLoadingTopMargin;
    private float mLoadingViewHeight;
    private float mLoadingViewWidth;
    private OnLoadEmptyClickListener mOnLoadEmptyClickListener;
    private OnLoadErrorNetClickListener mOnLoadErrorNetClickListener;
    private OnLoadFailClickListener mOnLoadFailClickListener;
    private OnLoadingListener mOnLoadingListener;
    private ProgressBar mProgressBar;
    private int mTextBottomMargin;
    private int mTextLeftMargin;
    private int mTextRightMargin;
    private float mTextSize;
    private int mTextTopMargin;
    private TextView mTextView;

    public interface OnLoadEmptyClickListener {
        void onLoadEmptyClick();
    }

    public interface OnLoadErrorNetClickListener {
        void onLoadErrorNetClick();
    }

    public interface OnLoadFailClickListener {
        void onLoadFailClick();
    }

    public interface OnLoadingListener {
        void onLoadingEnd(View view);

        void onLoadingStart(View view);
    }

    private int getCommonGravity(int i) {
        if ((8388615 & i) == 0) {
            i |= GravityCompat.START;
        }
        return (i & 112) == 0 ? i | 48 : i;
    }

    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDefaultLoadingColor = 0;
        this.mContext = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0539R.styleable.LoadView);
        initCurrentStatus(typedArrayObtainStyledAttributes.getInt(C0539R.styleable.LoadView_load_current_status, 0));
        this.mLoadingGravity = typedArrayObtainStyledAttributes.getInt(C0539R.styleable.LoadView_load_loading_gravity, typedArrayObtainStyledAttributes.getInt(C0539R.styleable.LoadView_load_gravity, LoadManager.getInstance().getLoadingGravity()));
        this.mImageTextGravity = typedArrayObtainStyledAttributes.getInt(C0539R.styleable.LoadView_load_image_text_gravity, typedArrayObtainStyledAttributes.getInt(C0539R.styleable.LoadView_load_gravity, LoadManager.getInstance().getImageTextGravity()));
        this.mLoadingLeftMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_loading_margin_left, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_loading_margin, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin_left, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin, LoadManager.getInstance().getLoadingLeftMargin()))));
        this.mLoadingTopMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_loading_margin_top, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_loading_margin, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin_top, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin, LoadManager.getInstance().getLoadingTopMargin()))));
        this.mLoadingRightMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_loading_margin_right, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_loading_margin, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin_right, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin, LoadManager.getInstance().getLoadingRightMargin()))));
        this.mLoadingBottomMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_loading_margin_bottom, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_loading_margin, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin_bottom, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin, LoadManager.getInstance().getLoadingBottomMargin()))));
        this.mImageTextLeftMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_text_margin_left, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_text_margin, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin_left, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin, LoadManager.getInstance().getImageTextLeftMargin()))));
        this.mImageTextTopMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_text_margin_top, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_text_margin, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin_top, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin, LoadManager.getInstance().getImageTextTopMargin()))));
        this.mImageTextRightMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_text_margin_right, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_text_margin, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin_right, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin, LoadManager.getInstance().getImageTextRightMargin()))));
        this.mImageTextBottomMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_text_margin_bottom, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_text_margin, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin_bottom, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_margin, LoadManager.getInstance().getImageTextBottomMargin()))));
        this.mImageLeftMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_margin_left, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_margin, LoadManager.getInstance().getImageLeftMargin()));
        this.mImageTopMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_margin_top, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_margin, LoadManager.getInstance().getImageTopMargin()));
        this.mImageRightMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_margin_right, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_margin, LoadManager.getInstance().getImageRightMargin()));
        this.mImageBottomMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_margin_bottom, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_margin, LoadManager.getInstance().getImageBottomMargin()));
        this.mTextLeftMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_text_margin_left, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_text_margin, LoadManager.getInstance().getTextLeftMargin()));
        this.mTextTopMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_text_margin_top, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_text_margin, LoadManager.getInstance().getTextTopMargin()));
        this.mTextRightMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_text_margin_right, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_text_margin, LoadManager.getInstance().getTextRightMargin()));
        this.mTextBottomMargin = (int) typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_text_margin_bottom, typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_text_margin, LoadManager.getInstance().getTextBottomMargin()));
        CharSequence string = typedArrayObtainStyledAttributes.getString(C0539R.styleable.LoadView_load_text_fail);
        this.mFailText = string == null ? LoadManager.getInstance().getFailText() : string;
        CharSequence string2 = typedArrayObtainStyledAttributes.getString(C0539R.styleable.LoadView_load_text_error_net);
        this.mErrorNetText = string2 == null ? LoadManager.getInstance().getErrorNetText() : string2;
        CharSequence string3 = typedArrayObtainStyledAttributes.getString(C0539R.styleable.LoadView_load_text_empty);
        this.mEmptyText = string3 == null ? LoadManager.getInstance().getEmptyText() : string3;
        this.mFailRes = typedArrayObtainStyledAttributes.getResourceId(C0539R.styleable.LoadView_load_image_fail, LoadManager.getInstance().getFailRes());
        this.mErrorNetRes = typedArrayObtainStyledAttributes.getResourceId(C0539R.styleable.LoadView_load_image_error_net, LoadManager.getInstance().getErrorNetRes());
        this.mEmptyRes = typedArrayObtainStyledAttributes.getResourceId(C0539R.styleable.LoadView_load_image_empty, LoadManager.getInstance().getEmptyRes());
        this.mFailTextColor = typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_text_color_fail, typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_text_color, LoadManager.getInstance().getFailTextColor()));
        this.mErrorNetTextColor = typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_text_color_error_net, typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_text_color, LoadManager.getInstance().getErrorNetTextColor()));
        this.mEmptyTextColor = typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_text_color_empty, typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_text_color, LoadManager.getInstance().getEmptyTextColor()));
        this.mFailImageColor = typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_image_color_fail, typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_image_color, LoadManager.getInstance().getFailImageColor()));
        this.mErrorNetImageColor = typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_image_color_error_net, typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_image_color, LoadManager.getInstance().getErrorNetImageColor()));
        this.mEmptyImageColor = typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_image_color_empty, typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_image_color, LoadManager.getInstance().getEmptyImageColor()));
        this.mTextSize = typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_text_size, LoadUtils.sp2px(this.mContext, LoadManager.getInstance().getTextSize()));
        int color = typedArrayObtainStyledAttributes.getColor(C0539R.styleable.LoadView_load_default_loading_color, LoadManager.getInstance().getDefaultLoadingColor());
        this.mDefaultLoadingColor = color;
        if (color == 0) {
            this.mDefaultLoadingColor = LoadUtils.getDarkColorAccent(context);
        }
        this.mFailImageColorEnabled = typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_image_color_fail_enabled, typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_image_color_enabled, LoadManager.getInstance().isFailImageColorEnabled()));
        this.mErrorNetImageColorEnabled = typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_image_color_error_net_enabled, typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_image_color_enabled, LoadManager.getInstance().isErrorNetImageColorEnabled()));
        this.mEmptyImageColorEnabled = typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_image_color_empty_enabled, typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_image_color_enabled, LoadManager.getInstance().isEmptyImageColorEnabled()));
        this.mImageViewWidth = typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_width, LoadManager.getInstance().getImageViewWidth());
        this.mImageViewHeight = typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_image_height, LoadManager.getInstance().getImageViewHeight());
        this.mLoadingViewWidth = typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_loading_width, LoadManager.getInstance().getLoadingViewWidth());
        this.mLoadingViewHeight = typedArrayObtainStyledAttributes.getDimension(C0539R.styleable.LoadView_load_loading_height, LoadManager.getInstance().getLoadingViewHeight());
        this.mIsLoadingClickable = Boolean.valueOf(typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_loading_clickable, typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_clickable, LoadManager.getInstance().getLoadingClickable().booleanValue())));
        this.mIsFailClickable = Boolean.valueOf(typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_fail_clickable, typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_clickable, LoadManager.getInstance().getFailClickable().booleanValue())));
        this.mIsErrorNetClickable = Boolean.valueOf(typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_error_net_clickable, typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_clickable, LoadManager.getInstance().getErrorNetClickable().booleanValue())));
        this.mIsEmptyClickable = Boolean.valueOf(typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_empty_clickable, typedArrayObtainStyledAttributes.getBoolean(C0539R.styleable.LoadView_load_clickable, LoadManager.getInstance().getEmptyClickable().booleanValue())));
        initView();
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        if (childCount > 3) {
            throw new IllegalStateException("LoadView can host only one ‘Loading’ child view");
        }
        if (childCount == 3) {
            View childAt = getChildAt(2);
            removeView(childAt);
            removeView(getChildAt(1));
            addView(childAt);
            this.mLoadView = childAt;
            this.mLoadingViewWidth = childAt.getMeasuredWidth();
            this.mLoadingViewHeight = this.mLoadView.getMeasuredHeight();
            changeView();
        }
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getChildAt(i3).getLayoutParams();
            if (i3 == 0) {
                layoutParams.width = -2;
                layoutParams.height = -2;
            } else if (i3 == 1) {
                layoutParams.width = (int) this.mLoadingViewWidth;
                layoutParams.height = (int) this.mLoadingViewHeight;
            }
        }
        setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        setLayout();
        super.onLayout(z, i, i2, i3, i4);
    }

    private void initView() {
        this.mImageView = new ImageView(this.mContext);
        TextView textView = new TextView(this.mContext);
        this.mTextView = textView;
        textView.setGravity(17);
        LinearLayout linearLayout = new LinearLayout(this.mContext);
        this.mLinearLayout = linearLayout;
        linearLayout.setOrientation(1);
        this.mLinearLayout.setClickable(true);
        this.mLinearLayout.addView(this.mImageView, -2, -2);
        this.mLinearLayout.addView(this.mTextView, -2, -2);
        this.mLinearLayout.setOnClickListener(new MyOnClickListener());
        setImageTextSize();
        addView(this.mLinearLayout, -2, -2);
        if (LoadManager.getInstance().getLoadingLayoutId() != 0) {
            this.mLoadView = LayoutInflater.from(this.mContext).inflate(LoadManager.getInstance().getLoadingLayoutId(), (ViewGroup) this, false);
        }
        if (this.mLoadView == null) {
            this.mProgressBar = new ProgressBar(this.mContext);
            setDefaultLoadingColor();
            this.mLoadView = this.mProgressBar;
        }
        addView(this.mLoadView, -2, -2);
        setLayout();
        changeView();
    }

    private void setLayout() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getChildAt(i).getLayoutParams();
            if (i == 0) {
                layoutParams.gravity = this.mImageTextGravity;
                layoutParams.setMargins(this.mImageTextLeftMargin, this.mImageTextTopMargin, this.mImageTextRightMargin, this.mImageTextBottomMargin);
            }
            if (i == 1) {
                layoutParams.gravity = this.mLoadingGravity;
                layoutParams.setMargins(this.mLoadingLeftMargin, this.mLoadingTopMargin, this.mLoadingRightMargin, this.mLoadingBottomMargin);
            }
        }
    }

    /* JADX INFO: renamed from: com.gyf.loadview.LoadView$1 */
    static /* synthetic */ class C05381 {
        static final /* synthetic */ int[] $SwitchMap$com$gyf$loadview$LoadStatus;

        static {
            int[] iArr = new int[LoadStatus.values().length];
            $SwitchMap$com$gyf$loadview$LoadStatus = iArr;
            try {
                iArr[LoadStatus.UNDO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$gyf$loadview$LoadStatus[LoadStatus.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$gyf$loadview$LoadStatus[LoadStatus.LOADING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$gyf$loadview$LoadStatus[LoadStatus.FAIL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$gyf$loadview$LoadStatus[LoadStatus.ERROR_NET.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$gyf$loadview$LoadStatus[LoadStatus.EMPTY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private void changeView() {
        switch (C05381.$SwitchMap$com$gyf$loadview$LoadStatus[this.mCurrentStatus.ordinal()]) {
            case 1:
            case 2:
                setClickable(false);
                this.mLinearLayout.setVisibility(8);
                this.mLoadView.setVisibility(8);
                this.mLinearLayout.setClickable(false);
                loadingEnd();
                break;
            case 3:
                setClickable(this.mIsLoadingClickable.booleanValue());
                this.mLinearLayout.setVisibility(8);
                this.mLoadView.setVisibility(0);
                this.mLinearLayout.setClickable(false);
                loadingStart();
                break;
            case 4:
                setClickable(this.mIsFailClickable.booleanValue());
                this.mImageView.setImageResource(this.mFailRes);
                if (this.mFailImageColorEnabled) {
                    setImageDrawable(this.mFailImageColor);
                }
                this.mTextView.setText(this.mFailText);
                this.mTextView.setTextColor(this.mFailTextColor);
                this.mLinearLayout.setVisibility(0);
                this.mLoadView.setVisibility(8);
                this.mLinearLayout.setClickable(true);
                loadingEnd();
                break;
            case 5:
                setClickable(this.mIsErrorNetClickable.booleanValue());
                this.mImageView.setImageResource(this.mErrorNetRes);
                if (this.mErrorNetImageColorEnabled) {
                    setImageDrawable(this.mErrorNetImageColor);
                }
                this.mTextView.setText(this.mErrorNetText);
                this.mTextView.setTextColor(this.mErrorNetTextColor);
                this.mLinearLayout.setVisibility(0);
                this.mLoadView.setVisibility(8);
                this.mLinearLayout.setClickable(true);
                loadingEnd();
                break;
            case 6:
                setClickable(this.mIsEmptyClickable.booleanValue());
                this.mImageView.setImageResource(this.mEmptyRes);
                if (this.mEmptyImageColorEnabled) {
                    setImageDrawable(this.mEmptyImageColor);
                }
                this.mTextView.setText(this.mEmptyText);
                this.mTextView.setTextColor(this.mEmptyTextColor);
                this.mLinearLayout.setVisibility(0);
                this.mLoadView.setVisibility(8);
                this.mLinearLayout.setClickable(true);
                loadingEnd();
                break;
        }
        changeImageTextView();
    }

    private void loadingStart() {
        OnLoadingListener onLoadingListener = this.mOnLoadingListener;
        if (onLoadingListener == null || this.mIsLoading) {
            return;
        }
        this.mIsLoading = true;
        onLoadingListener.onLoadingStart(this.mLoadView);
    }

    private void loadingEnd() {
        OnLoadingListener onLoadingListener = this.mOnLoadingListener;
        if (onLoadingListener == null || !this.mIsLoading) {
            return;
        }
        this.mIsLoading = false;
        onLoadingListener.onLoadingEnd(this.mLoadView);
    }

    private void changeImageTextView() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mImageView.getLayoutParams();
        layoutParams.setMargins(this.mImageLeftMargin, this.mImageTopMargin, this.mImageRightMargin, this.mImageBottomMargin);
        this.mImageView.setLayoutParams(layoutParams);
        ImageView imageView = this.mImageView;
        imageView.setVisibility(imageView.getDrawable() == null ? 8 : 0);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTextView.getLayoutParams();
        layoutParams2.setMargins(this.mTextLeftMargin, this.mTextTopMargin, this.mTextRightMargin, this.mTextBottomMargin);
        this.mTextView.setLayoutParams(layoutParams2);
        this.mTextView.setTextSize(0, this.mTextSize);
        TextView textView = this.mTextView;
        textView.setVisibility("".equals(textView.getText().toString().trim()) ? 8 : 0);
    }

    public void setCurrentStatus(LoadStatus loadStatus) {
        this.mCurrentStatus = loadStatus;
        changeView();
    }

    public LoadStatus getCurrentStatus() {
        return this.mCurrentStatus;
    }

    public void setLoadingView(int i) {
        setLoadingView(LayoutInflater.from(this.mContext).inflate(i, (ViewGroup) this, false), (int) this.mLoadingViewWidth, (int) this.mLoadingViewHeight);
    }

    public void setLoadingView(int i, int i2, int i3) {
        setLoadingView(LayoutInflater.from(this.mContext).inflate(i, (ViewGroup) this, false), i2, i3);
    }

    public void setLoadingView(View view) {
        setLoadingView(view, (int) this.mLoadingViewWidth, (int) this.mLoadingViewHeight);
    }

    public void setLoadingView(View view, int i, int i2) {
        if (this.mIsLoading) {
            return;
        }
        this.mLoadingViewWidth = i;
        this.mLoadingViewHeight = i2;
        removeView(this.mLoadView);
        this.mLoadView = view;
        addView(view);
        changeView();
    }

    public void setLoadingViewSize(int i, int i2) {
        this.mLoadingViewWidth = i;
        this.mLoadingViewHeight = i2;
        requestLayout();
    }

    public void setFailRes(int i) {
        this.mFailRes = i;
        changeView();
    }

    public void setErrorNetRes(int i) {
        this.mErrorNetRes = i;
        changeView();
    }

    public void setEmptyRes(int i) {
        this.mEmptyRes = i;
        changeView();
    }

    public void setFailText(CharSequence charSequence) {
        this.mFailText = charSequence;
        changeView();
    }

    public void setErrorNetText(CharSequence charSequence) {
        this.mErrorNetText = charSequence;
        changeView();
    }

    public void setEmptyText(CharSequence charSequence) {
        this.mEmptyText = charSequence;
        changeView();
    }

    public void setTextColor(int i) {
        this.mFailTextColor = i;
        this.mErrorNetTextColor = i;
        this.mEmptyTextColor = i;
        changeView();
    }

    public void setFailTextColor(int i) {
        this.mFailTextColor = i;
        changeView();
    }

    public void setErrorNetTextColor(int i) {
        this.mErrorNetTextColor = i;
        changeView();
    }

    public void setEmptyTextColor(int i) {
        this.mEmptyTextColor = i;
        changeView();
    }

    public void setImageColor(int i) {
        this.mFailImageColor = i;
        this.mErrorNetImageColor = i;
        this.mEmptyImageColor = i;
        changeView();
    }

    public void setFailImageColor(int i) {
        this.mFailImageColor = i;
        changeView();
    }

    public void setErrorNetImageColor(int i) {
        this.mErrorNetImageColor = i;
        changeView();
    }

    public void setEmptyImageColor(int i) {
        this.mEmptyImageColor = i;
        changeView();
    }

    public void setDefaultLoadingColor(int i) {
        this.mDefaultLoadingColor = i;
        setDefaultLoadingColor();
        changeView();
    }

    private void setDefaultLoadingColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(this.mDefaultLoadingColor));
            this.mProgressBar.setIndeterminateTintMode(PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void setTextSize(int i) {
        this.mTextSize = LoadUtils.sp2px(this.mContext, i);
        changeView();
    }

    private void setImageDrawable(int i) {
        Drawable drawable = this.mImageView.getDrawable();
        if (drawable != null) {
            drawable.setColorFilter(i, PorterDuff.Mode.SRC_IN);
            this.mImageView.setImageDrawable(drawable);
        }
    }

    public void setImageColorEnabled(boolean z) {
        this.mFailImageColorEnabled = z;
        this.mErrorNetImageColorEnabled = z;
        this.mEmptyImageColorEnabled = z;
        changeView();
    }

    public void setFailImageColorEnabled(boolean z) {
        this.mFailImageColorEnabled = z;
        changeView();
    }

    public void setErrorNetImageColorEnabled(boolean z) {
        this.mErrorNetImageColorEnabled = z;
        changeView();
    }

    public void setEmptyImageColorEnabled(boolean z) {
        this.mEmptyImageColorEnabled = z;
        changeView();
    }

    public void setMargins(int i, int i2, int i3, int i4) {
        this.mImageTextLeftMargin = i;
        this.mImageTextTopMargin = i2;
        this.mImageTextRightMargin = i3;
        this.mImageTextBottomMargin = i4;
        this.mLoadingLeftMargin = i;
        this.mLoadingTopMargin = i2;
        this.mLoadingRightMargin = i3;
        this.mLoadingBottomMargin = i4;
        requestLayout();
    }

    public void setImageTextMargins(int i, int i2, int i3, int i4) {
        this.mImageTextLeftMargin = i;
        this.mImageTextTopMargin = i2;
        this.mImageTextRightMargin = i3;
        this.mImageTextBottomMargin = i4;
        requestLayout();
    }

    public void setLoadingMargins(int i, int i2, int i3, int i4) {
        this.mLoadingLeftMargin = i;
        this.mLoadingTopMargin = i2;
        this.mLoadingRightMargin = i3;
        this.mLoadingBottomMargin = i4;
        requestLayout();
    }

    public void setImageMargins(int i, int i2, int i3, int i4) {
        this.mImageLeftMargin = i;
        this.mImageTopMargin = i2;
        this.mImageRightMargin = i3;
        this.mImageBottomMargin = i4;
        changeView();
    }

    public void setTextMargins(int i, int i2, int i3, int i4) {
        this.mTextLeftMargin = i;
        this.mTextTopMargin = i2;
        this.mTextRightMargin = i3;
        this.mTextBottomMargin = i4;
        changeView();
    }

    public void setImageViewSize(float f, float f2) {
        this.mImageViewWidth = f;
        this.mImageViewHeight = f2;
        setImageTextSize();
    }

    public void setGravity(int i) {
        int commonGravity = getCommonGravity(i);
        if (commonGravity != this.mLoadingGravity || commonGravity != this.mImageTextGravity) {
            requestLayout();
        }
        this.mLoadingGravity = commonGravity;
        this.mImageTextGravity = commonGravity;
    }

    public void setLoadingGravity(int i) {
        int commonGravity = getCommonGravity(i);
        if (commonGravity != this.mLoadingGravity) {
            requestLayout();
        }
        this.mLoadingGravity = commonGravity;
    }

    public void setImageTextGravity(int i) {
        int commonGravity = getCommonGravity(i);
        if (commonGravity != this.mImageTextGravity) {
            requestLayout();
        }
        this.mImageTextGravity = commonGravity;
    }

    public void isLoadClickable(Boolean bool) {
        this.mIsLoadingClickable = bool;
        this.mIsFailClickable = bool;
        this.mIsErrorNetClickable = bool;
        this.mIsEmptyClickable = bool;
        changeView();
    }

    public void isLoadingClickable(Boolean bool) {
        this.mIsLoadingClickable = bool;
        changeView();
    }

    public void isFailClickable(Boolean bool) {
        this.mIsFailClickable = bool;
        changeView();
    }

    public void isErrorNetClickable(Boolean bool) {
        this.mIsErrorNetClickable = bool;
        changeView();
    }

    public void isEmptyClickable(Boolean bool) {
        this.mIsEmptyClickable = bool;
        changeView();
    }

    public void setOnFailClickListener(OnLoadFailClickListener onLoadFailClickListener) {
        this.mOnLoadFailClickListener = onLoadFailClickListener;
    }

    public void setOnErrorNetClickListener(OnLoadErrorNetClickListener onLoadErrorNetClickListener) {
        this.mOnLoadErrorNetClickListener = onLoadErrorNetClickListener;
    }

    public void setOnEmptyClickListener(OnLoadEmptyClickListener onLoadEmptyClickListener) {
        this.mOnLoadEmptyClickListener = onLoadEmptyClickListener;
    }

    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
        this.mOnLoadingListener = onLoadingListener;
    }

    public boolean isLoading() {
        return this.mIsLoading;
    }

    class MyOnClickListener implements View.OnClickListener {
        MyOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (LoadView.this.mCurrentStatus == LoadStatus.FAIL) {
                if (LoadView.this.mOnLoadFailClickListener != null) {
                    LoadView.this.setCurrentStatus(LoadStatus.LOADING);
                    LoadView.this.mOnLoadFailClickListener.onLoadFailClick();
                    return;
                }
                return;
            }
            if (LoadView.this.mCurrentStatus == LoadStatus.ERROR_NET) {
                if (LoadView.this.mOnLoadErrorNetClickListener != null) {
                    LoadView.this.setCurrentStatus(LoadStatus.LOADING);
                    LoadView.this.mOnLoadErrorNetClickListener.onLoadErrorNetClick();
                    return;
                }
                return;
            }
            if (LoadView.this.mCurrentStatus != LoadStatus.EMPTY || LoadView.this.mOnLoadEmptyClickListener == null) {
                return;
            }
            LoadView.this.setCurrentStatus(LoadStatus.LOADING);
            LoadView.this.mOnLoadEmptyClickListener.onLoadEmptyClick();
        }
    }

    private void initCurrentStatus(int i) {
        if (i == 0) {
            this.mCurrentStatus = LoadStatus.UNDO;
            return;
        }
        if (i == 1) {
            this.mCurrentStatus = LoadStatus.LOADING;
            return;
        }
        if (i == 2) {
            this.mCurrentStatus = LoadStatus.FAIL;
            return;
        }
        if (i == 3) {
            this.mCurrentStatus = LoadStatus.ERROR_NET;
        } else if (i == 4) {
            this.mCurrentStatus = LoadStatus.EMPTY;
        } else {
            if (i != 5) {
                return;
            }
            this.mCurrentStatus = LoadStatus.SUCCESS;
        }
    }

    private void setImageTextSize() {
        ImageView imageView = this.mImageView;
        if (imageView != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.width = (int) this.mImageViewWidth;
            layoutParams.height = (int) this.mImageViewHeight;
            layoutParams.gravity = 1;
            this.mImageView.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTextView.getLayoutParams();
            layoutParams2.width = -2;
            layoutParams2.height = -2;
            layoutParams2.gravity = 1;
            this.mTextView.setLayoutParams(layoutParams2);
        }
    }
}
