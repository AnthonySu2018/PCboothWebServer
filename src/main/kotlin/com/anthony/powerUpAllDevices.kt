package com.anthony

import com.sioux.anthony.androidapp.homepage.sendUDP
import com.sioux.anthony.androidapp.homepage.sendUDPHEX
import org.apache.tomcat.jni.Time

fun powerUpAllDevices() {

    println("机房时序电源开")
    sendUDPHEX("172.18.0.71",40004,"55 01 A4 00 00 A5")
    //机房时序电源开
    Thread.sleep(5000)

/*  正确好用
    println("LED屏电箱开电")
    sendUDPHEX("172.18.0.6",5000,"00 00 00 00 00 06 01 05 08 01 FF 00")
    //LED屏电箱开电
    Thread.sleep(5000)

    */



}
