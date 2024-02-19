package com.anthony

import java.io.OutputStream
import java.net.Socket

fun sendTCPHEXStr(remoteHost:String,remotePort:Int,action:String){

    println("sendTCPHEXStr函数被调用")

    val actionStr = action.replace(" ","")
    println(actionStr)
    val outputData: ByteArray = hexStringToByteArray(actionStr)
    Socket(remoteHost, remotePort).use { socket ->
        val outputStream: OutputStream = socket.getOutputStream()
        outputStream.write(outputData)
        outputStream.flush() // 确保数据发送
    }
}