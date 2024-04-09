package com.example.dictionaryapp.presenter.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPagerAdapter(
    fm: Fragment,
    private val firstFragment: Fragment,
    private val secondFragment: Fragment,
) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> firstFragment
        else -> secondFragment

    }

}
