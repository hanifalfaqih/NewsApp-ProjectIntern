package id.allana.newsapp.ui.detail

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialSharedAxis
import id.allana.newsapp.base.BaseFragment
import id.allana.newsapp.databinding.FragmentDetailNewsBinding
import id.allana.newsapp.util.textFormatTime


class DetailNewsFragment : BaseFragment<FragmentDetailNewsBinding>(
    FragmentDetailNewsBinding::inflate
) {

    private val args: DetailNewsFragmentArgs by navArgs()
    private val newsTitle: String by lazy {
        args.newsTitle.toString()
    }
    private val newsImageUrl: String by lazy {
        args.newsImageUrl.toString()
    }
    private val newsPublishedAt: String by lazy {
        args.newsPublishedAt.toString()
    }
    private val newsSource: String by lazy {
        args.newsSource.toString()
    }
    private val newsUrl: String by lazy {
        args.newsUrl.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            this.duration = 500L
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            this.duration = 500L
        }

    }

    override fun initToolbar() {
        val toolbar = getViewBinding().toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun initView() {
        setDataToView()
    }

    private fun setDataToView() {
        getViewBinding().apply {
            tvTitleNews.text = newsTitle
            tvSourceNews.text = newsSource
            tvDateNews.text = textFormatTime(newsPublishedAt)

            Glide.with(requireContext())
                .load(newsImageUrl)
                .into(sivNews)

            webViewArticle.apply {
                settings.javaScriptEnabled
                webViewClient = object: WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        getViewBinding().pbLoadingNews.visibility = View.GONE
                        getViewBinding().webViewArticle.visibility = View.VISIBLE
                    }
                }
                loadUrl(newsUrl)
            }
        }
    }

    override fun observeData() {}

}
