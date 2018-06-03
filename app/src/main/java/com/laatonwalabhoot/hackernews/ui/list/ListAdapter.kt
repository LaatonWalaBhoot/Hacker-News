package com.laatonwalabhoot.hackernews.ui.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.data.managers.db.RealmDatabaseManager
import com.laatonwalabhoot.hackernews.data.models.Article
import io.realm.RealmChangeListener

class ListAdapter : RecyclerView.Adapter<ListViewHolder>(),
        RealmChangeListener<List<Article>>,
        ListViewHolder.OnArticleClickListener {

    private var list: List<Article> = ArrayList()
    private var onItemClickListener: OnItemClickListener? = null

    init {
        RealmDatabaseManager.getInstance().setChangeListener(this)
    }

    /************************************
     * OVERRIDDEN METHODS
     ************************************/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_article, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.setArticle(list.get(index = position))
        holder.setOnArticleClickListener(this)
    }

    override fun getItemCount(): Int {
        return when (list.size) {
            0 -> 0
            else -> list.size
        }
    }

    override fun onArticleClick(listViewHolder: ListViewHolder, position: Int) {
        onItemClickListener!!.onItemClick(list[position])
    }

    override fun onChange(t: List<Article>) {
        list = t
        notifyDataSetChanged()
    }

    /************************************
     * PUBLIC METHODS
     ************************************/
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    /************************************
     * PUBLIC INTERFACE to handle click event
     ************************************/
    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }
}