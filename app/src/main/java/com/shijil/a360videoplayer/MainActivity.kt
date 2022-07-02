package com.shijil.a360videoplayer

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (player_view.videoSurfaceView as SphericalGLSurfaceView)
            .setDefaultStereoMode(C.STEREO_MODE_TOP_BOTTOM)
    }

    override fun onStart() {
        super.onStart()

        initializePlayer()

    }

    override fun onResume() {
        super.onResume()
        if (player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()

        releasePlayer()

    }

    override fun onStop() {
        super.onStop()

        releasePlayer()

    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "javiermarsicano-VR-app")
        // Create a media source using the supplied URI
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        val uri = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-1/360/congo.mp4")
        val mediaSource = buildMediaSource(uri)
        player?.prepare(mediaSource)

        player_view.player = player
        player_view.onResume()
    }

    private fun releasePlayer() {
        player_view.onPause()
        player?.release()
        player = null
    }
}
