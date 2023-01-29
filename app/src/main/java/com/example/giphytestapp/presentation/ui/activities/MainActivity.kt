package com.example.giphytestapp.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.R
import com.example.giphytestapp.R as AppR
import com.example.giphytestapp.common.NavigationAction
import com.example.giphytestapp.databinding.ActivityMainBinding
import com.example.giphytestapp.presentation.ui.fragments.MainScreenFragment
import com.example.giphytestapp.presentation.ui.fragments.ProgressFragment
import com.example.giphytestapp.presentation.viewmodels.AppViewModel
import com.example.offline.presentation.ui.fragments.NoInternetFragment
import com.example.offline.presentation.ui.fragments.SearchQueryFragment
import com.example.offline.presentation.viewmodels.SearchesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchesViewModel.Contract {
    private val appViewModel by viewModel<AppViewModel>()
    private val searchesViewModel by viewModel<SearchesViewModel>()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavStateObserver()
        setupOfflineStateObserver()
        setupOfflineStateContract()
        setContentView(binding.root)
    }

    private fun setupNavStateObserver() {
        appViewModel.navigationViewModel.state.observe(this) { navState ->
            if (navState.action == NavigationAction.POP) {
                supportFragmentManager.popBackStack()
            }

            if (navState.fragment != null) {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.abc_fade_in,
                        R.anim.abc_fade_out,
                        R.anim.abc_fade_in,
                        R.anim.abc_fade_out
                    )
                    .replace(binding.frameLayout.id, navState.fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun setupOfflineStateObserver() {
        appViewModel.networkViewModel.networkState.observe(this) { isOnline ->
            if (isOnline) {
                Toast.makeText(this, resources.getString(AppR.string.connection_est), Toast.LENGTH_LONG).show()
                appViewModel.navigationViewModel.navigateTo(MainScreenFragment(), null)
            } else {
                Toast.makeText(this, resources.getString(AppR.string.connection_lost), Toast.LENGTH_LONG).show()
                appViewModel.navigationViewModel.navigateTo(NoInternetFragment(), null)
            }
        }
    }

    private fun setupOfflineStateContract() {
        searchesViewModel.setContract(this)
    }

    override fun exitApp() {
        Intent(Intent.ACTION_MAIN).also {
            it.addCategory(Intent.CATEGORY_HOME)
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            ContextCompat.startActivity(this, it, null)
        }
    }

    override fun navigateToOffline() {
        appViewModel.navigationViewModel.navigateTo(SearchQueryFragment(), null)
    }

    override fun navigateToLoading() {
        appViewModel.navigationViewModel.navigateTo(ProgressFragment(), null)
    }

    override fun navigateToCollection(searchQuery: String) {
        appViewModel.navigationViewModel.navigateTo(MainScreenFragment())
        appViewModel.collectionViewModel.getGifs(
            searchQuery = searchQuery,
            queryIsNew = true,
            online = false,
            navigationModel = appViewModel.navigationViewModel
        )
    }

    override fun removeLastFragment() {
        appViewModel.navigationViewModel.removeLastFragment()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.lastOrNull() is MainScreenFragment) {
            exitApp()
        } else if (supportFragmentManager.fragments.lastOrNull() is NoInternetFragment) {
            exitApp()
        } else {
            super.onBackPressed()
        }
    }
}