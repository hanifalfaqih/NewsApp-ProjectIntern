package id.allana.newsapp.ui.list

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialElevationScale
import id.allana.newsapp.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        exitTransition = MaterialElevationScale(false).apply {
            duration = 300.toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 300.toLong()
        }

        initMenu()
    }

    private fun initMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)

                val searchMenu = menu.findItem(R.id.action_search)
                val searchView = searchMenu.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(this@ListNewsFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun initToolbar() {
        val toolbar = getViewBinding().toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setIcon(R.drawable.icon_news)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
    }

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
            if (isLoading) getViewBinding().pbNews.visibility = View.VISIBLE else getViewBinding().pbNews.visibility = View.INVISIBLE
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
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}