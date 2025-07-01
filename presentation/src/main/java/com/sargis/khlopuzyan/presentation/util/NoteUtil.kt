package com.sargis.khlopuzyan.presentation.util

import androidx.compose.ui.graphics.toArgb
import com.sargis.khlopuzyan.presentation.ui.theme.BabyBlue
import com.sargis.khlopuzyan.presentation.ui.theme.LightGreen
import com.sargis.khlopuzyan.presentation.ui.theme.RedOrange
import com.sargis.khlopuzyan.presentation.ui.theme.RedPink
import com.sargis.khlopuzyan.presentation.ui.theme.Violet

object NoteUtil {
    fun noteColors(): List<Int> {
        return listOf(RedOrange.toArgb(), LightGreen.toArgb(), Violet.toArgb(), BabyBlue.toArgb(), RedPink.toArgb())
    }
}