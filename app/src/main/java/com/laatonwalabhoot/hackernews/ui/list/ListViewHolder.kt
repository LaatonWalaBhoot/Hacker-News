package com.laatonwalabhoot.hackernews.ui.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.data.models.Article
import com.laatonwalabhoot.hackernews.utils.TimeUpdateUtils


class ListViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        view.setOnClickListener(this)
    }

    private var score: TextView = view.findViewById(R.id.score)
    private var title: TextView = view.findViewById(R.id.title)
    private var url: TextView = view.findViewById(R.id.url)
    private var comments: TextView = view.findViewById(R.id.comments)
    private var time: TextView = view.findViewById(R.id.time)
    private var onArticleClickListener: OnArticleClickListener? = null

    /************************************
     * OVERRIDDEN METHODS
     ************************************/
    override fun onClick(v: View?) {
        onArticleClickListener!!.onArticleClick(this, position = adapterPosition)
    }

    /************************************
     * PUBLIC METHODS
     ************************************/
    fun setArticle(article: Article) {
        score.text = article.score
        title.text = article.title
        comments.text = article.descendants
        url.text = article.url
        time.text = TimeUpdateUtils.getInstance().getTimeDifference(article.time!!)
                .plus(" · ")
                .plus(article.by)
    }

    fun setOnArticleClickListener(onArticleClickListener: OnArticleClickListener) {
        this.onArticleClickListener = onArticleClickListener
    }

    /************************************
     * PUBLIC INTERFACE to handle click event
     ************************************/
    interface OnArticleClickListener {
        fun onArticleClick(listViewHolder: ListViewHolder, position: Int)
    }
}