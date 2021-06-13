package com.example.testproject.model

import com.example.testproject.view.SocketHandle
import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.Executors

class FirstScreenModel {

    var out : PrintWriter? = null
    lateinit var socket :Socket

    fun connectClickedModel(ip: String, port: Int, socketHandle: SocketHandle) {
        Executors.newSingleThreadExecutor().execute {
            println(ip)
            println(port)
//        val threadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(threadPolicy)
            socket = Socket(ip, port)
            out = PrintWriter(socket.getOutputStream(), true)
            socketHandle.setSocket(socket)
            socketHandle.setPrintWriter(out!!)
            println("11111111111111")
        }

    }
}