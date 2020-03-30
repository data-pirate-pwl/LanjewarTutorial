package com.example.lanjewartutorial.proFrag.aboutme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
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

open class AboutmeFragment : Fragment() , View.OnClickListener {

    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var refUsers: DatabaseReference
    lateinit var name:String
    lateinit var fname:String
    lateinit var mname:String
    lateinit var lname:String
    lateinit var gender:String
    lateinit var dob:String



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
               name =  "  Student Name : "+p0.child(userr!!.uid).child("name").value.toString()
                fname ="  Father Name : " + p0.child(userr!!.uid).child("fname").value.toString()
                mname = "  Mother Name : "+p0.child(userr!!.uid).child("mname").value.toString()
                lname = "  Last Name : "+p0.child(userr!!.uid).child("lname").value.toString()
                dob= "  Date Of Birth : "+p0.child(userr!!.uid).child("dob").value.toString()
                gender="  Gender : "+p0.child(userr!!.uid).child("gender").value.toString()

            }
        }
        )
        val btn_01:Button=root.findViewById(R.id.personal_details)
        val btn_02:Button=root.findViewById(R.id.contact)
        val btn_03:Button=root.findViewById(R.id.course)

        btn_01.setOnClickListener(this)
        btn_02.setOnClickListener(this)
        btn_03.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View?) {
        lateinit var fragment : Fragment

        when(v!!.id)
        {
            R.id.personal_details ->
            {
                fragment = PersonalDetailsFragment()
                replaceFragment(fragment)
            }
            R.id.contact ->
            {
                fragment = ContactDetailsFragment()
                replaceFragment(fragment)
            }
            R.id.course ->
            {
                fragment = AppliedCourseFragment()
                replaceFragment(fragment)
            }
        }
    }

    private fun replaceFragment(somefragment: Fragment) {
        var transition : FragmentTransaction=fragmentManager!!.beginTransaction()
        transition.replace(R.id.nav_host_fragment_profile,somefragment)
        transition.addToBackStack(null)
        transition.commit()
    }
}

