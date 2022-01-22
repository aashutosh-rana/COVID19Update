package com.bcebhagalpur.covid19_update.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bcebhagalpur.covid19_update.BuildConfig

import com.bcebhagalpur.covid19_update.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth

    private lateinit var tvFirstName: TextView
    private lateinit var tvLastName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvEmailVerified: TextView
    private lateinit var btnShareThisApp: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_profile, container, false)

        mDatabase =FirebaseDatabase.getInstance()
        mDatabaseReference =mDatabase.reference.child("Users")
        mAuth =FirebaseAuth.getInstance()
        tvFirstName =view.findViewById(R.id.tv_first_name)
        tvLastName =view.findViewById(R.id.tv_last_name)
        tvEmail =view.findViewById(R.id.tv_email)
        tvEmailVerified =view.findViewById(R.id.tv_email_verifiied)
        btnShareThisApp=view.findViewById(R.id.btnShareThisApp)
        btnShareThisApp.setOnClickListener { share() }

        return view
    }

    override fun onStart() {
        try {
            super.onStart()
            val mUser = mAuth.currentUser
            val mUserReference = mDatabaseReference.child(mUser!!.uid)
            tvEmail.text = mUser.email
            tvEmailVerified.text = mUser.isEmailVerified.toString()

            mUserReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    tvFirstName.text = snapshot.child("firstName").value as String
                    tvLastName.text = snapshot.child("lastName").value as String

//                        btnSignOut.setOnClickListener { tvFirstName.text=snapshot.child("").value as String
//                        tvLastName.text=snapshot.child("").value as String
//                        mAuth.signOut() }

                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }catch (e:KotlinNullPointerException){
            Toast.makeText(activity as Context,"Login Or SignUp to see your profile",Toast.LENGTH_LONG).show()
        }
    }
    private fun share() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Covid-19 updater")
            var shareMessage = "\nLet me recommend you Covid-19 updater application\n\n"
            shareMessage =
                """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                    
                    
                    """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            Toast.makeText(context, "some error occurred", Toast.LENGTH_SHORT).show()
        }
    }

}
