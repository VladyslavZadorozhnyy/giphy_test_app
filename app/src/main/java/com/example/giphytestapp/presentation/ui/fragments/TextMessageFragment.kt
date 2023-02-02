package com.example.giphytestapp.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.giphytestapp.R
import com.example.giphytestapp.databinding.FragmentTextMessageBinding

class TextMessageFragment(
) : Fragment() {
    private val binding by lazy { FragmentTextMessageBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupSubtitle()
        return binding.root
    }

    private fun setupSubtitle() {
        binding.subtitle.text = arguments?.getString(MESSAGE_KEY)
            ?: resources.getString(R.string.no_message)
    }

    companion object {
        const val MESSAGE_KEY = "subtitle"
    }
}