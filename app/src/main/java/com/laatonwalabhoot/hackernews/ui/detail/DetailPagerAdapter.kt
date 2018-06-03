package com.laatonwalabhoot.hackernews.ui.detail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.laatonwalabhoot.hackernews.ui.detail.article.ArticleFragment
import com.laatonwalabhoot.hackernews.ui.detail.comments.CommentFragment

class DetailPagerAdapter(fragmentManager: FragmentManager, var int: Int) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CommentFragment.newInstance()
            else -> ArticleFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> when(int) {
                0-> "Comments"
                1-> int.toString().plus(" Comment")
                else -> int.toString().plus(" Comments")
            }
            else -> "Article"
        }
    }

    override fun getCount(): Int {
        return 2
    }
}