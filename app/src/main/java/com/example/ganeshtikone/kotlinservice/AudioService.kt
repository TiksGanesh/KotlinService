package com.example.ganeshtikone.kotlinservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class AudioService : Service() {

    private val TAG:String = AudioService::class.simpleName!!
    private lateinit var mediaPlayer:MediaPlayer

    // static variables and method inside comapnion object
    companion object {
        private var isServiceRunning:Boolean = false

        fun getServiceState():Boolean{
            return isServiceRunning
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"onCreate executed")
        mediaPlayer = MediaPlayer.create(
                applicationContext,
                R.raw.bollywood_song
        )

        mediaPlayer.setOnCompletionListener {
            stopSelf()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG,"onStartCommand executed")
        isServiceRunning = true
        mediaPlayer.start()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        isServiceRunning = false

        if (mediaPlayer.isPlaying){
            mediaPlayer.stop()
            mediaPlayer.release()
        }

        Log.e(TAG,"onDestroy executed")
        super.onDestroy()
    }

}
