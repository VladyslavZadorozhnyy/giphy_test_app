package com.example.giphytestapp.domain.usecase

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.offline.data.network.NetworkListener
import java.io.File
import java.nio.ByteBuffer

class UploadGifsUseCase(
    private val glideManager: RequestManager
) {
    operator fun invoke(
        url: String,
        imageView: ImageView,
        placeholder: CircularProgressDrawable,
        onCache: (bytebuffer: ByteBuffer) -> Unit
    ) {
        glideManager.load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    (resource as? GifDrawable)?.let { gifDrawable ->
                        onCache(gifDrawable.buffer)
                    }
                    return false
                }

            })
            .placeholder(placeholder)
            .into(imageView)
    }
}