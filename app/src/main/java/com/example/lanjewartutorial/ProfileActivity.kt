package com.example.lanjewartutorial

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.widget.Toolbar;
import com.example.lanjewartutorial.proFrag.academic
import com.example.lanjewartutorial.proFrag.student_personal
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.pro_tem.*

class ProfileActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var user:FirebaseUser
    lateinit var refUsers: DatabaseReference
    private var firebaseUserID:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mAuth = FirebaseAuth.getInstance()

        val bundle:Bundle? = intent.extras
        val email=bundle!!.getString("email")
        val password = bundle!!.getString("password")

        mAuth.signInWithEmailAndPassword(email!!,password!!).addOnCanceledListener{
            val intent= Intent(this@ProfileActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener{
            mAuth.signOut()
            val intent= Intent(this@ProfileActivity,LoginActivity::class.java)
            startActivity(intent)

        }

        val userr=mAuth.currentUser
        refUsers = FirebaseDatabase.getInstance().getReference("Users")
        refUsers.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot)
            {
                user_name.text= p0.child(userr!!.uid).child("username").value.toString()
            }

        });

    }

    }


