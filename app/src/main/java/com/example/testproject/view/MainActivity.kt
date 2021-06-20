package com.example.testproject.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.view_model.MainViewModel

// MainActivity - where the user is flying the plane with the remote control
class MainActivity : AppCompatActivity() {
    private var mViewDataBinding: ViewDataBinding? = null
    private var throttleBar: SeekBar? = null
    private var rudderBar: SeekBar? = null
    private var socketHandle : SocketHandle? = null
    private var myJoystick: Joystick? = null
    private lateinit var myViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewBinding() // initialize the data binding
        socketHandle = intent.getSerializableExtra("socket") as SocketHandle // get the
        // socketHandle from the firstScreenActivity
        myViewModel.initSocketHandler(socketHandle!!.getSocket()!!, socketHandle!!.getPrintWriter()!!)
        myJoystick = findViewById<Joystick>(R.id.myJoystickID)
        throttleBar = findViewById<SeekBar>(R.id.throttleSeekBar)
        rudderBar = findViewById<SeekBar>(R.id.rudderSeekBar)
        throttleListener() // set listener for throttle
        rudderListener() // set listener for rudder
        joystickListener() // set listener for joystick
    }

    // initialize the data binding between the view and the view-model
    private fun initializeViewBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        myViewModel = if (!::myViewModel.isInitialized) ViewModelProvider(this).get(MainViewModel::class.java) else myViewModel
        mViewDataBinding?.setVariable(BR.loginviewmodel, myViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    // set what to do when the throttle seekBar is moving
    private fun throttleListener() {
        throttleBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) { // seekBar moving
                myViewModel.updateThrottle(progress)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })
    }


    // set what to do when the rudder seekBar is moving
    private fun rudderListener() {
        rudderBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) { // seekBar moving
                myViewModel.updateRudder(progress)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })
    }

    // set what to do when the joystick is touched
   @SuppressLint("ClickableViewAccessibility")
   private fun joystickListener() {
        myJoystick?.setOnTouchListener(
        (View.OnTouchListener { v, e ->
            if (v != null) // if view != null - call updateAileronAndElevator with matching values
                myViewModel.updateAileronAndElevator(myJoystick!!.getCenterX(), myJoystick!!.getCenterY(), myJoystick!!.getBigRadius(), v.width, v.height)
            v?.onTouchEvent(e) ?: true
        }))
   }

    // override - close the socket before destroy
    override fun onDestroy() {
        myViewModel.closeSocket()
        super.onDestroy()
    }
}