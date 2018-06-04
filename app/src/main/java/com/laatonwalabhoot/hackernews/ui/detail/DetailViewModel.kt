package com.laatonwalabhoot.hackernews.ui.detail

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.laatonwalabhoot.hackernews.common.Constants
import com.laatonwalabhoot.hackernews.data.remote.ApiService
import com.laatonwalabhoot.hackernews.data.models.Article
import com.laatonwalabhoot.hackernews.data.models.Comment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {

    private lateinit var article: Article
    private var apiStatus: MutableLiveData<String> = MutableLiveData()
    private lateinit var disposableObserver: DisposableObserver<Comment>
    private lateinit var onDataChangeListener: OnDataChangeListener


    fun setArticleForFragments(article: Article) {
        this.article = article
    }

    fun getArticleForFragments(): Article {
        return article
    }

    @SuppressLint("CheckResult")
    fun initList(apiService: ApiService) {
        apiStatus.postValue(Constants.STATUS_START)
        disposableObserver = Observable.fromIterable(article.kids)
                .flatMap {
                    apiService.getComment(it)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Comment>() {
                    override fun onComplete() {
                        apiStatus.postValue(Constants.STATUS_COMPLETE)
                    }

                    override fun onNext(t: Comment) {
                        apiStatus.postValue(Constants.STATUS_NEXT)
                        onDataChangeListener.onDataChange(t)
                    }

                    override fun onError(e: Throwable) {
                        apiStatus.postValue(Constants.STATUS_ERROR
                                .plus(" : ")
                                .plus(e.message))
                    }
                })
    }

    fun getApiStatus(): MutableLiveData<String> {
        /*
         * Aishwarya: Listener for data changes
         * Live mutable data will emit response
         * according to which we will populate comments
         */
        return apiStatus
    }

    fun stopApiRequest() {
        if (::disposableObserver.isInitialized) {
            disposableObserver.dispose()
        }
        apiStatus.postValue(Constants.STATUS_STOP)
    }

    fun setOnDataChangeListener(onDataChangeListener: OnDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener
    }

    interface OnDataChangeListener {
        fun onDataChange(comment: Comment)
    }
}