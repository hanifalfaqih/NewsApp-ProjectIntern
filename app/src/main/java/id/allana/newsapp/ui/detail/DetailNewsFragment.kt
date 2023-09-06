package id.allana.newsapp.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import id.allana.newsapp.R
import id.allana.newsapp.adapter.BindingAdapter.Companion.BASE_URL_PHOTO
import id.allana.newsapp.base.BaseFragment
import id.allana.newsapp.databinding.FragmentDetailNewsBinding
import id.allana.newsapp.viewmodel.NewsDetailViewModel


class DetailNewsFragment : BaseFragment<FragmentDetailNewsBinding>(
    FragmentDetailNewsBinding::inflate
) {

    private val newsDetailViewModel: NewsDetailViewModel by viewModels()
    private val args: DetailNewsFragmentArgs by navArgs()
    private val newsId: String by lazy {
        args.newsId.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_news_container
            duration = 300.toLong()
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        newsDetailViewModel.getDetailNews(newsId)
    }

    override fun observeData() {
        observeDetailNews()
    }

    private fun observeDetailNews() {
        newsDetailViewModel.newsDetailData.observe(viewLifecycleOwner) { newsDetail ->
            Glide.with(this)
                .load("$BASE_URL_PHOTO${newsDetail?.data?.thumb}")
                .placeholder(R.drawable.ic_placeholder_loading)
                .into(getViewBinding().sivNews)
            getViewBinding().news = newsDetail?.data
        }
    }

}
