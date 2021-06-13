package com.example.testproject.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.databinding.ActivityMainBinding
import com.example.testproject.view_model.FirstScreenViewModel
import com.example.testproject.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private var mViewDataBinding: ViewDataBinding? = null
    private var throttleBar: SeekBar? = null
    private var rudderBar: SeekBar? = null
    private var socketHandle : SocketHandle? = null
    private var myJoystick: Joystick? = null
    private lateinit var myViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewBinding()
        socketHandle = intent.getSerializableExtra("socket") as SocketHandle
        myViewModel.initSocketHandler(socketHandle!!.getSocket()!!, socketHandle!!.getPrintWriter()!!)
        myJoystick = findViewById<Joystick>(R.id.myJoystickID)
        throttleBar = findViewById<SeekBar>(R.id.throttleSeekBar)
        rudderBar = findViewById<SeekBar>(R.id.rudderSeekBar)
        throttleListener()
        rudderListener()
        joystickListener()
    }

    private fun initializeViewBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        myViewModel = if (!::myViewModel.isInitialized) ViewModelProvider(this).get(MainViewModel::class.java) else myViewModel
        mViewDataBinding?.setVariable(BR.loginviewmodel, myViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    private fun throttleListener() {
        throttleBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                myViewModel.updateThrottle(progress)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })
    }


    private fun rudderListener() {
        rudderBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                myViewModel.updateRudder(progress)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })
    }

   @SuppressLint("ClickableViewAccessibility") private fun joystickListener() {
        myJoystick?.setOnTouchListener(
        (View.OnTouchListener { v, e ->
            if (v != null)
                myViewModel.updateAileronAndElevator(myJoystick!!.getCenterX(), myJoystick!!.getCenterY(), myJoystick!!.getBigRadius(), v.width, v.height)
            v?.onTouchEvent(e) ?: true
        }))
    }

    override fun onDestroy() {
        myViewModel.closeSocket()
        super.onDestroy()
    }
}