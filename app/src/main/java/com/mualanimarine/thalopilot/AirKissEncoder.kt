package com.mualanimarine.thalopilot

import kotlin.random.Random

class AirKissEncoder(
    ssid: String,
    password: String
) {
    private val encodedData = ArrayList<Int>(65536)
    private val randomChar: Char = Random.nextInt(127).toChar()

    init {
        repeat(10) {
            leadingPart()
            magicCode(ssid, password)
            repeat(15) {
                prefixCode(password)
                val payload = password + randomChar + ssid
                val payloadBytes = payload.encodeToByteArray()
                var index = 0
                var sequenceIndex = 0
                while (index + 4 <= payloadBytes.size) {
                    sequence(sequenceIndex++, payloadBytes.copyOfRange(index, index + 4))
                    index += 4
                }
                if (index < payloadBytes.size) {
                    sequence(sequenceIndex, payloadBytes.copyOfRange(index, payloadBytes.size))
                }
            }
        }
    }

    fun getEncodedData(): IntArray = encodedData.toIntArray()

    private fun appendEncodedData(value: Int) {
        encodedData += value
    }

    private fun crc8(data: ByteArray): Int {
        var crc = 0
        for (source in data) {
            var current = source.toInt() and 0xFF
            repeat(8) {
                val mix = (crc xor current) and 0x01
                crc = crc ushr 1
                if (mix != 0) {
                    crc = crc xor 0x8C
                }
                current = current ushr 1
            }
        }
        return crc and 0xFF
    }

    private fun leadingPart() {
        repeat(50) {
            for (value in 1..4) {
                appendEncodedData(value)
            }
        }
    }

    private fun magicCode(ssid: String, password: String) {
        val totalLength = ssid.length + password.length + 1
        val ssidCrc = crc8(ssid.encodeToByteArray())
        val values = intArrayOf(
            ((totalLength ushr 4) and 0x0F).let { if (it == 0) 8 else it },
            (totalLength and 0x0F) or 0x10,
            ((ssidCrc ushr 4) and 0x0F) or 0x20,
            (ssidCrc and 0x0F) or 0x30
        )
        repeat(20) {
            values.forEach(::appendEncodedData)
        }
    }

    private fun prefixCode(password: String) {
        val passwordLength = password.length
        val lengthCrc = crc8(byteArrayOf(passwordLength.toByte()))
        intArrayOf(
            ((passwordLength ushr 4) and 0x0F) or 0x40,
            (passwordLength and 0x0F) or 0x50,
            ((lengthCrc ushr 4) and 0x0F) or 0x60,
            (lengthCrc and 0x0F) or 0x70
        ).forEach(::appendEncodedData)
    }

    private fun sequence(index: Int, payload: ByteArray) {
        val packet = ByteArray(payload.size + 1)
        packet[0] = index.toByte()
        payload.copyInto(packet, destinationOffset = 1)
        appendEncodedData(crc8(packet) or 0x80)
        appendEncodedData(index or 0x80)
        payload.forEach { appendEncodedData((it.toInt() and 0xFF) or 0x80) }
    }
}

