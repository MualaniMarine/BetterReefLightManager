package com.mualanimarine.thalopilot.util

object HexStringUtil {
    fun hexToByteArray(value: String): ByteArray {
        return ByteArray(value.length / 2) { index ->
            value.substring(index * 2, index * 2 + 2).toInt(16).toByte()
        }
    }

    fun byteArrayToHex(value: ByteArray): String {
        val builder = StringBuilder()
        value.forEach { current ->
            builder.append(((current.toInt() shr 4) and 0xF).toString(16))
            builder.append((current.toInt() and 0xF).toString(16))
        }
        return builder.toString()
    }
}

