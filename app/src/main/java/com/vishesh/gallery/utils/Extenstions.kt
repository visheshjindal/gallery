package com.vishesh.gallery.utils

import android.content.res.Configuration
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


/**
 * Get category of the screen
 * @return SizeClass: Enum SizeClass which has Screen Sizes like Small, Normal etc
 */
fun AppCompatActivity.getScreenSizeCategory(): SizeClass {

    return when (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
        Configuration.SCREENLAYOUT_SIZE_SMALL -> SizeClass.SMALL
        Configuration.SCREENLAYOUT_SIZE_NORMAL -> SizeClass.NORMAL
        Configuration.SCREENLAYOUT_SIZE_LARGE -> SizeClass.LARGE
        Configuration.SCREENLAYOUT_SIZE_XLARGE -> SizeClass.XLARGE
        else -> SizeClass.UNDEFINED
    }
}

/**
 * helper function to hide the keyboard
 */
fun AppCompatActivity.hideKeyboard() {
    val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
}