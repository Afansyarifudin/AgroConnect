package com.example.agroconnect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

class NetworkConnectivityWatcher(private val context: Context) {

    private var connectivityReceiver: BroadcastReceiver? = null
    private var isConnected = false

    fun startWatchingConnectivity() {
        connectivityReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val isPreviouslyConnected = isConnected
                isConnected = isNetworkAvailable()

                if (isConnected && !isPreviouslyConnected) {
                    showToast("Internet connection is available")
                } else if (!isConnected) {
                    showToast("No internet connection")
                }
            }
        }

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(connectivityReceiver, filter)
    }

    fun stopWatchingConnectivity() {
        connectivityReceiver?.let {
            context.unregisterReceiver(it)
            connectivityReceiver = null
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}

