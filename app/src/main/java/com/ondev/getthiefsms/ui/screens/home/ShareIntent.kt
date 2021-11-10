package com.ondev.getthiefsms.ui.screens.home

import android.content.Context
import android.content.Intent

object ShareIntent {
    fun shareIt(ctx: Context, shareText: String, shareBy: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(
            Intent.EXTRA_TEXT,
            shareText
        )
        ctx.startActivity(
            Intent.createChooser(
                intent,
                shareBy
            )
        )
    }

}