package com.example.lanjewartutorial

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_reset.*

class ResetActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var refUsers: DatabaseReference
    private var firebaseUserID:String=""
    lateinit var nDialog:ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)
        mAuth = FirebaseAuth.getInstance()

        reset_re.setOnClickListener{
            resetPassword()
        }
        verify_re.setOnClickListener {
            verifyUser()
        }

    }

    private fun verifyUser() {
        val email=email_reset.text.toString()
        val password=pass_re.text.toString()
        if(email.isEmpty())
        {
            email_reset.error = "Required"
        }
        else if(password.isEmpty())
        {
            pass_re.error = "Required"
        }
        else {

            nDialog = ProgressDialog.show(this,"The Tuition Centre","Checking...",true);

            Toast.makeText(this@ResetActivity, "Processing....", Toast.LENGTH_SHORT).show()
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                taskk ->
                if(taskk.isSuccessful) {
                    if(mAuth.currentUser!!.isEmailVerified)
                    {
                        val dialogBuilder = android.app.AlertDialog.Builder(this)

                        dialogBuilder.setMessage("Your Email is already verified.")
                            .setCancelable(false)
                            .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                                    dialog, id -> startActivity(intent)
                            })

                        val alert = dialogBuilder.create()
                        // set title for alert dialog box
                        alert.setTitle("The Tuition Centre")
                        // show alert dialog
                        mAuth.signOut()
                        nDialog.dismiss()
                        alert.show()

                    }
                    else {
                        nDialog.dismiss()
                        nDialog = ProgressDialog.show(this,"The Tuition Centre","Sending new Link..",true);

                        mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                val intent=Intent(this,MainActivity::class.java)
                                val dialogBuilder = android.app.AlertDialog.Builder(this)

                                dialogBuilder.setMessage("E-mail verification link sent successfully.")
                                    .setCancelable(false)
                                    .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                                            dialog, id -> startActivity(intent)
                                    })

                                val alert = dialogBuilder.create()
                                // set title for alert dialog box
                                alert.setTitle("The Tuition Centre")
                                // show alert dialog
                                mAuth.signOut()
                                nDialog.dismiss()
                                alert.show()

                            } else {
                                val intent=Intent(this,MainActivity::class.java)

                                val dialogBuilder = android.app.AlertDialog.Builder(this)

                                dialogBuilder.setMessage("Error ${task.exception!!.message}")
                                    .setCancelable(false)
                                    .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                                            dialog, id -> startActivity(intent)
                                    })

                                val alert = dialogBuilder.create()
                                // set title for alert dialog box
                                alert.setTitle("The Tuition Centre")
                                // show alert dialog
                                mAuth.signOut()
                                nDialog.dismiss()
                                alert.show()
                            }
                        }
                    }
                }
                else
                {
                    val intent=Intent(this,MainActivity::class.java)

                    val dialogBuilder = android.app.AlertDialog.Builder(this)

                    dialogBuilder.setMessage("Error ${taskk.exception!!.message}")
                        .setCancelable(false)
                        .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                                dialog, id -> startActivity(intent)
                        })

                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle("The Tuition Centre")
                    // show alert dialog
                    mAuth.signOut()
                    nDialog.dismiss()
                    alert.show()
                }
            }

        }
    }

    private fun resetPassword() {

        val email=email_reset.text.toString()
        if(email.isEmpty())
        {
            email_reset.error = "Required"
        }
        else
        {
            Thread(Runnable {

                this@ResetActivity.runOnUiThread(java.lang.Runnable {
                    progress_Bar.visibility = View.VISIBLE
                })
            }).start()
            Toast.makeText(this@ResetActivity,"Processing....",Toast.LENGTH_SHORT).show()
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener{
                task ->
                if(task.isSuccessful)
                {
                    Toast.makeText(this@ResetActivity,"Password Reset Link Sent Successfully",Toast.LENGTH_SHORT).show()
                    val intent= Intent(this@ResetActivity,LoginActivity::class.java)
                    startActivity(intent)
                    Thread(Runnable {

                        this@ResetActivity.runOnUiThread(java.lang.Runnable {
                            progress_Bar.visibility = View.INVISIBLE
                        })
                    }).start()
                }
                else
                {
                    Toast.makeText(this@ResetActivity,"Error "+task.exception!!.message,Toast.LENGTH_SHORT).show()
                    val intent= Intent(this@ResetActivity,ResetActivity::class.java)
                    startActivity(intent)
                    Thread(Runnable {

                        this@ResetActivity.runOnUiThread(java.lang.Runnable {
                            progress_Bar.visibility = View.INVISIBLE
                        })
                    }).start()
                }
            }
        }
    }
}
