package com.example.lanjewartutorial.ui.login
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.lanjewartutorial.LoginActivity
import com.example.lanjewartutorial.R
import com.example.lanjewartutorial.RegisterActivity

class LoginFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val login : Button =  root.findViewById(R.id.log)
        val reg: Button =  root.findViewById(R.id.regme)
        reg.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, RegisterActivity::class.java)
            startActivity(intent)
        })
        login.setOnClickListener(View.OnClickListener {
            val intent =  Intent(activity, LoginActivity::class.java)

            startActivity(intent)


        })
        return root
    }
}
