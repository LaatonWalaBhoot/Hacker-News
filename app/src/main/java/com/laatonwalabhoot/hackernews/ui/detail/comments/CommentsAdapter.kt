package com.laatonwalabhoot.hackernews.ui.detail.comments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.data.models.Comment
import com.laatonwalabhoot.hackernews.ui.detail.DetailViewModel

class CommentsAdapter : RecyclerView.Adapter<CommentsViewHolder>(), DetailViewModel.OnDataChangeListener {

    private var list: ArrayList<Comment> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_comment, parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.setComment(list[position])
    }

    override fun getItemCount(): Int {
        return when (list.size) {
            0 -> 0
            else -> list.size
        }
    }

    override fun onDataChange(comment: Comment) {
        list.add(comment)
        notifyDataSetChanged()
    }

    fun getListener(): DetailViewModel.OnDataChangeListener {
        return this
    }
}