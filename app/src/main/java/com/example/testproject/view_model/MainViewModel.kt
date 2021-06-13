package com.example.testproject.view_model

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.testproject.model.MainModel
import java.io.PrintWriter
import java.net.Socket


class MainViewModel : ViewModel(){
    private var myModel : MainModel = MainModel()

    fun initSocketHandler(s: Socket, pw: PrintWriter) {
        myModel.initSocketHandlerModel(s, pw)
    }

    fun updateThrottle(throttleVal : Int) {
        myModel.updateThrottleModel(throttleVal)
    }

    fun updateRudder(rudderVal: Int) {
        myModel.updateRudderModel(rudderVal)
    }

    fun updateAileronAndElevator(x: Float, y: Float, r: Float, width: Int, height: Int) {
        myModel.updateAileronAndElevatorModel(x, y, r, width, height)
    }

    fun closeSocket() {
        myModel.closeSocketModel()
    }
}