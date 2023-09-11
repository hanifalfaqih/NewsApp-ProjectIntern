package id.allana.newsapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
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
        newsDetailViewModel.getDetailNews(newsId)
    }

    override fun observeData() {
        observeDetailNews()
    }

    private fun observeDetailNews() {
        newsDetailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                /**
                 * FOR IMAGE
                 */
                getViewBinding().sivNews.visibility = View.GONE
                getViewBinding().loadingImageNews.visibility = View.VISIBLE

                /**
                 * FOR CONTENT
                 */
                getViewBinding().pbNews.visibility = View.VISIBLE
                getViewBinding().containerContentNews.visibility = View.GONE
            } else {
                /**
                 * FOR IMAGE
                 */
                getViewBinding().sivNews.visibility = View.VISIBLE
                getViewBinding().loadingImageNews.visibility = View.GONE

                /**
                 * FOR CONTENT
                 */
                getViewBinding().pbNews.visibility = View.GONE
                getViewBinding().containerContentNews.visibility = View.VISIBLE
            }
        }
        newsDetailViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) Snackbar.make(requireView(), newsDetailViewModel.errorMessage, Snackbar.LENGTH_SHORT).show()
        }
        newsDetailViewModel.newsDetailData.observe(viewLifecycleOwner) { newsDetail ->
            Glide.with(this)
                .load("$BASE_URL_PHOTO${newsDetail?.data?.thumb}")
                .into(getViewBinding().sivNews)
            getViewBinding().news = newsDetail?.data
        }
    }

}
