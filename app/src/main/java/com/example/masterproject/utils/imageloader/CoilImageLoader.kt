package com.example.masterproject.utils.imageloader

import android.widget.ImageView
import coil.load
import com.example.masterproject.R

class CoilImageLoader : AppImageLoader {

    override fun loadInto(url: String, container: ImageView) {
        container.load(url) {
            target(
                onSuccess = { result ->
                    container.setImageDrawable(result)
                },
                onStart = {
                    container.setImageResource(R.drawable.ic_launcher_background)
                },
                onError = {
                    container.setImageResource(R.drawable.ic_launcher_background)
                },
            )
        }
    }
}