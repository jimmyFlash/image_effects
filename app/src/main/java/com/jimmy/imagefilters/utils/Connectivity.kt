package com.jimmy.imagefilters.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build


object Connectivity {

    @Suppress("DEPRECATION")
    fun isConnected(context: Context): Boolean {
        val connMgr =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT > 22 ){

            val networkCapabilities = connMgr.getNetworkCapabilities(connMgr.activeNetwork)
            // NET_CAPABILITY_VALIDATED - Indicates that connectivity on this network was successfully validated.
            // NET_CAPABILITY_INTERNET - Indicates that this network should be able to reach the internet.
            if(networkCapabilities != null
                && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
                return true

        }else{

            val activeNetwork: NetworkInfo? = connMgr.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnected == true
            if(isConnected)  return true


        }
        return false
    }
}