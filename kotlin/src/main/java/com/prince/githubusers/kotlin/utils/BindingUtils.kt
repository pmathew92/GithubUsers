package com.prince.githubusers.kotlin.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.prince.githubusers.kotlin.GlideApp
import timber.log.Timber


/**
 * set the views visibility to View.GONE if value is fale, else View.VISIBLE
 *
 * @param view  View
 * @param value hide true or false
 */
@BindingAdapter("gone")
fun setGone(view: View, value: Boolean?) {
    Timber.d("came here ${value}")
    if (value != null)
        view.visibility = if (value) View.VISIBLE else View.GONE
}


/**
 * set the views visibility to View.INVISIBLE if value is false, else View.VISIBLE
 *
 * @param view  View
 * @param value hide true or false
 */
@BindingAdapter("invisible")
fun setInvisible(view: View, value: Boolean?) {
    if (value != null)
        view.visibility = if (value) View.VISIBLE else View.INVISIBLE
}


/**
 * Load an image url to the specified view
 *
 * @param imageView
 * @param imageUrl
 */
@BindingAdapter("imageUrl")
fun displayImage(imageView: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        GlideApp.with(imageView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView)
    }
}


/**
 * Load an image url to the specified view
 *
 * @param imageView
 * @param imageUrl
 */
@BindingAdapter("circleImageUrl")
fun displayCircleImage(imageView: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        GlideApp.with(imageView.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView)
    }
}

