package com.rildev.projectuas

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

//class ini buat ngatur viewpager biar bisa gerak-gerak gitu
//geser kanan, geser kiri
class MyAdapter(val activity: AppCompatActivity, val fragments:ArrayList<Fragment>)
    : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return fragments.size
    }
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}