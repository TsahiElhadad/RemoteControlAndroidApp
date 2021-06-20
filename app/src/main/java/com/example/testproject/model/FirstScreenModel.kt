package com.example.testproject.model

import com.example.testproject.view.SocketHandle
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.Callable
import java.util.concurrent.Executors


// Class Model of the FirstScreen Activity
class FirstScreenModel {

    private var out : PrintWriter? = null
    private lateinit var socket :Socket

    // Function that trying to connect to server by ip and port we get and the socketHandle object
    // and doing that in different thread(thread pool of 1 thread) than the main thread.
    fun connectClickedModel(ip: String, port: Int, socketHandle: SocketHandle): Boolean? {
        val executor = Executors.newSingleThreadExecutor()
        return executor!!.submit(Callable {
            return@Callable try {
                socket = Socket()
                socket.connect(InetSocketAddress(ip, port), 2000)
                out = PrintWriter(socket.getOutputStream(), true)
                socketHandle.setSocket(socket)
                socketHandle.setPrintWriter(out!!)
                true
            } catch (e: Exception) {
                false
            }
        }).get()
    }
}