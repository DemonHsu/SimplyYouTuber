package com.example.movieapp.detail.ui

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.movieapp.detail.databinding.ActivityPlayerBinding
import com.example.movieapp.presentation.common.PlayerHelper
import com.google.android.youtube.player.*
import com.google.android.youtube.player.YouTubePlayer.Provider;

open class PlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private lateinit var binding: ActivityPlayerBinding
    private var mAutoRotation = false
    var player: YouTubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initializing video player with developer key
        binding.youtubeView.initialize("AIzaSyCOUruZsAbuxAiUTxZdRMq8-y7XG0Gj_K4", this)

        mAutoRotation = Settings.System.getInt(
            contentResolver,
            Settings.System.ACCELEROMETER_ROTATION, 0
        ) === 1
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun onInitializationSuccess(provider: Provider?, player: YouTubePlayer, wasRestored: Boolean) {
         this.player = player

        if (mAutoRotation) {
            player.addFullscreenControlFlag(
                YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                        or YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                        or YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE
                        or YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT
            )
        } else {
           player.addFullscreenControlFlag(
                (YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                        or YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                        or YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT)
            )
        }

        player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
        if (!wasRestored) {
            val extras = intent.extras
            if (extras != null) {
                extras.getString(PlayerHelper.VIDEO_KEY)?.let {
                   player.loadVideo(it)
                }
            }

        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, errorReason: YouTubeInitializationResult) {
        if (errorReason.isUserRecoverableError) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show()
        } else {
            // String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            youTubePlayerProvider.initialize("AIzaSyCOUruZsAbuxAiUTxZdRMq8-y7XG0Gj_K4", this)
        }
    }

    private val youTubePlayerProvider: YouTubePlayer.Provider
        private get() = binding.youtubeView


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }


    companion object {
        private const val RECOVERY_REQUEST = 1
    }
}
