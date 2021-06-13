package com.example.testproject.view_model

import androidx.lifecycle.ViewModel
import com.example.testproject.model.MainModel


class MainViewModel : ViewModel(){
    var myModel : MainModel = MainModel()
    var iP = ""
    var pORT = ""


    fun updateThrottle(throttleVal : Int) {
        myModel.connectButton = "myModel.connectButton"
        println(throttleVal)
        myModel.updateThrottleModel(throttleVal)
    }

    fun updateRudder(rudderVal: Int) {
        myModel.updateRudderModel(rudderVal)
    }

    fun updateAileronAndElevator(x: Float, y: Float, r: Float, width: Int, height: Int) {
        myModel.updateAileronAndElevatorModel(x, y, r, width, height)
    }

    fun connectClicked() {
            myModel.connectClickedModel(iP, pORT.toInt())
    }

}