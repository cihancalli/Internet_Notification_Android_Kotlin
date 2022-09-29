package com.zerdasoftware.internetnotification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zerdasoftware.internetnotification.Constants.DEVICE_TOKEN
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_send.setOnClickListener {
            val title: String = eTxt_title.text.toString().trim()
            val message: String = eTxt_message.text.toString().trim()
            if (title != "" && message != "") {
                FCMSend().pushNotification(this@MainActivity,DEVICE_TOKEN,title,message)
            }
        }
    }
}