package com.example.testproject.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    var mViewDataBinding: ViewDataBinding? = null
    var throttleBar: SeekBar? = null
    var rudderBar: SeekBar? = null
    var connectButton: Button? = null
    var myJoystick: Joystick? = null
    lateinit var myViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewBinding()
        myJoystick = findViewById<Joystick>(R.id.myJoystickID)
        throttleBar = findViewById<SeekBar>(R.id.throttleSeekBar)
        rudderBar = findViewById<SeekBar>(R.id.rudderSeekBar)
        connectButton = findViewById<Button>(R.id.connectButton)
        connectButton?.setOnClickListener { connectToFlightGear() }
        throttleListener()
        rudderListener()
        joystickListener()
    }

    private fun initializeViewBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        myViewModel = if (!::myViewModel.isInitialized) getViewModel() else myViewModel
        mViewDataBinding?.setVariable(BR.loginviewmodel, myViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    fun getViewModel(): MainViewModel {
        return ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun throttleListener() {
        throttleBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                myViewModel.updateThrottle(progress)
                // write custom code for progress is changed
            }
            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }
            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })
    }


    fun rudderListener() {
        rudderBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                myViewModel.updateRudder(progress)
                // write custom code for progress is changed
            }
            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }
            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })
    }

    @SuppressLint("ShowToast", "SetTextI18n")
    private fun connectToFlightGear() {
        val hideKeyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            myViewModel.connectClicked()
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
        }
    }


    @SuppressLint("ClickableViewAccessibility") fun joystickListener() {
        myJoystick?.setOnTouchListener(
        (View.OnTouchListener { v, e ->
            if (v != null)
                myViewModel.updateAileronAndElevator(myJoystick!!.centerX, myJoystick!!.centerY, myJoystick!!.bigRadius, v.width, v.height)
            v?.onTouchEvent(e) ?: true
        }))
    }



}