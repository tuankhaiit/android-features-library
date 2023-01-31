package com.tuankhaiit.androidfeatureslibrary.simple_bottom_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tuankhaiit.androidfeatureslibrary.simplebottommenu.R
import com.tuankhaiit.androidfeatureslibrary.simplebottommenu.databinding.FragmentSimpleeBottomNavigationBinding
import com.tuankhaiit.core.presentation.fragment.BaseFragment

class SimpleBottomNavigationFragment : BaseFragment() {

    private lateinit var binding: FragmentSimpleeBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimpleeBottomNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navView: BottomNavigationView = binding.bottomNav

        val navController = findNavController(requireActivity(), R.id.navHostSimpleBottomNavigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(requireBaseActivity(), navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    companion object {
        fun newInstance() : SimpleBottomNavigationFragment {
            return SimpleBottomNavigationFragment()
        }
    }
}