package com.zsasko.laravelechoandroidsample

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.zsasko.laravelechoandroidsample.model.MessageCreated
import net.mrbin99.laravelechoandroid.Echo
import net.mrbin99.laravelechoandroid.EchoCallback
import net.mrbin99.laravelechoandroid.EchoOptions

const val SERVER_URL = "http://10.0.2.2:6001"
const val CHANNEL_MESSAGES = "messages"
const val EVENT_MESSAGE_CREATED = "MessageCreated"
const val TAG = "msg"

open class BaseSocketActivity : AppCompatActivity() {

    private var _receivedEvent = MutableLiveData<Any>()
    var receivedEvent = _receivedEvent

    private var echo: Echo? = null

    protected fun connectToSocket() {
        val options = EchoOptions()
        options.host = SERVER_URL

        echo = Echo(options)
        echo?.connect(object : EchoCallback {
            override fun call(vararg args: Any?) {
                log("successful connect")
                listenForEvents()
            }
        }, object : EchoCallback {
            override fun call(vararg args: Any?) {
                log("error while connecting: " + args.toString())
            }
        })
    }

    protected fun listenForEvents() {
        echo?.let {
            it.channel(CHANNEL_MESSAGES)
                .listen(EVENT_MESSAGE_CREATED) {
                    val newEvent = MessageCreated.parseFrom(it)
                    displayNewEvent(newEvent)
                }
        }
    }

    protected fun disconnectFromSocket() {
        echo?.disconnect()
    }

    private fun log(message: String) {
        Log.i(TAG, message)
    }

    private fun displayNewEvent(event: MessageCreated?) {
        log("new event " + event?.message)
        _receivedEvent.postValue(event)
    }
}