package com.example.lanjewartutorial.proFrag.update

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.lanjewartutorial.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_update.*


class UpdateFragment : Fragment(),View.OnClickListener {

    var storage = FirebaseStorage.getInstance();
    lateinit var mStorageReference: StorageReference;
    private lateinit var btnUpload: Button
    private lateinit var btnChoose: Button
    lateinit var imageView: ImageView
    private lateinit var filePath: Uri
    private var PICK_IMAGE_REQUEST: Int = 1
    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var mImageUri: Uri
    lateinit var imgBit: Bitmap
    lateinit var inn: TextView
    lateinit var refUsers: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        val root = inflater.inflate(R.layout.fragment_update, container, false)

        mAuth = FirebaseAuth.getInstance()
        val userr = mAuth.currentUser

//        var choose:Button=root.findViewById(R.id.cho)
//        var upload:Button=root.findViewById(R.id.up_date)
//        inn = root.findViewById(R.id.t)
//
//        choose.setOnClickListener{
//            openFileChooser()
//
//
//        }
//        upload.setOnClickListener{
//            uploading.visibility=View.VISIBLE
//            //uploadFile()
//        }
//
//        refUsers = FirebaseDatabase.getInstance().reference.child("Users")
//        mStorageReference=FirebaseStorage.getInstance().getReference("Users/${mAuth.currentUser?.uid}")

        val btn_personal_details_update: Button = root.findViewById(R.id.personal_details)
        val btn_contact_details_update: Button = root.findViewById(R.id.contact)
        val btn_profile_picture_update: Button = root.findViewById(R.id.pp)
        btn_personal_details_update.setOnClickListener(this)
        btn_contact_details_update.setOnClickListener(this)
        return root

    }


//    private fun uploadFile() {
//
//        var fire: StorageReference =
//            mStorageReference.child(mAuth.currentUser!!.uid.toString() + ".jpg")
//        fire.putFile(mImageUri).addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot?> {snapshot->
//            val downloadUrl: String = fire.get
//
//            inn.text=downloadUrl
//
//            refUsers.child(mAuth.currentUser!!.uid).child("profile").setValue(downloadUrl)
//
//
//            Toast.makeText(context, downloadUrl.toString()+"Upload Done", Toast.LENGTH_LONG).show()
//            uploading.visibility=View.GONE
//        })
//
//
//
//    }



    override fun onClick(v: View?) {
        lateinit var fragment : Fragment

        when(v!!.id)
        {
            R.id.personal_details ->
            {
                fragment = PersonalDetailsUpdater()
                replaceFragment(fragment)
            }
            R.id.contact ->
            {
                fragment = ContactDetailsUpdater()
                replaceFragment(fragment)
            }
            R.id.course ->
            {
               // fragment =
                replaceFragment(fragment)
            }
        }
    }

//    private fun openFileChooser() {
//        val intent = Intent()
//        intent.setType("image/*")
//        intent.setAction(Intent.ACTION_GET_CONTENT)
//        startActivityForResult(intent,PICK_IMAGE_REQUEST)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
//    {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK  && data!==null && data!!.data!=null)
//        {
//            mImageUri = data!!.data!!
//
//            Glide.with(this@UpdateFragment).load(mImageUri).into(pr0)
//
//
//
//        }
//    }

    private fun replaceFragment(somefragment: Fragment) {
        var transition : FragmentTransaction =fragmentManager!!.beginTransaction()
        transition.replace(R.id.nav_host_fragment_profile,somefragment)
        transition.addToBackStack(null)
        transition.commit()
    }

}
