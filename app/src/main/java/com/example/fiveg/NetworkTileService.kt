package com.example.fiveg

import android.content.Intent
import android.net.Uri
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

/**
 * Quick Settings tile that opens the dialer with the network diagnostics code.
 * Tap the tile to open: *#*#4636#*#
 *
 * User manually enters the * and # to trigger the network menu.
 */
class NetworkTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        updateTile()
    }

    override fun onClick() {
        super.onClick()
        openDialerWithCode()
    }

    private fun updateTile() {
        val tile = qsTile ?: return
        tile.state = Tile.STATE_INACTIVE
        tile.label = "5G"
        tile.subtitle = "Network Diagnostics"
        tile.updateTile()
    }

    private fun openDialerWithCode() {
        val diagnosticsCode = "*#*#4636#*#"
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.fromParts("tel", diagnosticsCode, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}
