package com.example.giphytestapp.presentation.states

import androidx.fragment.app.Fragment
import com.example.giphytestapp.common.NavigationAction
import com.example.giphytestapp.presentation.ui.fragments.MainScreenFragment

data class NavigationState(
    val action: NavigationAction = NavigationAction.REPLACE,
    val fragment: Fragment? = MainScreenFragment()
)