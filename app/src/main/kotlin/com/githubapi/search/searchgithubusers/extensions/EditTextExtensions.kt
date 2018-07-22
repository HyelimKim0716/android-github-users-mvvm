package com.githubapi.search.searchgithubusers.extensions

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.hideKeyboard(activity: FragmentActivity?) {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}