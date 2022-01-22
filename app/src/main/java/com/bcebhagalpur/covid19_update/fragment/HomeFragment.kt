package com.bcebhagalpur.covid19_update.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.bcebhagalpur.covid19_update.R
import com.bcebhagalpur.covid19_update.activity.StartActivity
import com.bcebhagalpur.covid19_update.activity.WorldMeterActivity

class HomeFragment : Fragment() {
    lateinit var btnIndia:Button
    lateinit var btnWorld:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view =inflater.inflate(R.layout.fragment_home, container, false)

        btnIndia=view.findViewById(R.id.btnIndia)
        btnWorld=view.findViewById(R.id.btnWorld)

        btnIndia.setOnClickListener {
            val intent= Intent(context,StartActivity::class.java)
            startActivity(intent)
        }
        btnWorld.setOnClickListener {
            val intent= Intent(context,WorldMeterActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}
