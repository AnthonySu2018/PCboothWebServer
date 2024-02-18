package com.anthony

import com.sioux.anthony.androidapp.homepage.sendUDP
import com.sioux.anthony.androidapp.homepage.sendUDPHEXStr
import org.apache.tomcat.jni.Time

fun powerUpAllDevices() {



    println("机房时序电源可以手动开")
    println("灯光可以手动开")

    println("LED屏电箱开电")
    sendUDPHEXStr("172.18.0.6",5000,"00 00 00 00 00 06 01 05 08 01 FF 00")
    //LED屏电箱开电,代码正确好用
    Thread.sleep(3000)

    println("序厅LED播放盒开")
    sendUDP("172.18.0.33",50505,"172.18.0.33PWRONEND")

    println("Intel Vision LED播放盒开")
    sendUDP("172.18.0.34",50505,"172.18.0.34PWRONEND")

    println("CCG LED播放盒开")
    sendUDP("172.18.0.35",50505,"172.18.0.35PWRONEND")

    println("汽车 LED播放盒开")
    sendUDP("172.18.0.36",50505,"172.18.0.36PWRONEND")

}

