package com.mualanimarine.thalopilot.model

data class TimeLuminance(
    var hour: Int = 0,
    var minute: Int = 0,
    var luminanceValue: ByteArray = byteArrayOf()
) {
    fun luminanceString(): String = luminanceValue.joinToString(", ")
}

