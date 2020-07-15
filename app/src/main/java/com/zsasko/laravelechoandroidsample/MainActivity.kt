package com.zsasko.laravelechoandroidsample

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zsasko.laravelechoandroidsample.model.MessageCreated
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseSocketActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectToSocket()
        initLiveDataListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnectFromSocket()
    }

    private fun initLiveDataListener() {
        receivedEvent.observe(this, Observer {
            displayEventData(it)
        })
    }

    private fun displayEventData(event: Any) {
        if (event is MessageCreated) {
            text_message.apply {
                val newText = event.message + "\n" + this.text.toString()
                text = newText
            }
        }
    }
}