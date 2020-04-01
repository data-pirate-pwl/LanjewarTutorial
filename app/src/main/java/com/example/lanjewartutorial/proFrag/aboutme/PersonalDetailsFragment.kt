package com.example.lanjewartutorial.proFrag.aboutme

import android.annotation.SuppressLint
import android.app.ProgressDialog
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
       val nDialog = ProgressDialog.show(activity,"The Tuition Centre","Loading...",true);
        refUsers = FirebaseDatabase.getInstance().getReference("Users")
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            @SuppressLint("CheckResult")
            override fun onDataChange(p0: DataSnapshot) {

                try {


                    uname?.text = p0.child(userr!!.uid).child("username").value.toString() + "  "
                    sname?.text = p0.child(userr!!.uid).child("name").value.toString() + "  "
                    fname?.text = p0.child(userr!!.uid).child("fname").value.toString() + "  "
                    mname?.text = p0.child(userr!!.uid).child("mname").value.toString() + "  "
                    lname?.text = p0.child(userr!!.uid).child("lname").value.toString() + "  "
                    dob?.text = p0.child(userr!!.uid).child("dob").value.toString() + "  "
                    aadhar?.text = p0.child(userr!!.uid).child("aadhar").value.toString() + "  "
                    gender?.text = p0.child(userr!!.uid).child("gender").value.toString() + "  "
                    cno.text = p0.child(userr!!.uid).child("studentmobile").value.toString()
                    eid.text = p0.child(userr!!.uid).child("studentemail").value.toString()
                    pad.text = p0.child(userr!!.uid).child("permanantaddress").value.toString()
                    city.text = p0.child(userr!!.uid).child("city").value.toString()
                    Glide.with(this@PersonalDetailsFragment)
                        .load(p0.child(userr.uid).child("profile").value.toString()).into(pro)
                    nDialog.dismiss()
                }
                catch (e:Exception)
                {

                }

            }
        })

        return root
    }

}