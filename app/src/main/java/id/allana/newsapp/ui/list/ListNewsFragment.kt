package id.allana.newsapp.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import id.allana.newsapp.adapter.NewsAdapter
import id.allana.newsapp.adapter.NewsSearchAdapter
import id.allana.newsapp.base.BaseFragment
import id.allana.newsapp.databinding.FragmentListNewsBinding
import id.allana.newsapp.model.News
import id.allana.newsapp.viewmodel.NewsViewModel


class ListNewsFragment : BaseFragment<FragmentListNewsBinding>(
    FragmentListNewsBinding::inflate
) {

    private val newsViewModel: NewsViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }
    private val newsSearchAdapter: NewsSearchAdapter by lazy {
        NewsSearchAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            this.duration = 500L
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            this.duration = 500L
        }
    }

    override fun initToolbar() {}

    override fun initView() {
        newsViewModel.getAllNews()
        setupSearchNews()
        initRecyclerView()
    }

    override fun observeData() {
        observeListNews()
    }

    private fun setupSearchNews() {
        val searchView = getViewBinding().searchViewNews
        searchView.editText.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                newsViewModel.searchNews(it.toString())
            } else {
                newsSearchAdapter.submitList(emptyList())
            }
        }
    }

    private fun initRecyclerView() {
        getViewBinding().rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        getViewBinding().rvSearchNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsSearchAdapter
        }
    }

    private fun observeListNews() {
        newsViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) Snackbar.make(requireView(), newsViewModel.errorMessage, Snackbar.LENGTH_SHORT).show()
        }


        newsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                getViewBinding().rvNews.visibility = View.GONE
                getViewBinding().loadingImageNews.visibility = View.VISIBLE
            } else {
                getViewBinding().rvNews.visibility = View.VISIBLE
                getViewBinding().loadingImageNews.visibility = View.GONE
            }
        }
        newsViewModel.newsData.observe(viewLifecycleOwner) { allNews ->
            setResultData(allNews?.data)
        }


        newsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                getViewBinding().rvSearchNews.visibility = View.GONE
                getViewBinding().loadingImageSearchNews.visibility = View.VISIBLE
            } else {
                getViewBinding().rvSearchNews.visibility = View.VISIBLE
                getViewBinding().loadingImageSearchNews.visibility = View.GONE
            }
        }
        newsViewModel.newsSearchData.observe(viewLifecycleOwner) { allSearchNews ->
            setResultSearchData(allSearchNews?.data)
        }
    }

    private fun setResultData(data: List<News?>?) {
        Log.d(ListNewsFragment::class.java.simpleName, "RESULT LIST NEWS -> ${data.toString()}")
        newsAdapter.submitList(data)
    }

    private fun setResultSearchData(data: List<News?>?) {
        Log.d(ListNewsFragment::class.java.simpleName, "RESULT SEARCH NEWS -> ${data.toString()}")
        newsSearchAdapter.submitList(data)
    }
}