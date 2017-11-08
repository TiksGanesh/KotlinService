package com.example.ganeshtikone.kotlinservice

import android.app.IntentService
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.MalformedURLException
import java.net.URL


/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * TODO: Customize class - update intent actions and extra parameters.
 */
class ImageDownloadService : IntentService("ImageDownloadService") {

    private val LOG_TAG: String = ImageDownloadService::class.simpleName!!

    // static variables and method inside comapnion object
    companion object {
        private var isActivityVisible:Boolean = false

        fun setServiceState(flag:Boolean){
            isActivityVisible = flag
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        if (null != intent){
            val mediaUrl = intent.getStringExtra("media_url")
            val imageBitmap = downloadImageFrom(mediaUrl)

            if (isActivityVisible){
                // return data to UI thread
            }
        }
    }

    /**
     * Download Image from server
     *
     */
    fun downloadImageFrom(url:String):Bitmap? {

        var bitmap: Bitmap? = null

        if (isActivityVisible){
            val inputStream = setupConnection(url)
            if (null != inputStream && isActivityVisible){
                bitmap = BitmapFactory.decodeStream(inputStream)
            }

        }
        return bitmap
    }


    /**
     * Setup Connection with remote server
     */
    private fun setupConnection(url: String):InputStream? {

        var inputStream:InputStream? = null

        try {
            val mediaUrl = URL(url)
            val connection:HttpURLConnection = mediaUrl.openConnection() as HttpURLConnection

            // set header
            connection.requestMethod = "GET"
            connection.connectTimeout = 10*60*60
            connection.readTimeout = 10*60*60
            connection.doInput = true
            connection.doOutput = true

            // connect with remote server
            connection.connect()

            // Get Response Code
            val responseCodeFromRequest:Int = connection.responseCode

            // Return input stream
            if (HTTP_OK == responseCodeFromRequest){
                inputStream =  connection.inputStream
                connection.disconnect()
            }
        }catch (ioException:IOException){
            Log.e(LOG_TAG,ioException.localizedMessage)
        }catch (malformUrlException:MalformedURLException){
            Log.e(LOG_TAG,malformUrlException.localizedMessage)
        }finally {
            if (null != inputStream){
                inputStream.close()

            }
        }
        return inputStream
    }
}
