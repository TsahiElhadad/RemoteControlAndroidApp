package com.example.testproject.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.view_model.FirstScreenViewModel
import com.google.android.material.snackbar.Snackbar
import java.io.PrintWriter
import java.io.Serializable
import java.lang.Exception
import java.net.Socket


object SocketHandle : Serializable {
    var out : PrintWriter? = null
    var socket : Socket? = null

    @JvmName("getSocket1")
    fun getSocket() : Socket? {
        return socket
    }

    @JvmName("setSocket1")
    fun setSocket(s: Socket) {
        this.socket = s
    }

    fun getPrintWriter() : PrintWriter? {
        println("getPrintWriter")
        println(out)
        return out
    }

    fun setPrintWriter(pw: PrintWriter) {
        this.out = pw
        println("setPrintWriter")
        println(out)
    }
}



class FirstScreenActivity : AppCompatActivity() {

    var mdb: ViewDataBinding? = null
    var connectButton: Button? = null
    var socketHandle = SocketHandle
    lateinit var myViewModel: FirstScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewBinding()
//        mdb = DataBindingUtil.setContentView(this, R.layout.first_screen)
//        myViewModel = if (!::myViewModel.isInitialized) ViewModelProvider(this).get(FirstScreenViewModel::class.java) else myViewModel
//        mdb?.setVariable(BR.firstscreenviewmodel, myViewModel)
        connectButton = findViewById<Button>(R.id.connectButton)
        connectButton?.setOnClickListener { connectToFlightGear() }
    }

    private fun initializeViewBinding() {
        mdb = DataBindingUtil.setContentView(this, R.layout.first_screen)
        myViewModel = if (!::myViewModel.isInitialized) ViewModelProvider(this).get(FirstScreenViewModel::class.java) else myViewModel
        mdb?.setVariable(BR.firstscreenviewmodel, myViewModel)
        mdb?.executePendingBindings()
    }

    @SuppressLint("ShowToast", "SetTextI18n")
    private fun connectToFlightGear() {
        val hideKeyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            println("insideeee")
            myViewModel.connectClicked(socketHandle)
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("socket", socketHandle)
            }
            startActivity(intent)
            connectButton?.text = "disconnect"
            val x = this.currentFocus
            hideKeyboard.hideSoftInputFromWindow(x?.windowToken, 0)
            val snackBar : Snackbar = Snackbar.make(findViewById(R.id.layoutID), "Status:       Connected", Snackbar.LENGTH_LONG)
            snackBar.setTextColor(Color.GREEN)
            snackBar.show()
        }
        catch (e : Exception) {
            println("Error in connect to server MAIN ACTIVITY")


            val x = this.currentFocus
            hideKeyboard.hideSoftInputFromWindow(x?.windowToken, 0)
            val snackBar : Snackbar = Snackbar.make(findViewById(R.id.layoutID), "Status:       Disconnected", Snackbar.LENGTH_LONG)
            snackBar.setTextColor(Color.RED)
            snackBar.show()

//            Toast.makeText(findViewById(R.id.layoutID), "connection failed", Toast.LENGTH_LONG).show()
        }
    }

}