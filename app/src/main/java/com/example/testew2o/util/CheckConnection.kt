package com.example.testew2o.util

import android.content.Context
import java.util.*

/**
 * Classe crawler utilizada para verificar status de connectividade
 **/
public class CheckConnection(context: Context) : TimerTask() {
    val networkUtils = NetworkUtils()

    private val context: Context
    override fun run() {
        if (networkUtils.isNetworkAvailable(context)) { //CONNECTED
        } else { //DISCONNECTED
        }
    }

    init {
        this.context = context
    }
}