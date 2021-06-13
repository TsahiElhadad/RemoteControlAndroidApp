package com.example.testproject.view_model

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.testproject.model.FirstScreenModel
import com.example.testproject.view.SocketHandle

class FirstScreenViewModel : ViewModel(){
    private var myModel : FirstScreenModel = FirstScreenModel()

    var iP : ObservableField<String> = ObservableField<String>("")
    var pORT : ObservableField<String> = ObservableField<String>("")

    fun connectClicked(socketHandle: SocketHandle) : Boolean? {
        return myModel.connectClickedModel(iP.get()!!, pORT.get()!!.toInt(), socketHandle)
    }
}