package com.example.testproject.model


import androidx.databinding.BaseObservable
import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// Class Model of the Main Activity joystick window
class MainModel : BaseObservable() {
    private var out : PrintWriter? = null
    private var executor :ExecutorService? = null
    private var socket :Socket? = null

    // Constructor that initial thread pool of 1 thread.
    init {
        executor = Executors.newSingleThreadExecutor()
    }

    // Initial the Socket and printWriter
    fun initSocketHandlerModel(s: Socket, pw: PrintWriter) {
        socket = s
        out = pw
    }

    // Function that write to FlightGear server new value of Throttle.
    fun updateThrottleModel(value :Int) {
        executor?.execute {
            val v = (value / 10000f)
            out?.print("set /controls/engines/current-engine/throttle $v\r\n")
            out?.flush()
        }
    }

    // Function that write to FlightGear server new value of Rudder.
    fun updateRudderModel(value :Int) {
        executor?.execute {
            val v = (value / 10000f) - 1
            out?.print("set /controls/flight/rudder $v\r\n")
            out?.flush()
        }
    }

    // Function that write to FlightGear server new value of Aileron and Elevator.
    fun updateAileronAndElevatorModel(x: Float, y: Float, radius: Float, width: Int, height: Int) {
        executor?.execute {
            var aileron = (x - (width / 2.0f)) / radius
            var elevator = ((height / 2.0f) - y) / radius
            if (aileron > 1)
                aileron = 1.0f
            else if (aileron < -1)
                aileron = -1.0f
            if (elevator > 1)
                elevator = 1.0f
            else if (elevator < -1)
                elevator = -1.0f
            out?.print("set /controls/flight/aileron $aileron\r\n")
            out?.flush()
            out?.print("set /controls/flight/elevator $elevator\r\n")
            out?.flush()
        }
    }

    // Function that close the socket we open with flightGear server.
    fun closeSocketModel() {
        if(!socket?.isClosed!!) {
            socket!!.close()
        }
    }
}