package com.noviherlambang.submission3githubuser.ui.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.noviherlambang.submission3githubuser.R
import com.noviherlambang.submission3githubuser.ui.fragment.FollowerFragment
import com.noviherlambang.submission3githubuser.ui.fragment.FollowingFragment
import com.noviherlambang.submission3githubuser.ui.fragment.RepositoryFragment

class SectionPagerAdapter(private val mCtx: Context, fm: FragmentManager, data: Bundle) :
    FragmentPagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    @StringRes
    private val TAB_TITLE = intArrayOf(R.string.tab_1,
        R.string.tab_2, R.string.tab_3
    )
    override fun getCount(): Int = 3
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = RepositoryFragment()
            1 -> fragment = FollowerFragment()
            2 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }



    override fun getPageTitle(position: Int): CharSequence? {
        return mCtx.resources.getString(TAB_TITLE[position])
    }
}