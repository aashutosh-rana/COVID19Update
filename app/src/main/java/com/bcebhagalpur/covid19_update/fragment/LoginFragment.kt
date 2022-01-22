package com.bcebhagalpur.covid19_update.fragment

import android.content.Context
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.bcebhagalpur.covid19_update.R
import com.bcebhagalpur.covid19_update.activity.ForgotPasswordActivity
import com.bcebhagalpur.covid19_update.activity.MainActivity
import com.bcebhagalpur.covid19_update.activity.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private val TAG = "LoginActivity"

    //global variables
    private lateinit var email: String
    private lateinit var password: String

    //UI elements
    private lateinit var tvForgotPassword: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnCreateAccount: Button
    private lateinit var mProgressBar: ProgressDialog

    //Firebase references
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view= inflater.inflate(R.layout.fragment_login, container, false)



        tvForgotPassword = view.findViewById(R.id.tv_forgot_password)
        etEmail =view.findViewById(R.id.et_email)
        etPassword =view.findViewById(R.id.et_password)
        btnLogin =view.findViewById(R.id.btn_login)
        btnCreateAccount =view.findViewById(R.id.btn_register_account)
        btnCreateAccount.setOnClickListener { startActivity(Intent(context,RegistrationActivity::class.java)) }
        tvForgotPassword
            .setOnClickListener { startActivity(
                Intent(context, ForgotPasswordActivity::class.java)
            ) }
        initialise()
        return view
    }

    private fun initialise() {
        mProgressBar = ProgressDialog(activity as Context)
        mAuth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener { loginUser() }
    }

    private fun loginUser() {

        email = etEmail.text.toString()
        password = etPassword.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgressBar.setMessage("Logging User...")
            mProgressBar.show()

            Log.d(TAG, "Logging in user.")

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    mProgressBar.hide()

                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        updateUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(context, "Password and Email doesn't match",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(context, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUI() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
