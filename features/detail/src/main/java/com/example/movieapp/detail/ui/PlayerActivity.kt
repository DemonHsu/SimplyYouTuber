package com.example.movieapp.detail.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.method.ScrollingMovementMethod
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.movieapp.core.common.ISODate
import com.example.movieapp.core.common.Secrets
import com.example.movieapp.detail.databinding.ActivityPlayerBinding
import com.example.movieapp.presentation.common.PlayerHelper
import com.example.movieapp.presentation.model.MovieUI
import com.google.android.youtube.player.*
import com.google.android.youtube.player.YouTubePlayer.Provider
import io.getstream.avatarview.coil.loadImage
import java.time.format.DateTimeFormatter


open class PlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private lateinit var binding: ActivityPlayerBinding
    private var mAutoRotation = false
    var player: YouTubePlayer? = null
    var key = Secrets().getQBjQeGcZ("com.example.movieapp")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // Initializing video player with developer key
        binding.youtubeView.initialize(key, this)

        val extras = intent.extras
        if (extras != null) {
            extras.getParcelable<MovieUI>(PlayerHelper.VIDEO_KEY)?.let { movie ->
                binding.itemMovieTitle.text = movie.videoTitle
                binding.itemOwnerTitle.text = movie.ownerTitle
                binding.itemMovieOwnerIcon.loadImage(movie.ownerPosterPath)
                binding.itemOverview.movementMethod = ScrollingMovementMethod()
                binding.itemOverview.text = movie.overview

                ISODate.getDate(movie.uploadDate)?.let {
                    binding.itemUpdateTime.text = it.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                }
            }
        }

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
                extras.getParcelable<MovieUI>(PlayerHelper.VIDEO_KEY)?.let {
                   player.loadVideo(it.id)
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
            youTubePlayerProvider.initialize(key, this)
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

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onResume() {
        super.onResume()
        player?.play()
    }

    companion object {
        private const val RECOVERY_REQUEST = 1
    }
}
