package com.example.lanjewartutorial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.*
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.lanjewartutorial.proFrag.aboutme.AboutmeFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.fragment_aboutme.*
import kotlinx.android.synthetic.main.log_pre.*

class ProfileActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var refUsers: DatabaseReference
    private var firebaseUserID:String=""
     var email : String? = null


    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val toolbar: Toolbar = findViewById(R.id.toolbar_profile)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout_profile)
        val navView: NavigationView = findViewById(R.id.nav_view_profile)

        val navController = findNavController(R.id.nav_host_fragment_profile)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_profile_aboutme, R.id.nav_register, R.id.nav_login,R.id.nav_contact,R.id.nav_aboutus,R.id.action_logout), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //var text1:String =findViewById(R.id.user_name_profile)
        Thread(Runnable {
            this@ProfileActivity.runOnUiThread(java.lang.Runnable {
                pb.visibility = View.VISIBLE
            })
        }).start()

        mAuth = FirebaseAuth.getInstance()

        val bundle:Bundle? = intent.extras
        email=bundle!!.getString("email")
        val password = bundle!!.getString("password")

        mAuth.signInWithEmailAndPassword(email!!,password!!).addOnCanceledListener{
            val intent= Intent(this@ProfileActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        updateNavHead()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.profile, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_profile)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun updateNavHead()
    {
        val navView: NavigationView = findViewById(R.id.nav_view_profile)
        val headerView:View = navView.getHeaderView(0)
        var username_pro :TextView = headerView.findViewById(R.id.user_name_profile)
        var userpic:ImageView = headerView.findViewById(R.id.profile_pic)
        var mail : TextView = headerView.findViewById(R.id.stu_email)
        val userr=mAuth.currentUser
        refUsers = FirebaseDatabase.getInstance().getReference("Users")
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            @SuppressLint("CheckResult")
            override fun onDataChange(p0: DataSnapshot)
            {
                username_pro.text=p0.child(userr!!.uid).child("username").value.toString()
                mail.text =email
                Glide.with(this@ProfileActivity).load(p0.child(userr!!.uid).child("profile").value.toString()).into(userpic)
                Thread(Runnable {
                    this@ProfileActivity.runOnUiThread(java.lang.Runnable {
                        pb.visibility = View.INVISIBLE
                    })
                }).start()

            }

        });
    }
}
