package com.example.lanjewartutorial.proFrag.update

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lanjewartutorial.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.updaterpersonaldetails.*
import java.lang.Exception
import java.util.*


open class PersonalDetailsUpdater :Fragment() {
    lateinit var stu_name:String
    lateinit var father:String
    lateinit var mother:String
    lateinit var last:String
    lateinit var aadhar:String
    lateinit var gender:String
    lateinit var datebirth:String
    lateinit var prog:ProgressBar
    lateinit var mStorageReference: StorageReference;
    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var refUsers: DatabaseReference
    lateinit var date:TextView
    private var mYear:Int = 0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    lateinit var day:String
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_personaldetailsupdater, container, false)

        mAuth = FirebaseAuth.getInstance()

        val userr = mAuth.currentUser
        refUsers = FirebaseDatabase.getInstance().getReference("Users")
        date=root.findViewById(R.id.date_of_birth)
        date.setOnClickListener(View.OnClickListener { // Process to get Current Date
            val c: Calendar = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)

            // Launch Date Picker Dialog
            val dpd = DatePickerDialog(
                context!!,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                   date.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    day="$dayOfMonth-${monthOfYear+1}-$year"
                }, mYear, mMonth, mDay
            )
            dpd.show()
            refUsers = FirebaseDatabase.getInstance().reference.child("Users")
            prog=root.findViewById(R.id.sub)
            var btn_submit:Button=root.findViewById(R.id.submit)
            stu_name=sname?.text.toString()
            father=fname?.text.toString()
            mother=mname?.text.toString()
            last=lname?.text.toString()
            datebirth=date_of_birth?.text.toString()
            aadhar=uid?.text.toString()
            try {


                btn_submit.setOnClickListener {
                    prog.visibility=View.VISIBLE
                    submitdata()
                }
            }
            catch (e:Exception)
            {
                //s
            }

        })



        return root

    }
    private fun submitdata() {
        prog.visibility=View.VISIBLE
        upload()

    }

    private fun upload() {
        prog.visibility=View.VISIBLE
        refUsers.child(mAuth.currentUser!!.uid).child("name")?.setValue(stu_name!!)
        refUsers.child(mAuth.currentUser!!.uid).child("fname")?.setValue(father!!)
        refUsers.child(mAuth.currentUser!!.uid).child("lname")?.setValue(last!!)
        refUsers.child(mAuth.currentUser!!.uid).child("mname")?.setValue(mother!!)
        refUsers.child(mAuth.currentUser!!.uid).child("aadhar")?.setValue(aadhar!!)
        refUsers.child(mAuth.currentUser!!.uid).child("dob")!!.setValue(day!!)
        //refUsers.child(mAuth.currentUser!!.uid).child("gender").setValue()
      prog.visibility=View.GONE
    }

}