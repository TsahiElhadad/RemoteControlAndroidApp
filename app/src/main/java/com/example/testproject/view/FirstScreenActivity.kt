package com.example.testproject.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.BoringLayout
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.view_model.FirstScreenViewModel
import com.google.android.material.snackbar.Snackbar


// FirstScreenActivity - where the user is trying to connect to flight fear
class FirstScreenActivity : AppCompatActivity() {

    private var mdb: ViewDataBinding? = null
    private var connectButton: Button? = null
    private var socketHandle = SocketHandle
    private lateinit var myViewModel: FirstScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewBinding() // initialize the data binding
        connectButton = findViewById<Button>(R.id.connectButton)
        connectButton?.setOnClickListener { connectToFlightGear() } // set click listener for button
    }

    // initialize the data binding between the view and the view-model
    private fun initializeViewBinding() {
        mdb = DataBindingUtil.setContentView(this, R.layout.first_screen)
        myViewModel = if (!::myViewModel.isInitialized) ViewModelProvider(this).get(FirstScreenViewModel::class.java) else myViewModel
        mdb?.setVariable(BR.firstscreenviewmodel, myViewModel)
        mdb?.executePendingBindings()
    }

    // when the connect button will be pressed
    private fun connectToFlightGear() {
        try {
            val isProcessSuccess = myViewModel.connectClicked(socketHandle) // trying to connect
            // and isProcessSuccess will hold the data if the connection succeeded or not
            if (isProcessSuccess == true) { // if connection success
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("socket", socketHandle)
                }
                startActivity(intent) // start new activity (MainActivity) and pass the socketHandle
            } else { // if connection failed
                val hideKeyboard: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                if (hideKeyboard.isActive) // hide the keyboard
                    hideKeyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                val snackBar: Snackbar = Snackbar.make(findViewById(R.id.layoutID),"Fail to connect! Check your IP / PORT", Snackbar.LENGTH_LONG)
                snackBar.setTextColor(Color.RED)
                snackBar.show() // pop message that connection failed
            }
        } catch (e : Exception) { // connection failed
            val hideKeyboard: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (hideKeyboard.isActive) // hide the keyboard
                hideKeyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            val snackBar: Snackbar = Snackbar.make(findViewById(R.id.layoutID),"Fail to connect! Check your IP / PORT", Snackbar.LENGTH_LONG)
            snackBar.setTextColor(Color.RED)
            snackBar.show() // pop message that connection failed
        }
    }
}