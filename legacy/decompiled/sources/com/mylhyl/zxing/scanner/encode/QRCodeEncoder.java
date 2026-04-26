package com.mylhyl.zxing.scanner.encode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import androidx.core.net.MailTo;
import com.espressif.iot.esptouch.util.ByteUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.common.BitMatrix;
import com.mylhyl.zxing.scanner.encode.QREncode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class QRCodeEncoder {
    private static final int BLACK = -16777216;
    private static final int WHITE = -1;
    private Context context;
    private QREncode.Builder encodeBuild;

    private static Bitmap getRoundedBitmap(Bitmap bitmap, float f, int i) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(i);
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmapCreateBitmap;
    }

    private static Bitmap getCircleBitmap(Bitmap bitmap, int i) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(i);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmapCreateBitmap;
    }

    QRCodeEncoder(QREncode.Builder builder, Context context) {
        this.context = context;
        this.encodeBuild = builder;
        if (builder.getColor() == 0) {
            this.encodeBuild.setColor(-16777216);
        }
        if (this.encodeBuild.getSize() == 0) {
            this.encodeBuild.setSize(getSmallerDimension(context.getApplicationContext()));
        }
        encodeContentsFromZXing(builder);
    }

    private static List<String> getAllBundleValues(Bundle bundle, String[] strArr) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            Object obj = bundle.get(str);
            arrayList.add(obj == null ? null : obj.toString());
        }
        return arrayList;
    }

    private static int getSmallerDimension(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int i = point.x;
        int i2 = point.y;
        if (i >= i2) {
            i = i2;
        }
        return (i * 7) / 8;
    }

    private static Bitmap addBackground(Bitmap bitmap, Bitmap bitmap2) {
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        int width2 = bitmap.getWidth();
        int height2 = bitmap.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, (Paint) null);
        if (!bitmap2.isRecycled()) {
            bitmap2.recycle();
        }
        canvas.drawBitmap(bitmap, (width - width2) / 2, (height - height2) / 2, (Paint) null);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        canvas.save();
        canvas.restore();
        return bitmapCreateBitmap;
    }

    private Bitmap addLogo(Bitmap bitmap) {
        Paint paint;
        Bitmap logoBitmap = this.encodeBuild.getLogoBitmap();
        int logoSize = this.encodeBuild.getLogoSize();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int width2 = logoBitmap.getWidth();
        int height2 = logoBitmap.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        Matrix matrix = new Matrix();
        float f = width;
        float f2 = width2;
        float f3 = ((f * 1.0f) / f2) / 5.0f;
        float f4 = height;
        float f5 = height2;
        float f6 = ((f4 * 1.0f) / f5) / 5.0f;
        float f7 = (f - (f2 * f3)) / 2.0f;
        float f8 = (f4 - (f5 * f6)) / 2.0f;
        if (logoSize > 0) {
            float f9 = logoSize * 1.0f;
            f3 = f9 / f2;
            f6 = f9 / f5;
            f7 = (width - logoSize) / 2;
            f8 = (height - logoSize) / 2;
        }
        float f10 = f8;
        float f11 = f7;
        matrix.postScale(f3, f6);
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(logoBitmap, 0, 0, width2, height2, matrix, false);
        float logoBorder = this.encodeBuild.getLogoBorder();
        if (logoBorder > 0.0f) {
            int logoBorderColor = this.encodeBuild.getLogoBorderColor() != -1 ? this.encodeBuild.getLogoBorderColor() : -1;
            Bitmap bitmapCreateBitmap3 = Bitmap.createBitmap((int) (bitmapCreateBitmap2.getWidth() + logoBorder), (int) (bitmapCreateBitmap2.getHeight() + logoBorder), Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(bitmapCreateBitmap3);
            canvas2.drawARGB(0, 0, 0, 0);
            Paint paint2 = new Paint();
            paint2.setAntiAlias(true);
            paint2.setColor(logoBorderColor);
            QRLogoBorderType logoBorderType = this.encodeBuild.getLogoBorderType();
            Rect clipBounds = canvas2.getClipBounds();
            if (logoBorderType == QRLogoBorderType.RECTANGLE) {
                canvas2.drawRect(clipBounds, paint2);
            } else if (logoBorderType == QRLogoBorderType.CIRCLE) {
                canvas2.drawOval(new RectF(clipBounds), paint2);
                paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                bitmapCreateBitmap2 = getCircleBitmap(bitmapCreateBitmap2, logoBorderColor);
            } else {
                float logoBorderRadius = this.encodeBuild.getLogoBorderRadius();
                canvas2.drawRoundRect(new RectF(clipBounds), logoBorderRadius, logoBorderRadius, paint2);
                paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                bitmapCreateBitmap2 = getRoundedBitmap(bitmapCreateBitmap2, logoBorderRadius, logoBorderColor);
            }
            float f12 = logoBorder / 2.0f;
            paint = null;
            canvas.drawBitmap(bitmapCreateBitmap3, f11 - f12, f10 - f12, (Paint) null);
            if (!bitmapCreateBitmap3.isRecycled()) {
                bitmapCreateBitmap3.recycle();
            }
        } else {
            paint = null;
        }
        canvas.drawBitmap(bitmapCreateBitmap2, f11, f10, paint);
        if (!bitmapCreateBitmap2.isRecycled()) {
            bitmapCreateBitmap2.recycle();
        }
        if (!logoBitmap.isRecycled()) {
            logoBitmap.recycle();
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        canvas.save();
        canvas.restore();
        return bitmapCreateBitmap;
    }

    Bitmap encodeAsBitmap() throws WriterException {
        String encodeContents = this.encodeBuild.getEncodeContents();
        BarcodeFormat barcodeFormat = this.encodeBuild.getBarcodeFormat();
        int color = this.encodeBuild.getColor();
        int size = this.encodeBuild.getSize();
        if (encodeContents == null) {
            return null;
        }
        EnumMap enumMap = new EnumMap(EncodeHintType.class);
        enumMap.put(EncodeHintType.CHARACTER_SET, Charset.forName(ByteUtil.ESPTOUCH_ENCODING_CHARSET).name());
        enumMap.put(EncodeHintType.MARGIN, Integer.valueOf(this.encodeBuild.getMargin()));
        try {
            BitMatrix bitMatrixEncode = new MultiFormatWriter().encode(encodeContents, barcodeFormat, size, size, enumMap);
            int width = bitMatrixEncode.getWidth();
            int height = bitMatrixEncode.getHeight();
            int[] iArr = new int[width * height];
            for (int i = 0; i < height; i++) {
                int i2 = i * width;
                for (int i3 = 0; i3 < width; i3++) {
                    if (bitMatrixEncode.get(i3, i)) {
                        int[] colors = this.encodeBuild.getColors();
                        if (colors != null) {
                            int i4 = size / 2;
                            if (i3 < i4 && i < i4) {
                                iArr[(i * size) + i3] = colors[0];
                            } else if (i3 < i4 && i > i4) {
                                iArr[(i * size) + i3] = colors[1];
                            } else if (i3 > i4 && i > i4) {
                                iArr[(i * size) + i3] = colors[2];
                            } else {
                                iArr[(i * size) + i3] = colors[3];
                            }
                        } else {
                            iArr[i2 + i3] = color;
                        }
                    } else {
                        int qrBackgroundColor = this.encodeBuild.getQrBackgroundColor();
                        int i5 = i2 + i3;
                        if (qrBackgroundColor == 0) {
                            qrBackgroundColor = -1;
                        }
                        iArr[i5] = qrBackgroundColor;
                    }
                }
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
            if (this.encodeBuild.getQrBackground() != null) {
                bitmapCreateBitmap = addBackground(bitmapCreateBitmap, this.encodeBuild.getQrBackground());
            }
            return this.encodeBuild.getLogoBitmap() != null ? addLogo(bitmapCreateBitmap) : bitmapCreateBitmap;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    private void encodeContentsFromZXing(QREncode.Builder builder) {
        if (builder.getBarcodeFormat() == null || builder.getBarcodeFormat() == BarcodeFormat.QR_CODE) {
            builder.setBarcodeFormat(BarcodeFormat.QR_CODE);
            encodeQRCodeContents(builder);
        }
    }

    /* JADX INFO: renamed from: com.mylhyl.zxing.scanner.encode.QRCodeEncoder$1 */
    static /* synthetic */ class C05471 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$client$result$ParsedResultType;

        static {
            int[] iArr = new int[ParsedResultType.values().length];
            $SwitchMap$com$google$zxing$client$result$ParsedResultType = iArr;
            try {
                iArr[ParsedResultType.WIFI.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.CALENDAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.ISBN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.PRODUCT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.VIN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.URI.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.TEXT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.EMAIL_ADDRESS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.TEL.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.SMS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.ADDRESSBOOK.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.GEO.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    private void encodeQRCodeContents(QREncode.Builder builder) {
        switch (C05471.$SwitchMap$com$google$zxing$client$result$ParsedResultType[builder.getParsedResultType().ordinal()]) {
            case 1:
                this.encodeBuild.setEncodeContents(builder.getContents());
                break;
            case 2:
                this.encodeBuild.setEncodeContents(builder.getContents());
                break;
            case 3:
                this.encodeBuild.setEncodeContents(builder.getContents());
                break;
            case 4:
                this.encodeBuild.setEncodeContents(builder.getContents());
                break;
            case 5:
                this.encodeBuild.setEncodeContents(builder.getContents());
                break;
            case 6:
                this.encodeBuild.setEncodeContents(builder.getContents());
                break;
            case 7:
                this.encodeBuild.setEncodeContents(builder.getContents());
                break;
            case 8:
                this.encodeBuild.setEncodeContents(MailTo.MAILTO_SCHEME + builder.getContents());
                break;
            case 9:
                this.encodeBuild.setEncodeContents("tel:" + builder.getContents());
                break;
            case 10:
                this.encodeBuild.setEncodeContents("sms:" + builder.getContents());
                break;
            case 11:
                Uri addressBookUri = builder.getAddressBookUri();
                Bundle bundle = addressBookUri != null ? new ParserUriToVCard().parserUri(this.context, addressBookUri) : null;
                if ((bundle != null && bundle.isEmpty()) || bundle == null) {
                    bundle = builder.getBundle();
                }
                if (bundle != null) {
                    String string = bundle.getString("name");
                    String string2 = bundle.getString("company");
                    String string3 = bundle.getString("postal");
                    List<String> allBundleValues = getAllBundleValues(bundle, ParserUriToVCard.PHONE_KEYS);
                    List<String> allBundleValues2 = getAllBundleValues(bundle, ParserUriToVCard.PHONE_TYPE_KEYS);
                    List<String> allBundleValues3 = getAllBundleValues(bundle, ParserUriToVCard.EMAIL_KEYS);
                    String string4 = bundle.getString(ParserUriToVCard.URL_KEY);
                    String[] strArrEncode = (builder.isUseVCard() ? new VCardContactEncoder() : new MECARDContactEncoder()).encode(Collections.singletonList(string), string2, Collections.singletonList(string3), allBundleValues, allBundleValues2, allBundleValues3, string4 != null ? Collections.singletonList(string4) : null, bundle.getString(ParserUriToVCard.NOTE_KEY));
                    if (!strArrEncode[1].isEmpty()) {
                        this.encodeBuild.setEncodeContents(strArrEncode[0]);
                    }
                }
                break;
            case 12:
                Bundle bundle2 = builder.getBundle();
                if (bundle2 != null) {
                    float f = bundle2.getFloat("LAT", Float.MAX_VALUE);
                    float f2 = bundle2.getFloat("LONG", Float.MAX_VALUE);
                    if (f != Float.MAX_VALUE && f2 != Float.MAX_VALUE) {
                        this.encodeBuild.setEncodeContents("geo:" + f + ',' + f2);
                        break;
                    }
                }
                break;
        }
    }
}
