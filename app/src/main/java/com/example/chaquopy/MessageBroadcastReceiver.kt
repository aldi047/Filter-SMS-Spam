package com.example.chaquopy

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform


class MessageBroadcastReceiver : BroadcastReceiver() {
    private var sender: String? = null
    private var message: String? = null

    override fun onReceive(context: Context?, intent: Intent) {
        Log.d("Listener jadi null", "null")
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action){
            val data = intent.extras!!.get("pdus") as Array<Object>
            for (i in 0 until data.count()) {
                // getting sms message on below line.
                val smsMessage = SmsMessage.createFromPdu(data[i] as ByteArray)
                sender = smsMessage.displayOriginatingAddress
                message = smsMessage.messageBody
                Log.d("Datakuuu", smsMessage.displayOriginatingAddress + ' ' + smsMessage.messageBody)
            }
            if (!Python.isStarted())
                Python.start(AndroidPlatform(context!!))

            val py = Python.getInstance()
            val pyObj = py.getModule("predict")
            val obj = pyObj.callAttr("main",message)

            val db = DBHelper(context!!, null)
            var id: Int = 0
            if (obj.toInt() == 0){
                id = db.addData(DBHelper.TABLE_HOME, sender!!, message!!)
            } else {
                id = db.addData(DBHelper.TABLE_SPAM, sender!!, message!!)
            }
        }
    }
}