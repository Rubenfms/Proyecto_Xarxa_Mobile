package com.xarxa.proyecto_xarxa_mobile

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.math.MathUtils
import com.google.android.material.navigation.NavigationView
import com.xarxa.proyecto_xarxa_mobile.databinding.ActivityAccionesBinding

class AccionesActivity : AppCompatActivity() {

    private lateinit var navigationView: NavigationView
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var screen: FrameLayout
    private lateinit var binding: ActivityAccionesBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.bottomAppBar)

        navigationView = binding.navigationView
        bottomAppBar = binding.bottomAppBar
        screen = binding.screen
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        logicaNavigation()
    }

    private fun logicaNavigation() {
        val bottomSheetBehavior = BottomSheetBehavior.from(navigationView)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomAppBar.setNavigationOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                screen.visibility = View.VISIBLE
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                screen.visibility = View.GONE
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            navigationItemSelected(menuItem)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            screen.visibility = View.GONE
            true
        }

        screen.setOnClickListener {
            screen.visibility = View.GONE
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val baseColor = Color.BLACK
                val baseAlpha =
                    ResourcesCompat.getFloat(resources, R.dimen.material_emphasis_medium)
                val offset = (slideOffset - (-1f)) / (1f - (-1f)) * (1f - 0f) + 0f
                val alpha = MathUtils.lerp(0f, 255f, offset * baseAlpha).toInt()
                val color = Color.argb(alpha, baseColor.red, baseColor.green, baseColor.blue)
                screen.setBackgroundColor(color)
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }
        })
    }

    private fun navigationItemSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.principalOption -> {
                navController.navigate(R.id.action_global_principalFragment)
            }
            R.id.entregaOption -> {
                navController.navigate(R.id.entregaFragment)
            }
            R.id.devolucionOption -> {
                navController.navigate(R.id.devolucionFragment)
            }
            R.id.localizacionOption -> {
                navController.navigate(R.id.localizacionFragment)
            }
            R.id.busquedaLibrosOption -> {
                navController.navigate(R.id.busquedaLibrosFragment)
            }
        }

    }
}