package com.breaksol.buttontapper.activities

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.breaksol.buttontapper.R
import com.breaksol.buttontapper.databinding.ActivityMainBinding
import com.breaksol.buttontapper.fragments.MainMenuFragment
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val fragmentManager = this.supportFragmentManager
        val fragment = MainMenuFragment.newInstance()
        val fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.flHolder, fragment)
        fragmentTransaction.commit()
    }

    fun replaceFragments(fragmentClass: Class<*>) {
        var fragment: Fragment? = null
        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // Insert the fragment by replacing any existing fragment
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragment != null) {
            fragmentManager.beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.flHolder, fragment)
                .commit()
        }
    }

}