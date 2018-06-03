package com.laatonwalabhoot.hackernews.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.common.Constants
import com.laatonwalabhoot.hackernews.data.models.Article
import com.laatonwalabhoot.hackernews.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), ListAdapter.OnItemClickListener {

    private lateinit var listViewModel: ListViewModel
    private var listAdapter: ListAdapter = ListAdapter()

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
        setAdapter()
        setSubscriber()
        fab_btn.setOnClickListener {
            when (listViewModel.getApiStatus().value) {
                Constants.STATUS_START -> {
                    stopWorking()
                }
                else -> {
                    listViewModel.initCall()
                    startWorking()
                }
            }
        }
    }

    override fun onItemClick(article: Article) {
        startActivity(DetailActivity.newIntent(context!!, article))
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
        listViewModel.initList()
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