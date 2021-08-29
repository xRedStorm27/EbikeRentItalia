package com.tricolor.ebikerentitalia

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.EditText
import android.widget.Toast

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var loginButton: Button? = null
    private var username: EditText? = null
    private var password: EditText? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContentView(R.layout.fragment_login)


        loginButton = findViewById(R.id.login)
        username   = findViewById(R.id.username)
        password   = findViewById(R.id.password)

        loginButton?.setOnClickListener {
            val un = username?.text.toString()
            val pwd = password?.text.toString()

            auth.signInWithEmailAndPassword(un, pwd).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    setContentView(R.layout.activity_main)
                    val toolbar: Toolbar = findViewById(R.id.toolbar)
                    setSupportActionBar(toolbar)
                    actionBar?.setDisplayHomeAsUpEnabled(true)

                    val fab: FloatingActionButton = findViewById(R.id.fab)
                    fab.setOnClickListener { view ->
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }

                    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
                    val navView: NavigationView = findViewById(R.id.nav_view)
                    val navController = findNavController(R.id.nav_host_fragment)
                    // Passing each menu ID as a set of Ids because each
                    // menu should be considered as top level destinations.
                    appBarConfiguration = AppBarConfiguration(
                        setOf(
                            R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
                        ), drawerLayout
                    )
                    setupActionBarWithNavController(navController, appBarConfiguration)
                    navView.setupWithNavController(navController)


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Autenticazione fallita. Riprova",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}