package id.allana.newsapp.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import id.allana.newsapp.adapter.NewsAdapter
import id.allana.newsapp.base.BaseFragment
import id.allana.newsapp.databinding.FragmentListNewsBinding
import id.allana.newsapp.model.News
import id.allana.newsapp.viewmodel.NewsViewModel


class ListNewsFragment : BaseFragment<FragmentListNewsBinding>(
    FragmentListNewsBinding::inflate
), SearchView.OnQueryTextListener {

    private val newsViewModel: NewsViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
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
        initRecyclerView()
    }

    override fun observeData() {
        observeListNews()
    }

    private fun initRecyclerView() {
        getViewBinding().rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun observeListNews() {
        newsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                getViewBinding().rvNews.visibility = View.GONE
                getViewBinding().loadingImageNews.visibility = View.VISIBLE
            } else {
                getViewBinding().rvNews.visibility = View.VISIBLE
                getViewBinding().loadingImageNews.visibility = View.GONE
            }
        }
        newsViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) Snackbar.make(requireView(), newsViewModel.errorMessage, Snackbar.LENGTH_SHORT).show()
        }
        newsViewModel.newsData.observe(viewLifecycleOwner) { allNews ->
            setResultData(allNews?.data)
        }
    }

    private fun setResultData(data: List<News?>?) {
        Log.d(ListNewsFragment::class.java.simpleName, data.toString())
        newsAdapter.submitList(data)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}