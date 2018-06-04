package com.laatonwalabhoot.hackernews.ui.list

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.laatonwalabhoot.hackernews.common.Constants
import com.laatonwalabhoot.hackernews.data.remote.ApiService
import com.laatonwalabhoot.hackernews.data.managers.db.RealmDatabaseManager
import com.laatonwalabhoot.hackernews.data.models.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.realm.RealmList

class ListViewModel : ViewModel() {

    private var apiStatus: MutableLiveData<String> = MutableLiveData()
    private lateinit var disposableObserver: DisposableObserver<Article>
    private lateinit var realmList: RealmList<Article>
    private lateinit var apiService: ApiService

    /************************************
     * PUBLIC METHODS
     ************************************/
    fun initCall(apiService: ApiService) {
        apiStatus.postValue(Constants.STATUS_START)
        disposableObserver = apiService.getTopArticles()
                .flatMap {
                    Observable.fromIterable(checkOverlappingIds(it))
                }
                .flatMap {
                    apiService.getArticle(it.toString())
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Article>() {
                    override fun onComplete() {
                        apiStatus.postValue(Constants.STATUS_COMPLETE)
                    }

                    override fun onNext(t: Article) {
                        RealmDatabaseManager.getInstance().addToRealm(t)
                    }

                    override fun onError(e: Throwable) {
                        apiStatus.postValue(Constants.STATUS_ERROR
                                .plus(" : ")
                                .plus(e.message))
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun initList(apiService: ApiService) {
        realmList = RealmList()
        return when (RealmDatabaseManager.getInstance().checkRealmStatus()) {
            true -> initCall(apiService)
            false -> RealmDatabaseManager.getInstance().getFromRealmDb()
        }
    }

    fun getApiStatus(): MutableLiveData<String> {
        return apiStatus
    }

    fun stopApiRequest() {
        if(::disposableObserver.isInitialized) {
            disposableObserver.dispose()
        }
        apiStatus.postValue(Constants.STATUS_STOP)
    }

    /************************************
     * PRIVATE METHODS
     ************************************/
    private fun checkOverlappingIds(list: List<Int>): List<Int> {
        return when (RealmDatabaseManager.getInstance().getAllData().size) {
            0 -> list
            else -> {
                val arrayList: ArrayList<Int> = ArrayList()
                val articleList: List<Article> = RealmDatabaseManager.getInstance().getAllData()
                arrayList.addAll(list)
                for (article: Article in articleList) {
                    if (arrayList.contains(article.id!!.toInt())) {
                        arrayList.remove(article.id!!.toInt())
                    }
                }
                arrayList
            }
        }
    }
}