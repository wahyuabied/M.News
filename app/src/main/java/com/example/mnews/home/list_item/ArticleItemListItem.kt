package com.example.mnews.home.list_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mnews.adapter.DiffableListItemType
import com.example.mnews.databinding.ListViewArticleBinding
import com.example.mnews.home.view_model.ArticleItemViewModel
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class ArticleItemListItem(val viewModel: ArticleItemViewModel, val listener: Listener) :
    AbstractBindingItem<ListViewArticleBinding>(),
    DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf(hashCode())

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
    ): ListViewArticleBinding {
        return ListViewArticleBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListViewArticleBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.apply {
            tvTitle.setText(viewModel.title)
            tvDesc.setText(viewModel.desc)
            tvSource.setText(viewModel.sourceName)
            tvAuthor.setText(if(viewModel.author.isEmpty()) "Unknown Author" else viewModel.author)
            tvDate.setText(viewModel.date)
            clContent.setOnClickListener {
                listener.onClick(viewModel.url)
            }

            Glide.with(ivView.context).load(viewModel.image).into(ivView)
        }
    }

    interface Listener {
        fun onClick(url:String)
    }
}