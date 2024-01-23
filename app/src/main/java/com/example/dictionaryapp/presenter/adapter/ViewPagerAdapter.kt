package com.example.dictionaryapp.presenter.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dictionaryapp.presenter.home.HomeScreen
import com.example.dictionaryapp.presenter.speak.SpeakScreen

class ViewPagerAdapter(fr: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fr, lifecycle) {

    private val page1 = HomeScreen()
    private val page2 = SpeakScreen()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> page1
            else -> page2
        }
    }
}