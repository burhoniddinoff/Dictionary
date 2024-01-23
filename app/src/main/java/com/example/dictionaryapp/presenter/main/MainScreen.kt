package com.example.dictionaryapp.presenter.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.ScreenMainBinding
import com.example.dictionaryapp.presenter.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private var adapter: ViewPagerAdapter? = null
    private val item = arrayListOf("Dictionary", "Speak")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = item[pos]
        }.attach()
    }
}