package com.example.lanjewartutorial.proFrag.aboutme

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.lanjewartutorial.R
import com.example.lanjewartutorial.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_aboutme.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_profile.*
import com.example.lanjewartutorial.ProfileActivity as ProfileActivity1

open class AboutmeFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var refUsers: DatabaseReference
    private var firebaseUserID:String=""
    private lateinit var aboutmeViewModel:AboutmeViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        val root = inflater.inflate(R.layout.fragment_aboutme, container, false)

        mAuth = FirebaseAuth.getInstance()
        val userr=mAuth.currentUser
        refUsers = FirebaseDatabase.getInstance().getReference("Users")
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(p0: DataSnapshot) {
                //Glide.with(this@AboutmeFragment).load(p0.child(userr!!.uid).child("profile").value.toString()).into(profile_profile)
                pname.text =  "  Student Name : "+p0.child(userr!!.uid).child("name").value.toString()
                father.text ="  Father Name : " + p0.child(userr!!.uid).child("fname").value.toString()
                mother.text = "  Mother Name : "+p0.child(userr!!.uid).child("mname").value.toString()
                last.text = "  Last Name : "+p0.child(userr!!.uid).child("lname").value.toString()
                DOB.text = "  Date Of Birth : "+p0.child(userr!!.uid).child("dob").value.toString()
                gender.text="  Gender : "+p0.child(userr!!.uid).child("gender").value.toString()

            }
        }
        )

        return root
    }




}
