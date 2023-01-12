package com.example.offline.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.offline.R


class OfflineFragment : Fragment() {
    private val actionBarShown: Boolean by lazy {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.isShowing ?: false
    }

    override fun onStart() {
        super.onStart()
        if (actionBarShown) {
            (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_offline, container, false)
    }

    override fun onStop() {
        super.onStop()
        if (actionBarShown) {
            (requireActivity() as? AppCompatActivity)?.supportActionBar?.show()
        }
    }
}