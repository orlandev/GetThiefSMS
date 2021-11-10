package com.ondev.getthiefsms.ui.screens.home

import android.content.Context
import android.content.Intent

object ShareIntent {
    fun shareIt(ctx: Context, shareText: String, shareBy: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        ctx.startActivity(
            Intent.createChooser(
                sendIntent,
                shareBy
            )
        )
    }

}