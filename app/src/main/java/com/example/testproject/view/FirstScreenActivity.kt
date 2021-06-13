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



class FirstScreenActivity : AppCompatActivity() {

    private var mdb: ViewDataBinding? = null
    private var connectButton: Button? = null
    private var socketHandle = SocketHandle
    private lateinit var myViewModel: FirstScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewBinding()
        connectButton = findViewById<Button>(R.id.connectButton)
        connectButton?.setOnClickListener { connectToFlightGear() }
    }

    private fun initializeViewBinding() {
        mdb = DataBindingUtil.setContentView(this, R.layout.first_screen)
        myViewModel = if (!::myViewModel.isInitialized) ViewModelProvider(this).get(FirstScreenViewModel::class.java) else myViewModel
        mdb?.setVariable(BR.firstscreenviewmodel, myViewModel)
        mdb?.executePendingBindings()
    }


    private fun connectToFlightGear() {
        try {
            val isProcessSuccess = myViewModel.connectClicked(socketHandle)
            if (isProcessSuccess == true) {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("socket", socketHandle)
                }
                startActivity(intent)
            } else {
                val hideKeyboard: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                if (hideKeyboard.isActive)
                    hideKeyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                val snackBar: Snackbar = Snackbar.make(findViewById(R.id.layoutID),"Fail to connect! Check your IP / PORT", Snackbar.LENGTH_LONG)
                snackBar.setTextColor(Color.RED)
                snackBar.show()
            }
        } catch (e : Exception) {
            val hideKeyboard: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (hideKeyboard.isActive)
                hideKeyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            val snackBar: Snackbar = Snackbar.make(findViewById(R.id.layoutID),"Fail to connect! Check your IP / PORT", Snackbar.LENGTH_LONG)
            snackBar.setTextColor(Color.RED)
            snackBar.show()
        }
    }
}