package com.example.lanjewartutorial.proFrag.update

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lanjewartutorial.R
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_contactdetailsupdate.*
import java.util.concurrent.TimeUnit

class ContactDetailsUpdater :Fragment() {
    lateinit var pno:String
    lateinit var verificationId:String
    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var refUsers: DatabaseReference
    lateinit var nDialog: ProgressDialog
    var c=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_contactdetailsupdate, container, false)
        mAuth = FirebaseAuth.getInstance()
        val userr = mAuth.currentUser
        refUsers = FirebaseDatabase.getInstance().getReference("Users")

        try {


            root.findViewById<Button>(R.id.send).setOnClickListener{
                if(c == 0){
                nDialog = ProgressDialog.show(activity,"The Tuition Centre","Checking ...",true);
                    pno="+91"+contact_no_updater.text.toString()
                    if (pno.length != 13) {
                        nDialog.dismiss()
                        contact_no_updater.error = "Enter Valid Contact Number"
                    }

                    else {
                        contact_no_updater.visibility = View.INVISIBLE
                        lc.text = pno
                        lc.visibility = View.VISIBLE
                        nDialog.dismiss()
                        sendVerificationCode(pno)
                    }

                    }
                else
                {
                    val dialogBuilder = android.app.AlertDialog.Builder(activity)

                    dialogBuilder.setMessage("Already Verified")
                        .setCancelable(false)
                        .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                                dialog, id -> dialog.dismiss()
                        })

                    val alert = dialogBuilder.create()
                    alert.setTitle("The Tuition Centre")
                    alert.show()
                }
            }

        }
        catch (e:Exception){}
        return root
    }
    private fun sendVerificationCode(pno: String) {
        try {
            nDialog = ProgressDialog.show(activity,"The Tuition Centre","Verifying...",true);
            PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(pno, 45, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mCallBack)
        } catch (e: java.lang.Exception) {
        }
    }

    private var mCallBack = object:PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            try {
                refUsers.child(mAuth.currentUser!!.uid).child("studentmobile")?.setValue(pno!!)
                c++
                val dialogBuilder = android.app.AlertDialog.Builder(activity)

                dialogBuilder.setMessage("Verification Successful")
                    .setCancelable(false)
                    .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                            dialog, id -> dialog.dismiss()
                    })

                val alert = dialogBuilder.create()
                alert.setTitle("The Tuition Centre")
                nDialog.dismiss()
                alert.show()
                send.text="Verified"
            }catch (e:java.lang.Exception){}
        }

        override fun onCodeAutoRetrievalTimeOut(p0: String) {
            try {
                val dialogBuilder = android.app.AlertDialog.Builder(activity)

                dialogBuilder.setMessage("Request Timed Out..\nMake sure that no entered is correct and the sim is inserted in same device from which you are trying to verify your contact number.")
                    .setCancelable(false)
                    .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                            dialog, id -> dialog.dismiss()
                    })

                val alert = dialogBuilder.create()
                alert.setTitle("The Tuition Centre")
                nDialog.dismiss()
                alert.show()
                contact_no_updater.visibility = View.VISIBLE
                lc.visibility = View.INVISIBLE

            }catch (e:java.lang.Exception){}

        }

        override fun onVerificationFailed(p0: FirebaseException) {
            nDialog.dismiss()
            val dialogBuilder = android.app.AlertDialog.Builder(activity)

            dialogBuilder.setMessage("Error : $p0 !")
                .setCancelable(false)
                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id -> dialog.dismiss()
                })

            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("The Tuition Centre")
            nDialog.dismiss()
            alert.show()
        }

    }
}


























