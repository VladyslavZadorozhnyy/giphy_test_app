package com.example.giphytestapp.presentation.ui.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import com.example.giphytestapp.R

class CustomDialog(activity: Activity) : Dialog(activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)
    }
}