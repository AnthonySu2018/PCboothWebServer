package com.anthony


fun hexStringToByteArray(hexString: String): ByteArray {
    check(hexString.length % 2 == 0) { "Hex string must have an even number of characters" }

    return hexString.toLowerCase().windowed(2, 2, true)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}

