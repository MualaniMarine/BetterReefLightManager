package com.mylhyl.zxing.scanner;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.zxing.BarcodeFormat;
import com.mylhyl.zxing.scanner.camera.open.CameraFacing;
import com.mylhyl.zxing.scanner.common.Scanner;
import com.mylhyl.zxing.scanner.decode.DecodeFormatManager;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public final class ScannerOptions {
    public static final int DEFAULT_LASER_LINE_HEIGHT = 2;
    private double cameraZoomRatio;
    private String characterSet;
    private boolean createQrThumbnail;
    private Collection<BarcodeFormat> decodeFormats;
    private boolean frameCornerHide;
    private boolean frameCornerInside;
    private int frameHeight;
    private boolean frameHide;
    private int frameTopMargin;
    private int frameWidth;
    private Drawable laserLineDrawable;
    private int laserLineResId;
    private boolean laserMoveFullScreen;
    private int mediaResId;
    private boolean scanFullScreen;
    private boolean scanInvert;
    private boolean showQrThumbnail;
    private boolean tipTextToFrameTop;
    private ViewfinderCallback viewfinderCallback;
    private boolean viewfinderHide;
    private LaserStyle laserStyle = LaserStyle.COLOR_LINE;
    private int laserLineColor = Scanner.color.VIEWFINDER_LASER;
    private int laserLineHeight = 2;
    private int laserLineMoveSpeed = 6;
    private int frameStrokeColor = -1;
    private float frameStrokeWidth = 1.0f;
    private int frameCornerColor = Scanner.color.VIEWFINDER_LASER;
    private int frameCornerLength = 15;
    private int frameCornerWidth = 2;
    private String tipText = "将二维码放入框内，即可自动扫描";
    private int tipTextColor = -1;
    private int tipTextSize = 15;
    private int tipTextToFrameMargin = 20;
    private CameraFacing cameraFacing = CameraFacing.BACK;
    private int frameOutsideColor = Scanner.color.VIEWFINDER_MASK;

    public enum LaserStyle {
        COLOR_LINE,
        RES_LINE,
        DRAWABLE_LINE,
        RES_GRID,
        DRAWABLE_GRID
    }

    public interface ViewfinderCallback {
        void onDraw(View view, Canvas canvas, Rect rect);
    }

    protected ScannerOptions() {
    }

    public LaserStyle getLaserStyle() {
        return this.laserStyle;
    }

    public int getLaserLineColor() {
        return this.laserLineColor;
    }

    public int getLaserLineResId() {
        return this.laserLineResId;
    }

    public Drawable getLaserLineDrawable() {
        return this.laserLineDrawable;
    }

    public int getLaserLineHeight() {
        return this.laserLineHeight;
    }

    public int getLaserLineMoveSpeed() {
        return this.laserLineMoveSpeed;
    }

    public boolean isLaserMoveFullScreen() {
        return this.laserMoveFullScreen;
    }

    public int getFrameStrokeColor() {
        return this.frameStrokeColor;
    }

    public float getFrameStrokeWidth() {
        return this.frameStrokeWidth;
    }

    public int getFrameWidth() {
        return this.frameWidth;
    }

    public int getFrameHeight() {
        return this.frameHeight;
    }

    public int getFrameCornerColor() {
        return this.frameCornerColor;
    }

    public int getFrameCornerLength() {
        return this.frameCornerLength;
    }

    public int getFrameCornerWidth() {
        return this.frameCornerWidth;
    }

    public boolean isFrameCornerInside() {
        return this.frameCornerInside;
    }

    public boolean isFrameCornerHide() {
        return this.frameCornerHide;
    }

    public int getFrameTopMargin() {
        return this.frameTopMargin;
    }

    public boolean isFrameHide() {
        return this.frameHide;
    }

    public boolean isViewfinderHide() {
        return this.viewfinderHide;
    }

    public String getTipText() {
        return this.tipText;
    }

    public int getTipTextColor() {
        return this.tipTextColor;
    }

    public int getTipTextSize() {
        return this.tipTextSize;
    }

    public boolean isTipTextToFrameTop() {
        return this.tipTextToFrameTop;
    }

    public int getTipTextToFrameMargin() {
        return this.tipTextToFrameMargin;
    }

    public int getMediaResId() {
        return this.mediaResId;
    }

    public Collection<BarcodeFormat> getDecodeFormats() {
        return this.decodeFormats;
    }

    public boolean isCreateQrThumbnail() {
        return this.createQrThumbnail;
    }

    public boolean isShowQrThumbnail() {
        return this.showQrThumbnail;
    }

    public CameraFacing getCameraFacing() {
        return this.cameraFacing;
    }

    public boolean isScanFullScreen() {
        return this.scanFullScreen;
    }

    public boolean isScanInvert() {
        return this.scanInvert;
    }

    public double getCameraZoomRatio() {
        return this.cameraZoomRatio;
    }

    public ViewfinderCallback getViewfinderCallback() {
        return this.viewfinderCallback;
    }

    public int getFrameOutsideColor() {
        return this.frameOutsideColor;
    }

    public String getCharacterSet() {
        return this.characterSet;
    }

    public static final class Builder {
        private ScannerOptions options = new ScannerOptions();

        public ScannerOptions build() {
            return this.options;
        }

        public Builder setLaserLineColor(int i) {
            this.options.laserStyle = LaserStyle.COLOR_LINE;
            this.options.laserLineColor = i;
            return this;
        }

        public Builder setLaserLine(LaserStyle laserStyle, int i) {
            this.options.laserStyle = laserStyle;
            if (laserStyle == LaserStyle.COLOR_LINE) {
                this.options.laserLineColor = i;
            } else {
                this.options.laserLineResId = i;
            }
            return this;
        }

        public Builder setLaserLine(LaserStyle laserStyle, Drawable drawable) {
            this.options.laserStyle = laserStyle;
            this.options.laserLineDrawable = drawable;
            return this;
        }

        public Builder setLaserLineHeight(int i) {
            this.options.laserLineHeight = i;
            return this;
        }

        public Builder setLaserMoveSpeed(int i) {
            this.options.laserLineMoveSpeed = i;
            return this;
        }

        public Builder setLaserMoveFullScreen(boolean z) {
            this.options.laserMoveFullScreen = z;
            return this;
        }

        public Builder setFrameStrokeColor(int i) {
            this.options.frameStrokeColor = i;
            return this;
        }

        public Builder setFrameStrokeWidth(float f) {
            this.options.frameStrokeWidth = f;
            return this;
        }

        public Builder setFrameSize(int i, int i2) {
            this.options.frameWidth = i;
            this.options.frameHeight = i2;
            return this;
        }

        public Builder setFrameCornerColor(int i) {
            this.options.frameCornerColor = i;
            return this;
        }

        public Builder setFrameCornerLength(int i) {
            this.options.frameCornerLength = i;
            return this;
        }

        public Builder setFrameCornerWidth(int i) {
            this.options.frameCornerWidth = i;
            return this;
        }

        public Builder setFrameCornerInside(boolean z) {
            this.options.frameCornerInside = z;
            return this;
        }

        public Builder setFrameCornerHide(boolean z) {
            this.options.frameCornerHide = z;
            if (!z) {
                this.options.laserMoveFullScreen = false;
            }
            return this;
        }

        public Builder setFrameTopMargin(int i) {
            this.options.frameTopMargin = i;
            return this;
        }

        public Builder setFrameHide(boolean z) {
            this.options.frameHide = z;
            if (!z) {
                this.options.laserMoveFullScreen = false;
            }
            return this;
        }

        public Builder setViewfinderHide(boolean z) {
            this.options.viewfinderHide = z;
            return this;
        }

        public Builder setTipText(String str) {
            this.options.tipText = str;
            return this;
        }

        public Builder setTipTextColor(int i) {
            this.options.tipTextColor = i;
            return this;
        }

        public Builder setTipTextSize(int i) {
            this.options.tipTextSize = i;
            return this;
        }

        public Builder setTipTextToFrameMargin(int i) {
            this.options.tipTextToFrameMargin = i;
            return this;
        }

        public Builder setTipTextToFrameTop(boolean z) {
            this.options.tipTextToFrameTop = z;
            return this;
        }

        public Builder setMediaResId(int i) {
            this.options.mediaResId = i;
            return this;
        }

        public Builder setScanMode(String str) {
            this.options.decodeFormats = DecodeFormatManager.parseDecodeFormats(str);
            return this;
        }

        public Builder setScanMode(BarcodeFormat... barcodeFormatArr) {
            this.options.decodeFormats = DecodeFormatManager.parseDecodeFormats(barcodeFormatArr);
            return this;
        }

        public Builder setCreateQrThumbnail(boolean z) {
            this.options.createQrThumbnail = z;
            return this;
        }

        public Builder setShowQrThumbnail(boolean z) {
            this.options.showQrThumbnail = z;
            return this;
        }

        public Builder setCameraFacing(CameraFacing cameraFacing) {
            this.options.cameraFacing = cameraFacing;
            return this;
        }

        public Builder setScanFullScreen(boolean z) {
            this.options.scanFullScreen = z;
            if (z) {
                this.options.frameHide = true;
                this.options.frameCornerHide = true;
                this.options.laserMoveFullScreen = true;
            }
            return this;
        }

        public Builder setScanInvert(boolean z) {
            this.options.scanInvert = z;
            return this;
        }

        public Builder setCameraZoomRatio(double d) {
            this.options.cameraZoomRatio = d;
            return this;
        }

        public Builder setViewfinderCallback(ViewfinderCallback viewfinderCallback) {
            this.options.viewfinderCallback = viewfinderCallback;
            return this;
        }

        public Builder setFrameOutsideColor(int i) {
            this.options.frameOutsideColor = i;
            return this;
        }

        public Builder setCharacterSet(String str) {
            this.options.characterSet = str;
            return this;
        }
    }
}
