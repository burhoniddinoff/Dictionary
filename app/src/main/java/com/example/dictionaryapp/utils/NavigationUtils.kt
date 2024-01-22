package com.example.dictionaryapp.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.dictionaryapp.R

fun FragmentActivity.addScreen(fm: Fragment) {
    supportFragmentManager.beginTransaction().add(R.id.container, fm).commit()
}

fun FragmentActivity.replaceScreen(fm: Fragment) {
    supportFragmentManager.beginTransaction().replace(R.id.container, fm)
        .addToBackStack(fm::class.java.name).commit()
}

fun FragmentActivity.replaceScreenWithoutSave(fm: Fragment) {
    supportFragmentManager.beginTransaction().replace(R.id.container, fm).commit()
}


fun FragmentActivity.popBackStack() {
    supportFragmentManager.popBackStack()
}

fun Fragment.replaceScreen(fm: Fragment) {
    requireActivity().replaceScreen(fm)
}

fun Fragment.replaceScreenWithoutSave(fm: Fragment) {
    requireActivity().replaceScreenWithoutSave(fm)
}

fun Fragment.popBackStack() {
    requireActivity().popBackStack()
}

fun String.createSpannable(query: String): SpannableString {
    val spannable = SpannableString(this)
    val startIndex = this.indexOf(query)
    val endIndex = startIndex + query.length
    if (startIndex < 0 || endIndex > this.length) return spannable
    spannable.setSpan(
        ForegroundColorSpan(Color.RED),
        startIndex, // start
        endIndex, // end
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    return spannable
}
