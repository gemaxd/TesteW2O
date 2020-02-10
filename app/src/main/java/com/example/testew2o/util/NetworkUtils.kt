package com.example.testew2o.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Classe crawler para verificação do status de conectividade
 * */
class NetworkUtils {

    public fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}