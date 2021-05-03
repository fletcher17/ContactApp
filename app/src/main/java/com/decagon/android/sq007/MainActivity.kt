package com.decagon.android.sq007

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contactFragment = ContactFragment()

        setCurrentFragment(contactFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.mainActivity_bottomNavigation_view)

        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.contacts_bottomNav -> setCurrentFragment(contactFragment)
            }
            true
        }

    }

    fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction()
        .apply { replace(R.id.mainActivity_frameLayoutView, fragment)
                commit()
        }
}
