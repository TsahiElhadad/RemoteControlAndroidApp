package com.example.testproject.view_model

import android.annotation.SuppressLint
import android.widget.Button
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.testproject.model.MainModel
import java.io.PrintWriter
import java.net.Socket


//class MainViewModel(b: Button?) : ViewModel(), Observable{
class MainViewModel : ViewModel(){
    var myModel : MainModel = MainModel()
    var iP : ObservableField<String> = ObservableField<String>("")
    var pORT : ObservableField<String> = ObservableField<String>("")
//    var statusConnect : ObservableField<String> = ObservableField<String>("connect")
//    var button : ObservableField<Button> = ObservableField()


    fun initSocketHandler(s: Socket, pw: PrintWriter) {
        myModel.initSocketHandlerModel(s, pw)
    }

    @SuppressLint("SetTextI18n")
    fun updateThrottle(throttleVal : Int) {
        myModel.updateThrottleModel(throttleVal)
    }

    fun updateRudder(rudderVal: Int) {
        myModel.updateRudderModel(rudderVal)
    }

    fun updateAileronAndElevator(x: Float, y: Float, r: Float, width: Int, height: Int) {
        myModel.updateAileronAndElevatorModel(x, y, r, width, height)
    }

    fun connectClicked() {
            myModel.connectClickedModel(iP.get()!!, pORT.get()!!.toInt())
    }
}