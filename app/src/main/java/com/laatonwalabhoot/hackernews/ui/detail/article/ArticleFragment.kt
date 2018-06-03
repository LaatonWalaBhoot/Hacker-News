package com.laatonwalabhoot.hackernews.ui.detail.article

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.ui.detail.DetailViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel

    companion object {
        fun newInstance() = ArticleFragment()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel = ViewModelProviders.of(activity!!).get(DetailViewModel::class.java)
        setupWebView()
    }

    private fun setupWebView() {
        progress.visibility = View.VISIBLE
        web_view.loadUrl(detailViewModel.getArticleForFragments().url)
        web_view.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                when (web_view != null) {
                    true -> {
                        progress!!.visibility = View.GONE
                    }
                }
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                when (web_view != null) {
                    true -> {
                        progress!!.visibility = View.GONE
                        placeholder!!.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}