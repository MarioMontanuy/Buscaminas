package com.example.buscaminas

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi

class SoundService : Service() {

    private var bombSound: MediaPlayer? = null
    private var winnerSound: MediaPlayer? = null
    private var gameOverSound: MediaPlayer? = null
    override fun onBind(p0: Intent?): IBinder? {
    }

    override fun onCreate() {
        super.onCreate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.getStringExtra("sound")) {
            "bombSound" -> {
                bombSound = MediaPlayer.create(
                    this,
                    Uri.parse("android.resource://com.example.buscaminas/" + R.raw.bomb_sound)
                )
                bombSound!!.start()
            }
            "winnerSound" -> {
                winnerSound = MediaPlayer.create(
                    this,
                    Uri.parse("android.resource://com.example.buscaminas/" + R.raw.winner_sound)
                )
                winnerSound!!.start()
            }
            "gameOverSound" -> {
                gameOverSound = MediaPlayer.create(
                    this,
                    Uri.parse("android.resource://com.example.buscaminas/" + R.raw.game_over)
                )
                gameOverSound!!.start()
            }
        }
        return startId
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bombSound?.isPlaying == true) {
            bombSound!!.stop()
        }
        if (winnerSound?.isPlaying == true) {
            winnerSound!!.stop()
        }
        if (gameOverSound?.isPlaying == true) {
            gameOverSound!!.stop()
        }
    }
}