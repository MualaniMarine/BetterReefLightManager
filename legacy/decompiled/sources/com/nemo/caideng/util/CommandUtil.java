package com.nemo.caideng.util;

import com.nemo.caideng.model.TimeLuminance;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CommandUtil {
    public static String ALL_SET = "1007";
    public static String CHANGE_MODEL = "1004";
    public static String DEMONSTRATION = "100A";
    public static String FUNCTION_SYNC_TIME = "1003";
    public static String HAND_MODEL_LUMINANCE = "1005";
    public static String MODEL_RESULT = "ABAAA5A1";
    public static String PASS_SET = "1009";
    public static String PREVIEW_MODEL_LUMINANCE = "1006";
    public static String RECEIVE_START_TAG = "ABAA";
    public static String TIME_NUM = "18";
    public static String START_TAG = "AAA5";
    public static String ALL_READ = "1008";
    public static String END_TAG = "BB";
    public static String READ_ALL = START_TAG + ALL_READ + END_TAG;

    public static byte[] syncTime(byte b, byte b2, byte b3) {
        return HexStringUtil.hexToByteArray(START_TAG + FUNCTION_SYNC_TIME + HexStringUtil.byteArrayToHex(new byte[]{b, b2, b3}) + END_TAG);
    }

    public static byte[] changeModel(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(START_TAG);
        sb.append(CHANGE_MODEL);
        sb.append(z ? "01" : "00");
        sb.append(END_TAG);
        return HexStringUtil.hexToByteArray(sb.toString());
    }

    public static byte[] handModelLuminance(byte[] bArr) {
        return HexStringUtil.hexToByteArray(START_TAG + HAND_MODEL_LUMINANCE + HexStringUtil.byteArrayToHex(bArr) + END_TAG);
    }

    public static byte[] previewModelLuminance(byte[] bArr) {
        return HexStringUtil.hexToByteArray(START_TAG + PREVIEW_MODEL_LUMINANCE + HexStringUtil.byteArrayToHex(bArr) + END_TAG);
    }

    public static byte[] allSet(byte b, byte b2, byte b3, byte[] bArr, List<TimeLuminance> list, boolean z) {
        byte[] bArr2 = {b, b2, b3};
        StringBuilder sb = new StringBuilder();
        String strByteArrayToHex = HexStringUtil.byteArrayToHex(bArr2);
        sb.append(START_TAG + ALL_SET + HexStringUtil.byteArrayToHex(bArr) + TIME_NUM);
        byte[] bArr3 = new byte[8];
        for (TimeLuminance timeLuminance : list) {
            bArr3[0] = (byte) timeLuminance.getHour();
            bArr3[1] = (byte) timeLuminance.getMinute();
            for (int i = 0; i < timeLuminance.getLuminanceValue().length; i++) {
                bArr3[i + 2] = timeLuminance.getLuminanceValue()[i];
            }
            sb.append(HexStringUtil.byteArrayToHex(bArr3));
        }
        sb.append(z ? "01" : "00");
        sb.append(strByteArrayToHex);
        sb.append(END_TAG);
        return HexStringUtil.hexToByteArray(sb.toString());
    }

    public static byte[] allRead() {
        return HexStringUtil.hexToByteArray(START_TAG + ALL_READ + END_TAG);
    }

    public static byte[] openDemonstration(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(START_TAG);
        sb.append(DEMONSTRATION);
        sb.append(z ? "00" : "01");
        sb.append(END_TAG);
        return HexStringUtil.hexToByteArray(sb.toString());
    }
}
