package com.example.radionativafm941_piratini

import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var player: SimpleExoPlayer
    lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerView = findViewById(R.id.playerView)

        verifyConnection()
        initializePlayer()
    }

    /*
    Ao tentar conectar a rádio, se após alguns segundos não conseguir, tentar link secundário.
    Se mesmo assim não conseguir se conectar, informar que a rádio está OFFLINE.
     */
    private fun initializePlayer() {
        try {
            player = SimpleExoPlayer.Builder(this).build()
            val streamURL: Uri = Uri.parse(R.string.stream_url.toString())

            // Bind the player to the view.
            playerView.player = player

            // Build the media item.
            val mediaItem: MediaItem = MediaItem.fromUri(streamURL)
            // Set the media item to be played.
            player.setMediaItem(mediaItem)
            // Prepare the player.
            player.prepare()
            // Start the playback.
            player.play()

        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("TAG", "Error : $e")
        }
    }

    private fun verifyConnection() {
        val isNetworkEnable: Boolean
        val locationManager: LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if(isNetworkEnable) {
            Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Not Connected", Toast.LENGTH_LONG).show()
        }

    }

}