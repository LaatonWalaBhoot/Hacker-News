package com.laatonwalabhoot.hackernews.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.laatonwalabhoot.hackernews.HackerNews
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.common.Constants
import com.laatonwalabhoot.hackernews.data.models.Article
import com.laatonwalabhoot.hackernews.data.remote.ApiService
import com.laatonwalabhoot.hackernews.di.components.DaggerListFragmentComponent
import com.laatonwalabhoot.hackernews.di.components.ListFragmentComponent
import com.laatonwalabhoot.hackernews.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_list.*
import retrofit2.Retrofit
import javax.inject.Inject

class ListFragment : Fragment(), ListAdapter.OnItemClickListener {

    private lateinit var listViewModel: ListViewModel
    private lateinit var component: ListFragmentComponent

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var listAdapter: ListAdapter

    @Inject
    lateinit var gson: Gson


    companion object {
        fun newInstance() = ListFragment()
    }

    /************************************
     * OVERRIDDEN METHODS
     ************************************/
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        component = DaggerListFragmentComponent.builder()
                .appComponent(HackerNews.newInstance()
                        .getApp(activity as ListActivity)
                        .getAppComponent())
                .build()
        component.injectListFragment(this)
        setAdapter()
        setSubscriber()
        fab_btn.setOnClickListener {
            when (listViewModel.getApiStatus().value) {
                Constants.STATUS_START -> {
                    stopWorking()
                }
                else -> {
                    listViewModel.initCall(apiService)
                    startWorking()
                }
            }
        }
    }

    override fun onItemClick(article: Article) {
        startActivity(Intent(context, DetailActivity::class.java)
                .putExtra(Constants.EXTRA_ARTICLE, gson.toJson(article)))
    }

    override fun onPause() {
        when(listViewModel.getApiStatus().value == Constants.STATUS_START) {
            true -> stopWorking()
        }
        super.onPause()
    }

    /************************************
     * PRIVATE METHODS
     ************************************/
    private fun setAdapter() {
        articles_list.layoutManager = LinearLayoutManager(context)
        articles_list.adapter = listAdapter
        listAdapter.setOnItemClickListener(this)
        listViewModel.initList(apiService)
    }

    private fun setSubscriber() {
        val apiStatusObserver = Observer<String> {
            when {
                it.equals(Constants.STATUS_START) -> {
                    startWorking()
                }
                it.equals(Constants.STATUS_COMPLETE) -> {
                    stopWorking()
                }
                it!!.contains(Constants.STATUS_ERROR) -> {
                    stopWorking()
                }
            }
        }
        listViewModel.getApiStatus().observe(this, apiStatusObserver)
    }

    private fun startWorking() {
        progress.visibility = View.VISIBLE
        last_updated.text = getString(R.string.working)
        fab_btn.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_cancel))
    }

    private fun stopWorking() {
        listViewModel.stopApiRequest()
        progress.visibility = View.GONE
        last_updated.text = getString(R.string.updated)
        fab_btn.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_refresh))
    }
}