package com.mualanimarine.thalopilot

object Constant {
    const val TAG = "ThaloPilot"
    const val DEVICE_PORT = 8266

    object MessageTag {
        const val CONNECT_ENABLE = 1
        const val CONNECT_DISABLE = 2
        const val DEVICE_INFO = 3
        const val CONNECT_SOCKET = 4
        const val COMMAND_RESEND = 5
    }

    object Model {
        const val MODEL_SPS = 1
        const val MODEL_LPS = 2
        const val MODEL_SL = 3
    }

    object Type {
        const val TYPE_K7 = 1
        const val TYPE_X4 = 2
    }

    object Hand {
        val K7_DEFAULT = byteArrayOf(50, 50, 50, 50, 50, 50)
        val X4_DEFAULT = byteArrayOf(50, 50, 50, 0, 0, 0)
    }
}

