package id.allana.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.allana.newsapp.databinding.ItemNewsLayoutBinding
import id.allana.newsapp.model.News
import id.allana.newsapp.ui.list.ListNewsFragmentDirections

class NewsAdapter: ListAdapter<News, NewsAdapter.NewsViewHolder>(NewsComparator()) {

    inner class NewsViewHolder(private val binding: ItemNewsLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: News) {
            binding.news = data
            binding.executePendingBindings()

            itemView.setOnClickListener {
                val directions = ListNewsFragmentDirections.actionListNewsFragmentToDetailNewsFragment(data.id)
                it.findNavController().navigate(directions)
            }

        }
    }

    class NewsComparator: DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val binding = ItemNewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}