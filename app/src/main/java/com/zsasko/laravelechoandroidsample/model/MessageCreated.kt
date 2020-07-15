package com.zsasko.laravelechoandroidsample.model

import com.google.gson.Gson

class MessageCreated(
    val message: String
) {

    companion object {
        fun parseFrom(value: Array<Any>): MessageCreated? {
            val messageData = value[1] as org.json.JSONObject
            try {
                return Gson().fromJson(messageData.toString(), MessageCreated::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

}