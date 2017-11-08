package com.example.ganeshtikone.kotlinservice

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupOnClickListener()
    }

    /**
     * Setup ClickListener on buttons
     */
    private fun setupOnClickListener() {

        buttonStartService.setOnClickListener(View.OnClickListener {
            if (AudioService.getServiceState()){
                stopAudioService()
                buttonStartService.text = getString(R.string.button_start_service);
            }else{
                startAudioService()
                buttonStartService.text = getString(R.string.button_stop_service);
            }
        })
    }

    /**
     * Start Service
     */
    private fun startAudioService() {

        val audioServiceIntent = Intent(this,AudioService::class.java)
        startService(audioServiceIntent)
    }

    /**
     * Stop Service
     */
    private fun stopAudioService(){
        val audioServiceIntent = Intent(this,AudioService::class.java)
        stopService(audioServiceIntent)

    }
}
