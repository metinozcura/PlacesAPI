package com.metinozcura.placesapi.helper.ext

import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.metinozcura.placesapi.R

fun Fragment.showDialog(@StringRes resId: Int, positiveAction: () -> Unit, negativeAction: () -> Unit) {
    AlertDialog.Builder(requireContext())
        .setTitle(getString(R.string.warning))
        .setMessage(getString(resId))
        .setPositiveButton(android.R.string.yes) { _, _ -> positiveAction.invoke() }
        .setNegativeButton(android.R.string.no) { _, _ -> negativeAction.invoke() }
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show()
}