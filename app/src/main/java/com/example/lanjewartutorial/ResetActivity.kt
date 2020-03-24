package com.example.lanjewartutorial

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

            Thread(Runnable {
                // dummy thread mimicking some operation whose progress cannot be tracked

                // display the indefinite progressbar
                this@ResetActivity.runOnUiThread(java.lang.Runnable {
                    progress_Bar.visibility = View.VISIBLE
                })
            }).start()
            Toast.makeText(this@ResetActivity, "Processing....", Toast.LENGTH_SHORT).show()
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                taskk ->
                if(taskk.isSuccessful) {
                    if(mAuth.currentUser!!.isEmailVerified)
                    {
                        Toast.makeText(
                            this@ResetActivity,
                            "Email is Already Verified",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent= Intent(this@ResetActivity,LoginActivity::class.java)
                        startActivity(intent)

                    }
                    else {
                        mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@ResetActivity,
                                    "Email Verification Link Sent Succesfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                Thread(Runnable {
                                    this@ResetActivity.runOnUiThread(java.lang.Runnable {
                                        progress_Bar.visibility = View.INVISIBLE
                                    })
                                }).start()
                            } else {
                                Toast.makeText(
                                    this@ResetActivity,
                                    "Error " + task.exception!!.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                Thread(Runnable {
                                    this@ResetActivity.runOnUiThread(java.lang.Runnable {
                                        progress_Bar.visibility = View.INVISIBLE
                                    })
                                }).start()
                            }
                        }
                    }
                    mAuth.signOut()


                }
                else
                {
                    Toast.makeText(
                        this@ResetActivity,
                        "Error " + taskk.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
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
