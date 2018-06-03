package com.laatonwalabhoot.hackernews.data.managers.db

import com.laatonwalabhoot.hackernews.data.models.Article
import io.realm.Realm
import io.realm.RealmChangeListener

class RealmDatabaseManager {

    private lateinit var changeListener: RealmChangeListener<List<Article>>

    companion object {
        private val realmDatabase: RealmDatabaseManager = RealmDatabaseManager()

        @Synchronized
        fun getInstance(): RealmDatabaseManager {
            return realmDatabase
        }
    }

    /************************************
     * PUBLIC METHODS
     ************************************/
    fun checkRealmStatus(): Boolean {
        val realm: Realm = Realm.getDefaultInstance()
        return when {
            realm.isEmpty -> {
                realm.close()
                true
            }
            else -> {
                realm.close()
                false
            }
        }
    }

    fun addToRealm(article: Article) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.use { _ ->
            realm.executeTransaction(Realm.Transaction {
                run {
                    realm.copyToRealm(article)
                }
            })
            changeListener.onChange(realm.copyFromRealm(realm.where(Article::class.java).findAll()))
        }
    }

    fun getFromRealmDb() {
        val realm: Realm = Realm.getDefaultInstance()
        changeListener.onChange(realm.copyFromRealm(realm.where(Article::class.java).findAll()))
        realm.close()
    }

    fun getAllData(): List<Article> {
        val realm: Realm = Realm.getDefaultInstance()
        val list: List<Article> = realm.copyFromRealm(realm.where(Article::class.java).findAll())
        realm.close()
        return list
    }

    fun setChangeListener(changeListener: RealmChangeListener<List<Article>>) {
        this.changeListener = changeListener
    }
}