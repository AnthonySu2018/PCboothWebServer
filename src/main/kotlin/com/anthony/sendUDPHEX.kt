package com.sioux.anthony.androidapp.homepage



import com.anthony.hexStringToByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.IOError
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress



fun sendUDPHEX(remoteHost:String,remotePort:Int,action:String){
    println("sendUDPHEX函数被调用")
    val socket = DatagramSocket()

    val remoteIP = InetAddress.getByName(remoteHost)

    val actionStr = action.replace(" ","")
    println(actionStr)

    val outputData: ByteArray = hexStringToByteArray(actionStr)


    val stringBuilder = StringBuilder()
    for(i in outputData.indices){stringBuilder.append(outputData[i])}
    println(stringBuilder.toString())


    try {

        runBlocking {
            launch(Dispatchers.IO) {
                val outputPacket = DatagramPacket(outputData, outputData.size, remoteIP, remotePort)

                socket.send(outputPacket)
                println( "数据已发送")


            }


        }




    } catch (_:IOException){
    }finally{
        socket.close();
    }


    /*

        val ipObject= InetAddress.getByName(remoteHost)
        Log.d("button", ipObject.hostAddress)

        Log.d("button", remotePort.toString())

        val bytes = action.toByteArray()

        val stringBuilder = StringBuilder()
        for(i in bytes.indices){stringBuilder.append(bytes[i])}
        Log.d("button", stringBuilder.toString())
    */






}