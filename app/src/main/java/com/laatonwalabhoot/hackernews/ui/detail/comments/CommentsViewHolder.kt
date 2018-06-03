package com.laatonwalabhoot.hackernews.ui.detail.comments

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.data.models.Comment
import com.laatonwalabhoot.hackernews.utils.TimeUpdateUtils

class CommentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var timeStamp: TextView = view.findViewById(R.id.time_stamp)
    private var commentText: TextView = view.findViewById(R.id.comment_text)

    fun setComment(comment: Comment) {
        timeStamp.text = TimeUpdateUtils.getInstance().getDateFromTime(comment.time.toString())
                .plus(" - ")
                .plus(TimeUpdateUtils.getInstance().getFormattedTimeFromTime(comment.time.toString()))
                .plus(" · ")
                .plus(comment.by)
        commentText.text = comment.text
    }
}