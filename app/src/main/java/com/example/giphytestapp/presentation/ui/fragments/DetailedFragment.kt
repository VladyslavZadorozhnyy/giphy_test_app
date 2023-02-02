package com.example.giphytestapp.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.giphytestapp.R
import com.example.giphytestapp.common.Constants.LOADING_ITEM
import com.example.giphytestapp.databinding.FragmentDetailedBinding
import com.example.giphytestapp.domain.model.GifModel
import com.example.giphytestapp.presentation.ui.dialogs.DeleteDialog
import com.example.giphytestapp.presentation.ui.recyclerviews.GifDetailedAdapter
import com.example.giphytestapp.presentation.viewmodels.CollectionViewModel
import com.example.giphytestapp.presentation.viewmodels.NavigationViewModel
import com.example.offline.presentation.viewmodels.NetworkViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DetailedFragment : Fragment(), GifDetailedAdapter.Contract {
    private val binding by lazy { FragmentDetailedBinding.inflate(layoutInflater) }

    private val networkVm by sharedViewModel<NetworkViewModel>()
    private val navigationVm by sharedViewModel<NavigationViewModel>()
    private val collectionVm by sharedViewModel<CollectionViewModel>()

    private val viewPagerAdapter by lazy { binding.viewPager.adapter as? GifDetailedAdapter }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        setupStateObserver()
        setupViewPager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailedAdapter = GifDetailedAdapter(this)
        binding.viewPager.adapter = detailedAdapter
    }

    override fun onRemoveButtonClicked(position: Int) {
        DeleteDialog.Builder(context)
            .setTitle(resources.getString(R.string.delete_dialog_title))
            .setMessage(resources.getString(R.string.delete_dialog_message))
            .setPositiveButton(resources.getString(R.string.delete_dialog_positive_button)) {
                viewPagerAdapter?.let { collectionVm.removeGif(position) }
            }
            .setNegativeButton(resources.getString(R.string.delete_dialog_negative_button), null)
            .show()
    }

    override fun loadGifToView(model: GifModel, imageView: ImageView) {
        val circularProgressDrawable = CircularProgressDrawable(requireContext()).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
        collectionVm.loadGifToView(model, imageView, circularProgressDrawable)
    }

    private fun setupStateObserver() {
        collectionVm.state.observe(viewLifecycleOwner) { state ->
            viewPagerAdapter?.let {
                if (it.firstSetup()) {
                    binding.viewPager.doOnPreDraw {
                        val detailedGifPosition = arguments?.getInt(DETAILED_GIF_KEY) ?: 0
                        binding.viewPager.setCurrentItem(detailedGifPosition, false)
                    }
                }

                it.updateItems(state.gifs)
            }
        }
    }

    private fun setupViewPager() {
        val onPageChangeCallback = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewPagerAdapter?.let {
                    val currentGifs = collectionVm.state.value?.gifs

                    if (position == currentGifs?.lastIndex && currentGifs.lastOrNull() != LOADING_ITEM) {
                        collectionVm.getGifs(queryIsNew = false, navigationModel = navigationVm,
                            online = networkVm.stateOnline.value ?: true)
                    }
                }
            }
        }
        binding.viewPager.registerOnPageChangeCallback(onPageChangeCallback)
    }

    companion object {
        const val DETAILED_GIF_KEY = "detailed_position"
    }
}