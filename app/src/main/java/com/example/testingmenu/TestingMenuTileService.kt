package com.example.testingmenu

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

/**
 * Tile opens the dialer pre-filled with *#*#4636#*# (missing the last *
 * intentionally). The dialer fires the secret-code action the moment it
 * detects that final * being typed — so the user just taps one key and
 * the Testing screen opens. Pre-filling the full string including the last
 * * doesn't trigger anything since the code is pasted, not typed.
 */
class TestingMenuTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        qsTile?.apply {
            state = Tile.STATE_INACTIVE
            label = getString(R.string.tile_label)
            updateTile()
        }
    }

    override fun onClick() {
        super.onClick()

        // Leave off the last * — user taps it once in the dialer and it fires.
        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:*%23*%234636%23*%23")).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        if (Build.VERSION.SDK_INT >= 34) {
            val pi = PendingIntent.getActivity(
                this, 0, dialIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            startActivityAndCollapse(pi)
        } else {
            @Suppress("DEPRECATION")
            startActivityAndCollapse(dialIntent)
        }
    }
}
