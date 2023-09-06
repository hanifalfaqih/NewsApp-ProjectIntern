package id.allana.newsapp.adapter

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.Locale

class BindingAdapter {
    companion object {
        const val BASE_URL_PHOTO = "https://tamasya.technice.id/"
        @JvmStatic
        @BindingAdapter("app:textFormatTime")
        fun textFormatTime(textView: MaterialTextView, dateTime: String?) {
            if (dateTime.isNullOrEmpty()) {
                textView.text = null
                return
            }

            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val formatter = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
            val result = parser.parse(dateTime)?.let { formatter.format(it) }
            textView.text = result
        }

        @JvmStatic
        @BindingAdapter("app:loadImageWithGlide")
        fun loadImageWithGlide(image: ShapeableImageView, url: String) {
            if (url.isEmpty()) {
//                image.setImageResource(R.drawable.ic_placeholder_loading)
                return
            }
            Glide.with(image)
                .load("$BASE_URL_PHOTO$url")
                .into(image)
        }
    }
}