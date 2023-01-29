package com.example.giphytestapp.presentation.viewmodels

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.giphytestapp.common.NavigationAction
import com.example.giphytestapp.presentation.states.NavigationState

class NavigationViewModel : ViewModel() {
    private val _state = MutableLiveData(NavigationState())
    val state: LiveData<NavigationState> = _state

    fun removeLastFragment() {
        _state.value = NavigationState(NavigationAction.POP, null)
    }

    fun navigateTo(nextFragment: Fragment, fragmentArguments: Bundle? = null) {
        val currentFragment = _state.value?.fragment

        if (nextFragment != currentFragment) {
            nextFragment.arguments = fragmentArguments
            _state.value = NavigationState(NavigationAction.REPLACE, nextFragment)
        }
    }
}