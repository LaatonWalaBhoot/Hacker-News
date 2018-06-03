package com.laatonwalabhoot.hackernews.ui.detail.comments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.common.Constants
import com.laatonwalabhoot.hackernews.ui.detail.DetailViewModel
import kotlinx.android.synthetic.main.fragment_comments.*

class CommentFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private val commentsAdapter: CommentsAdapter = CommentsAdapter()

    companion object {
        fun newInstance() = CommentFragment()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        detailViewModel = ViewModelProviders.of(activity!!).get(DetailViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setSubscriber()
    }

    private fun setAdapter() {
        comments_list.layoutManager = LinearLayoutManager(context)
        comments_list.adapter = commentsAdapter
        detailViewModel.setOnDataChangeListener(commentsAdapter.getListener())
        detailViewModel.initList()
    }

    private fun setSubscriber() {
        val apiStatusObserver = Observer<String> {
            when {
                it.equals(Constants.STATUS_START) -> {
                    progress!!.visibility = View.VISIBLE
                }
                it.equals(Constants.STATUS_COMPLETE) -> {
                    progress!!.visibility = View.GONE
                }
                it.equals(Constants.STATUS_NEXT) -> {
                    progress!!.visibility = View.GONE
                }
                it!!.contains(Constants.STATUS_ERROR) -> {
                    progress!!.visibility = View.GONE
                    placeholder!!.visibility = View.VISIBLE
                }
            }
        }
        detailViewModel.getApiStatus().observe(this, apiStatusObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        detailViewModel.stopApiRequest()
    }
}