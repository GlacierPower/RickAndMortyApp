package util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


object ExtensionFunction {
    fun ImageView.loadImage(url: String, placeholder: Int? = null, error: Int? = null) {
        val glideRequest = Glide.with(this.context)
            .load(url)

        placeholder?.let {
            glideRequest.apply(RequestOptions().placeholder(it))
        }

        error?.let {
            glideRequest.apply(RequestOptions().error(it))
        }

        glideRequest.into(this)
    }

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.showIf(isVisible: Boolean) {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}