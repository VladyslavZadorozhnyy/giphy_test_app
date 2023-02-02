package com.example.giphytestapp.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.R
import com.example.giphytestapp.R as AppR
import com.example.giphytestapp.common.NavigationAction
import com.example.giphytestapp.databinding.ActivityMainBinding
import com.example.giphytestapp.presentation.ui.fragments.MainScreenFragment
import com.example.giphytestapp.presentation.ui.fragments.NoInternetFragment
import com.example.giphytestapp.presentation.ui.fragments.ProgressFragment
import com.example.giphytestapp.presentation.ui.fragments.SearchQueryFragment
import com.example.giphytestapp.presentation.viewmodels.CollectionViewModel
import com.example.giphytestapp.presentation.viewmodels.NavigationViewModel
import com.example.giphytestapp.presentation.viewmodels.SearchesViewModel
import com.example.offline.presentation.viewmodels.NetworkViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val networkVm by viewModel<NetworkViewModel>()
    private val searchesVm by viewModel<SearchesViewModel>()
    private val collectionVm by viewModel<CollectionViewModel>()
    private val navigationVm by viewModel<NavigationViewModel>()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavStateObserver()
        setupOfflineStateObserver()
        setContentView(binding.root)
    }

    private fun setupNavStateObserver() {
        navigationVm.state.observe(this) { navState ->
            Log.d("AAADIP", "navState: ${navState}")
            if (navState.action == NavigationAction.POP) {
                supportFragmentManager.popBackStack()
            } else if (navState.action == NavigationAction.EXIT) {
                Intent(Intent.ACTION_MAIN).also {
                    it.addCategory(Intent.CATEGORY_HOME)
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    ContextCompat.startActivity(this, it, null)
                }
            } else {
                if (navState.fragment != null) {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
                            R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(binding.frameLayout.id, navState.fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    private fun setupOfflineStateObserver() {
        searchesVm.queriesState.observe(this) { state ->
            if (state.isLoading) {
                navigationVm.navigateTo(ProgressFragment(), null)
            } else if (state.chosenQuery != "") {
                navigationVm.navigateTo(MainScreenFragment(), null)
                collectionVm.getGifs(searchQuery = state.chosenQuery, queryIsNew = true,
                    online = false, navigationModel = navigationVm)
            } else if (state.searchQueries.isNotEmpty()) {
                navigationVm.navigateTo(SearchQueryFragment(), null)
            }
        }

        networkVm.stateOnline.observe(this) { isOnline ->
            if (isOnline) {
                Toast.makeText(this, resources.getString(AppR.string.connection_est), Toast.LENGTH_LONG).show()
                navigationVm.navigateTo(MainScreenFragment(), null)
            } else {
                Toast.makeText(this, resources.getString(AppR.string.connection_lost), Toast.LENGTH_LONG).show()
                navigationVm.navigateTo(NoInternetFragment(), null)
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.lastOrNull() is MainScreenFragment) {
            navigationVm.exitApplication()
        } else if (supportFragmentManager.fragments.lastOrNull() is NoInternetFragment) {
            navigationVm.exitApplication()
        } else if (supportFragmentManager.fragments.lastOrNull() !is ProgressFragment) {
            super.onBackPressed()
        }
    }
}