package com.example.testproject.view_model

import androidx.lifecycle.ViewModel
import com.example.testproject.model.MainModel
import java.io.PrintWriter
import java.net.Socket

// Class of ViewModel MainActivity of the joystick window
class MainViewModel : ViewModel(){
    private var myModel : MainModel = MainModel()

    // initial the socket handler class
    fun initSocketHandler(s: Socket, pw: PrintWriter) {
        myModel.initSocketHandlerModel(s, pw)
    }

    // Function that update the Throttle values in the model
    fun updateThrottle(throttleVal : Int) {
        myModel.updateThrottleModel(throttleVal)
    }

    // Function that update the Rudder values in the model
    fun updateRudder(rudderVal: Int) {
        myModel.updateRudderModel(rudderVal)
    }

    // Function that update the Aileron and Elevator values in the model
    fun updateAileronAndElevator(x: Float, y: Float, r: Float, width: Int, height: Int) {
        myModel.updateAileronAndElevatorModel(x, y, r, width, height)
    }

    // Function that close the socket
    fun closeSocket() {
        myModel.closeSocketModel()
    }
}