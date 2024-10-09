package com.example.dsladapterdemo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeChildBrahFragmentAdapter(
    private var items: List<String>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount() = items.size

    override fun createFragment(position: Int): Fragment {
       return when (position){
           0 -> ExploreBrvahFragment.newInstance()
           1 -> SquareBrvahFragment.newInstance()
           else -> ExploreBrvahFragment.newInstance()
       }
    }

}