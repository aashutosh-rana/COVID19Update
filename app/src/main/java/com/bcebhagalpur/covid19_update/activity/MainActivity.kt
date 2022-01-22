package com.bcebhagalpur.covid19_update.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bcebhagalpur.covid19_update.*
import com.bcebhagalpur.covid19_update.fragment.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

//    private var doubleBackToExitPressedOnce: Boolean = false
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var toolBar: androidx.appcompat.widget.Toolbar
    private lateinit var frameLayout: FrameLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolBar = findViewById(R.id.toolBar)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)

        var previousMenuItem: MenuItem? = null

        setUpToolbar()

        openHome()
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity
            , drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener{

            if (previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId){
                R.id.home ->{ openHome()
                    drawerLayout.closeDrawers()
                }
                R.id.safetyTips ->{

                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,SafetyTipsFragment()).commit()
                    supportActionBar?.title="Safety Tips"
                    drawerLayout.closeDrawers()

                }
                R.id.login ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, LoginFragment()).commit()
                    supportActionBar?.title="Login"
                    drawerLayout.closeDrawers()

                }
                R.id.share ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ShareFragment()).commit()
                    supportActionBar?.title="Share"
                    drawerLayout.closeDrawers()
                }
                R.id.profile ->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, ProfileFragment()).commit()
                        supportActionBar?.title = "My Profile"
                        drawerLayout.closeDrawers()
                }
                R.id.contactUs ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ContactUsFragment()).commit()
                    supportActionBar?.title="Developer Contact"
                    drawerLayout.closeDrawers()
                }
            }

            return@setNavigationItemSelectedListener true

        }

    }


    private fun setUpToolbar(){
        setSupportActionBar(toolBar)
        supportActionBar?.title="Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }
    private fun openHome(){
        val fragment= HomeFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
        supportActionBar?.title="Covid_19 Update"
        navigationView.setCheckedItem(R.id.home)
    }

    override fun onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            return super.onBackPressed()
//        }
//            this.doubleBackToExitPressedOnce = true
//            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
//            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        drawerLayout.closeDrawers()
        when(supportFragmentManager.findFragmentById(R.id.frameLayout)){
            !is HomeFragment ->openHome()
            else ->super.onBackPressed()
        }
    }
}


