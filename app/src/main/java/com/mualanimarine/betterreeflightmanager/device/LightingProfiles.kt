package com.mualanimarine.betterreeflightmanager.device

import com.mualanimarine.betterreeflightmanager.Constant
import com.mualanimarine.betterreeflightmanager.model.TimeLuminance

fun defaultHandValues(type: Int): ByteArray {
    return if (type == Constant.Type.TYPE_K7) {
        Constant.Hand.K7_DEFAULT.copyOf()
    } else {
        Constant.Hand.X4_DEFAULT.copyOf()
    }
}

fun defaultSchedule(type: Int, model: Int): List<TimeLuminance> {
    return (0..23).map { hour ->
        TimeLuminance(
            hour = hour,
            minute = 0,
            luminanceValue = defaultScheduleValues(type, model, hour)
        )
    }
}

private fun defaultScheduleValues(type: Int, model: Int, hour: Int): ByteArray {
    return when (model) {
        Constant.Model.MODEL_LPS -> defaultLpsValues(type, hour)
        Constant.Model.MODEL_SL -> defaultSlValues(type, hour)
        else -> defaultSpsValues(type, hour)
    }
}

private fun defaultSpsValues(type: Int, hour: Int): ByteArray {
    return when (hour) {
        9 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(30, 30, 30, 30, 30, 30) else byteArrayOf(30, 30, 30, 0, 0, 0)
        10 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(50, 50, 50, 50, 50, 50) else byteArrayOf(50, 50, 50, 0, 0, 0)
        in 11..18 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(95, 95, 95, 95, 95, 95) else byteArrayOf(95, 95, 95, 0, 0, 0)
        19 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(50, 50, 50, 50, 50, 50) else byteArrayOf(50, 50, 50, 0, 0, 0)
        20 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(30, 30, 30, 30, 30, 30) else byteArrayOf(30, 30, 30, 0, 0, 0)
        else -> byteArrayOf(0, 0, 0, 0, 0, 0)
    }
}

private fun defaultLpsValues(type: Int, hour: Int): ByteArray {
    return when (hour) {
        9 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(0, 10, 0, 3, 10, 0) else byteArrayOf(0, 10, 0, 0, 0, 0)
        10 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(1, 30, 20, 5, 30, 0) else byteArrayOf(1, 30, 20, 0, 0, 0)
        in 11..18 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(5, 80, 50, 10, 80, 0) else byteArrayOf(5, 80, 50, 0, 0, 0)
        19 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(1, 30, 20, 5, 30, 0) else byteArrayOf(1, 30, 20, 0, 0, 0)
        20 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(0, 10, 0, 3, 10, 0) else byteArrayOf(0, 10, 0, 0, 0, 0)
        else -> byteArrayOf(0, 0, 0, 0, 0, 0)
    }
}

private fun defaultSlValues(type: Int, hour: Int): ByteArray {
    return when (hour) {
        9 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(5, 20, 20, 20, 20, 0) else byteArrayOf(5, 20, 20, 0, 0, 0)
        10 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(10, 40, 40, 40, 40, 5) else byteArrayOf(10, 40, 40, 0, 0, 0)
        in 11..18 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(30, 90, 90, 70, 90, 20) else byteArrayOf(30, 90, 90, 0, 0, 0)
        19 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(10, 40, 40, 40, 40, 5) else byteArrayOf(10, 40, 40, 0, 0, 0)
        20 -> if (type == Constant.Type.TYPE_K7) byteArrayOf(5, 20, 20, 20, 20, 0) else byteArrayOf(5, 20, 20, 0, 0, 0)
        else -> byteArrayOf(0, 0, 0, 0, 0, 0)
    }
}

data class DeviceSnapshot(
    val handValues: ByteArray,
    val schedule: List<TimeLuminance>,
    val autoMode: Boolean,
    val deviceName: String?
)

fun parseDeviceSnapshot(payload: ByteArray): DeviceSnapshot? {
    if (payload.size < 204) return null
    val handValues = payload.copyOfRange(4, 10)
    val schedule = mutableListOf<TimeLuminance>()
    var cursor = 11
    repeat(24) {
        if (cursor + 8 > payload.size) return null
        schedule += TimeLuminance(
            hour = payload[cursor].toInt() and 0xFF,
            minute = payload[cursor + 1].toInt() and 0xFF,
            luminanceValue = payload.copyOfRange(cursor + 2, cursor + 8)
        )
        cursor += 8
    }
    val autoMode = payload[203].toInt() == 1
    val deviceName = if (payload.size >= 215) {
        payload.copyOfRange(204, 215)
            .toString(Charsets.UTF_8)
            .trim('\u0000', ' ', '\n', '\r', '\t')
            .ifBlank { null }
    } else {
        null
    }
    return DeviceSnapshot(
        handValues = handValues,
        schedule = schedule,
        autoMode = autoMode,
        deviceName = deviceName
    )
}

