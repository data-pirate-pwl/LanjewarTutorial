package com.example.lanjewartutorial.proFrag.aboutme

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.lanjewartutorial.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_personaldetails.*
import kotlinx.android.synthetic.main.fragment_profile_details_1.*

class PersonalDetailsFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var refUsers: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_personaldetails, container, false)

        mAuth = FirebaseAuth.getInstance()
        val userr=mAuth.currentUser
        refUsers = FirebaseDatabase.getInstance().getReference("Users")
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            @SuppressLint("CheckResult")
            override fun onDataChange(p0: DataSnapshot) {

                 uname.text=p0.child(userr!!.uid).child("username").value.toString()+"  "
                 sname.text=p0.child(userr!!.uid).child("name").value.toString()+"  "
                 fname.text=p0.child(userr!!.uid).child("fname").value.toString()+"  "
                 mname.text=p0.child(userr!!.uid).child("mname").value.toString()+"  "
                 lname.text=p0.child(userr!!.uid).child("lname").value.toString()+"  "
                   dob.text=p0.child(userr!!.uid).child("dob").value.toString()+ "  "
                aadhar.text=p0.child(userr!!.uid).child("aadhar").value.toString()+ "  "
                gender.text=p0.child(userr!!.uid).child("gender").value.toString()+ "  "
                Glide.with(this@PersonalDetailsFragment).load(p0.child(userr!!.uid).child("profile").value.toString()).into(pro)
                pb.visibility = View.INVISIBLE

            }
        })

        return root
    }
}