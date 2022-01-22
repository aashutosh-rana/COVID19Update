package com.bcebhagalpur.covid19_update.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import com.bcebhagalpur.covid19_update.R

class ShareFragment : Fragment() {

    private lateinit var btnShareThisApp:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_share, container, false)
        btnShareThisApp=view.findViewById(R.id.btnShareThisApp)
        btnShareThisApp.setOnClickListener { share() }
        share()
        return view
    }

    private fun share() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Covid-19 updater")
            var shareMessage = "\nLet me recommend you Covid-19 updater application\n\n"
            shareMessage =
                """
                    ${shareMessage}https://github.com/aashutosh-rana/Covid-19-Updater/blob/master/app-release.apk
                    """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            Toast.makeText(context, "some error occurred", Toast.LENGTH_SHORT).show()
        }
    }
}
