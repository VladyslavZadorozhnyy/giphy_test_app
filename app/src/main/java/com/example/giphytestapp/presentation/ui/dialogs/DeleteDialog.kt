package com.example.giphytestapp.presentation.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.example.giphytestapp.databinding.DialogLayoutBinding

class DeleteDialog(
    context: Context?,
    private val title: String?,
    private val message: String?,
    private val positiveButtonText: String?,
    private val negativeButtonText: String?,
    private val positiveButtonCallBack: (() -> Unit)?,
    private val negativeButtonCallBack: (() -> Unit)?
) : AlertDialog(context) {
    private val binding: DialogLayoutBinding by lazy { DialogLayoutBinding.inflate(layoutInflater) }

    private fun setupViews() {
        binding.title.text = title
        binding.message.text = message
        binding.positiveButton.text = positiveButtonText
        binding.negativeButton.text = negativeButtonText

        binding.positiveButton.setOnClickListener {
            positiveButtonCallBack?.invoke()
            dismiss()
        }

        binding.negativeButton.setOnClickListener {
            negativeButtonCallBack?.invoke()
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setContentView(binding.root)
    }

    private constructor(
        builder: Builder
    ) : this(
        builder.context,
        builder.titleValue,
        builder.messageValue,
        builder.positiveButtonText,
        builder.negativeButtonText,
        builder.positiveButtonCallback,
        builder.negativeButtonCallback
    )

    class Builder(
        val context: Context?
    ) {
        var titleValue: String? = null
        var messageValue: String? = null
        var positiveButtonText: String? = null
        var negativeButtonText: String? = null
        var positiveButtonCallback: (() -> Unit)? = null
        var negativeButtonCallback: (() -> Unit)? = null

        fun setTitle(value: String): Builder {
            if (titleValue == null) {
                titleValue = value
            }

            return this
        }

        fun setMessage(value: String): Builder {
            if (messageValue == null) {
                messageValue = value
            }

            return this
        }

        fun setPositiveButton(value: String, callBack: (() -> Unit)?): Builder {
            if (positiveButtonText == null) {
                positiveButtonText = value
            }

            if (positiveButtonCallback == null) {
                positiveButtonCallback = callBack
            }

            return this
        }

        fun setNegativeButton(value: String, callBack: (() -> Unit)?): Builder {
            if (negativeButtonText == null) {
                negativeButtonText = value
            }

            if (negativeButtonCallback == null) {
                negativeButtonCallback = callBack
            }

            return this
        }

        fun show() = DeleteDialog(this).show()
    }
}