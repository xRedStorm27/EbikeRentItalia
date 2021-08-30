package com.tricolor.ebikerentitalia.ui.home

//import android.Manifest
//import android.content.Context.LOCATION_SERVICE
//import android.content.pm.PackageManager
//import android.location.Location
//import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tricolor.ebikerentitalia.R
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import org.json.JSONArray
import org.json.JSONObject


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

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val data = MutableList<JSONObject>(2, fun (x: Int): JSONObject { return JSONObject() })

        db.collection("Luoghi")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        data.add(JSONObject(document.data))
                    }
                } else {
                    Log.d("DBM", "Error getting documents: ", task.exception)
                }
            }


        /*val homemap: MapView = root.findViewById(R.id.home_map)
        homemap.setTileSource(TileSourceFactory.MAPNIK)
        getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        homemap.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        homemap.setMultiTouchControls(true)
        homemap.controller.setZoom(17)

        var latitude: Double
        var longitude: Double
        val l: Location

        locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            l = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
            latitude= l.latitude
            longitude = l.longitude
            homemap.controller.setCenter(GeoPoint(latitude, longitude))
            val myPosition = Marker(homemap)
            myPosition.position = GeoPoint(latitude, longitude)
            myPosition.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            homemap.overlays.add(myPosition)




        }*/

        //mainWebView.webViewClient = JSInjectorWebViewClient("")

        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it


        })

        mainWebView.webViewClient = MarkerWebViewClient(data)

        mainWebView.settings.allowContentAccess = true
        mainWebView.settings.allowFileAccess = true
        mainWebView.settings.javaScriptEnabled = true
        mainWebView.loadUrl("file:///android_asset/home.html")
        //mainWebView.loadUrl("http://192.168.1.167/home.php")


        return root
    }

    class MarkerWebViewClient(marks: MutableList<JSONObject>): WebViewClient() {
        val Marks = marks
        override fun onPageFinished(view: WebView?, url: String?) {
            Log.d("Marks", "here")
            Log.d("Marks", Marks.toString())
            for(mark in Marks) {
                Log.d("Marks", mark.toString())
                view?.loadUrl("javascript:createMark(" + mark.toString() + ")")
            }

            super.onPageFinished(view, url)
        }
    }

}