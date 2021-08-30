package com.tricolor.ebikerentitalia.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tricolor.ebikerentitalia.R

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        val mainWebView: WebView = root.findViewById(R.id.gallery_webview)
        galleryViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        mainWebView.settings.allowContentAccess = true
        mainWebView.settings.allowFileAccess = true
        mainWebView.loadUrl("file:///android_asset/gallery.html")

        return root
    }

}