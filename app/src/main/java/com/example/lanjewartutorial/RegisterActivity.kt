package com.example.lanjewartutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.register_layout.*

class RegisterActivity : AppCompatActivity() {

    lateinit var mAuth:FirebaseAuth
    lateinit var refUsers:DatabaseReference
    private var firebaseUserID:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        btn_register.setOnClickListener{
            registerUser()
        }
    }

    private fun registerUser()
    {
        val username:String=et_reg_name.text.toString()
        val email:String=et_reg_email.text.toString()
        val password:String=et_reg_password.text.toString()
        val passcon:String=et_reg_passwordcon.text.toString()

        if(username.isEmpty())
        {
            et_reg_name.setError("Field Required")
        }
        else if(username.length>10)
        {
            et_reg_name.setError("Username Cannot be greater than 10 character.")
        }
        else if(email.isEmpty())
        {
            et_reg_email.setError("Field Required")
        }
        else if(password.isEmpty())
        {
            et_reg_password.setError("Field Required")
        }
        else if(password.length<8)
        {
             et_reg_password.setError("Password Too Short")
        }
        else if(!passcon.equals(password))
        {
            et_reg_passwordcon.setError("Password doesn't match")
        }
        else
        {
            Thread(Runnable {
                // dummy thread mimicking some operation whose progress cannot be tracked

                // display the indefinite progressbar
                this@RegisterActivity.runOnUiThread(java.lang.Runnable {
                    progress_Bar.visibility = View.VISIBLE
                })
            }).start()

            Toast.makeText(this@RegisterActivity,"Registration In Process, Please Wail...",Toast.LENGTH_LONG).show()
            mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful) {
                        mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener{task ->

                            if(task.isSuccessful) {
                                firebaseUserID = mAuth.currentUser!!.uid
                                refUsers = FirebaseDatabase.getInstance().reference.child("Users")
                                    .child(firebaseUserID)
                                val userHashMap = HashMap<String, Any>()
                                userHashMap["uid"] = firebaseUserID
                                userHashMap["username"] = username
                                userHashMap["profile"] =
                                    "https://firebasestorage.googleapis.com/v0/b/tuition-center-fbadb.appspot.com/o/ava.png?alt=media&token=0ce7daa5-2361-4efa-af30-42f4ea0a04f9"
                                userHashMap["status"] = "offline"
                                userHashMap["search"] = username.toLowerCase()
                                userHashMap["facebook"] = "https://m.facebook.com"
                                userHashMap["dob"] = "01/01/2001"
                                userHashMap["course"] = "none"
                                userHashMap["class"] = "none"
                                userHashMap["studentmobile"] = ".."
                                userHashMap["fathermobile"] = ".."
                                userHashMap["studentemail"] = email
                                userHashMap["fatheremail"] = ".."
                                userHashMap["currentaddress"] = ".."
                                userHashMap["permanantaddress"] = ".."
                                userHashMap["city"] = ".."
                                userHashMap["aadhar"] = ".."
                                userHashMap["name"] = ".."
                                userHashMap["fname"] = ".."
                                userHashMap["mname"] = ".."
                                userHashMap["lname"] = ".."
                                userHashMap["gender"]=".."
                                userHashMap["lclassgrade"] = "0"
                                userHashMap["class10"] = "0"
                                userHashMap["class12"] = "0"
                                userHashMap["school/college"] = ""
                                refUsers.updateChildren(userHashMap)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val intent =
                                                Intent(this@RegisterActivity, MainActivity::class.java)
                                            startActivity(intent)
                                        }
                                    }
                                mAuth.signOut()
                                Toast.makeText(this@RegisterActivity, "A verification Link Is Sent To your E-Mail ID,Click Link To verify.", Toast.LENGTH_LONG).show()
                            }

                            }
                        }





                        else
                        Toast.makeText(
                            this@RegisterActivity,
                            "Error" + task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    Thread(Runnable {
                        this@RegisterActivity.runOnUiThread(java.lang.Runnable {
                            progress_Bar.visibility = View.INVISIBLE
                        })
                    }).start()

                }
        }
    }
}
