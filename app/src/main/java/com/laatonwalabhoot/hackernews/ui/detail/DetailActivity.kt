package com.laatonwalabhoot.hackernews.ui.detail

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import com.laatonwalabhoot.hackernews.R
import kotlinx.android.synthetic.main.activity_detail.*
import com.google.gson.Gson
import com.laatonwalabhoot.hackernews.common.Constants
import com.laatonwalabhoot.hackernews.data.models.Article
import com.laatonwalabhoot.hackernews.ui.detail.comments.CommentFragment
import com.laatonwalabhoot.hackernews.utils.TimeUpdateUtils

class DetailActivity : AppCompatActivity() {

    private lateinit var detailPagerAdapter: DetailPagerAdapter
    private lateinit var article: Article
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        fun newIntent(context: Context, article: Article): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_ARTICLE, Gson().toJson(article))
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        initIntent(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initIntent(bundle: Bundle?) {
        article = Gson().fromJson(intent.getStringExtra(Constants.EXTRA_ARTICLE), Article::class.java)
        detailViewModel.setArticleForFragments(article)

        //initializing Top view
        title_text.text = article.title
        date_text.text = TimeUpdateUtils.getInstance().getDateFromTime(article.time!!)
        /*
         * Aishwarya: Differentiating between articles with Url
         * and without one and acting accordingly
         */
        when (TextUtils.isEmpty(article.url)) {
            true ->
                if (bundle == null) {
                    setupFragment()
                }
            false -> setupViewPager()
        }
    }

    private fun setupViewPager() {
        url_text.text = article.url
        container.visibility = View.GONE
        detailPagerAdapter = DetailPagerAdapter(supportFragmentManager,
                article.descendants!!.toInt())
        viewpager.adapter = detailPagerAdapter
        tabs.setupWithViewPager(viewpager)
    }

    private fun setupFragment() {
        url_text.visibility = View.GONE
        viewpager.visibility = View.GONE
        tabs.visibility = View.GONE
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, CommentFragment.newInstance())
                .commitNow()
    }
}