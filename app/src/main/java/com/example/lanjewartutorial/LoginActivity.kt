package com.example.lanjewartutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.login_layout.*

class LoginActivity : AppCompatActivity()
{
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth= FirebaseAuth.getInstance()


        reset.setOnClickListener{
            val intent=Intent(this@LoginActivity,ResetActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener{
            loginUser()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent=Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)

    }

    private fun loginUser() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        if(email.isEmpty())
        {
            et_email.setError("Field Required")
        }
        else if(password.isEmpty())
        {

            et_password.setError("Field Required")
        }
        else
        {
            Thread(Runnable {
                this@LoginActivity.runOnUiThread(java.lang.Runnable {
                    progress_Bar.visibility = View.VISIBLE
                })
            }).start()
            Toast.makeText(this@LoginActivity,"Signing in , Please Wail...",Toast.LENGTH_LONG).show()


            mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    task->
                    if(task.isSuccessful)
                    {
                        if (mAuth.currentUser!!.isEmailVerified){
                        val intent = Intent (this@LoginActivity,ProfileActivity::class.java)
                            intent.putExtra("email",email)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)}
                        else
                        {

                            Toast.makeText(this@LoginActivity,"User Not Yet Verified",Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                            Thread(Runnable {
                                // dummy thread mimicking some operation whose progress cannot be tracked

                                // display the indefinite progressbar
                                this@LoginActivity.runOnUiThread(java.lang.Runnable {
                                    progress_Bar.visibility = View.INVISIBLE
                                })
                            }).start()
                        }
                    }
                    else
                    {
                        Toast.makeText(this@LoginActivity,"Error "+task.exception!!.message,Toast.LENGTH_SHORT).show()
                        mAuth.signOut()
                        Thread(Runnable {
                            // dummy thread mimicking some operation whose progress cannot be tracked

                            // display the indefinite progressbar
                            this@LoginActivity.runOnUiThread(java.lang.Runnable {
                                progress_Bar.visibility = View.INVISIBLE
                            })
                        }).start()
                    }
                }
        }
    }
}
