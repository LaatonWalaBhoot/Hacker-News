package com.laatonwalabhoot.hackernews.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class Article: RealmObject() {

    @SerializedName("by")
    @Expose
    var by: String? = null
    @SerializedName("descendants")
    @Expose
    var descendants: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("kids")
    @Expose
    var kids: RealmList<String>? = null
    @SerializedName("score")
    @Expose
    var score: String? = null
    @SerializedName("time")
    @Expose
    var time: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null

}