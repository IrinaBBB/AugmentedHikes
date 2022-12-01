package ru.irinavb.augmentedhikes.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.irinavb.augmentedhikes.R
import ru.irinavb.augmentedhikes.databinding.ActivityMainBinding
import ru.irinavb.augmentedhikes.ui.sensors.accelerometer.AccelerometerFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val accelerometerFragment = AccelerometerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_accelerometer -> {
                navController.navigate(R.id.navigation_accelerometer)
            }
            R.id.option_magnetometer -> {
                navController.navigate(R.id.navigation_magnetometer)
            }
            R.id.option_gyroscope -> {
                navController.navigate(R.id.navigation_gyroscope)
            }
            R.id.option_orientation -> {
                navController.navigate(R.id.navigation_orientation)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }
}