package com.espinas.raman.HelperAndUtils

import android.util.Log
import com.myapps.testapp.utils.APP_TAG
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory


/**
 * Send a ping to googles primary DNS.
 * If successful, that means we have internet.
 */
object DoesNetworkHaveInternet {

    // Make sure to execute this on a background thread.
    fun execute(socketFactory: SocketFactory): Boolean {
        return try{
            Log.d(APP_TAG, "PINGING google.")
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Log.d(APP_TAG, "PING success.")
            true
        }catch (e: IOException){
            Log.e(APP_TAG, "No internet connection. ${e}")
            false
        }
    }
}