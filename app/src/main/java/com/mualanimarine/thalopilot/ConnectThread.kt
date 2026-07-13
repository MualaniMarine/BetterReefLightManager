package com.mualanimarine.thalopilot

import android.os.Handler
import android.util.Log
import com.mualanimarine.thalopilot.util.CommandUtil
import com.mualanimarine.thalopilot.util.HexStringUtil
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ConnectThread(
    private val ipAddress: String,
    private val port: Int,
    private val handler: Handler
) : Thread("ConnectThread") {
    private val commandQueue = ConcurrentLinkedQueue<ByteArray>()
    private val sendExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private var socket: Socket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null

    override fun run() {
        try {
            socket = Socket(ipAddress, port)
            inputStream = socket?.getInputStream()
            outputStream = socket?.getOutputStream()
            handler.obtainMessage(Constant.MessageTag.CONNECT_ENABLE, ipAddress).sendToTarget()

            val buffer = ByteArray(1024)
            while (!isInterrupted && socket?.isClosed == false) {
                val readSize = inputStream?.read(buffer) ?: -1
                if (readSize <= 0) continue
                val payload = buffer.copyOf(readSize)
                val payloadHex = HexStringUtil.byteArrayToHex(payload).lowercase()
                Log.d(Constant.TAG, "socket recv: $payloadHex")

                if (payloadHex != CommandUtil.MODEL_RESULT.lowercase() && commandQueue.isNotEmpty()) {
                    if (payloadHex.startsWith(CommandUtil.RECEIVE_START_TAG.lowercase())) {
                        commandQueue.poll()
                        handler.obtainMessage(Constant.MessageTag.DEVICE_INFO, payload).sendToTarget()
                    } else {
                        handler.obtainMessage(Constant.MessageTag.COMMAND_RESEND, commandQueue.poll()).sendToTarget()
                    }
                } else {
                    handler.obtainMessage(Constant.MessageTag.DEVICE_INFO, payload).sendToTarget()
                }
            }
        } catch (e: IOException) {
            Log.e(Constant.TAG, "socket connect/read failed", e)
            handler.obtainMessage(Constant.MessageTag.CONNECT_DISABLE, ipAddress).sendToTarget()
        }
    }

    @Synchronized
    fun sendData(payload: ByteArray?) {
        if (payload == null || outputStream == null) return
        sendExecutor.execute {
            try {
                val payloadHex = HexStringUtil.byteArrayToHex(payload).lowercase()
                Log.d(Constant.TAG, "socket send: $payloadHex")
                if (payloadHex == CommandUtil.READ_ALL.lowercase()) {
                    commandQueue.offer(payload)
                }
                outputStream?.write(payload)
                outputStream?.flush()
            } catch (e: IOException) {
                Log.e(Constant.TAG, "socket send failed", e)
                handler.obtainMessage(Constant.MessageTag.CONNECT_DISABLE, ipAddress).sendToTarget()
            }
        }
    }

    @Synchronized
    fun closeQuietly() {
        interrupt()
        sendExecutor.shutdownNow()
        runCatching { inputStream?.close() }
        runCatching { outputStream?.close() }
        runCatching { socket?.close() }
    }
}

