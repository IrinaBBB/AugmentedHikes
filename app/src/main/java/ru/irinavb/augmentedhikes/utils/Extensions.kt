package ru.irinavb.augmentedhikes.utils

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.irinavb.augmentedhikes.R

fun Fragment.openFragment(fragment: Fragment) {
    (activity as AppCompatActivity).supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        .replace(R.id.nav_host_fragment_activity_main, fragment)
        .commit()
}

fun AppCompatActivity.openFragment(fragment: Fragment) {
    Log.d("MyLog", "Frag name: ${fragment.javaClass}")
    if (supportFragmentManager.fragments.isNotEmpty()) {
        if (supportFragmentManager.fragments[0].javaClass == fragment.javaClass) return
    }
    supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        .replace(R.id.nav_host_fragment_activity_main, fragment)
        .commit()
}

fun Fragment.showToast(text: String) {
    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}