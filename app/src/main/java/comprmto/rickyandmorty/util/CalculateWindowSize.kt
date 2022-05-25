package comprmto.rickyandmorty.util

import android.app.Activity
import androidx.window.layout.WindowMetricsCalculator


class CalculateWindowSize(val activity: Activity) {

    private val metrics = WindowMetricsCalculator.getOrCreate()
        .computeCurrentWindowMetrics(activity)

    fun calculateCurrentHeightSize(): WindowSizeClass {

        val heightDp = metrics.bounds.height() / activity.resources.displayMetrics.density


        val heightWindowClasses = when {
            heightDp < 480f -> WindowSizeClass.COMPACT
            heightDp < 900f -> WindowSizeClass.MEDIUM
            else -> WindowSizeClass.EXPANDED
        }

        return heightWindowClasses

    }

    fun calculateCurrentWidthSize(): WindowSizeClass {

        val widthDp = metrics.bounds.width() / activity.resources.displayMetrics.density

        val widthDpClasses = when {
            widthDp < 600f -> WindowSizeClass.COMPACT
            widthDp < 840f -> WindowSizeClass.MEDIUM
            else -> WindowSizeClass.EXPANDED
        }

        return widthDpClasses
    }

}

