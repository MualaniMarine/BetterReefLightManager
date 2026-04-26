package com.mylhyl.zxing.scanner.encode;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.result.ParsedResultType;

/* JADX INFO: loaded from: classes.dex */
public final class QREncode {
    private QRCodeEncoder mQRCodeEncoder;

    private QREncode() {
    }

    private QREncode(QRCodeEncoder qRCodeEncoder) {
        this.mQRCodeEncoder = qRCodeEncoder;
    }

    @Deprecated
    public static Bitmap encodeQR(QRCodeEncoder qRCodeEncoder) {
        try {
            return qRCodeEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap encodeAsBitmap() {
        try {
            return this.mQRCodeEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class Builder {
        private Uri addressBookUri;
        BarcodeFormat barcodeFormat;
        private Bundle bundle;
        private int color;
        private int[] colors;
        private String contents;
        private Context context;
        private String encodeContents;
        private Bitmap logoBitmap;
        private float logoBorder;
        private int logoSize;
        private Bitmap qrBackground;
        private int qrBackgroundColor;
        private int size;
        private ParsedResultType parsedResultType = ParsedResultType.TEXT;
        private boolean useVCard = true;
        private int logoBorderColor = -1;
        private QRLogoBorderType logoBorderType = QRLogoBorderType.ROUNDED;
        private float logoBorderRadius = 30.0f;
        private int margin = 4;

        public Builder(Context context) {
            this.context = context;
        }

        BarcodeFormat getBarcodeFormat() {
            return this.barcodeFormat;
        }

        Builder setBarcodeFormat(BarcodeFormat barcodeFormat) {
            this.barcodeFormat = barcodeFormat;
            return this;
        }

        ParsedResultType getParsedResultType() {
            return this.parsedResultType;
        }

        public Builder setParsedResultType(ParsedResultType parsedResultType) {
            this.parsedResultType = parsedResultType;
            return this;
        }

        Uri getAddressBookUri() {
            return this.addressBookUri;
        }

        public Builder setAddressBookUri(Uri uri) {
            this.addressBookUri = uri;
            return this;
        }

        Bundle getBundle() {
            return this.bundle;
        }

        public Builder setBundle(Bundle bundle) {
            this.bundle = bundle;
            return this;
        }

        String getContents() {
            return this.contents;
        }

        public Builder setContents(String str) {
            this.contents = str;
            return this;
        }

        String getEncodeContents() {
            return this.encodeContents;
        }

        Builder setEncodeContents(String str) {
            this.encodeContents = str;
            return this;
        }

        int getColor() {
            return this.color;
        }

        public Builder setColor(int i) {
            this.color = i;
            return this;
        }

        int[] getColors() {
            return this.colors;
        }

        public Builder setColors(int i, int i2, int i3, int i4) {
            this.colors = null;
            this.colors = new int[]{i, i2, i3, i4};
            return this;
        }

        boolean isUseVCard() {
            return this.useVCard;
        }

        public Builder setUseVCard(boolean z) {
            this.useVCard = z;
            return this;
        }

        int getSize() {
            return this.size;
        }

        public Builder setSize(int i) {
            this.size = i;
            return this;
        }

        public Builder setLogoBitmap(Bitmap bitmap, int i) {
            this.logoBitmap = bitmap;
            this.logoSize = i;
            return this;
        }

        float getLogoBorder() {
            return this.logoBorder;
        }

        public Builder setLogoBorder(float f) {
            this.logoBorder = f;
            return this;
        }

        int getLogoBorderColor() {
            return this.logoBorderColor;
        }

        public Builder setLogoBorderColor(int i) {
            this.logoBorderColor = i;
            return this;
        }

        QRLogoBorderType getLogoBorderType() {
            return this.logoBorderType;
        }

        public Builder setLogoBorderType(QRLogoBorderType qRLogoBorderType) {
            this.logoBorderType = qRLogoBorderType;
            return this;
        }

        float getLogoBorderRadius() {
            return this.logoBorderRadius;
        }

        public Builder setLogoBorderRadius(float f) {
            this.logoBorderRadius = f;
            return this;
        }

        @Deprecated
        public QRCodeEncoder buildDeprecated() {
            checkParams();
            return new QRCodeEncoder(this, this.context.getApplicationContext());
        }

        public QREncode build() {
            checkParams();
            return new QREncode(new QRCodeEncoder(this, this.context.getApplicationContext()));
        }

        int getQrBackgroundColor() {
            return this.qrBackgroundColor;
        }

        int getMargin() {
            return this.margin;
        }

        public Builder setMargin(int i) {
            this.margin = i;
            return this;
        }

        int getLogoSize() {
            return this.logoSize;
        }

        Bitmap getQrBackground() {
            return this.qrBackground;
        }

        public Builder setQrBackground(Bitmap bitmap) {
            this.qrBackground = bitmap;
            return this;
        }

        public Builder setQrBackground(int i) {
            this.qrBackgroundColor = i;
            return this;
        }

        Bitmap getLogoBitmap() {
            return this.logoBitmap;
        }

        public Builder setLogoBitmap(Bitmap bitmap) {
            this.logoBitmap = bitmap;
            return this;
        }

        private void checkParams() {
            if (this.context == null) {
                throw new IllegalArgumentException("context no found...");
            }
            ParsedResultType parsedResultType = this.parsedResultType;
            if (parsedResultType == null) {
                throw new IllegalArgumentException("parsedResultType no found...");
            }
            if (parsedResultType != ParsedResultType.ADDRESSBOOK && this.parsedResultType != ParsedResultType.GEO && this.contents == null) {
                throw new IllegalArgumentException("parsedResultType not ParsedResultType.ADDRESSBOOK and ParsedResultType.GEO, contents no found...");
            }
            if ((this.parsedResultType == ParsedResultType.ADDRESSBOOK || this.parsedResultType == ParsedResultType.GEO) && this.bundle == null && this.addressBookUri == null) {
                throw new IllegalArgumentException("parsedResultType yes ParsedResultType.ADDRESSBOOK or ParsedResultType.GEO, bundle and addressBookUri no found...");
            }
        }
    }
}
