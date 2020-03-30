package com.example.lanjewartutorial

import android.app.ProgressDialog
import android.content.DialogInterface
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
    lateinit var nDialog: ProgressDialog
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
            nDialog = ProgressDialog.show(this,"The Tuition Centre","Signing in...",true);
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
                            nDialog.dismiss()
                            val dialogBuilder = android.app.AlertDialog.Builder(this)

                            dialogBuilder.setMessage("You have not yet verified your E-mail account. Please Check your inbox to verify.")
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
                    else
                    {

                        nDialog.dismiss()
                        val dialogBuilder = android.app.AlertDialog.Builder(this)

                        dialogBuilder.setMessage("Error : ${task.exception!!.message} !")
                            .setCancelable(false)
                            .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                                    dialog, id -> dialog.dismiss()
                            })

                        val alert = dialogBuilder.create()
                        alert.setTitle("The Tuition Centre")
                        mAuth.signOut()
                        alert.show()
                    }
                }
        }
    }
}
