package com.example.lanjewartutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.widget.Toolbar;
import com.example.lanjewartutorial.proFrag.academic
import com.example.lanjewartutorial.proFrag.student_personal
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.pro_tem.*

class ProfileActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var user:FirebaseUser
    lateinit var refUsers: DatabaseReference
    private var firebaseUserID:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        mAuth = FirebaseAuth.getInstance()

        val bundle:Bundle? = intent.extras
        val email=bundle!!.getString("email")
        val password = bundle!!.getString("password")

        mAuth.signInWithEmailAndPassword(email!!,password!!).addOnCanceledListener{
            val intent= Intent(this@ProfileActivity,LoginActivity::class.java)
            startActivity(intent)
        }
        setSupportActionBar(toolbar_profile)
        user_name.text="aa"
        val toolbar:Toolbar=findViewById(R.id.toolbar_profile)
        setSupportActionBar(toolbar_profile)
        supportActionBar!!.title=""


        val tabLayout:TabLayout =findViewById(R.id.tab_layout)
        val viewPager :ViewPager = findViewById(R.id.view_pager)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(student_personal(),"About Me")
        viewPagerAdapter.addFragment(academic(),"Academic")
        viewPager.adapter=viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        logout.setOnClickListener{
            mAuth.signOut()
            val intent= Intent(this@ProfileActivity,LoginActivity::class.java)
            startActivity(intent)

        }

        refUsers = FirebaseDatabase.getInstance().reference.child(mAuth.currentUser.toString())

    }


    internal class ViewPagerAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {

        private val fragments:ArrayList<Fragment>
        private val titles: ArrayList<String>

        init {
            fragments= ArrayList<Fragment>()
            titles= ArrayList<String>()

        }
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }


        fun addFragment(fragment: Fragment,title:String)
        {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return  titles[position]
        }


    }
}

