package com.ondev.getthiefsms.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import com.ondev.getthiefsms.data.entity.PhoneNumberData
import com.ondev.getthiefsms.data.repository.PhoneRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class SmsBroadcast : BroadcastReceiver(), CoroutineScope {
    private val pduType = "pdus"

    @Inject
    lateinit var smsRepository: PhoneRepository

    override fun onReceive(context: Context, intent: Intent) {

        // Get the SMS message.
        val bundle = intent.extras
        val msgs: Array<SmsMessage?>
        val format = bundle!!.getString("format")
        // Retrieve the SMS message received.
        val pdus = bundle[pduType] as Array<*>?
        Log.d("RECEIVE_MESSAGE","RECEIVED")
        when {
            !pdus.isNullOrEmpty() -> {
                // Check the Android version.
                // Fill the msgs array.
                msgs = arrayOfNulls(pdus.size)
                msgs.indices.forEach { i ->
                    // Check Android version and use appropriate createFromPdu.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // If Android version M or newer:
                        msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray, format)
                    } else {
                        // If Android version L or older:
                        msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                    }

                    val isValid = msgs[i]!!.messageBody.startsWith("th13f")

                    if (isValid) {
                        Log.d("RECEIVE_MESSAGE","IS VALID")
                        val phoneValid = PhoneNumberData(msgs[i]!!.originatingAddress.toString())
                        launch {
                            smsRepository.insert(phoneValid)
                        }
                    }
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}