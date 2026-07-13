package com.mualanimarine.thalopilot.util

import com.mualanimarine.thalopilot.model.TimeLuminance

object CommandUtil {
    const val START_TAG = "AAA5"
    const val END_TAG = "BB"
    const val RECEIVE_START_TAG = "ABAA"
    const val FUNCTION_SYNC_TIME = "1003"
    const val CHANGE_MODEL = "1004"
    const val HAND_MODEL_LUMINANCE = "1005"
    const val PREVIEW_MODEL_LUMINANCE = "1006"
    const val ALL_SET = "1007"
    const val ALL_READ = "1008"
    const val PASS_SET = "1009"
    const val DEMONSTRATION = "100A"
    const val TIME_NUM = "18"
    const val MODEL_RESULT = "ABAAA5A1"
    const val READ_ALL = START_TAG + ALL_READ + END_TAG

    fun syncTime(hour: Byte, minute: Byte, second: Byte): ByteArray {
        return HexStringUtil.hexToByteArray(
            START_TAG + FUNCTION_SYNC_TIME + HexStringUtil.byteArrayToHex(byteArrayOf(hour, minute, second)) + END_TAG
        )
    }

    fun changeModel(autoMode: Boolean): ByteArray {
        val modeFlag = if (autoMode) "01" else "00"
        return HexStringUtil.hexToByteArray(START_TAG + CHANGE_MODEL + modeFlag + END_TAG)
    }

    fun handModelLuminance(value: ByteArray): ByteArray {
        return HexStringUtil.hexToByteArray(START_TAG + HAND_MODEL_LUMINANCE + HexStringUtil.byteArrayToHex(value) + END_TAG)
    }

    fun previewModelLuminance(value: ByteArray): ByteArray {
        return HexStringUtil.hexToByteArray(START_TAG + PREVIEW_MODEL_LUMINANCE + HexStringUtil.byteArrayToHex(value) + END_TAG)
    }

    fun allSet(
        hour: Byte,
        minute: Byte,
        second: Byte,
        handValue: ByteArray,
        schedule: List<TimeLuminance>,
        autoMode: Boolean
    ): ByteArray {
        val builder = StringBuilder()
        builder.append(START_TAG)
            .append(ALL_SET)
            .append(HexStringUtil.byteArrayToHex(handValue))
            .append(TIME_NUM)

        val row = ByteArray(8)
        schedule.forEach { item ->
            row[0] = item.hour.toByte()
            row[1] = item.minute.toByte()
            row.fill(0.toByte(), 2, row.size)
            item.luminanceValue.forEachIndexed { index, value ->
                if (index < 6) row[index + 2] = value
            }
            builder.append(HexStringUtil.byteArrayToHex(row))
        }

        builder.append(if (autoMode) "01" else "00")
            .append(HexStringUtil.byteArrayToHex(byteArrayOf(hour, minute, second)))
            .append(END_TAG)

        return HexStringUtil.hexToByteArray(builder.toString())
    }

    fun allRead(): ByteArray = HexStringUtil.hexToByteArray(READ_ALL)

    fun openDemonstration(open: Boolean): ByteArray {
        val demoFlag = if (open) "00" else "01"
        return HexStringUtil.hexToByteArray(START_TAG + DEMONSTRATION + demoFlag + END_TAG)
    }
}

