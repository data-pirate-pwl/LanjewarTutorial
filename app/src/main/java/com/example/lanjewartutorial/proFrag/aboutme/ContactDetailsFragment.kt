package com.example.lanjewartutorial.proFrag.aboutme

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lanjewartutorial.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_contactdetails.*

class ContactDetailsFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var refUsers: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_contactdetails, container, false)

        mAuth = FirebaseAuth.getInstance()

        mAuth = FirebaseAuth.getInstance()
        val userr=mAuth.currentUser
        refUsers = FirebaseDatabase.getInstance().getReference("Users")
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            @SuppressLint("CheckResult")
            override fun onDataChange(p0: DataSnapshot) {

                cno.text=p0.child(userr!!.uid).child("studentmobile").value.toString()
                Fcno.text=p0.child(userr!!.uid).child("fathermobile").value.toString()
                eid.text=p0.child(userr!!.uid).child("studentemail").value.toString()
                pad.text=p0.child(userr!!.uid).child("permanantaddress").value.toString()
                city.text=p0.child(userr!!.uid).child("city").value.toString()


            }
        })

        return root

    }
}
