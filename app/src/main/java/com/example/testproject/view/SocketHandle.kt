package com.example.testproject.view

import java.io.PrintWriter
import java.net.Socket
import java.io.Serializable


object SocketHandle : Serializable {
    private var out : PrintWriter? = null
    private var socket : Socket? = null


    fun getSocket() : Socket? {
        return socket
    }

    fun setSocket(s: Socket) {
        this.socket = s
    }

    fun getPrintWriter() : PrintWriter? {
        return out
    }

    fun setPrintWriter(pw: PrintWriter) {
        this.out = pw
    }
}