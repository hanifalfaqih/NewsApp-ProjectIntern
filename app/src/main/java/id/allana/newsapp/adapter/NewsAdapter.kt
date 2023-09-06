package id.allana.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.allana.newsapp.R
import id.allana.newsapp.databinding.ItemNewsLayoutBinding
import id.allana.newsapp.model.News
import id.allana.newsapp.ui.list.ListNewsFragmentDirections
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter: ListAdapter<News, NewsAdapter.NewsViewHolder>(NewsComparator()) {

    inner class NewsViewHolder(private val binding: ItemNewsLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: News) {
            binding.news = data
            binding.executePendingBindings()

            itemView.setOnClickListener {
                val newsCardDetailTransitionName =  itemView.context.getString(R.string.news_card_detail_transition_name)
                val extras = FragmentNavigatorExtras(it to newsCardDetailTransitionName)
                val directions = ListNewsFragmentDirections.actionListNewsFragmentToDetailNewsFragment(data.id)

                it.findNavController().navigate(directions, extras)
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

    fun formatTime(dateTime: String): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return parser.parse(dateTime)?.let { formatter.format(it) }
    }
}