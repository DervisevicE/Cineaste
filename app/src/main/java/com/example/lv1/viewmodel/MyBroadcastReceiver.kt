package com.example.lv1.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetwork
        if(cm.getNetworkCapabilities(netInfo)!=null)
            Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(context, "Disconnecter", Toast.LENGTH_LONG).show()
    }
}
