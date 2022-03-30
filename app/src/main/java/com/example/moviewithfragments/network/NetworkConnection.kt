package com.example.moviewithfragments.network

import android.content.Context
import android.net.ConnectivityManager

    fun Context.isNetworkAvailable(): Boolean {
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo =conManager.activeNetworkInfo
        return internetInfo!=null && internetInfo.isConnected
    }


