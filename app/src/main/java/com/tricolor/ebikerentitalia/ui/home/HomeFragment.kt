package com.tricolor.ebikerentitalia.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tricolor.ebikerentitalia.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        val mainWebView: WebView = root.findViewById(R.id.home_webview)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        mainWebView.getSettings().setAllowContentAccess(true)
        mainWebView.getSettings().setAllowFileAccess(true)
        mainWebView.settings.javaScriptEnabled = true
        //mainWebView.loadUrl("file:///android_asset/home.html")
        mainWebView.loadUrl("http://192.168.1.167/home.php")
        return root
    }
}