package id.allana.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.allana.newsapp.databinding.ItemNewsLayoutBinding
import id.allana.newsapp.model.ArticlesItem
import id.allana.newsapp.ui.list.ListNewsFragmentDirections

class NewsAdapter: ListAdapter<ArticlesItem, NewsAdapter.NewsViewHolder>(NewsComparator()) {

    inner class NewsViewHolder(private val binding: ItemNewsLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticlesItem) {
            binding.news = data
            binding.executePendingBindings()

            itemView.setOnClickListener {
                val directions = ListNewsFragmentDirections.actionListNewsFragmentToDetailNewsFragment(
                    newsTitle = data.title,
                    newsImageUrl = data.urlToImage,
                    newsPublishedAt = data.publishedAt,
                    newsSource = data.source?.name,
                    newsUrl = data.url
                )
                it.findNavController().navigate(directions)
            }

            Glide.with(itemView.context)
                .load(data.urlToImage)
                .into(binding.sivNews)

        }
    }

    class NewsComparator: DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
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