@file:JvmName("ViewExtensions")

package com.skylarkdrones.dronemissionops.asdk

import android.view.View


/**
 * Set the view [View.VISIBLE].
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Set the view [View.GONE].
 */
fun View.hide() {
    visibility = View.GONE
}

/**
 * Toggle the view between [View.GONE] and [View.VISIBLE]
 */
fun View.toggleVisibility() {
    if (visibility == View.VISIBLE) hide()
    else show()
}