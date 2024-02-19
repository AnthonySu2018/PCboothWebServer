package com.anthony


fun hexStringToByteArray(hexString: String): ByteArray {

    val hexStr = hexString.replace(" ","")
    check(hexStr.length % 2 == 0) { "Hex string must have an even number of characters" }

    return hexStr.windowed(2, 2, true)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}

