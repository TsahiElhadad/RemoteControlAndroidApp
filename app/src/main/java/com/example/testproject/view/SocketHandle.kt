package com.example.testproject.view

import java.io.PrintWriter
import java.net.Socket
import java.io.Serializable

// SocketHandle object - for passing the Socket and the PrintWriter between the activities
object SocketHandle : Serializable {
    private var out : PrintWriter? = null
    private var socket : Socket? = null

    // socket getter
    fun getSocket() : Socket? {
        return socket
    }

    // socket setter
    fun setSocket(s: Socket) {
        this.socket = s
    }

    // out getter
    fun getPrintWriter() : PrintWriter? {
        return out
    }

    // out setter
    fun setPrintWriter(pw: PrintWriter) {
        this.out = pw
    }
}