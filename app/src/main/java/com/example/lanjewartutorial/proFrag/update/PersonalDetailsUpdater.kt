package com.example.lanjewartutorial.proFrag.update

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.lanjewartutorial.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_personaldetailsupdater.*
import kotlinx.android.synthetic.main.fragment_profile_details_1.*
import kotlinx.android.synthetic.main.updaterpersonaldetails.*
import kotlinx.android.synthetic.main.updaterpersonaldetails.fname
import kotlinx.android.synthetic.main.updaterpersonaldetails.lname
import kotlinx.android.synthetic.main.updaterpersonaldetails.mname
import kotlinx.android.synthetic.main.updaterpersonaldetails.sname
import kotlinx.coroutines.delay
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
    lateinit var mAuth: FirebaseAuth
    lateinit var refUsers: DatabaseReference
    lateinit var date:TextView
    private var mYear:Int = 0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    var day:String = "dd/mm/yyyy"
    lateinit var nDialog:ProgressDialog

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
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            @SuppressLint("CheckResult")
            override fun onDataChange(p0: DataSnapshot) {

                try {


                    user.text = p0.child(userr!!.uid).child("username").value.toString() + "  "
                } catch (e: Exception) {
                }
            }
        }
        )
        val radio=root.findViewById(R.id.gen) as RadioGroup
        radio.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId)
            {
                R.id.male->textView2.text="Male"
                R.id.female->textView2.text="Female"
            }

        }



        date=root.findViewById(R.id.date_of_birth)
        date.setOnClickListener(View.OnClickListener { //
              val c: Calendar = Calendar.getInstance()
              mYear = c.get(Calendar.YEAR)
              mMonth = c.get(Calendar.MONTH)
              mDay = c.get(Calendar.DAY_OF_MONTH)

            // Launch Date Picker Dialog
            val dpd = DatePickerDialog(
                context!!,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    date.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    day = "$dayOfMonth-${monthOfYear + 1}-$year"
                }, mYear, mMonth, mDay
            )
            dpd.show()




            refUsers = FirebaseDatabase.getInstance().reference.child("Users")


            var btn_submit:Button=root.findViewById(R.id.submit)

            try {


                btn_submit.setOnClickListener {
                    if(sname.text.isEmpty())
                    {
                        sname.error = "Required"
                    }
                    else if(fname.text.isEmpty())
                    {
                        fname.error = "Required"
                    }
                    else if(mname.text.isEmpty())
                    {
                        mname.error = "Required"
                    }
                    else if(lname.text.isEmpty())
                    {
                        lname.error = "Required"
                    }
                    else if(date_of_birth.text==" dd/mm/yyyy")
                    {
                        date_of_birth.error="Required"
                    }
                    else if(uid.text.isEmpty())
                    {
                        uid.error="Required"
                    }
                    else if(textView2.text.equals(" Gender : "))
                    {
                        textView2.error="Required"
                    }
                    else {
                        nDialog = ProgressDialog.show(
                            activity,
                            "The Tuition Centre",
                            "Uploading Data..",
                            true
                        );
                        stu_name = sname?.text.toString()
                        father = fname?.text.toString()
                        mother = mname?.text.toString()
                        last = lname?.text.toString()
                        datebirth = date_of_birth?.text.toString()
                        aadhar = uid?.text.toString()
                        gender = textView2.text.toString()
                        submitdata()
                    }



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
        upload()


    }

    private fun upload() {
 try{
        refUsers.child(mAuth.currentUser!!.uid).child("name")?.setValue(stu_name!!)
            .addOnSuccessListener {
                refUsers.child(mAuth.currentUser!!.uid).child("fname")?.setValue(father!!)
                    .addOnSuccessListener {
                        refUsers.child(mAuth.currentUser!!.uid).child("lname")?.setValue(last!!)
                            .addOnSuccessListener {
                                refUsers.child(mAuth.currentUser!!.uid).child("mname")
                                    ?.setValue(mother!!)
                                    .addOnSuccessListener {
                                        refUsers.child(mAuth.currentUser!!.uid).child("aadhar")
                                            ?.setValue(aadhar!!)
                                            .addOnSuccessListener {
                                                refUsers.child(mAuth.currentUser!!.uid)
                                                    .child("gender").setValue(gender!!)
                                                    .addOnSuccessListener {
                                                        refUsers.child(mAuth.currentUser!!.uid)
                                                            .child("dob")!!.setValue(day!!)
                                                            .addOnSuccessListener {
                                                                nDialog.dismiss()
                                                                val dialogBuilder =
                                                                    android.app.AlertDialog.Builder(
                                                                        activity
                                                                    )

                                                                dialogBuilder.setMessage("Data Updated Successfully !")
                                                                    .setCancelable(false)
                                                                    .setPositiveButton(
                                                                        "Proceed",
                                                                        DialogInterface.OnClickListener { dialog, id ->
                                                                            go()
                                                                        })

                                                                val alert = dialogBuilder.create()
                                                                // set title for alert dialog box
                                                                alert.setTitle("The Tuition Centre")
                                                                // show alert dialog
                                                                alert.show()
                                                            }

                                                    }
                                            }
                                    }
                            }
                    }
            }




 }
 catch (e:Exception) {
 }
    }

    private fun go() {
        var fragment : Fragment =UpdateFragment()
        replaceFragment(fragment)
    }

    private fun replaceFragment(somefragment: Fragment) {
        var transition : FragmentTransaction =fragmentManager!!.beginTransaction()
        transition.replace(R.id.nav_host_fragment_profile,somefragment)
        transition.addToBackStack(null)
        transition.commit()
    }

}