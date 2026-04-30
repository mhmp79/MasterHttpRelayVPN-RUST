package com.therealaleph.mhrv

import android.content.Intent
import android.service.quicksettings.TileService

class VpnTileService : TileService() {

    override fun onClick() {
        val intent = Intent(this, MhrvVpnService::class.java)
        if (VpnState.isRunning()) {
            intent.action = MhrvVpnService.ACTION_STOP
            startService(intent)
            qsTile.state = TileService.STATE_INACTIVE
        } else {
            startService(intent)  // no action = startEverything()
            qsTile.state = TileService.STATE_ACTIVE
        }
        qsTile.updateTile()
    }

    override fun onLongClick() {
        super.onLongClick()
        val appIntent = packageManager.getLaunchIntentForPackage(packageName)
        startActivityAndCollapse(appIntent)
    }

    override fun onStartListening() {
        super.onStartListening()
        qsTile.state = if (VpnState.isRunning()) STATE_ACTIVE else STATE_INACTIVE
        qsTile.updateTile()
    }
}
